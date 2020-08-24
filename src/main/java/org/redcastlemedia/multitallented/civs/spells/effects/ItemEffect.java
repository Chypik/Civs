package org.redcastlemedia.multitallented.civs.spells.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.redcastlemedia.multitallented.civs.items.CVItem;
import org.redcastlemedia.multitallented.civs.spells.Spell;
import org.redcastlemedia.multitallented.civs.spells.SpellConstants;

public class ItemEffect extends Effect {

    private boolean potionExtended;
    private boolean potionUpgraded;
    private boolean transformHeldItem;
    private List<String> transformReq;
    private String potionType;
    private String mat = "STONE";
    private String target = SpellConstants.SELF;

    public ItemEffect(Spell spell, String key, Object target, Entity origin, int level, Object value) {
        super(spell, key, target, origin, level);
        if (value instanceof ConfigurationSection) {
            ConfigurationSection section = (ConfigurationSection) value;
            String matString = section.getString("item", "STONE");
            if (matString != null) {
                this.mat = matString;
            }
            this.transformHeldItem = section.getBoolean("transform", false);
            this.transformReq = section.getStringList("transform-reqs");
            this.potionType = section.getString("potion.type", null);
            this.potionUpgraded = section.getBoolean("potion.upgraded", false);
            this.potionExtended = section.getBoolean("potion.ticks", false);
            String tempTarget = section.getString(SpellConstants.TARGET, SpellConstants.NOT_A_STRING);
            if (!SpellConstants.NOT_A_STRING.equals(tempTarget)) {
                this.target = tempTarget;
            } else {
                this.target = SpellConstants.SELF;
            }
        } else if (value instanceof String) {
            this.mat = (String) value;
            this.target = SpellConstants.SELF;
            this.transformHeldItem = false;
            this.transformReq = new ArrayList<>();
        }
    }

    public boolean meetsRequirement() {
        if (!this.transformHeldItem) {
            return true;
        }
        Object target = getTarget();
        if (!(target instanceof Player)) {
            return false;
        }
        Player player = (Player) target;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        return this.transformReq.contains(itemStack.getType().name().toUpperCase());
    }
    public void apply() {
        Object target = getTarget();
        if (!(target instanceof Player)) {
            return;
        }
        Player player = (Player) target;
        if (this.transformHeldItem) {
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (!this.transformReq.isEmpty() &&
                    !this.transformReq.contains(itemStack.getType().name().toUpperCase())) {
                return;
            }
        }
        CVItem cvItem = CVItem.createCVItemFromString(this.mat);
        ItemStack itemStack = cvItem.createItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta instanceof PotionMeta && potionType != null) {
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();
            PotionType potionEffectType = PotionType.valueOf(potionType);
            potionMeta.setBasePotionData(new PotionData(potionEffectType, this.potionExtended, this.potionUpgraded));
            itemStack.setItemMeta(potionMeta);
        }
        if (this.transformHeldItem) {
            player.getInventory().setItemInMainHand(itemStack);
        } else {
            if (player.getInventory().firstEmpty() > -1) {
                player.getInventory().addItem(itemStack);
            } else {
                player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
            }
        }
    }
}
