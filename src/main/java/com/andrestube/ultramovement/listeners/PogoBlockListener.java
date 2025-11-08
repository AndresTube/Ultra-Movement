package com.andrestube.ultramovement.listeners;

import com.andrestube.ultramovement.items.CustomItems;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import java.util.HashSet;
import java.util.Set;

/**
 * Maneja la lógica para el Bloque de Impulso (Pogo Block).
 * Se activa al golpear el bloque con clic izquierdo.
 * Se puede romper agachándose y rompiendo el bloque.
 */
public class PogoBlockListener implements Listener {

    private static final double BOOST_Y = 1.2;
    private static final double BOOST_FORWARD_MULTIPLIER = 0.1;

    // Track placed custom pogo blocks by location so vanilla slime blocks aren't treated as pogo blocks
    private final Set<Location> placedPogoBlocks = new HashSet<>();

    @EventHandler
    public void onPlayerHitBlock(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        
        // 1. Verificamos que sea una acción de GOLPEAR/ATACAR un bloque (Clic Izquierdo)
        if (event.getAction() != Action.LEFT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        // 2. Verificamos que el bloque golpeado sea un Pogo Block colocado por nuestro plugin
        Block clickedBlock = event.getClickedBlock();
        Location clickedLoc = clickedBlock.getLocation();
        if (!placedPogoBlocks.contains(clickedLoc)) {
            return;
        }

        // Si el jugador está agachado, no activamos el impulso (permite romper)
        if (player.isSneaking()) {
            return;
        }
            
        // 3. Calculamos el vector de impulso
        Vector lookDirection = player.getLocation().getDirection();
        
        // Reiniciamos el vector de velocidad para asegurar un impulso limpio.
        player.setVelocity(new Vector(0, 0, 0)); 
        
        // Crea el vector final de impulso (fuerte vertical, ligero horizontal)
        Vector finalBoost = new Vector(
            lookDirection.getX() * BOOST_FORWARD_MULTIPLIER, 
            BOOST_Y,
            lookDirection.getZ() * BOOST_FORWARD_MULTIPLIER
        );

        // 4. Aplicamos el impulso al jugador.
        player.setVelocity(finalBoost);

        // 5. Reproduce efectos
        player.playSound(player.getLocation(), Sound.ENTITY_SLIME_JUMP, 1.0f, 0.8f);
        
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        Location loc = block.getLocation();
        // Solo manejamos bloques que fueron colocados como Pogo Blocks por el plugin
        if (!placedPogoBlocks.contains(loc)) return;

        // Si el jugador no está agachado, cancelar la ruptura
        if (!player.isSneaking()) {
            event.setCancelled(true);
            player.sendMessage("§c¡Agáchate (Shift) para romper el Pogo Block!");
            return;
        }

        // El jugador está agachado: permitir romper y dropear el item personalizado
        placedPogoBlocks.remove(loc);
        event.setCancelled(true);
        block.setType(Material.AIR);
        block.getWorld().dropItemNaturally(block.getLocation(), CustomItems.createPogoBlock());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        // Cuando un jugador coloca nuestro item personalizado, registramos su ubicación
        if (event.getItemInHand() == null) return;
        if (CustomItems.isPogoBlock(event.getItemInHand())) {
            placedPogoBlocks.add(event.getBlock().getLocation());
        }
    }
}