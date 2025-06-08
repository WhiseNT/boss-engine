package com.whisent.core.caps;

import com.whisent.BossEngine;
import com.whisent.core.BossSkill;
import com.whisent.core.registries.BossSkillRegistry;
import com.whisent.skill.SnowBallSkill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BossSkills implements IBossSkills{
    private final Map<String, BossSkill> skills = new HashMap<>();
    private final LivingEntity owner;
    private boolean initialized = false;

    public BossSkills(LivingEntity owner) {
        this.owner = owner;
    }
    public void initialize() {
        if (!initialized) {
            // 使用注册表获取技能实例
            BossSkillRegistry.getSkill(new ResourceLocation(BossEngine.MODID, "snowball"))
                    .ifPresent(skill -> this.addSkill("snowball", skill));
            BossSkillRegistry.getSkill(new ResourceLocation(BossEngine.MODID, "melee_attack"))
                    .ifPresent(skill -> this.addSkill("melee_attack", skill));
            initialized = true;
        }
    }
    // 新增方法：施放特定技能
    public boolean castSkill(String skillId) {
        BossSkill skill = skills.get(skillId);
        if (skill != null && skill.getCurrentCooldown() <= 0) {
            skill.cast(this.owner); // 传入持有者
            return true;
        }
        return false;
    }

    // 新增方法：获取所有技能
    public Map<String, BossSkill> getAllSkills() {
        return Collections.unmodifiableMap(this.skills);
    }
    @Override
    public Map<String, BossSkill> getSkills() {
        return Collections.unmodifiableMap(skills);
    }
    public boolean hasSkill(String skillName) {
        return this.skills.containsKey(skillName);
    }

    @Override
    public boolean addSkill(String skillName, BossSkill skill) {
        if (this.hasSkill(skillName)) {
            return false;
        }
        this.skills.put(skillName, skill.copy());
        System.out.println("添加技能：" +this.skills);
        System.out.println("添加技能：" + skillName);
        return true;
    }

    @Override
    public void setSkills(Map<String, BossSkill> skills) {
        this.skills.clear();
        this.skills.putAll(skills);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CompoundTag skillsNbt = new CompoundTag();

        for (Map.Entry<String, BossSkill> entry : skills.entrySet()) {
            CompoundTag skillNbt = new CompoundTag();
            entry.getValue().serializeInto(skillNbt);
            skillsNbt.put(entry.getKey(), skillNbt);
        }

        nbt.put("skills", skillsNbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (!nbt.contains("skills")) return;

        CompoundTag skillsNbt = nbt.getCompound("skills");
        this.skills.clear();

        for (String key : skillsNbt.getAllKeys()) {
            CompoundTag skillNbt = skillsNbt.getCompound(key);
            BossSkill template = BossSkillRegistry.getSkill(
                    new ResourceLocation(BossEngine.MODID, skillNbt.getString("id"))).get();

            BossSkill skill = template.copy(); // 关键！必须创建新实例
            skill.deserializeFrom(skillNbt); // 让技能自己处理反序列化

            this.skills.put(key, skill);
        }
    }
}
