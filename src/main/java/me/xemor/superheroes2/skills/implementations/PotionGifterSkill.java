package me.xemor.superheroes2.skills.implementations;

import me.xemor.superheroes2.SkillCooldownHandler;
import me.xemor.superheroes2.Superhero;
import me.xemor.superheroes2.Superheroes2;
import me.xemor.superheroes2.data.HeroHandler;
import me.xemor.superheroes2.skills.Skill;
import me.xemor.superheroes2.skills.skilldata.PotionGifterSkillData;
import me.xemor.superheroes2.skills.skilldata.SkillData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.Collection;

public class PotionGifterSkill extends SkillImplementation {

    SkillCooldownHandler skillCooldownHandler = new SkillCooldownHandler();

    public PotionGifterSkill(HeroHandler heroHandler) {
        super(heroHandler);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent e) {
        Player player = e.getPlayer();
        Superhero superhero = heroHandler.getSuperhero(player);
        Collection<SkillData> skillDatas = superhero.getSkillData(Skill.getSkill("POTIONGIFTER"));
        for (SkillData skillData : skillDatas) {
            PotionGifterSkillData gifterData = (PotionGifterSkillData) skillData;
            Entity entity = e.getRightClicked();
            if (skillCooldownHandler.isCooldownOver(gifterData, player.getUniqueId())) {
                if (entity instanceof LivingEntity) {
                    LivingEntity lEntity = (LivingEntity) entity;
                    if (skillData.areConditionsTrue(player, lEntity)) {
                        World world = lEntity.getWorld();
                        world.spawnParticle(Particle.VILLAGER_HAPPY, lEntity.getLocation().add(0, 1, 0), 1);
                        lEntity.addPotionEffect(gifterData.getPotionEffect());
                        skillCooldownHandler.startCooldown(gifterData, gifterData.getCooldown(), player.getUniqueId());
                        Component giverMessage = MiniMessage.miniMessage().deserialize(gifterData.getGiverMessage(), Placeholder.unparsed("player",player.getDisplayName()));
                        Superheroes2.getBukkitAudiences().player(player).sendMessage(giverMessage);
                        if (lEntity instanceof Player) {
                            Player receiver = (Player) lEntity;
                            Component receiverMessage = MiniMessage.miniMessage().deserialize(gifterData.getReceiverMessage(),
                                    Placeholder.unparsed("gifter", player.getDisplayName()),
                                    Placeholder.unparsed("player", receiver.getDisplayName()));
                            Superheroes2.getBukkitAudiences().player(player).sendMessage(receiverMessage);
                        }
                    }
                }
            }
        }
    }
}
