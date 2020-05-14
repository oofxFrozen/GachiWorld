package ga.gachicode;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;

public class PrivateRegion implements Serializable {

    String owner, name;
    ArrayList<String> officers, members = new ArrayList<>();
    int minX, minY, minZ,
            maxX, maxY, maxZ;

    boolean PVP_FLAG = false, MOVE_FLAG = true, PLACE_FLAG = false, USE_FLAG = false, BREAK_FLAG = false;

    PrivateRegion thisRegion = this;

    public String getName() {
        return name;
    }

    public PrivateRegion(String owner, String name, Location firstLocation, Location secondLocation) {

        this.owner = owner;
        this.name = name;
        RegionManager.regionNames.add(name);

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

        ArrayList<PrivateRegion> playersRegions;
        if (RegionManager.privateRegions.get(owner) == null) {
            playersRegions = new ArrayList<>();
        } else {
            playersRegions = RegionManager.privateRegions.get(owner);
        }
        playersRegions.add(thisRegion);
        this.addMember(owner);
        RegionManager.privateRegions.put(owner, playersRegions);

    }

    public boolean containsBlock(Location blockLocation) {
        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();
        return blockX <= this.maxX && blockX >= this.minY &&
                blockY <= this.maxY && blockY >= this.minY &&
                blockZ <= this.maxZ && blockZ >= this.minZ;
    }
    public boolean containsBlock(int blockX, int blockY, int blockZ) {
        return blockX <= this.maxX && blockX >= this.minY &&
                blockY <= this.maxY && blockY >= this.minY &&
                blockZ <= this.maxZ && blockZ >= this.minZ;
    }
    private boolean containsBlock(int blockX, int blockY, int blockZ, int maxX, int maxY, int maxZ, int minX, int minY, int minZ) {
        return blockX <= maxX && blockX >= minX &&
                blockY <= maxY && blockY >= minY &&
                blockZ <= maxZ && blockZ >= minZ;
    }

    public boolean containsPrivate(Location firstLocation, Location secondLocation) {
        int minX, minY, minZ,
                maxX, maxY, maxZ;

        boolean contains;

        if (firstLocation.getBlockX() >= secondLocation.getBlockX()) {
            maxX = firstLocation.getBlockX();
            minX = secondLocation.getBlockX();
        } else {
            minX = firstLocation.getBlockX();
            maxX = secondLocation.getBlockX();
        }
        if (firstLocation.getBlockY() >= secondLocation.getBlockY()) {
            maxY = firstLocation.getBlockY();
            minY = secondLocation.getBlockY();
        } else {
            minY = firstLocation.getBlockY();
            maxY = secondLocation.getBlockY();
        }
        if (firstLocation.getBlockZ() >= secondLocation.getBlockZ()) {
            maxZ = firstLocation.getBlockZ();
            minZ = secondLocation.getBlockZ();
        } else {
            minZ = firstLocation.getBlockZ();
            maxZ = secondLocation.getBlockZ();
        }

        ArrayList<BlockByCoords> check = new ArrayList<>();
        ArrayList<BlockByCoords> main = new ArrayList<>();

        BlockByCoords check1 = new BlockByCoords(minX, minY, minZ);
        check.add(check1);
        BlockByCoords check2 = new BlockByCoords(minX, maxY, minZ);
        check.add(check2);
        BlockByCoords check3 = new BlockByCoords(minX, minY, maxZ);
        check.add(check3);
        BlockByCoords check4 = new BlockByCoords(minX, maxY, maxZ);
        check.add(check4);
        BlockByCoords check5 = new BlockByCoords(maxX, minY, minZ);
        check.add(check5);
        BlockByCoords check6 = new BlockByCoords(maxX, maxY, minZ);
        check.add(check6);
        BlockByCoords check7 = new BlockByCoords(maxX, minY, maxZ);
        check.add(check7);
        BlockByCoords check8 = new BlockByCoords(maxX, maxY, maxZ);
        check.add(check8);

        BlockByCoords main1 = new BlockByCoords(this.minX, this.minY, this.minZ);
        main.add(main1);
        BlockByCoords main2 = new BlockByCoords(this.minX, this.maxY, this.minZ);
        main.add(main2);
        BlockByCoords main3 = new BlockByCoords(this.minX, this.minY, this.maxZ);
        main.add(main3);
        BlockByCoords main4 = new BlockByCoords(this.minX, this.maxY, this.maxZ);
        main.add(main4);
        BlockByCoords main5 = new BlockByCoords(this.maxX, this.minY, this.minZ);
        main.add(main5);
        BlockByCoords main6 = new BlockByCoords(this.maxX, this.maxY, this.minZ);
        main.add(main6);
        BlockByCoords main7 = new BlockByCoords(this.maxX, this.minY, this.maxZ);
        main.add(main7);
        BlockByCoords main8 = new BlockByCoords(this.maxX, this.maxY, this.maxZ);
        main.add(main8);

        for (int i = 0; i < 8; i++) {
            contains = containsBlock(check.get(i).getBlockX(), check.get(i).getBlockY(), check.get(i).getBlockZ());
            if (contains) {
                return true;
            }
        }
        for (int i = 0; i < 8; i++) {
            contains = containsBlock(main.get(i).getBlockX(), main.get(i).getBlockY(), main.get(i).getBlockZ(), maxX, maxY, maxZ, minX, minY, minZ);
            if (contains) {
                return true;
            }
        }

        return false;

    }
    public boolean containsPrivate(int maxX, int maxY, int maxZ, int minX, int minY, int minZ) {

        boolean contains;

        ArrayList<BlockByCoords> check = new ArrayList<>();
        ArrayList<BlockByCoords> main = new ArrayList<>();

        BlockByCoords check1 = new BlockByCoords(minX, minY, minZ);
        check.add(check1);
        BlockByCoords check2 = new BlockByCoords(minX, maxY, minZ);
        check.add(check2);
        BlockByCoords check3 = new BlockByCoords(minX, minY, maxZ);
        check.add(check3);
        BlockByCoords check4 = new BlockByCoords(minX, maxY, maxZ);
        check.add(check4);
        BlockByCoords check5 = new BlockByCoords(maxX, minY, minZ);
        check.add(check5);
        BlockByCoords check6 = new BlockByCoords(maxX, maxY, minZ);
        check.add(check6);
        BlockByCoords check7 = new BlockByCoords(maxX, minY, maxZ);
        check.add(check7);
        BlockByCoords check8 = new BlockByCoords(maxX, maxY, maxZ);
        check.add(check8);

        BlockByCoords main1 = new BlockByCoords(this.minX, this.minY, this.minZ);
        main.add(main1);
        BlockByCoords main2 = new BlockByCoords(this.minX, this.maxY, this.minZ);
        main.add(main2);
        BlockByCoords main3 = new BlockByCoords(this.minX, this.minY, this.maxZ);
        main.add(main3);
        BlockByCoords main4 = new BlockByCoords(this.minX, this.maxY, this.maxZ);
        main.add(main4);
        BlockByCoords main5 = new BlockByCoords(this.maxX, this.minY, this.minZ);
        main.add(main5);
        BlockByCoords main6 = new BlockByCoords(this.maxX, this.maxY, this.minZ);
        main.add(main6);
        BlockByCoords main7 = new BlockByCoords(this.maxX, this.minY, this.maxZ);
        main.add(main7);
        BlockByCoords main8 = new BlockByCoords(this.maxX, this.maxY, this.maxZ);
        main.add(main8);

        for (int i = 0; i < 8; i++) {
            contains = containsBlock(check.get(i).getBlockX(), check.get(i).getBlockY(), check.get(i).getBlockZ());
            if (contains) {
                return true;
            }
        }
        for (int i = 0; i < 8; i++) {
            contains = containsBlock(main.get(i).getBlockX(), check.get(i).getBlockY(), check.get(i).getBlockZ(), maxX, maxY, maxZ, minX, minY, minZ);
            if (contains) {
                return true;
            }
        }

        return false;

    }

    public int getSize() {
        return (this.maxX-this.minX+1)*(this.maxY-this.minY+1)*(this.maxZ-this.minZ+1);
    }

    public void addMember(String member) {
        if (!members.contains(member)) {
            this.members.add(member);
        }
    }

    public void addOfficer(String officer) {
        if (!members.contains(officer)) {
            this.members.add(officer);
        }
        if (!officers.contains(officer)) {
            this.officers.add(officer);
        }
    }

    public String getOwner() {
        return this.owner;
    }

    public ArrayList<String> getMembers() {
        return this.members;
    }

    public ArrayList<String> getOfficers() {
        return this.officers;
    }

}
