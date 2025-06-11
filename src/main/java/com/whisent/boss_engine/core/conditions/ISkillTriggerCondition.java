package com.whisent.boss_engine.core.conditions;

import net.minecraft.world.entity.LivingEntity;

public interface ISkillTriggerCondition {
    String getSkillId();
    boolean shouldTrigger(LivingEntity entity);

}
