package com.whisent.boss_engine.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class BossUtils {
    public static List<LivingEntity> getFacingEntities(LivingEntity caster,float distance,float angle) {
        Level level = caster.level();
        Vec3 lookAngle = caster.getLookAngle().normalize();
        Vec3 casterEyePos = caster.getEyePosition();
        List<LivingEntity> potentialTargets = level.getEntitiesOfClass(LivingEntity.class,
                caster.getBoundingBox().inflate(distance),
                target -> target != caster && target.isAlive());
        List<LivingEntity> finalEntities = new ArrayList<>();
        for (LivingEntity target : potentialTargets) {
            if (!target.isAlive() || !caster.hasLineOfSight(target)) continue;
            Vec3 toTarget = target.getEyePosition().subtract(casterEyePos).normalize();
            double angleCos = toTarget.dot(lookAngle);
            double angleDeg = Math.toDegrees(Math.acos(angleCos));
            if (angleDeg <= angle / 2.0F) {
                finalEntities.add(target);
            }
        }
        return finalEntities;
    }
    public static LivingEntity getNearestFacingEntity(LivingEntity caster,float distance,float angle) {
        List<LivingEntity> entities = getFacingEntities(caster, distance, angle);
        double finalDistance = Double.MAX_VALUE;
        LivingEntity finalEntity = null;
        for (LivingEntity entity : entities) {
            double dis = caster.distanceToSqr(entity);
            if (dis < finalDistance) {
                finalDistance = dis;
                finalEntity = entity;
            }
        }
        if (finalEntity != null) {
            return finalEntity;
        }
        return null;
    }


}
