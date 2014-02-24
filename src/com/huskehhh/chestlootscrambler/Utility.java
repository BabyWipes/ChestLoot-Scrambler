package com.huskehhh.chestlootscrambler;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    private static List<ItemStack[]> gearSwap = new ArrayList<ItemStack[]>();

    private static List<Location> chestLocs = new ArrayList<Location>();

    private static void scrambleChestLoot() {
        for (int i = 0; i <= chestLocs.size() - 1; i++) {
            Random rand = new Random();
            int r = rand.nextInt(chestLocs.size());
            BlockState state = chestLocs.get(r).getBlock().getState();
            if (state.getBlock().getType() == Material.CHEST) {
                Chest chest = (Chest) state;
                Inventory inv = chest.getBlockInventory();
                r = rand.nextInt(gearSwap.size());
                inv.setContents(gearSwap.get(r));
                gearSwap.remove(gearSwap.get(r));
            }
        }
    }

    private static void getGear() {
        for (int i = 0; i <= chestLocs.size() - 1; i++) {
            Block block = chestLocs.get(i).getBlock();
            BlockState state = block.getState();
            if (state.getBlock().getType() == Material.CHEST) {
                Chest chest = (Chest) state;
                Inventory inv = chest.getBlockInventory();
                gearSwap.add(inv.getContents());
            }
        }
        scrambleChestLoot();
    }

    private static void getChests(World w) {
        Chunk[] chunks = w.getLoadedChunks();
        for (int i = 0; i <= (chunks.length - 1); i++) {
            if (chunks[i] != null) {
                BlockState[] chests = chunks[i].getTileEntities();
                for (int x = 0; x <= (chests.length - 1); x++) {
                    if (chests[x] != null) {
                        if (chests[x].getBlock().getType() == Material.CHEST) {
                            chestLocs.add(chests[x].getBlock().getLocation());
                        }
                    }
                }
            }
        }
        getGear();
    }

    public static void prepareScrambler(World w) {
        getChests(w);
    }

}
