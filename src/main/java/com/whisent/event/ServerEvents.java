package com.whisent.event;

import com.whisent.core.caps.BossEngineCapabilities;
import com.whisent.core.caps.BossSkills;
import com.whisent.skill.SnowBallSkill;
import com.whisent.test.entity.BatEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.whisent.core.caps.BossEngineCapabilities.BOSS_SKILLS_CAPABILITY;

public class ServerEvents {
    @SubscribeEvent
    public static void onBatSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof BatEntity livingEntity) {
            livingEntity.getCapability(BOSS_SKILLS_CAPABILITY).ifPresent(skills -> {
                if (skills instanceof BossSkills bossSkills) {
                    bossSkills.initialize();
                }
            });
        }
    }
}
