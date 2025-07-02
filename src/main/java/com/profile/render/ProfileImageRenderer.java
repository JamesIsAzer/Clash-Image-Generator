package com.profile.render;

import com.profile.data.Profile;
import com.profile.utils.ImageManager;
import com.profile.utils.FontUtils;
import com.profile.utils.GradientManager;
import com.profile.data.Clan;
import com.profile.data.League;
import com.profile.data.LegendStatistics;
import com.profile.data.Achievement;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ProfileImageRenderer {

    static {
        FontLoader.loadCustomFonts();
    }

    public static BufferedImage render(Profile profile) throws IOException {
        boolean hasLegendStats = profile.legendStatistics != null && profile.legendStatistics.bestSeason != null;

        int width = 3500;
        int height = hasLegendStats ? 2550 : 2125;

        BufferedImage canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = canvas.createGraphics();
        setupGraphics(g);

        g.setColor(new Color(0xE8E8E0));
        g.fillRect(0, 0, width, height);

        drawNameCardSection(g, profile, 25, 25);
        drawAchievementsSection(g, profile.achievements, 75, hasLegendStats ? 1425 : 1000);

        if (hasLegendStats) {
            drawLegendLeagueSection(g, profile.legendStatistics, 25, 1000);
        }

        g.dispose();
        return canvas;
    }

    private static void setupGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
    }

    private static void drawNameCardSection(Graphics2D g, Profile profile, int x, int y) {
        int width = 3450;
        int height = 950;
        int radius = 10;
        int paddingTop = 75;
        int paddingLeft = 75;

        GradientPaint gradient = new GradientPaint(
            x, y, new Color(0x8C96AF),
            x + width, y + height, new Color(0x6B7899)
        );

        g.setPaint(gradient);
        drawRoundedRect(g, x, y, width, height, radius);

        g.setStroke(new BasicStroke(10));
        g.setColor(new Color(0x6A7798));
        drawRoundedRectOutline(g, x, y, width, height, radius);

        drawDividerLine(g, x + paddingLeft + 1400, x + paddingLeft + 1400, y + paddingTop, y + paddingTop + 700);
        drawDividerLine(g, x + paddingLeft + 2300, x + paddingLeft + 2300, y + paddingTop, y + paddingTop + 700);

        drawNameSection(g, profile, x + paddingLeft, y + paddingTop + 50);
        drawClanSection(g, profile, x + paddingLeft + 1600, y + paddingTop + 100);
        drawTownhallSection(g, profile, x + paddingLeft + 2200, y + paddingTop);

        drawSeasonalSection(g, profile, x, y, width, height, radius);
    }

    private static void drawRoundedRect(Graphics2D g, int x, int y, int width, int height, int radius) {
        g.fill(new RoundRectangle2D.Float(x, y, width, height, radius, radius));
    }

    private static void drawRoundedRectOutline(Graphics2D g, int x, int y, int width, int height, int radius) {
        g.draw(new RoundRectangle2D.Float(x, y, width, height, radius, radius));
    }

    private static void drawDividerLine(Graphics2D g, int x1, int x2, int y1, int y2) {
        g.setStroke(new BasicStroke(8));
        g.setColor(new Color(0x5B5F80));
        g.drawLine(x1, y1 - 3, x2, y2 - 3);

        g.setStroke(new BasicStroke(4));
        g.setColor(new Color(0xABAEC1));
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
        Integer rank = profile.legendStatistics.currentSeason.rank;

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

    private static void drawSeasonalSection(Graphics2D g, Profile profile, int x, int y, int width, int height, int radius) {
        int purpleHeight = 125;
        int purpleY = y + height - purpleHeight;

        Polygon purpleShape = new Polygon();
        purpleShape.addPoint(x, purpleY);
        purpleShape.addPoint(x + width, purpleY);
        purpleShape.addPoint(x + width, purpleY + purpleHeight - radius);
        purpleShape.addPoint(x + width - radius, purpleY + purpleHeight);
        purpleShape.addPoint(x + radius, purpleY + purpleHeight);
        purpleShape.addPoint(x, purpleY + purpleHeight - radius);
        g.setColor(new Color(0x4E4D79));
        g.fill(purpleShape);

        g.setColor(new Color(0x7964A5));
        g.fillRect(x, purpleY + 3, width, 5);

        int baseY = purpleY + 100;
        g.setFont(new Font("Clash", Font.PLAIN, 50));
        g.setColor(Color.WHITE);

        g.drawString("Troops donated:", 100, baseY - 50);
        g.drawString(String.valueOf(profile.donations), 625, baseY);

        g.drawString("Troops received:", 900, baseY - 50);
        g.drawString(String.valueOf(profile.donationsReceived), 1425, baseY);

        g.drawString("Attacks won:", 1815, baseY - 50);
        g.drawString(String.valueOf(profile.attackWins), 2215, baseY);

        g.drawString("Defenses won:", 2615, baseY - 50);
        g.drawString(String.valueOf(profile.defenseWins), 3065, baseY);
    }

    private static void drawLegendLeagueSection(Graphics2D g, LegendStatistics stats, int x, int y) {
        g.setFont(new Font("Clash", Font.BOLD, 70));
        g.setColor(Color.DARK_GRAY);
        g.drawString("Legend League Tournament", x + 1200, y + 50);

        g.setFont(new Font("Clash", Font.PLAIN, 50));
        if (stats.bestSeason != null) {
            g.drawString("Best: " + stats.bestSeason.id + " - Rank " + stats.bestSeason.rank + " - " + stats.bestSeason.trophies + " Trophies", x + 200, y + 200);
        }
        if (stats.previousSeason != null) {
            g.drawString("Previous: " + stats.previousSeason.id + " - Rank " + stats.previousSeason.rank + " - " + stats.previousSeason.trophies + " Trophies", x + 200, y + 300);
        }

        g.drawString("Legend trophies: " + stats.legendTrophies, x + 200, y + 400);
    }

    private static void drawAchievementsSection(Graphics2D g, Achievement[] achievements, int x, int y) {
        g.setFont(new Font("Clash", Font.PLAIN, 40));
        g.setColor(Color.BLACK);

        int cellHeight = 200;
        for (int i = 0; i < achievements.length && i < 15; i++) {
            Achievement a = achievements[i];
            if (a != null) {
                g.drawString(a.name + ": " + a.value + " (" + a.stars + " stars)", x, y + i * (cellHeight + 10));
            }
        }
    }
}
