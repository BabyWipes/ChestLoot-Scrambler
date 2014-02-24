package com.huskehhh.chestlootscrambler;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Scrambler extends JavaPlugin implements Listener {


    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Utility.getChests(e.getPlayer().getWorld());
        Utility.getGear();
        Utility.scrambleChestLoot();
        e.getPlayer().sendMessage(ChatColor.GREEN + "Scrambled Chests!");
    }


}
