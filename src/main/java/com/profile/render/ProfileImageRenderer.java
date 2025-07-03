package com.profile.render;

import com.profile.data.Profile;
import com.profile.utils.ImageManager;
import com.profile.utils.RenderingUtility;
import com.profile.utils.DateUtils;
import com.profile.utils.FontUtils;
import com.profile.utils.GradientManager;
import com.profile.data.Clan;
import com.profile.data.League;
import com.profile.data.LegendSeason;
import com.profile.data.LegendStatistics;
import com.profile.data.Achievement;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProfileImageRenderer {

    static {
        FontUtils.loadCustomFonts();
    }

    public static BufferedImage render(Profile profile) throws IOException {
        boolean hasLegendStats = profile.legendStatistics != null && profile.legendStatistics.bestSeason != null;

        int width = 3500;
        int height = hasLegendStats ? 2550 : 2125;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
                
        RenderingUtility.addRenderingHints(g);

        g.setColor(Color.decode("#E8E8E0"));
        g.fillRect(0, 0, width, height);

        drawNameCardSection(g, profile, 25, 25);
        drawAchievementsSection(g, profile.achievements, 75, hasLegendStats ? 1425 : 1000);

        if (hasLegendStats) {
            drawLegendLeagueSection(g, profile.legendStatistics, 25, 1000);
        }

        g.dispose();
        return image;
    }

    private static void drawNameCardSection(Graphics2D g, Profile profile, int x, int y) {
        int width = 3450;
        int height = 950;
        int radius = 10;
        int paddingTop = 75;
        int paddingLeft = 75;

        Paint gradient = GradientManager.createOptimizedGradient("namecard", x, y, width, height,  
            new Color[] {
                Color.decode("#8c96af"),
                Color.decode("#6b7899")
            },
            new float[] { 0f, 1f },
            false
        );

        g.setPaint(gradient);
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        g.setStroke(new BasicStroke(10));
        g.setColor(new Color(0x6A7798));
        RenderingUtility.drawRoundedRectOutline(g, x, y, width, height, radius);

        drawDividerLine(g, x + paddingLeft + 1400, x + paddingLeft + 1400, y + paddingTop, y + paddingTop + 700, Color.decode("#5b5f80"), Color.decode("#abaec1"));
        drawDividerLine(g, x + paddingLeft + 2300, x + paddingLeft + 2300, y + paddingTop, y + paddingTop + 700, Color.decode("#5b5f80"), Color.decode("#abaec1"));

        drawNameSection(g, profile, x + paddingLeft, y + paddingTop + 50);
        drawClanSection(g, profile, x + paddingLeft + 1600, y + paddingTop + 100);
        drawTownhallSection(g, profile, x + paddingLeft + 2200, y + paddingTop);

        drawSeasonalSection(g, profile, x, y, width, height, radius);
    }

    

    private static void drawDividerLine(Graphics2D g, int x1, int x2, int y1, int y2, Color colour1, Color colour2) {
        g.setStroke(new BasicStroke(8));
        g.setColor(colour1);
        g.drawLine(x1, y1 - 3, x2, y2 - 3);

        g.setStroke(new BasicStroke(4));
        g.setColor(colour2);
        g.drawLine(x1 - 2, y1, x2 - 2, y2);
    }

    public static void drawNameSection(Graphics2D g, Profile profile, int x, int y) {
        String username = profile.name;
        String playerTag = profile.tag;
        String clanRole = profile.role;
        var league = profile.league;
        int trophies = profile.trophies;
        int expLevel = profile.expLevel;

        // Load XP icon image
        BufferedImage xpImage = ImageManager.getCachedImage("xp");
        g.drawImage(xpImage, x, y - 10, 200, 200, null);

        // Draw XP level
        FontUtils.drawClashFont(g, String.valueOf(expLevel), x + 100, y + 90, 90, true, Color.WHITE, 6);

        // Draw username
        FontUtils.drawClashFont(g, username, x + 250, y - 30, 100, false, Color.WHITE, 6);

        // Draw tag
        FontUtils.drawClashFont(g, playerTag, x + 250, y + 95, 75, false, Color.decode("#CCCCCC"), 4);

        // Draw clan role if exists
        if (clanRole != null && !clanRole.isEmpty()) {
            FontUtils.drawClashFont(g, mapClanRole(clanRole), x + 250, y + 190, 75, false, Color.WHITE, 6);
        }

        // Extract rank from legend statistics if present
        Integer rank = null;

        if (profile.legendStatistics != null && profile.legendStatistics.currentSeason != null) {
            rank = profile.legendStatistics.currentSeason.rank;
        }

        // Draw league + trophy info
        drawLeagueTrophyBanner(g, x + 100, y + 300, 350, 350, trophies, league, rank);
    }

    public static String mapClanRole(String clanRole) {
        if (clanRole == null) return "";

        switch (clanRole) {
            case "member":
                return "Member";
            case "admin":
                return "Elder";
            case "coLeader":
                return "Co-leader";
            case "leader":
                return "Leader";
            default:
                return clanRole;
        }
    }

    public static void drawLeagueTrophyBanner(
        Graphics2D g, 
        int x, 
        int y, 
        int emblemWidth, 
        int emblemHeight,
        int trophies, 
        League league, 
        Integer rank
    ) {
        int lineStartFromEmblemX = x + (emblemWidth / 2);
        int lineEndX = x + emblemWidth + 750;
        int emblemCenterY = y + (emblemHeight / 2);
        int line1Y = emblemCenterY - 55;
        int line2Y = emblemCenterY + 50;

        int gradientWidth = 900 + (emblemWidth / 2);
        int gradientHeight = 700;

        // Gradient line 1 (dark fade)
        Paint gradient1 = GradientManager.createOptimizedGradient("leaguetrophybanner", x, y, gradientWidth, gradientHeight,
                new Color[]{new Color(0, 0, 0, 204), new Color(0, 0, 0, 0)}, // 0.8 alpha
                new float[]{0f, 1f},
                true);
        g.setPaint(gradient1);
        g.setStroke(new BasicStroke(90));
        g.drawLine(lineStartFromEmblemX, line1Y, lineEndX, line1Y);

        // Gradient line 2 (purple fade)
        Paint gradient2 = GradientManager.createOptimizedGradient("leaguetrophybanner2", x, y, gradientWidth, gradientHeight,
                new Color[]{
                        new Color(118, 82, 178, 255),
                        new Color(101, 82, 166, 255),
                        new Color(101, 82, 166, 0)
                },
                new float[]{0f, 0.5f, 1f},
                true);
        g.setPaint(gradient2);
        g.setStroke(new BasicStroke(110));
        g.drawLine(lineStartFromEmblemX, line2Y, lineEndX, line2Y);

        // League name text
        String leagueName = getLeagueName(league);

        FontUtils.drawClashFont(g, leagueName, lineStartFromEmblemX + 200, line1Y - 28, 55, false, Color.WHITE, 6);

        // Trophy icon
        BufferedImage trophyIcon = ImageManager.getCachedImage("trophy");
        if (trophyIcon != null) {
            g.drawImage(trophyIcon, lineStartFromEmblemX + 200, line2Y - 45, 90, 90, null);
        }

        // Emblem image
        BufferedImage emblemImage;
        if (league != null && league.iconUrls != null && league.iconUrls.medium != null) {
            emblemImage = ImageManager.loadImageFromURL(league.iconUrls.medium);
        } else {
            emblemImage = ImageManager.getCachedImage("Icon_HV_League_None");
        }

        if (emblemImage != null) {
            g.drawImage(emblemImage, x, y, emblemWidth, emblemHeight, null);
        }

        // Optional Rank Rendering (disabled for now)
        //if (false && rank != null) {
        //    int rankX = x + (emblemWidth / 2);
        //    int rankY = y + (emblemHeight / 2) + 10;
        //    FontUtils.clashFontScaled(g, leagueName, lineEndX, line2Y, gradientWidth, gradientHeight, false);
        //}

        // Trophy count
        FontUtils.drawClashFont(g, String.valueOf(trophies), lineStartFromEmblemX + 310, line2Y - 35, 85, false, Color.WHITE, 6);
    }

    public static String getLeagueName(League league) {
        if (league == null) return "Unranked";
        return league.name;
    }

    private static void drawClanSection(Graphics2D g, Profile profile, int x, int y) {
        Clan clan = profile.clan;
        int clanEmblemWidth = 500;
        int clanEmblemHeight = 500;

        if (clan != null) {
            FontUtils.drawClashFont(g, clan.name, x + (clanEmblemWidth / 2), y, 75, true, Color.WHITE, 6);
            BufferedImage clanEmblemImage = ImageManager.loadImageFromURL(clan.badgeUrls.medium);
            g.drawImage(clanEmblemImage, x, y + 50, clanEmblemWidth, clanEmblemHeight, null);
        } else {
            FontUtils.drawClashFont(g, "No Clan", x + (clanEmblemWidth / 2), y, 75, true, Color.WHITE, 6);
        }
    }

    private static void drawTownhallSection(Graphics2D g, Profile profile, int x, int y) {
        // Placeholder for townhall image rendering
        int townhallImageWidth = 610;
        int townhallImageHeight = 610;

        BufferedImage shineImage = ImageManager.getCachedImage("shine");
        BufferedImage townhallImage = ImageManager.getTownhallImage(profile.townHallLevel);

        g.drawImage(shineImage, x + 130, y - 150, townhallImageWidth + 400, townhallImageHeight + 400, null);
        g.drawImage(townhallImage, x + 330, y + 50, townhallImageWidth, townhallImageHeight, null);
    }

    public static void drawSeasonalSection(Graphics2D g, Profile profile, int x, int y, int width, int height, int radius) {
        int purpleHeight = 125;
        int purpleY = y + height - purpleHeight;

        // Draw purple background with rounded bottom corners
        Path2D path = new Path2D.Double();
        path.moveTo(x, purpleY);
        path.lineTo(x + width, purpleY);
        path.lineTo(x + width, purpleY + purpleHeight - radius);
        path.quadTo(x + width, purpleY + purpleHeight, x + width - radius, purpleY + purpleHeight);
        path.lineTo(x + radius, purpleY + purpleHeight);
        path.quadTo(x, purpleY + purpleHeight, x, purpleY + purpleHeight - radius);
        path.lineTo(x, purpleY);
        path.closePath();

        g.setColor(Color.decode("#4e4d79"));
        g.fill(path);

        // Draw top highlight line
        g.setColor(Color.decode("#7964a5"));
        g.fillRect(x, purpleY + 3, width, 5);

        // Draw seasonal stats
        int troopsDonated = profile.donations;
        int troopsReceived = profile.donationsReceived;
        int attacksWon = profile.attackWins;
        int defensesWon = profile.defenseWins;

        drawSeasonalPixelLine(g, 100, purpleY + 100, 500);
        FontUtils.drawClashFont(g, "Troops donated:", 100, purpleY + 50, 50, false, Color.WHITE, 6);
        seasonalStatBox(g, 625, purpleY + 20, String.valueOf(troopsDonated));

        drawSeasonalPixelLine(g, 900, purpleY + 100, 505);
        FontUtils.drawClashFont(g, "Troops received:", 900, purpleY + 50, 50, false, Color.WHITE, 6);
        seasonalStatBox(g, 1425, purpleY + 20, String.valueOf(troopsReceived));

        drawSeasonalPixelLine(g, 1815, purpleY + 100, 390);
        FontUtils.drawClashFont(g, "Attacks won:", 1815, purpleY + 50, 50, false, Color.WHITE, 6);
        seasonalStatBox(g, 2215, purpleY + 20, String.valueOf(attacksWon));

        drawSeasonalPixelLine(g, 2615, purpleY + 100, 440);
        FontUtils.drawClashFont(g, "Defenses won:", 2615, purpleY + 50, 50, false, Color.WHITE, 6);
        seasonalStatBox(g, 3065, purpleY + 20, String.valueOf(defensesWon));
    }

    private static void drawSeasonalPixelLine(Graphics2D g, int x, int y, int width) {
        // Top line (#2e2e48)
        g.setColor(Color.decode("#2e2e48"));
        g.fillRect(x, y, width, 4);

        // Bottom line (#7a6296)
        g.setColor(Color.decode("#7a6296"));
        g.fillRect(x, y + 4, width, 4);
    }

    private static void seasonalStatBox(Graphics2D g, int x, int y, String message) {
        int width = 250;
        int height = 90;

        g.setColor(Color.decode("#2e2c62"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, 50);

        FontUtils.drawClashFont(g, message, x + (width / 2), y + (height / 2), 50, true, Color.WHITE, 6);
    }

    public static void drawLegendLeagueSection(Graphics2D g, LegendStatistics legendStats, int x, int y) {
        LegendSeason bestSeason = legendStats.bestSeason;
        LegendSeason previousSeason = legendStats.previousSeason;
        int legendTrophies = legendStats.legendTrophies;

        int width = 3450;
        int height = 400;
        int radius = 10;
        int paddingTop = 50;
        int paddingLeft = 200;

        // Background gradient fill
        Paint gradient = GradientManager.createOptimizedGradient(
            "legendleaguesection", x, y, width, height,
            new Color[] {
                Color.decode("#4d4379"),
                Color.decode("#6f659b")
            },
            new float[] { 0f, 1f },
            false // false = vertical
        );
        g.setPaint(gradient);
        Shape roundedRect = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
        g.fill(roundedRect);

        // Inner horizontal stroke
        Paint gradient1 = GradientManager.createOptimizedGradient(
                "legendleaguesection1", x, y, width, height,
                new Color[]{
                    new Color(148, 113, 210, 0),
                    new Color(148, 113, 210, 255),
                    new Color(148, 113, 210, 0)
                },
                new float[]{0f, 0.5f, 1f},
                true
        );
        g.setStroke(new BasicStroke(90));
        g.setPaint(gradient1);
        g.drawLine(x, y + 50, x + width, y + 50);

        // Outer border
        g.setStroke(new BasicStroke(10));
        g.setColor(Color.decode("#493f75"));
        g.draw(roundedRect);

        // Title
        FontUtils.drawClashFont(g, "Legend League Tournament", x + (width / 2), y + 50, 70, true, Color.WHITE, 6);

        // Vertical divider lines
        drawDividerLine(g, x + paddingLeft + 1000, x + paddingLeft + 1000, y + paddingTop + 75, y + paddingTop + 325, Color.decode("#35304e"), Color.decode("#796fa5"));
        drawDividerLine(g, x + paddingLeft + 2175, x + paddingLeft + 2175, y + paddingTop + 75, y + paddingTop + 325, Color.decode("#35304e"), Color.decode("#796fa5"));

        // Sections
        drawTrophyLegendarySection(g, bestSeason, x + paddingLeft, y + (paddingTop / 2), "Best");
        drawTrophyLegendarySection(g, previousSeason, x + paddingLeft + 1100, y + (paddingTop / 2), "Previous");
        drawLegendTrophySection(g, legendTrophies, x + paddingLeft + 2400, y + (paddingTop / 2));
    }

    private static void drawTrophyLegendarySection(Graphics2D g, LegendSeason season, int x, int y, String type) {
        String rank = (season != null && season.rank != null) ? String.valueOf(season.rank) : null;
        Integer trophies = (season != null) ? season.trophies : null;
        String date = (season != null) ? season.id : null;

        if (season != null) {
            BufferedImage legendImage = ImageManager.getCachedImage("Icon_HV_League_Legend");
            if (legendImage != null) {
                g.drawImage(legendImage, x, y + 100, 250, 250, null);
            }

            if (rank != null) {
                FontUtils.clashFontScaled(g, rank, x + 125, y + 220, 170, 140, true);
            }

            FontUtils.drawClashFont(g,type + ": " + DateUtils.formatYearMonth(date), x + 275, y + 125, 50, false, Color.WHITE, 6);

            drawStatBanner(g, x + 275, y + 200, 150, 150, "trophy", trophies != null ? trophies : 0, Color.decode("#242135"));
        } else {
            BufferedImage unrankedImage = ImageManager.getCachedImage("Icon_HV_League_None");
            if (unrankedImage != null) {
                g.drawImage(unrankedImage, x, y + 100, 250, 250, null);
            }

            FontUtils.drawClashFont(g, "Did not place", x + 300, y + 250, 50, false, Color.decode("#dde2ff"), 6);

            FontUtils.drawClashFont(g, type + ": " + DateUtils.formatYearMonth(DateUtils.getLastYearMonth()), x + 275, y + 125, 50, false, Color.WHITE, 6);
        }
    }

    public static void drawLegendTrophySection(Graphics2D g, int legendTrophies, int x, int y) {
        FontUtils.drawClashFont(g, "Legend trophies:", x, y + 125, 50, false, Color.WHITE, 6);
        drawStatBanner(g, x, y + 200, 150, 150, "legendtrophy", legendTrophies, Color.decode("#242135"));
    }

    private static void drawStatBanner(Graphics2D g, int x, int y, int emblemWidth, int emblemHeight, String imageName, int stat, Color statBgColor) {
        int emblemCenterY = y + (emblemHeight / 2);
        int barHeight = 100;
        int barRadius = barHeight / 6;
        int barPadding = 20 + (emblemWidth / 2);
        int iconSize = 60;
        int spacingBetween = 20;

        BufferedImage statImage = ImageManager.getCachedImage(imageName);
        if (statImage == null) return;

        String statText = String.valueOf(stat);

        // Estimate text width using metrics
        
        Font font = new Font("Clash", Font.PLAIN, 70);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(statText);

        int barX = x + (emblemWidth / 2);
        int barY = emblemCenterY - (barHeight / 2);
        int barWidth = barPadding + iconSize + spacingBetween + textWidth + 80;

        // Draw the rounded rectangle background
        g.setColor(statBgColor != null ? statBgColor : Color.decode("#38385c"));
        drawRightRoundedRect(g, barX, barY, barWidth, barHeight, barRadius);


        // Draw emblem image
        g.drawImage(statImage, x, y, emblemWidth, emblemHeight, null);

        int iconX = barX + barPadding;

        // Draw the stat text
        int textX = iconX + iconSize + spacingBetween;
        int textY = emblemCenterY - 30;
        FontUtils.drawClashFont(g, statText, textX, textY, 70, false, Color.WHITE, 6);
    }

    private static void drawRightRoundedRect(Graphics2D g, int x, int y, int width, int height, int radius) {
        int arc = radius * 2;
        Path2D path = new Path2D.Double();
        path.moveTo(x, y);
        path.lineTo(x + width - arc, y);
        path.quadTo(x + width, y, x + width, y + radius);
        path.lineTo(x + width, y + height - radius);
        path.quadTo(x + width, y + height, x + width - arc, y + height);
        path.lineTo(x, y + height);
        path.closePath();
        g.fill(path);
    }

    public static void drawAchievementsSection(Graphics2D g, Achievement[] achievements, int x, int y) {
        Achievement goldLooted = achievements[5];
        Achievement troopDonations = achievements[14];
        Achievement obstaclesRemoved = achievements[3];
        Achievement clanGamePoints = achievements[31];

        Achievement elixirLooted = achievements[6];
        Achievement spellDonations = achievements[23];
        Achievement seasonChallengePts = achievements[35];
        Achievement warStars = achievements[20];
        Achievement successfulAttacks = achievements[12];

        Achievement darkElixirLooted = achievements[16];
        Achievement siegeDonations = achievements[40];
        Achievement campaignMapStars = achievements[1];
        Achievement clanWarLeagueStars = achievements[33];
        Achievement successfulDefenses = achievements[13];

        // First column
        drawAchievementCell(g, x, y, "Gold looted", "gold", goldLooted);
        drawAchievementCell(g, x, y + 225, "Troop donations", "troopdonation", troopDonations);
        drawAchievementCell(g, x, y + 450, "Obstacles removed", "obstaclesremoved", obstaclesRemoved);
        drawAchievementCell(g, x, y + 675, "Clan games points", "clangames", clanGamePoints);

        // Second column
        drawAchievementCell(g, x + 1125, y, "Elixir looted", "elixir", elixirLooted);
        drawAchievementCell(g, x + 1125, y + 225, "Spell donations", "spelldonation", spellDonations);
        drawAchievementCell(g, x + 1125, y + 450, "Season challenge pts", "goldpass", seasonChallengePts);
        drawAchievementCell(g, x + 1125, y + 675, "War stars", "warstar", warStars);
        drawAchievementCell(g, x + 1125, y + 900, "Successful attacks", "multiplayerattack", successfulAttacks);

        // Third column
        drawAchievementCell(g, x + 2250, y, "Dark elixir looted", "darkelixir", darkElixirLooted);
        drawAchievementCell(g, x + 2250, y + 225, "Siege donations", "siegemachinedonation", siegeDonations);
        drawAchievementCell(g, x + 2250, y + 450, "Campaign map stars", "campaigner", campaignMapStars);
        drawAchievementCell(g, x + 2250, y + 675, "Clan war league stars", "cwlstar", clanWarLeagueStars);
        drawAchievementCell(g, x + 2250, y + 900, "Successful defenses", "shield", successfulDefenses);

        ImageManager.drawSignature(g, x + 100, y + 850, 8);
    }

    public static void drawAchievementCell(Graphics2D g, int x, int y, String achievementTitle, String achievementIcon, Achievement achievement) {
        int width = 1100;
        int height = 200;
        int radius = 50;

        // Draw rounded rectangle path
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        // Create gradient fill
        Paint gradient = GradientManager.createOptimizedGradient(
            "achievementcell", x, y, width, height,
            new Color[] {
                Color.decode("#a8adb0"),
                Color.decode("#9ca5b0")
            },
            new float[] {0f, 1f},
            false // vertical gradient
        );

        g.setPaint(gradient);
        g.fill(new RoundRectangle2D.Float(x, y, width, height, radius, radius));

        // White border
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.WHITE);
        g.draw(new RoundRectangle2D.Float(x, y, width, height, radius, radius));

        // Reflection effect
        drawReflection(g, x + 25, y + 25, width - 50, (height / 2) - 25);

        // Draw the achievement banner inside the cell
        drawAchievementBanner(g, x, y, height, width, achievementTitle, achievementIcon, achievement);
    }

    public static void drawReflection(Graphics2D g, int x, int y, int width, int height) {
        int radius = 40;

        // Draw rounded rect path
        Shape roundedRect = new RoundRectangle2D.Float(x, y, width, height, radius, radius);

        // Create gradient fill
        Paint gradient = GradientManager.createOptimizedGradient(
            "reflection", x, y, width, height,
            new Color[]{
                new Color(255, 255, 255, (int)(0.25 * 255)), // 0.25 alpha
                new Color(255, 255, 255, (int)(0.10 * 255))  // 0.10 alpha
            },
            new float[]{0f, 1f},
            false // vertical gradient
        );

        g.setPaint(gradient);
        g.fill(roundedRect);
    }

    public static void drawAchievementBanner(
        Graphics2D g, int x, int y, int cellHeight, int cellWidth,
        String achievementTitle, String achievementIcon, Achievement achievement
    ) {
        int achievementStars = achievement.stars;
        int achievementValue = achievement.value;

        int achievementIconWidth = 150;
        int achievementIconHeight = 150;
        int barHeight = 80;
        int barRadius = barHeight / 5;
        int barPadding = (achievementIconWidth / 2) + 20;
        int spacingBetween = 20;

        int starsWidth = 288;
        int starsHeight = 115;
        int starsX = x + 30;
        int starsY = y + (cellHeight / 2) - (starsHeight / 2);

        int xFromStatIcon = x + starsWidth + 70;

        BufferedImage achievementIconImage = ImageManager.getCachedImage(achievementIcon);
        BufferedImage starsImage = ImageManager.getAchievementStarsImage(achievementStars);

        int iconY = y + (cellHeight / 2) - (achievementIconHeight / 2) + 10;
        int iconCenterY = iconY + (achievementIconHeight / 2) + 10;

        int barX = xFromStatIcon + (achievementIconWidth / 2);
        int barY = iconCenterY - (barHeight / 2);

        String text = FontUtils.formatNumberWithSpaces(achievementValue);
        Font font = new Font("Clash", Font.PLAIN, 60);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(text);

        int barWidth = Math.max(
                barPadding + spacingBetween + textWidth + 20,
                cellWidth - starsWidth - (achievementIconWidth / 2) - 300
        );

        g.setColor(Color.decode("#38385c"));
        Shape roundedRect = new RoundRectangle2D.Float(barX, barY, barWidth, barHeight, barRadius * 2, barRadius * 2);
        g.fill(roundedRect);

        int iconX = barX + barPadding;
        g.drawImage(achievementIconImage, xFromStatIcon, iconY, achievementIconWidth, achievementIconHeight, null);
        g.drawImage(starsImage, starsX, starsY, starsWidth, starsHeight, null);

        int textX = iconX + spacingBetween;
        int textY = iconCenterY - 30;
        FontUtils.drawClashFont(g, achievementTitle, iconX, textY - 55, 40, false, Color.WHITE, 6);
        FontUtils.drawClashFont(g, text, textX, textY, 60, false, Color.WHITE, 6);
    }
}
