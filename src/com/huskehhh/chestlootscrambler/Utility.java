package com.huskehhh.chestlootscrambler;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {

    private static YamlConfiguration cache = YamlConfiguration.loadConfiguration(new File("plugins/ChestLoot-Scrambler/cache.yml"));

    private static List<ItemStack[]> gearSwap = new ArrayList<ItemStack[]>();

    private static List<Location> chestLocs = new ArrayList<Location>();

    public static void scrambleChestLoot() {

        for (int i = 0; i <= chestLocs.size() - 1; i++) {
            Random rand = new Random();
            int r = rand.nextInt(chestLocs.size());

            BlockState state = chestLocs.get(r).getBlock().getState();

            if (state.getBlock().getType() == Material.CHEST) {

                Chest chest = (Chest) state.getBlock();
                Inventory inv = chest.getBlockInventory();

                r = rand.nextInt(chestLocs.size());

                inv.setContents(gearSwap.get(r));
                gearSwap.remove(r);
            }

        }

    }

    public static void getGear() {

        for (int i = 0; i <= chestLocs.size() - 1; i++) {

            Block block = chestLocs.get(i).getBlock();
            BlockState state = block.getState();

            if (block.getType() == Material.CHEST) {

                Chest chest = (Chest) state.getBlock();

                Inventory inv = chest.getBlockInventory();

                gearSwap.add(inv.getContents());
            }

        }

    }

    private static int getMaxX(int x, int x1) {
        return Math.max(x, x1);
    }

    private static int getMinX(int x, int x1) {
        return Math.min(x, x1);
    }

    private static int getMaxY(int y, int y1) {
        return Math.max(y, y1);
    }

    private static int getMinY(int y, int y1) {
        return Math.min(y, y1);
    }

    private static int getMaxZ(int z, int z1) {
        return Math.max(z, z1);
    }

    private static int getMinZ(int z, int z1) {
        return Math.min(z, z1);
    }

    public static void getChests(Location loc, Location loc1) {

        if (cache.getStringList(loc.getWorld().getName() + ".cache") != null) {
            List<String> chests = cache.getStringList(loc.getWorld().getName() + ".cache");

            for (int i = 0; i <= chests.size() - 1; i++) {
                String[] split = chests.get(i).split(":");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int z = Integer.parseInt(split[2]);
                chestLocs.add(new Location(loc.getWorld(), x, y, z));
            }
        } else {

            Chunk[] chunks = loc.getWorld().getLoadedChunks();
            for (int i = 0; i <= chunks.length - 1; i++) {
                if (chunks[i] != null) {
                    BlockState[] chests = chunks[i].getTileEntities();
                    for (int x = 0; x <= chests.length - 1; x++) {
                        if (chests[x] != null) {
                            if (chests[x].getBlock().getType() == Material.CHEST) {
                                addChest(chests[x].getBlock().getLocation());
                            }
                        }
                    }
                }
            }
        }

    }

    private static void addChest(Location loc) {

        chestLocs.add(loc);

        cache.getStringList(loc.getWorld().getName() + ".cache").add(loc.getBlockX() + ":" + loc.getBlockY() + ":" + loc.getBlockZ());

        try {
            cache.save("plugins/ChestLoot-Scrambler/cache.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void createCache() {

        boolean exists = new File("plugins/ChestLoot-Scrambler/cache.yml").exists();

        if (!exists) {
            new File("plugins/ChestLoot-Scrambler").mkdir();
            cache.options().header("ChestLoot-Scrambler, made by Husky!");
            try {
                cache.save("plugins/ChestLoot-Scrambler/cache.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
