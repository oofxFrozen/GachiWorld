package ga.gachicode;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        long startTime = System.currentTimeMillis();

        long endTime = System.currentTimeMillis();
        player.sendMessage(ChatColor.GREEN + "Total execution time: " + (endTime-startTime) + " ms");
        return false;
    }
}