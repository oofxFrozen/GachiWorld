package ga.gachicode;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TestCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        for (int i = 0; i < 100000; i++) {
            Location loc1 = new Location(player.getWorld(), i + (int) (Math.random() * i), i + (int) (Math.random() * i) -1, i + (int) (Math.random() * i) -1);
            Location loc2 = new Location(player.getWorld(), i + (int) (Math.random() * i), i + (int) (Math.random() * i), i + (int) (Math.random() * i));
            new PrivateRegion("" + i, "" + i, loc1, loc2);
        }
        long startTime = System.currentTimeMillis();
        Location location = new Location(player.getWorld(), -500, -500, -500);
        for (ArrayList<PrivateRegion> regionArrayList : RegionManager.privateRegions.values()) {
            for (PrivateRegion region : regionArrayList) {
                if (!region.containsBlock(location)) {
                }
            }
        }
        long endTime = System.currentTimeMillis();
        player.sendMessage(ChatColor.GREEN + "Total execution time: " + (endTime-startTime) + " ms");
        //20-80 ms
        return false;
    }
}