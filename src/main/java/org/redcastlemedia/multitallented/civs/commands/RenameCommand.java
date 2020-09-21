package org.redcastlemedia.multitallented.civs.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.redcastlemedia.multitallented.civs.Civs;
import org.redcastlemedia.multitallented.civs.events.RenameTownEvent;
import org.redcastlemedia.multitallented.civs.localization.LocaleConstants;
import org.redcastlemedia.multitallented.civs.localization.LocaleManager;
import org.redcastlemedia.multitallented.civs.towns.Town;
import org.redcastlemedia.multitallented.civs.towns.TownManager;
import org.redcastlemedia.multitallented.civs.util.Constants;
import org.redcastlemedia.multitallented.civs.util.Util;

@CivsCommand(keys = { "rename" }) @SuppressWarnings("unused")
public class RenameCommand extends CivCommand {

    public boolean runCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = null;
        if (commandSender instanceof Player) {
            player = (Player) commandSender;
        }
        LocaleManager localeManager = LocaleManager.getInstance();

        if (strings.length < 3) {
            if (player != null) {
                player.sendMessage(Civs.getPrefix() + localeManager.getTranslation(player,
                        "specify-town-name"));
            } else {
                commandSender.sendMessage(Civs.getPrefix() + "Please specify a town name");
            }
            return true;
        }

        //TODO cooldown on renaming?

        //0 rename
        //1 old town
        //2 new town
        String oldTownName = strings[1];
        String newTownName = strings[2];

        if (!Util.validateFileName(newTownName)) {
            if (player != null) {
                player.sendMessage(Civs.getPrefix() + localeManager.getTranslation(player,
                        "specify-town-name"));
            } else {
                commandSender.sendMessage("Invalid town name");
            }
            return true;
        }
        Town conflictingTown = TownManager.getInstance().getTown(newTownName);
        if (conflictingTown != null) {
            Util.sendMessageToPlayerOrConsole(commandSender, LocaleConstants.INVALID_TARGET,
                    "A town already has that name");
            return true;
        }

        Town town = TownManager.getInstance().getTown(oldTownName);
        if (town == null) {
            if (player != null) {
                player.sendMessage(Civs.getPrefix() + localeManager.getTranslation(player,
                        "no-permission"));
            } else {
                commandSender.sendMessage(Civs.getPrefix() + "Invalid region");
            }
            return true;
        }
        if (player != null && (!town.getPeople().containsKey(player.getUniqueId()) ||
                !town.getPeople().get(player.getUniqueId()).contains(Constants.OWNER))) {
            player.sendMessage(Civs.getPrefix() + localeManager.getTranslation(player,
                    "no-permission"));
            return true;
        }

        RenameTownEvent renameTownEvent = new RenameTownEvent(oldTownName, newTownName, town);
        Bukkit.getPluginManager().callEvent(renameTownEvent);

        TownManager.getInstance().removeTown(town, false, false);
        town.setName(newTownName);
        TownManager.getInstance().addTown(town);
        TownManager.getInstance().saveTown(town);

        if (player != null) {
            player.sendMessage(Civs.getPrefix() + localeManager.getTranslation(player,
                    "town-renamed").replace("$1", oldTownName)
                    .replace("$2", newTownName));
        } else {
            commandSender.sendMessage("Town has been renamed from " + oldTownName + " to " + newTownName);
        }

        return true;
    }

    @Override
    public boolean canUseCommand(CommandSender commandSender) {
        return true;
    }

    @Override
    public List<String> getWord(CommandSender commandSender, String[] args) {
        if (args.length == 2) {
            return getTownNames(args[1]);
        }
        return super.getWord(commandSender, args);
    }
}
