package com.andrestube.ultramovement.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class CustomItems {
    
    public static final String WING_BOOTS_KEY = "ultramovement_wing_boots";
    public static final String DASH_AMULET_KEY = "ultramovement_dash_amulet";
    public static final String POGO_BLOCK_KEY = "ultramovement_pogo_block";
    
    public static ItemStack createWingBoots() {
        ItemStack wingBoots = new ItemStack(Material.IRON_BOOTS);
        ItemMeta meta = wingBoots.getItemMeta();
        
        meta.setDisplayName(ChatColor.AQUA + "Iron Wing Boots");
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "Special boots infused with the power of flight",
            ChatColor.YELLOW + "Enables Double Jump ability"
        ));
        
        // Add a glow effect
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        
        // Add custom model data to identify these boots
        meta.setCustomModelData(8001);
        
        wingBoots.setItemMeta(meta);
        return wingBoots;
    }

    public static ItemStack createDashAmulet() {
        ItemStack amulet = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta meta = amulet.getItemMeta();
        
        meta.setDisplayName(ChatColor.BLUE + "Dash Amulet");
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "A mystical amulet that grants the power of swift movement",
            ChatColor.YELLOW + "Right-click to dash forward",
            ChatColor.RED + "Cooldown: 2 seconds"
        ));
        
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(8002);
        
        amulet.setItemMeta(meta);
        return amulet;
    }

    public static ItemStack createPogoBlock() {
        ItemStack pogoBlock = new ItemStack(Material.SLIME_BLOCK);
        ItemMeta meta = pogoBlock.getItemMeta();
        
        meta.setDisplayName(ChatColor.GREEN + "Energized Pogo Block");
        meta.setLore(Arrays.asList(
            ChatColor.GRAY + "A special block infused with bouncy energy",
            ChatColor.YELLOW + "Left-click to launch yourself upward"
        ));
        
        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(8003);
        
        pogoBlock.setItemMeta(meta);
        return pogoBlock;
    }
    
    public static ShapedRecipe createWingBootsRecipe(Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, WING_BOOTS_KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, createWingBoots());
        
        // Recipe: F F F
        //         I _ I
        //         I _ I
        // Where F = Feather, I = Iron Ingot
        recipe.shape("F F", "I I", "I I");
        recipe.setIngredient('F', Material.FEATHER);
        recipe.setIngredient('I', Material.IRON_INGOT);
        
        return recipe;
    }

    public static ShapedRecipe createDashAmuletRecipe(Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, DASH_AMULET_KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, createDashAmulet());
        
        // Recipe: _ P _
        //         P H P
        //         _ P _
        // Where P = Pearl, H = Heart of the Sea
        recipe.shape(" P ", "PHP", " P ");
        recipe.setIngredient('P', Material.ENDER_PEARL);
        recipe.setIngredient('H', Material.HEART_OF_THE_SEA);
        
        return recipe;
    }

    public static ShapedRecipe createPogoBlockRecipe(Plugin plugin) {
        NamespacedKey key = new NamespacedKey(plugin, POGO_BLOCK_KEY);
        ShapedRecipe recipe = new ShapedRecipe(key, createPogoBlock());
        
        // Recipe: R S R
        //         S P S
        //         R S R
        // Where R = Redstone, S = Slime Ball, P = Piston
        recipe.shape("RSR", "SPS", "RSR");
        recipe.setIngredient('R', Material.REDSTONE);
        recipe.setIngredient('S', Material.SLIME_BALL);
        recipe.setIngredient('P', Material.PISTON);
        
        return recipe;
    }
    
    public static boolean isWingBoots(ItemStack item) {
        if (item == null || item.getType() != Material.IRON_BOOTS) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 8001;
    }

    public static boolean isDashAmulet(ItemStack item) {
        if (item == null || item.getType() != Material.HEART_OF_THE_SEA) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 8002;
    }

    public static boolean isPogoBlock(ItemStack item) {
        if (item == null || item.getType() != Material.SLIME_BLOCK) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() && meta.getCustomModelData() == 8003;
    }
}