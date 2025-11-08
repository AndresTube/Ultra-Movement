package com.andrestube.ultramovement.listeners;

import com.andrestube.ultramovement.items.CustomItems;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;

/**
 * Maneja la habilidad de Dash/Impulso horizontal.
 * Activado al hacer clic derecho con el Amuleto de Dash personalizado.
 */
public class DashListener implements Listener {

    private static final double DASH_POWER = 1.5; 
    private static final int DASH_COOLDOWN_TICKS = 40; // 2 segundos de CD
    private static final int NO_FALL_TICKS = 10; // 0.5 segundos de efecto anti-caída

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        // 1. Verificar si es clic derecho
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // 2. Verificar si está sosteniendo el amuleto de dash personalizado
        if (!CustomItems.isDashAmulet(item)) {
            return;
        }
            
        // Verificar el cooldown nativo de Minecraft
        if (player.getCooldown(item.getType()) > 0) {
            player.sendMessage("§c¡El Dash está recargando!");
            return;
        }
            
        // 3. Ejecutamos el Dash

        // Obtenemos la dirección hacia donde está mirando
        Vector lookDirection = player.getLocation().getDirection();
        
        // Aplicamos un fuerte vector horizontal y reseteamos ligeramente la Y para un dash limpio
        Vector dashVector = lookDirection.multiply(DASH_POWER).setY(0.1);

        player.setVelocity(dashVector);

        // 4. Aplicamos un breve efecto de Resistencia a la Caída (para evitar daño por impacto horizontal)
        // Usamos SLOW_FALLING para amortiguar el aterrizaje sin parar el movimiento.
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, NO_FALL_TICKS, 0, false, false));
        
        // 5. Aplicamos Cooldown
        player.setCooldown(item.getType(), DASH_COOLDOWN_TICKS);
        
        // 6. Efectos visuales y sonido
        player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.8f, 2.0f);
        
        // Efecto de partículas de rastro
        player.getWorld().spawnParticle(
            org.bukkit.Particle.CLOUD, 
            player.getLocation(), 
            10, 0.5, 0.5, 0.5, 0.01 
        );
        
        event.setCancelled(true);
    }
}