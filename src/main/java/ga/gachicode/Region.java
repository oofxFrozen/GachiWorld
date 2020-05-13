package ga.gachicode;

import org.bukkit.Location;


public class Region {

    String owner;
    int minX, minY, minZ,
            maxX, maxY, maxZ;
    Location firstLocation, secondLocation;

    public Location getFirstLocation() {
        return firstLocation;
    }

    public Location getSecondLocation() {
        return secondLocation;
    }

    public Region(String owner, Location firstLocation, Location secondLocation) {

        this.owner = owner;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;

        if (firstLocation.getBlockX() >= secondLocation.getBlockX()) {
            this.maxX = firstLocation.getBlockX();
            this.minX = secondLocation.getBlockX();
        } else {
            this.minX = firstLocation.getBlockX();
            this.maxX = secondLocation.getBlockX();
        }
        if (firstLocation.getBlockY() >= secondLocation.getBlockY()) {
            this.maxY = firstLocation.getBlockY();
            this.minY = secondLocation.getBlockY();
        } else {
            this.minY = firstLocation.getBlockY();
            this.maxY = secondLocation.getBlockY();
        }
        if (firstLocation.getBlockZ() >= secondLocation.getBlockZ()) {
            this.maxZ = firstLocation.getBlockZ();
            this.minZ = secondLocation.getBlockZ();
        } else {
            this.minZ = firstLocation.getBlockZ();
            this.maxZ = secondLocation.getBlockZ();
        }

        Region currentRegion = RegionManager.currentRegions.get(owner);
        RegionManager.currentRegions.put(owner, currentRegion);

    }

    public int getSize() {
        return (this.maxX-this.minX+1)*(this.maxY-this.minY+1)*(this.maxZ-this.minZ+1);
    }

    public String getOwner() {
        return owner;
    }

    public int getMinX() {
        return minX;
    }

    public int getMinY() {
        return minY;
    }

    public int getMinZ() {
        return minZ;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public int getMaxZ() {
        return maxZ;
    }
}
