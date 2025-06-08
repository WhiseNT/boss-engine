package com.whisent.skill;

import com.whisent.core.BossSkill;
import com.whisent.utils.BossUtils;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MeleeAttackSkill extends BossSkill {
    private static final float DAMAGE = 5.0f; // 基础伤害值
    private int tickCount;
    public MeleeAttackSkill(String skillId) {
        super(skillId);
        this.setCurrentCooldown(0); // 初始无冷却
        this.setMaxCooldown(20);
        this.setMaxTickCount(10);
    }

    @Override
    public void castOn(Entity entity) {
        if (!(entity instanceof PathfinderMob caster)) {
            return;
        }
        LivingEntity e = BossUtils.getNearestFacingEntity( caster, 2, 120);
        if (e !=  null)  {
            super.castOn(entity);
        };
    }

    @Override
    public void preCast(Entity entity) {
        if (!(entity instanceof PathfinderMob caster)) {
            return;
        }
        entity.level().getServer().getPlayerList()
                .getPlayerByName("Dev")
                .sendSystemMessage(Component.literal("攻击前摇"+getTickCount()));
        super.preCast(entity);
    }

    @Override
    public void cast(Entity entity) {
        if (!(entity instanceof PathfinderMob caster)) {
            return;
        }
        LivingEntity bestTarget = BossUtils.getNearestFacingEntity( caster, 2, 120);
        if (bestTarget == null) {
            super.cast(entity);
            return;
        }
        bestTarget.sendSystemMessage(Component.literal("被攻击"));
        // 对目标造成伤害
        boolean attacked = bestTarget.hurt(bestTarget.damageSources().mobAttack(caster), DAMAGE);
        caster.lookAt(EntityAnchorArgument.Anchor.EYES, bestTarget.getEyePosition());
        if (attacked) {
            // 播放攻击音效
            entity.level().playSound(null, caster.getX(), caster.getY(), caster.getZ(),
                    SoundEvents.PLAYER_ATTACK_WEAK, SoundSource.HOSTILE, 1.0F, 1.0F);

            // TODO: 可选 - 添加击退、粒子效果等
        }

        super.cast(entity);
    }

    @Override
    public BossSkill copy() {
        return new MeleeAttackSkill(this.getSkillId());
    }
}
