package com.whisent.boss_engine.api;

import com.whisent.boss_engine.core.BossSkill;
import com.whisent.boss_engine.core.caps.BossEngineCapabilities;
import com.whisent.boss_engine.core.caps.IBossSkills;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.util.Map;

public class SkillCapHelper {
    // 私有构造防止实例化
    private SkillCapHelper() {}

    /**
     * 获取实体的技能Capability
     */
    public static LazyOptional<IBossSkills> getSkills(Entity entity) {
        return entity.getCapability(BossEngineCapabilities.BOSS_SKILLS_CAPABILITY);
    }

    /**
     * 安全获取技能Map（如果不存在则返回空Map）
     */
    public static Map<String, BossSkill> getSkillMap(Entity entity) {
        return getSkills(entity).resolve().map(IBossSkills::getSkills).orElse(Map.of());
    }

    /**
     * 检查实体是否拥有某个技能
     */
    public static boolean hasSkill(Entity entity, String skillId) {
        return getSkills(entity).resolve().map(skills -> skills.hasSkill(skillId)).orElse(false);
    }

    /**
     * 添加新技能到实体
     */
    public static boolean addSkill(Entity entity, BossSkill skill) {
        return getSkills(entity).map(skills -> {
            skills.addSkill(skill.getSkillId(), skill);
            return true;
        }).orElse(false);
    }

    /**
     * 从实体移除技能
     */
    public static boolean removeSkill(Entity entity, String skillId) {
        return getSkills(entity).map(skills -> {
            //skills.removeSkill(skillId);
            return true;
        }).orElse(false);
    }

    /**
     * 获取特定技能
     */
    @Nullable
    public static BossSkill getSkill(Entity entity, String skillId) {
        return getSkills(entity)
                .map(skills -> skills.getSkills().get(skillId))
                .orElse(null);
    }
}
