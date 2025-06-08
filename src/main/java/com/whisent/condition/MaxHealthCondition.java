package com.whisent.condition;

import com.whisent.core.conditions.ISkillTriggerCondition;
import net.minecraft.world.entity.LivingEntity;

public class MaxHealthCondition implements ISkillTriggerCondition {
    private String skillId;
    public MaxHealthCondition (String skillId) {
        this.skillId = skillId;
    }
    @Override
    public String getSkillId() {
        return this.skillId;
    }

    @Override
    public boolean shouldTrigger(LivingEntity entity) {
        return this.checkHealth(entity);
    }
    private boolean checkHealth(LivingEntity entity) {
        return entity.getHealth() <= entity.getMaxHealth()/2;
    }
}
