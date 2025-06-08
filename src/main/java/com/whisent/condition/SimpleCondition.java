package com.whisent.condition;

import com.whisent.core.conditions.ISkillTriggerCondition;
import net.minecraft.world.entity.LivingEntity;

public class SimpleCondition implements ISkillTriggerCondition {
    private String skillId;
    public SimpleCondition (String skillId) {
        this.skillId = skillId;
    }
    @Override
    public String getSkillId() {
        return skillId;
    }


    @Override
    public boolean shouldTrigger(LivingEntity entity) {
        return true;
    }
}
