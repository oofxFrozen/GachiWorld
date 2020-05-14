package ga.gachicode;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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

        loadConfig();

        try {
            loadRegions();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDisable() {
        try {
            saveRegions();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadRegions() throws IOException {

        File regions = new File(this.getDataFolder().getPath(), "regions.dat");
        File names = new File(this.getDataFolder().getPath(), "region_names.dat");

        if (!regions.exists()) {
            regions.createNewFile();
        }
        if (!names.exists()) {
            names.createNewFile();
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(regions)))
        {
            RegionManager.privateRegions = (HashMap<String, ArrayList<PrivateRegion>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(names)))
        {
            RegionManager.regionNames = (HashSet<String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveRegions() throws IOException {
        File regions = new File(this.getDataFolder().getPath(), "regions.dat");
        File names = new File(this.getDataFolder().getPath(), "region_names.dat");

        if (!regions.exists()) {
            regions.createNewFile();
        }
        if (!names.exists()) {
            names.createNewFile();
        }

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(regions)))
        {

            oos.writeObject(RegionManager.privateRegions);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(names)))
        {
            oos.writeObject(RegionManager.regionNames);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        saveDefaultConfig();
    }
}
