package com.profile.render;

import com.profile.data.Profile;
import com.profile.data.Unit;
import com.profile.utils.ImageManager;
import com.profile.utils.RenderingUtility;
import com.profile.utils.BlurUtils;
import com.profile.utils.FontUtils;
import com.profile.utils.GradientManager;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.IOException;

public class TroopShowcaseImageRenderer {

    static {
        FontUtils.loadCustomFonts();
    }

    public static BufferedImage render(Profile profile) throws IOException {
        int width = 2950;
        int height = 2050;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        RenderingUtility.addRenderingHints(g);

        // Background gradient
        GradientPaint background = new GradientPaint(
            0, 0, Color.decode("#8c96af"),
            width, height, Color.decode("#6b7899")
        );
        g.setPaint(background);
        g.fillRect(0, 0, width, height);

        // Draw each section
        drawHeroSection(g, 50, 50, profile.heroes);
        drawPetSection(g, 50, 675, profile.troops);
        drawTroopSection(g, 850, 50, profile.troops);
        drawSpellSection(g, 2150, 50, profile.spells);
        drawSiegeMachineSection(g, 850, 1675, profile.troops);
        ImageManager.drawSignature(g, 50, 1750, 8);

        g.dispose();
        return image;
    }

    public static boolean isMaxed(Unit[] list, String name) {
        for (Unit unit : list) {
            if (unit.name.equals(name)) {
                return unit.level == unit.maxLevel;
            }
        }
        return false;
    }

    public static boolean isUnlocked(Unit[] list, String name) {
        for (Unit unit : list) {
            if (unit.name.equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static int getLevel(Unit[] list, String name) {
        for (Unit unit : list) {
            if (unit.name.equals(name)) {
                return unit.level;
            }
        }
        return 0;
    }

    public static TroopData getTroopData(Unit[] list, String name) {
        return new TroopData(
            isMaxed(list, name),
            isUnlocked(list, name),
            getLevel(list, name)
        );
    }

    // Data holder class
    public static class TroopData {
        public final boolean maxed;
        public final boolean unlocked;
        public final int level;

        public TroopData(boolean maxed, boolean unlocked, int level) {
            this.maxed = maxed;
            this.unlocked = unlocked;
            this.level = level;
        }
    }

    public static void drawHeroSection(Graphics2D g, int x, int y, Unit[] heroes) {
        int width = 750;
        int height = 575;
        int radius = 25;

        g.setColor(Color.decode("#636e8f"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        FontUtils.drawClashFont(g, "Heroes", x + 25, y + 20, 70, false, Color.WHITE, 6);

        TroopData barbarianKing = getTroopData(heroes, "Barbarian King");
        TroopData archerQueen = getTroopData(heroes, "Archer Queen");
        TroopData minionPrince = getTroopData(heroes, "Minion Prince");
        TroopData grandWarden = getTroopData(heroes, "Grand Warden");
        TroopData royalChampion = getTroopData(heroes, "Royal Champion");

        drawTroopIcon(barbarianKing, g, "Icon_HV_Hero_Barbarian_King", x + 25, y + 100);
        drawTroopIcon(archerQueen, g, "Icon_HV_Hero_Archer_Queen", x + 275, y + 100);
        drawTroopIcon(minionPrince, g, "Icon_HV_Hero_Minion_Prince", x + 525, y + 100);

        drawTroopIcon(grandWarden, g, "Icon_HV_Hero_Grand_Warden", x + 25, y + 350);
        drawTroopIcon(royalChampion, g, "Icon_HV_Hero_Royal_Champion", x + 275, y + 350);
    }

    public static void drawPetSection(Graphics2D g, int x, int y, Unit[] pets) {
        int width = 750;
        int height = 1075;
        int radius = 25;

        g.setColor(Color.decode("#636e8f"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        FontUtils.drawClashFont(g, "Pets", x + 25, y + 20, 70, false, Color.WHITE, 6);

        TroopData lassi = getTroopData(pets, "L.A.S.S.I");
        TroopData mightyYak = getTroopData(pets, "Mighty Yak");
        TroopData electroOwl = getTroopData(pets, "Electro Owl");
        TroopData unicorn = getTroopData(pets, "Unicorn");
        TroopData phoenix = getTroopData(pets, "Phoenix");
        TroopData poisonLizard = getTroopData(pets, "Poison Lizard");
        TroopData diggy = getTroopData(pets, "Diggy");
        TroopData frosty = getTroopData(pets, "Frosty");
        TroopData spiritFox = getTroopData(pets, "Spirit Fox");
        TroopData angryJelly = getTroopData(pets, "Angry Jelly");
        TroopData sneezy = getTroopData(pets, "Sneezy");

        drawTroopIcon(lassi, g, "Icon_HV_Hero_Pets_LASSI", x + 25, y + 100);
        drawTroopIcon(electroOwl, g, "Icon_HV_Hero_Pets_Electro_Owl", x + 275, y + 100);
        drawTroopIcon(mightyYak, g, "Icon_HV_Hero_Pets_Mighty_Yak", x + 525, y + 100);
        drawTroopIcon(unicorn, g, "Icon_HV_Hero_Pets_Unicorn", x + 25, y + 350);
        drawTroopIcon(frosty, g, "Icon_HV_Hero_Pets_Frosty", x + 275, y + 350);
        drawTroopIcon(diggy, g, "Icon_HV_Hero_Pets_Diggy", x + 525, y + 350);
        drawTroopIcon(poisonLizard, g, "Icon_HV_Hero_Pets_Poison_Lizard", x + 25, y + 600);
        drawTroopIcon(phoenix, g, "Icon_HV_Hero_Pets_Phoenix", x + 275, y + 600);
        drawTroopIcon(spiritFox, g, "Icon_HV_Hero_Pets_Spirit_Fox", x + 525, y + 600);
        drawTroopIcon(angryJelly, g, "Icon_HV_Hero_Pets_Angry_Jelly", x + 25, y + 850);
        drawTroopIcon(sneezy, g, "Icon_HV_Hero_Pets_Sneezy", x + 275, y + 850);
    }

    public static void drawTroopSection(Graphics2D g, int x, int y, Unit[] troops) {
        int width = 1250;
        int height = 1575;
        int radius = 30;

        g.setColor(Color.decode("#636e8f"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        FontUtils.drawClashFont(g, "Troops", x + 25, y + 20, 70, false, Color.WHITE, 6);

        TroopData barbarian = getTroopData(troops, "Barbarian");
        TroopData archer = getTroopData(troops, "Archer");
        TroopData giant = getTroopData(troops, "Giant");
        TroopData goblin = getTroopData(troops, "Goblin");
        TroopData wallBreaker = getTroopData(troops, "Wall Breaker");
        TroopData balloon = getTroopData(troops, "Balloon");
        TroopData wizard = getTroopData(troops, "Wizard");
        TroopData healer = getTroopData(troops, "Healer");
        TroopData dragon = getTroopData(troops, "Dragon");
        TroopData pekka = getTroopData(troops, "P.E.K.K.A");
        TroopData babyDragon = getTroopData(troops, "Baby Dragon");
        TroopData miner = getTroopData(troops, "Miner");
        TroopData electroDragon = getTroopData(troops, "Electro Dragon");
        TroopData yeti = getTroopData(troops, "Yeti");
        TroopData dragonRider = getTroopData(troops, "Dragon Rider");
        TroopData electroTitan = getTroopData(troops, "Electro Titan");
        TroopData rootRider = getTroopData(troops, "Root Rider");
        TroopData thrower = getTroopData(troops, "Thrower");
        TroopData minion = getTroopData(troops, "Minion");
        TroopData hogRider = getTroopData(troops, "Hog Rider");
        TroopData valkyrie = getTroopData(troops, "Valkyrie");
        TroopData golem = getTroopData(troops, "Golem");
        TroopData witch = getTroopData(troops, "Witch");
        TroopData lavaHound = getTroopData(troops, "Lava Hound");
        TroopData bowler = getTroopData(troops, "Bowler");
        TroopData iceGolem = getTroopData(troops, "Ice Golem");
        TroopData headhunter = getTroopData(troops, "Headhunter");
        TroopData apprenticeWarden = getTroopData(troops, "Apprentice Warden");
        TroopData druid = getTroopData(troops, "Druid");
        TroopData furnace = getTroopData(troops, "Furnace");

        drawTroopIcon(barbarian, g, "Icon_HV_Barbarian", x + 25, y + 100);
        drawTroopIcon(archer, g, "Icon_HV_Archer", x + 275, y + 100);
        drawTroopIcon(giant, g, "Icon_HV_Giant", x + 525, y + 100);
        drawTroopIcon(goblin, g, "Icon_HV_Goblin", x + 775, y + 100);
        drawTroopIcon(wallBreaker, g, "Icon_HV_Wall_Breaker", x + 1025, y + 100);

        drawTroopIcon(balloon, g, "Icon_HV_Balloon", x + 25, y + 350);
        drawTroopIcon(wizard, g, "Icon_HV_Wizard", x + 275, y + 350);
        drawTroopIcon(healer, g, "Icon_HV_Healer", x + 525, y + 350);
        drawTroopIcon(dragon, g, "Icon_HV_Dragon", x + 775, y + 350);
        drawTroopIcon(pekka, g, "Icon_HV_P.E.K.K.A", x + 1025, y + 350);

        drawTroopIcon(babyDragon, g, "Icon_HV_Baby_Dragon", x + 25, y + 600);
        drawTroopIcon(miner, g, "Icon_HV_Miner", x + 275, y + 600);
        drawTroopIcon(electroDragon, g, "Icon_HV_Electro_Dragon", x + 525, y + 600);
        drawTroopIcon(yeti, g, "Icon_HV_Yeti", x + 775, y + 600);
        drawTroopIcon(dragonRider, g, "Icon_HV_Dragon_Rider", x + 1025, y + 600);

        drawTroopIcon(electroTitan, g, "Icon_HV_Electro_Titan", x + 25, y + 850);
        drawTroopIcon(rootRider, g, "Icon_HV_Root_Rider", x + 275, y + 850);
        drawTroopIcon(thrower, g, "Icon_HV_Thrower", x + 525, y + 850);
        drawTroopIcon(minion, g, "Icon_HV_Minion", x + 775, y + 850);
        drawTroopIcon(hogRider, g, "Icon_HV_Hog_Rider", x + 1025, y + 850);

        drawTroopIcon(valkyrie, g, "Icon_HV_Valkyrie", x + 25, y + 1100);
        drawTroopIcon(golem, g, "Icon_HV_Golem", x + 275, y + 1100);
        drawTroopIcon(witch, g, "Icon_HV_Witch", x + 525, y + 1100);
        drawTroopIcon(lavaHound, g, "Icon_HV_Lava_Hound", x + 775, y + 1100);
        drawTroopIcon(bowler, g, "Icon_HV_Bowler", x + 1025, y + 1100);

        drawTroopIcon(iceGolem, g, "Icon_HV_Ice_Golem", x + 25, y + 1350);
        drawTroopIcon(headhunter, g, "Icon_HV_Headhunter", x + 275, y + 1350);
        drawTroopIcon(apprenticeWarden, g, "Icon_HV_Apprentice_Warden", x + 525, y + 1350);
        drawTroopIcon(druid, g, "Icon_HV_Druid", x + 775, y + 1350);
        drawTroopIcon(furnace, g, "Icon_HV_Furnace", x + 1025, y + 1350);
    }

    public static void drawSpellSection(Graphics2D g, int x, int y, Unit[] spells) {
        int width = 750;
        int height = 1325;
        int radius = 30;

        g.setColor(Color.decode("#636e8f"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        FontUtils.drawClashFont(g, "Spells", x + 25, y + 20, 70, false, Color.WHITE, 6);

        TroopData lightning = getTroopData(spells, "Lightning Spell");
        TroopData heal = getTroopData(spells, "Healing Spell");
        TroopData rage = getTroopData(spells, "Rage Spell");
        TroopData jump = getTroopData(spells, "Jump Spell");
        TroopData freeze = getTroopData(spells, "Freeze Spell");
        TroopData clone = getTroopData(spells, "Clone Spell");
        TroopData invisibility = getTroopData(spells, "Invisibility Spell");
        TroopData recall = getTroopData(spells, "Recall Spell");
        TroopData revive = getTroopData(spells, "Revive Spell");
        TroopData poison = getTroopData(spells, "Poison Spell");
        TroopData earthquake = getTroopData(spells, "Earthquake Spell");
        TroopData haste = getTroopData(spells, "Haste Spell");
        TroopData skeleton = getTroopData(spells, "Skeleton Spell");
        TroopData bat = getTroopData(spells, "Bat Spell");
        TroopData overgrowth = getTroopData(spells, "Overgrowth Spell");

        drawTroopIcon(lightning, g, "Icon_HV_Spell_Lightning", x + 25, y + 100);
        drawTroopIcon(heal, g, "Icon_HV_Spell_Heal", x + 275, y + 100);
        drawTroopIcon(rage, g, "Icon_HV_Spell_Rage", x + 525, y + 100);
        drawTroopIcon(jump, g, "Icon_HV_Spell_Jump", x + 25, y + 350);
        drawTroopIcon(freeze, g, "Icon_HV_Spell_Freeze", x + 275, y + 350);
        drawTroopIcon(clone, g, "Icon_HV_Spell_Clone", x + 525, y + 350);
        drawTroopIcon(invisibility, g, "Icon_HV_Spell_Invisibility", x + 25, y + 600);
        drawTroopIcon(recall, g, "Icon_HV_Spell_Recall", x + 275, y + 600);
        drawTroopIcon(revive, g, "Icon_HV_Spell_Revive", x + 525, y + 600);
        drawTroopIcon(poison, g, "Icon_HV_Dark_Spell_Poison", x + 25, y + 850);
        drawTroopIcon(earthquake, g, "Icon_HV_Dark_Spell_Earthquake", x + 275, y + 850);
        drawTroopIcon(haste, g, "Icon_HV_Dark_Spell_Haste", x + 525, y + 850);
        drawTroopIcon(skeleton, g, "Icon_HV_Dark_Spell_Skeleton", x + 25, y + 1100);
        drawTroopIcon(bat, g, "Icon_HV_Dark_Spell_Bat", x + 275, y + 1100);
        drawTroopIcon(overgrowth, g, "Icon_HV_Dark_Spell_Overgrowth", x + 525, y + 1100);
    }

    public static void drawSiegeMachineSection(Graphics2D g, int x, int y, Unit[] siegeMachines) {
        int width = 2050;
        int height = 350;
        int radius = 30;

        g.setColor(Color.decode("#636e8f"));
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);

        FontUtils.drawClashFont(g, "Siege Machines", x + 25, y + 20, 70, false, Color.WHITE, 6);

        TroopData wallWrecker = getTroopData(siegeMachines, "Wall Wrecker");
        TroopData battleBlimp = getTroopData(siegeMachines, "Battle Blimp");
        TroopData stoneSlammer = getTroopData(siegeMachines, "Stone Slammer");
        TroopData siegeBarracks = getTroopData(siegeMachines, "Siege Barracks");
        TroopData logLauncher = getTroopData(siegeMachines, "Log Launcher");
        TroopData flameFlinger = getTroopData(siegeMachines, "Flame Flinger");
        TroopData battleDrill = getTroopData(siegeMachines, "Battle Drill");
        TroopData troopLauncher = getTroopData(siegeMachines, "Troop Launcher");
 
        drawTroopIcon(wallWrecker, g, "Icon_HV_Siege_Machine_Wall_Wrecker", x + 25, y + 100);
        drawTroopIcon(battleBlimp, g, "Icon_HV_Siege_Machine_Battle_Blimp", x + 275, y + 100);
        drawTroopIcon(stoneSlammer, g, "Icon_HV_Siege_Machine_Stone_Slammer", x + 525, y + 100);
        drawTroopIcon(siegeBarracks, g, "Icon_HV_Siege_Machine_Siege_Barracks", x + 775, y + 100);
        drawTroopIcon(logLauncher, g, "Icon_HV_Siege_Machine_Log_Launcher", x + 1025, y + 100);
        drawTroopIcon(flameFlinger, g, "Icon_HV_Siege_Machine_Flame_Flinger", x + 1275, y + 100);
        drawTroopIcon(battleDrill, g, "Icon_HV_Siege_Machine_Battle_Drill", x + 1525, y + 100);
        drawTroopIcon(troopLauncher, g, "Icon_HV_Siege_Machine_Troop_Launcher", x + 1775, y + 100);
    }

    public static void drawTroopIcon(
        TroopData troopData,
        Graphics2D g,
        String troopName,
        int x,
        int y
    ) {
        BufferedImage image = ImageManager.getCachedImage(troopName);
        drawTroopIconDisplay(g, troopData, image, x, y);
    }

    public static void drawTroopIconDisplay(
        Graphics2D g,
        TroopData troopData,
        BufferedImage image,
        int x,
        int y
    ) {
        int radius = 30;
        int width = 200;
        int height = 200;
        int borderWidth = 2;

        // Step 1: Draw drop shadow below everything (outside the clip)
        BlurUtils.drawDropShadow(g, x, y, width, height, radius, 0.5f);

        // Step 2: Clip to the rounded rectangle area
        RoundRectangle2D clipShape = new RoundRectangle2D.Float(x, y, width, height, radius, radius);
        Shape originalClip = g.getClip();
        g.setClip(clipShape);

        // Step 3: Draw inner box
        int paddingTop = 2;
        int paddingSides = 2;
        int innerX = x + paddingSides;
        int innerY = y + paddingTop;
        int innerWidth = width - paddingSides * 2;
        int innerHeight = height - paddingTop - paddingSides;
        int innerRadius = radius / 2;

        g.setColor(new Color(152, 152, 205)); // Inner box color
        RenderingUtility.drawRoundedRect(g, innerX, innerY, innerWidth, innerHeight, innerRadius);

        // Step 4: Draw troop image (grayscale if locked)
        BufferedImage drawnImage = troopData.unlocked ? image : toGrayscale(image);
        g.drawImage(drawnImage, x, y, width, height, null);

        // Step 5: Draw level box if unlocked
        int levelBoxWidth = 60;
        int levelBoxHeight = 60;
        int levelBoxPadding = 6;

        if (troopData.unlocked) {
            drawLevelBox(
                g,
                troopData.level,
                x + levelBoxPadding,
                y + height - levelBoxHeight - levelBoxPadding,
                levelBoxWidth,
                levelBoxHeight,
                8,
                troopData.maxed
            );
        }

        // Step 6: Restore clip so border is not cut off
        g.setClip(originalClip);

        // Step 7: Draw border
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(borderWidth));
        RenderingUtility.drawRoundedRectOutline(g, x, y, width, height, radius);
    }

    public static BufferedImage toGrayscale(BufferedImage original) {
        BufferedImage grayscale = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        op.filter(original, grayscale);

        return grayscale;
    }

    public static void drawLevelBox(Graphics2D g, int number, int x, int y, int width, int height, int radius, boolean maxed) {
        // Draw base box
        RenderingUtility.drawRoundedRect(g, x, y, width, height, radius);
        g.setColor(maxed ? new Color(0xE4A23F) : new Color(0x393939));
        g.fill(new RoundRectangle2D.Float(x, y, width, height, radius, radius));

        // Inner shadow bevel effect
        int bevelInset = 2;
        Paint bevelGradient = GradientManager.createOptimizedGradient(
                "bevel", x, y, width, height,
                new Color[]{
                        new Color(0, 0, 0, 64),  // 0.25 alpha
                        new Color(0, 0, 0, 0)
                },
                new float[]{0f, 0.5f},
                false
        );

        Shape bevelRect = new RoundRectangle2D.Float(
                x + bevelInset, y + bevelInset,
                width - bevelInset * 2, height - bevelInset * 2,
                radius - 1, radius - 1
        );

        g.setPaint(bevelGradient);
        g.setClip(bevelRect);
        g.fill(bevelRect);
        g.setClip(null);

        // Outer border glow
        g.setColor(Color.WHITE);
        g.setStroke(new BasicStroke(2));
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.draw(new RoundRectangle2D.Float(x, y, width, height, radius, radius));

        // Draw level number
        int fontSize = (int)(height * 0.6);
        Font font = new Font("ClashFont", Font.PLAIN, fontSize);
        g.setFont(font);

        int textX = x + width / 2;
        int textY = y + height / 2;

        // Shadow stroke
        FontUtils.drawClashFont(g, String.valueOf(number), textX, textY, fontSize, true, Color.WHITE, 2);
    }
}
