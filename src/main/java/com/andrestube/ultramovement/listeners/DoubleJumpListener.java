package com.andrestube.ultramovement.listeners;

import com.andrestube.ultramovement.UltraMovementPlugin;
import com.andrestube.ultramovement.items.CustomItems;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect; 
import org.bukkit.potion.PotionEffectType; 

/**
* Maneja la lógica para la habilidad de doble salto.
* Se requiere Iron Wing Boots para esta habilidad.
*/
public class DoubleJumpListener implements Listener {

    private final UltraMovementPlugin plugin;

    private static final double JUMP_POWER_Y = 0.8; 
    private static final double JUMP_POWER_FORWARD = 0.3;
    // Ticks de duración del efecto (5 ticks = 0.25 segundos)
    private static final int NO_FALL_TICKS = 5; 

    public DoubleJumpListener(UltraMovementPlugin plugin) {
        this.plugin = plugin;
    }
    
    /**
    * Recarga el doble salto cuando el jugador toca el suelo.
    */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Debes asegurar que esta línea se ejecute si quieres usar el comando /um
        // if (!plugin.isEnabled()) return; 

        Player player = event.getPlayer();
        
        // El doble salto solo se otorga en modo supervivencia/aventura
        if (player.getGameMode().name().contains("CREATIVE")) return;

        // Requiere las Iron Wing Boots
        ItemStack boots = player.getInventory().getBoots();
        if (boots == null || !CustomItems.isWingBoots(boots)) {
            return;
        }

        // Si el jugador está cayendo/quieto y no puede volar (salto no recargado)
        if (!player.getAllowFlight() && player.getVelocity().getY() <= 0.05) {
            
            Location loc = player.getLocation();
            
            // Verificamos si hay un bloque sólido justo debajo para confirmar que aterrizó.
            if (loc.clone().subtract(0, 0.1, 0).getBlock().getType().isSolid()) {
                
                // Recarga el doble salto
                player.setAllowFlight(true);
                
                // Marcamos con metadatos que este 'allowFlight' es nuestro doble salto.
                player.setMetadata(UltraMovementPlugin.DOUBLE_JUMP_KEY, new FixedMetadataValue(plugin, true));
            }
        }
    }

    /**
    * Ejecuta el doble salto cuando el jugador presiona ESPACIO en el aire.
    */
    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        // Debes asegurar que esta línea se ejecute si quieres usar el comando /um
        // if (!plugin.isEnabled()) return; 

        Player player = event.getPlayer();

        // 1. Ignoramos si está en Creative
        if (player.getGameMode().name().contains("CREATIVE")) {
            return;
        }
        
        // 2. Verificamos que tenga nuestro flag de doble salto activo
        if (player.hasMetadata(UltraMovementPlugin.DOUBLE_JUMP_KEY)) {
            
            // Cancelamos el evento para evitar el modo de vuelo
            event.setCancelled(true); 

            // Quitamos el flag de la metadata y de allowFlight
            player.setAllowFlight(false);
            player.removeMetadata(UltraMovementPlugin.DOUBLE_JUMP_KEY, plugin);
            
            // 3. Aplicamos el vector de impulso (hacia adelante y arriba)
            Vector lookDirection = player.getLocation().getDirection();
            
            Vector jumpVector = new Vector(
                lookDirection.getX() * JUMP_POWER_FORWARD,
                JUMP_POWER_Y,
                lookDirection.getZ() * JUMP_POWER_FORWARD
            );
            
            // Aplicamos la velocidad.
            player.setVelocity(player.getVelocity().add(jumpVector));

            // LÓGICA ANTI-DAÑO DE CAÍDA
            // Aplica el efecto de Caída Lenta por una fracción de segundo (0.25s)
            PotionEffect noFallEffect = new PotionEffect(
                PotionEffectType.SLOW_FALLING, 
                NO_FALL_TICKS, 
                0, // Nivel 0 (sin amplificación)
                false, // No mostrar partículas
                false // No mostrar icono
            );
            player.addPotionEffect(noFallEffect);
            
            // 4. Efectos visuales y sonido
            player.playSound(player.getLocation(), "entity.horse.jump", 1.0f, 1.2f);
        }
    }
}