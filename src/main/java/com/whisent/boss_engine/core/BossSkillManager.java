package com.whisent.boss_engine.core;

import com.whisent.boss_engine.core.caps.BossEngineCapabilities;
import com.whisent.boss_engine.core.caps.IBossSkills;
import com.whisent.boss_engine.core.conditions.ISkillTriggerCondition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BossSkillManager {
    private final LivingEntity owner;
    private final List<ISkillTriggerCondition> conditions = new ArrayList<>();
    private final IBossSkills skills;

    public BossSkillManager(LivingEntity owner) {
        this.owner = owner;
        initNBT();
        this.skills =
                owner.getCapability(BossEngineCapabilities.BOSS_SKILLS_CAPABILITY)
                        .resolve().orElseThrow();

    }

    public Map<String, BossSkill> getSkills() {
        return skills.getSkills();
    }

    public void addSkill(BossSkill skill) {
        skills.addSkill(skill.getSkillId(), skill);
    }

    public void addTriggerCondition(ISkillTriggerCondition condition) {
        conditions.add(condition);
    }
    public void tick() {
        if (owner.level().isClientSide) return;

        for (ISkillTriggerCondition condition : conditions) {
            if (condition.shouldTrigger(owner)) {
                BossSkill skill = skills.getSkills().get(condition.getSkillId());
                if (skill != null && skill.canCast() && skill.isEnabled()) {
                    skill.trigger(owner);
                    //System.out.println("释放");
                }
            }
        }
        for (BossSkill skill : skills.getSkills().values()) {
            if (skill.isPreparing()) {
                skill.prepareToCast(owner);
            } else if (skill.isCasting()) {
                skill.castTick(owner);
            }
        }
        skills.getSkills().values().forEach(BossSkill::updateCooldown);
    }
    public void initNBT() {
        if (!this.owner.getPersistentData().contains("bossEngineData")) {
            CompoundTag nbt = new CompoundTag();
            nbt.putString("state","IDLE");
            nbt.putString("stage","default");
            nbt.putInt("difficulty",0);
            this.owner.getPersistentData().put("bossEngineData",nbt);
        }
    }

    public String getState() {
        return this.owner.getPersistentData()
                .getCompound("bossEngineData")
                .getString("state");
    }
    public String getStage() {
        return this.owner.getPersistentData()
                .getCompound("bossEngineData")
                .getString("stage");
    }
    public int getDifficulty() {
        return this.owner.getPersistentData()
            .getCompound("bossEngineData")
            .getInt("difficulty");
    }
    public void setState(String state) {
        this.owner.getPersistentData()
                .getCompound("bossEngineData")
                .putString("state",state);
    }
    public void setStage(String stage) {
        this.owner.getPersistentData()
                .getCompound("bossEngineData")
                .putString("stage",stage);
    }
    public void setDifficulty(int difficulty) {
    this.owner.getPersistentData()
            .getCompound("bossEngineData")
            .putInt("difficulty",difficulty);
    }
}
