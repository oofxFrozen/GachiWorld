package ga.gachicode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class RegionManager implements Listener, CommandExecutor {

    public static HashMap<String, ArrayList<PrivateRegion>> privateRegions = new HashMap<>();
    public static HashMap<String, Region> currentRegions = new HashMap<>();
    public static HashMap<String, Location> currentFirstClicks = new HashMap<>();
    public static HashMap<String, Location> currentSecondClicks = new HashMap<>();
    public static HashSet<String> regionNames = new HashSet<>();

    @EventHandler
    public void setPrivateBorders (PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock() != null &&
                player.getInventory().getItemInMainHand().getType() == Material.WOODEN_AXE) {
            event.setCancelled(true);
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                currentFirstClicks.put(player.getName(), event.getClickedBlock().getLocation());
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                currentSecondClicks.put(player.getName(), event.getClickedBlock().getLocation());
            }
            if (currentFirstClicks.get(player.getName()) != null && currentSecondClicks.get(player.getName()) != null) {
                Location firstLoc = currentFirstClicks.get(player.getName());
                Location secondLoc = currentSecondClicks.get(player.getName());
                Region region = new Region(player.getName(), firstLoc, secondLoc);
                currentRegions.put(player.getName(), region);
                player.sendMessage(ChatColor.GRAY + "Current region size: " + region.getSize());
            }
        }
    }

    @EventHandler
    public void blockBreakCheck (BlockBreakEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        for (ArrayList<PrivateRegion> regionArrayList : privateRegions.values()) {
            for (PrivateRegion region : regionArrayList) {
                if (region.containsBlock(location)) {
                    if (region.getOwner().equals(player.getName())) {
                        return;
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void blockPlaceCheck (BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Location location = event.getBlock().getLocation();
        for (ArrayList<PrivateRegion> regionArrayList : privateRegions.values()) {
            for (PrivateRegion region : regionArrayList) {
                if (region.containsBlock(location)) {
                    if (region.getOwner().equals(player.getName())) {
                        return;
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void blockUseCheck (PlayerInteractEvent event) {
        if (event.getClickedBlock() == null || event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player player = event.getPlayer();
        Location location = event.getClickedBlock().getLocation();
        for (ArrayList<PrivateRegion> regionArrayList : privateRegions.values()) {
            for (PrivateRegion region : regionArrayList) {
                if (region.containsBlock(location)) {
                    if (region.getOwner().equals(player.getName())) {
                        return;
                    } else {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    public boolean canPrivate (String player, String name) {
        boolean contains;
        if (regionNames.contains(name)) {
            return false;
        }
        for (ArrayList<PrivateRegion> list : privateRegions.values()) {
            for (PrivateRegion region : list) {
               contains = region.containsPrivate(currentRegions.get(player).getFirstLocation(), currentRegions.get(player).getSecondLocation());
               if (contains) {
                   return false;
               }
            }
        }
        return true;
    }

    public boolean deleteRegion (String name) {
        for (ArrayList<PrivateRegion> list : privateRegions.values()) {
            for (PrivateRegion region : list) {
                if (region.getName().equals(name)) {
                    privateRegions.get(region.getOwner()).remove(region);
                    regionNames.remove(name);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        switch (args.length) {
            case 2:
                if (args[0].equals("claim")) {
                    if (currentRegions.get(player.getName()) == null) {
                        player.sendMessage(ChatColor.RED + "Прежде чем создавать регион, выделите территорию под него!");
                    }
                    if (canPrivate(player.getName(), args[1])) {
                        PrivateRegion privateRegion = new PrivateRegion(player.getName(), args[1], currentRegions.get(player.getName()).getFirstLocation(), currentRegions.get(player.getName()).getSecondLocation());
                        player.sendMessage(ChatColor.GREEN + "Регион успешно создан!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Регион пересекается с другим регионом, или уже существует регион с таким же именем!");
                    }
                }
                if (args[0].equals("delete")) {
                    if (deleteRegion(args[1])) {
                        player.sendMessage(ChatColor.GREEN + "Регион успешно удален!");
                    } else {
                        player.sendMessage(ChatColor.RED + "Такой регион не существует или не принадлежит вам!");
                    }
                }
                break;
            default:
                player.sendMessage(ChatColor.RED + "Недопустимое количество аргументов.");
                break;
        }
        return true;
    }
}
