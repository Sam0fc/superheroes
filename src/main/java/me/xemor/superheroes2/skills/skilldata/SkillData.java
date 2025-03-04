package me.xemor.superheroes2.skills.skilldata;

import me.xemor.skillslibrary2.conditions.ConditionList;
import me.xemor.superheroes2.Superheroes2;
import me.xemor.superheroes2.skills.Skill;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

public abstract class SkillData {

    private final int skill;
    private final ConfigurationSection configurationSection;
    private ConditionList conditions;

    public SkillData(int skill, ConfigurationSection configurationSection) {
        this.skill = skill;
        this.configurationSection = configurationSection;
        ConfigurationSection conditions = configurationSection.getConfigurationSection("conditions");
        if (conditions != null && Superheroes2.getInstance().hasSkillsLibrary()) {
            this.conditions = new ConditionList(conditions);
        }
    }

    public boolean areConditionsTrue(Player player, Object... objects) {
        if (conditions == null) return true;
        return conditions.ANDConditions(player, false, objects);
    }

    @Nullable
    public static SkillData create(int skill, ConfigurationSection configurationSection) {
        try {
            return Skill.getClass(skill).getConstructor(int.class, ConfigurationSection.class).newInstance(skill, configurationSection);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ConfigurationSection getData() {
        return configurationSection;
    }

    public int getSkill() {
        return skill;
    }

}
