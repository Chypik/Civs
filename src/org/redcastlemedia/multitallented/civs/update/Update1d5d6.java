package org.redcastlemedia.multitallented.civs.update;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.redcastlemedia.multitallented.civs.Civs;

public final class Update1d5d6 {
    private Update1d5d6() {

    }

    public static String update() {
        updateConfig();
        updateItemTypes();
        return "1.5.6";
    }

    private static void updateItemTypes() {
        File itemTypesFolder = new File(Civs.getInstance().getDataFolder(), "item-types");
        if (!itemTypesFolder.exists()) {
            return;
        }
        upgradeTowns(itemTypesFolder);
        addJammerTrap(itemTypesFolder);
        updateEmbassy(itemTypesFolder);
        // TODO update translations
    }

    private static void updateEmbassy(File itemTypesFolder) {
        File utilitiesFolder = new File(itemTypesFolder, "utilities");
        if (!utilitiesFolder.exists()) {
            return;
        }
        File embassyFile = new File(utilitiesFolder, "embassy.yml");
        if (embassyFile.exists()) {
            try {
                FileConfiguration config = new YamlConfiguration();
                config.load(embassyFile);
                List<String> townsList = config.getStringList("towns");
                townsList.add("settlement");
                townsList.add("hamlet");
                townsList.add("village");
                config.set("towns", townsList);
                List<String> preReqs = config.getStringList("pre-reqs");
                if (!preReqs.isEmpty() && preReqs.get(0).startsWith("member=")) {
                    preReqs.remove(0);
                    preReqs.add("member=settlement:hamlet:village:town:city:metropolis");
                }
                config.set("pre-reqs", preReqs);
                config.save(embassyFile);
            } catch (Exception e) {

            }
        }
        // TODO update council room, town hall, city hall, and capitol

    }
    private static void addJammerTrap(File itemTypesFolder) {
        File offenseFolder = new File(itemTypesFolder, "offense");
        if (!offenseFolder.exists()) {
            return;
        }
        File jammerFile = new File(offenseFolder, "jammer_trap.yml");
        if (jammerFile.exists()) {
            return;
        }
        try {
            jammerFile.createNewFile();
            FileConfiguration config = new YamlConfiguration();
            config.set("type", "region");
            config.set("icon", "SOUL_SAND");
            config.set("name", "Jammer_Trap");
            config.set("max", 1);
            config.set("price", 400);
            ArrayList<String> groups = new ArrayList<>();
            groups.add("offense");
            config.set("groups", groups);
            config.set("level", 1);
            ArrayList<String> buildReqs = new ArrayList<>();
            buildReqs.add("TNT*4");
            buildReqs.add("OBSIDIAN*2");
            buildReqs.add("g:fence*12");
            config.set("build-reqs", buildReqs);
            config.set("build-radius", 3);
            ArrayList<String> effects = new ArrayList<>();
            effects.add("block_break");
            effects.add("block_build");
            effects.add("jammer:100.30.30");
            effects.add("temporary:1800");
            effects.add("port:member");
            config.set("effects", effects);
            config.save(jammerFile);
        } catch (Exception e) {

        }
    }
    private static void upgradeTowns(File itemTypesFolder) {
        File townsFolder = new File(itemTypesFolder, "towns");
        if (!townsFolder.exists()) {
            return;
        }
        File settlementFile = new File(townsFolder, "settlement.yml");
        if (!settlementFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(settlementFile);
            List<String> limitList =  config.getStringList("limits");
            int embassyLimit = removeEmbassy(limitList);
            if (embassyLimit > 1) {
                limitList.add("embassy:" + embassyLimit);
            } else {
                limitList.add("embassy:1");
            }
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(settlementFile);
        } catch (Exception e) {

        }
        File hamletFile = new File(townsFolder, "hamlet.yml");
        if (!hamletFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(hamletFile);
            List<String> limitList =  config.getStringList("limits");
            int embassyLimit = removeEmbassy(limitList);
            if (embassyLimit > 2) {
                limitList.add("embassy:" + embassyLimit);
            } else {
                limitList.add("embassy:2");
            }
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(hamletFile);
        } catch (Exception e) {

        }
        File villageFile = new File(townsFolder, "village.yml");
        if (!villageFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(villageFile);
            List<String> limitList =  config.getStringList("limits");
            int embassyLimit = removeEmbassy(limitList);
            if (embassyLimit > 3) {
                limitList.add("embassy:" + embassyLimit);
            } else {
                limitList.add("embassy:3");
            }
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(villageFile);
        } catch (Exception e) {

        }
        File townFile = new File(townsFolder, "town.yml");
        if (!townFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(townFile);
            List<String> limitList =  config.getStringList("limits");
            int embassyLimit = removeEmbassy(limitList);
            if (embassyLimit > 5) {
                limitList.add("embassy:" + embassyLimit);
            } else {
                limitList.add("embassy:5");
            }
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(townFile);
        } catch (Exception e) {

        }
        File cityFile = new File(townsFolder, "city.yml");
        if (!cityFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(cityFile);
            List<String> limitList =  config.getStringList("limits");
            int embassyLimit = removeEmbassy(limitList);
            if (embassyLimit > 8) {
                limitList.add("embassy:" + embassyLimit);
            } else {
                limitList.add("embassy:8");
            }
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(cityFile);
        } catch (Exception e) {

        }
        File metropolisFile = new File(townsFolder, "metropolis.yml");
        if (!metropolisFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(metropolisFile);
            List<String> limitList =  config.getStringList("limits");
            limitList.add("jammer_trap:0");
            config.set("limits", limitList);
            config.save(metropolisFile);
        } catch (Exception e) {

        }
    }
    private static int removeEmbassy(List<String> limitList) {
        for (String limit : new ArrayList<>(limitList)) {
            if (limit.contains("embassy")) {
                int limitCount = Integer.parseInt(limit.split(":")[1]);
                limitList.remove(limit);
                return limitCount;
            }
        }
        return -1;
    }

    private static void updateConfig() {
        File configFile = new File(Civs.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            return;
        }
        try {
            FileConfiguration config = new YamlConfiguration();
            config.load(configFile);
            config.set("town-rings-crumble-to-gravel", true);
            config.set("allow-teleporting-out-of-hostile-towns", true);
            config.set("allow-offline-raiding", true);
            config.save(configFile);
        } catch (Exception exception) {
            return;
        }
    }
}
