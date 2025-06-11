package com.whisent.boss_engine.skill;

import com.whisent.boss_engine.BossEngine;
import com.whisent.boss_engine.core.BossSkill;
import com.whisent.boss_engine.core.registries.BossSkillRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSkills {
    public static final DeferredRegister<BossSkill> SKILLS =
            DeferredRegister.create(BossSkillRegistry.BOSS_SKILLS, BossEngine.MODID);

    public static final RegistryObject<BossSkill> SNOWBALL =
            SKILLS.register("snowball", () -> new SnowBallSkill( "snowball"));
    public static final RegistryObject<BossSkill>  MELEE_ATTACK =
            SKILLS.register("melee_attack", () -> new MeleeAttackSkill( "melee_attack"));
    public ModSkills(IEventBus modEventBus) {
        SKILLS.register(modEventBus);
    }

}
