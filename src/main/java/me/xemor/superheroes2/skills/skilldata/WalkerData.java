package me.xemor.superheroes2.skills.skilldata;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WalkerData extends SkillData {

    private Random random = new Random();
    private List<Material> blocksToPlace = new ArrayList<>();
    private HashSet<Material> blocksToReplace = new HashSet<>();
    private HashSet<Material> blocksToPlaceOn = new HashSet<>();
    private boolean isSneaking;
    private boolean blocksDrop;
    private boolean shouldRevert;
    private boolean aboveFloor;
    private boolean canPlaceFloating;
    private long revertsAfter;

    public WalkerData(int skill, ConfigurationSection configurationSection) {
        super(skill, configurationSection);
        List<String> blocksToReplaceStr = configurationSection.getStringList("blocksToReplace");
        for (String blockToReplaceStr : blocksToReplaceStr) {
            blocksToReplace.add(Material.valueOf(blockToReplaceStr));
        }
        List<String> blocksToPlaceStr = configurationSection.getStringList("blocksToPlace");
        for (String blockToPlaceStr : blocksToPlaceStr) {
            blocksToPlace.add(Material.valueOf(blockToPlaceStr));
        }
        List<String> blocksToPlaceOnStr = configurationSection.getStringList("blocksToPlaceOn");
        for (String blockToPlaceStr : blocksToPlaceOnStr) {
            blocksToPlaceOn.add(Material.valueOf(blockToPlaceStr));
        }
        isSneaking = configurationSection.getBoolean("isSneaking", false);
        blocksDrop = configurationSection.getBoolean("blocksDrop", true);
        shouldRevert = configurationSection.getBoolean("shouldRevert", false);
        revertsAfter = Math.round(configurationSection.getDouble("revertsAfter", 15) * 20);
        aboveFloor = configurationSection.getBoolean("aboveFloor", false);
        canPlaceFloating = configurationSection.getBoolean("canPlaceFloating", true);
    }

    public Material getReplacementBlock() {
        return blocksToPlace.get(random.nextInt(blocksToPlace.size()));
    }

    public boolean shouldReplace(Material material) {
        return blocksToReplace.contains(material);
    }

    public boolean isSneaking() {
        return isSneaking;
    }

    public boolean doesBlockDrop() {
        return blocksDrop;
    }

    public boolean shouldRevert() {
        return shouldRevert;
    }

    public long getRevertsAfter() {
        return revertsAfter;
    }

    public boolean isAboveFloor() {
        return aboveFloor;
    }

    public boolean canPlaceFloating() {
        return canPlaceFloating;
    }

    public boolean canPlaceOn(Material type) {
        return blocksToPlaceOn.size() == 0 || blocksToPlaceOn.contains(type);
    }
}
