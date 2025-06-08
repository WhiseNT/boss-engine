package com.whisent.skill;

import com.whisent.core.BossSkill;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.level.Level;

public class SnowBallSkill extends BossSkill {
    private static final int COOLDOWN = 20; // 1秒冷却 (20 ticks = 1秒)
    private static final float VELOCITY = 1.5f; // 发射速度

    public SnowBallSkill(String skillId) {
        super(skillId);
        this.setCurrentCooldown(0);
        this.setMaxTickCount(5);
        this.setMaxCooldown(20);

    }


    @Override
    public void preCast(Entity entity) {
        super.preCast(entity);
    }

    @Override
    public void cast(Entity entity) {
        System.out.println( "SnowBallSkill cast");
        Entity caster = entity;
        Level level = caster.level();
        //caster.moveTo( caster.getX() + 0.5, caster.getY(), caster.getZ());
        // 创建并发射原版雪球
        Snowball snowball = new Snowball(level, (LivingEntity) caster);
        snowball.shoot(
                caster.getLookAngle().x * VELOCITY,
                caster.getLookAngle().y * VELOCITY,
                caster.getLookAngle().z * VELOCITY,
                VELOCITY * 1.5f, // 速度系数
                1.0f // 散射度
        );

        level.addFreshEntity(snowball);

        // 播放发射音效
        level.playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                SoundEvents.SNOWBALL_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

        super.afterCast(entity);
    }

    @Override
    public void afterCast(Entity entity) {
        super.afterCast(entity);
    }

    @Override
    public BossSkill copy() {
        return new SnowBallSkill(this.getSkillId());
    }
}
