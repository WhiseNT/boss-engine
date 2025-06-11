package com.whisent.boss_engine.core;

import net.minecraft.world.entity.Entity;

public class CustomBossSkill extends BossSkill{
    BossSkillBuilder.PreCastCallback  preCastCallback = null;
    public BossSkillBuilder.CastOnCallback castOnCallback = null;
    public BossSkillBuilder.CastCallback castCallback = null;
    public BossSkillBuilder.AfterCastCallback afterCastCallback = null;
    public CustomBossSkill(String skillId,
                           BossSkillBuilder.PreCastCallback preCastCallback,
                           BossSkillBuilder.CastOnCallback castOnCallback,
                           BossSkillBuilder.CastCallback castCallback,
                           BossSkillBuilder.AfterCastCallback afterCastCallback
    ) {
        super(skillId);
    }

    @Override
    public void trigger(Entity entity) {
        if (castOnCallback != null) {
            castOnCallback.castOn(this);
        }
        super.trigger(entity);
    }

    @Override
    public void cast(Entity entity) {
        if (castCallback != null) {
            castCallback.cast(this);
        }
        super.cast(entity);
    }

    @Override
    public void prepareToCast(Entity entity) {
        if (preCastCallback != null) {
            preCastCallback.preCast(this);
        }
        super.prepareToCast(entity);
    }

    @Override
    public void afterCast(Entity entity) {
        if (afterCastCallback != null) {
            afterCastCallback.afterCast(this);
        }
        super.afterCast(entity);
    }

    @Override
    public BossSkill copy() {
        return new CustomBossSkill(this.getSkillId(),this.preCastCallback,
                this.castOnCallback,this.castCallback,this.afterCastCallback);
    }
}
