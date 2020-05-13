package ga.gachicode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class GachiWorld extends JavaPlugin {

    public static GachiWorld instance;

    public static GachiWorld getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        instance = this;
        Bukkit.getPluginManager().registerEvents(new RegionManager(), this);
        Objects.requireNonNull(getCommand("test")).setExecutor(new TestCMD());
        Objects.requireNonNull(getCommand("region")).setExecutor(new RegionManager());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
