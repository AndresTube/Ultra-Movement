package com.andrestube.ultramovement;

import com.andrestube.ultramovement.items.CustomItems;
import com.andrestube.ultramovement.listeners.DashListener;
import com.andrestube.ultramovement.listeners.PogoBlockListener;
import com.andrestube.ultramovement.listeners.DoubleJumpListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Clase principal del plugin UltraMovement.
 * Implementa mecánicas de doble salto, dash y bloques de impulso (Pogo Block).
 */
public class UltraMovementPlugin extends JavaPlugin {

    // Clave de metadatos estática para rastrear si un jugador tiene su doble salto cargado.
    public static final String DOUBLE_JUMP_KEY = "ultramovement_doubleJumpReady";

    private DoubleJumpListener doubleJumpListener;
    private PogoBlockListener pogoBlockListener;
    private DashListener dashListener;
    private boolean listenersEnabled = true;

    @Override
    public void onEnable() {
        // Inicializar listeners
        this.doubleJumpListener = new DoubleJumpListener(this);
        this.pogoBlockListener = new PogoBlockListener();
        this.dashListener = new DashListener();
        
        // Registrar listeners
        registerListeners();
        
        // Registrar las recetas personalizadas
        getServer().addRecipe(CustomItems.createWingBootsRecipe(this));
        getServer().addRecipe(CustomItems.createDashAmuletRecipe(this));
        getServer().addRecipe(CustomItems.createPogoBlockRecipe(this));
        
        getLogger().info("UltraMovement: ¡Doble salto, Dash y Pogo Block activados con éxito!");
    }

    @Override
    public void onDisable() {
        // Desregistrar todos los listeners al desactivar el plugin
        HandlerList.unregisterAll(this);
        getLogger().info("UltraMovement: Plugin desactivado.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("ultramovement")) {
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Uso: /um <enable|disable|get>");
            return true;
        }

        String sub = args[0].toLowerCase();

        if (sub.equals("enable") || sub.equals("disable")) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.RED + "Uso: /um <enable|disable>");
                return true;
            }

            if (sub.equals("enable")) {
                if (listenersEnabled) {
                    sender.sendMessage(ChatColor.YELLOW + "Los listeners ya están activados!");
                    return true;
                }
                registerListeners();
                listenersEnabled = true;
                sender.sendMessage(ChatColor.GREEN + "Listeners de UltraMovement activados!");
            } else {
                if (!listenersEnabled) {
                    sender.sendMessage(ChatColor.YELLOW + "Los listeners ya están desactivados!");
                    return true;
                }
                HandlerList.unregisterAll(this);
                listenersEnabled = false;
                sender.sendMessage(ChatColor.RED + "Listeners de UltraMovement desactivados!");
            }

            return true;
        }

        // /um get <name>
        if (sub.equals("get")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Este comando solo puede ser usado por jugadores.");
                return true;
            }
            if (!sender.hasPermission("ultramovement.admin")) {
                sender.sendMessage(ChatColor.RED + "No tienes permiso para usar este comando.");
                return true;
            }
            if (args.length != 2) {
                sender.sendMessage(ChatColor.RED + "Uso: /um get <wingboots|dashamulet|pogoblock>");
                return true;
            }

            Player player = (Player) sender;
            String name = args[1].toLowerCase();
            ItemStack toGive = null;
            switch (name) {
                case "wingboots":
                case "wing":
                case "boots":
                    toGive = CustomItems.createWingBoots();
                    break;
                case "dashamulet":
                case "amulet":
                case "dash":
                    toGive = CustomItems.createDashAmulet();
                    break;
                case "pogoblock":
                case "pogo":
                    toGive = CustomItems.createPogoBlock();
                    break;
                default:
                    player.sendMessage(ChatColor.RED + "Artículo desconocido. Usa: wingboots, dashamulet, pogoblock");
                    return true;
            }

            if (toGive != null) {
                player.getInventory().addItem(toGive);
                player.sendMessage(ChatColor.GREEN + "Has recibido: " + toGive.getItemMeta().getDisplayName());
            }

            return true;
        }

        sender.sendMessage(ChatColor.RED + "Uso: /um <enable|disable|get>");
        return true;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(doubleJumpListener, this);
        getServer().getPluginManager().registerEvents(pogoBlockListener, this);
        getServer().getPluginManager().registerEvents(dashListener, this);
    }
}