package com.whisent.core;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class BossSkill {
    private LivingEntity owner;
    private String skillId;
    private int currentCooldown; // 当前冷却时间（tick）
    private int maxCooldown;
    private boolean isEnabled;   // 是否启用
    private int chargeCount;     // 充能数
    private int effectStacks;    // 效果层数
    private int tickCount;      //施法时间
    private int maxTickCount;  //施法总周期
    private boolean casting;
    private ArrayList<Consumer> callbacks = new ArrayList<>();

    public BossSkill(String skillId) {
        this.skillId = skillId;
        this.currentCooldown = 0;
        this.isEnabled = true;
        this.chargeCount = 1;
        this.effectStacks = 0;
        this.casting = false;
    }

    // Getters and Setters
    public String getSkillId() {
        return skillId;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public BossSkill setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
        return this;
        }
    public boolean isEnabled() {
        return isEnabled;
    }
    public BossSkill setEnabled(boolean enabled) {
        isEnabled = enabled;
        return this;
    }
    public int getChargeCount() {
        return chargeCount;
    }
    public BossSkill setChargeCount(int chargeCount) {
        this.chargeCount = chargeCount;
        return this;
    }
    public int getEffectStacks() {
        return effectStacks;
    }
    public BossSkill setEffectStacks(int effectStacks) {
        this.effectStacks = effectStacks;
        return this;
    }
    public void castOn(Entity entity) {
        this.setCasting(true);
    }
    public void preCast(Entity entity) {
        if (isCasting()) {
            System.out.println( "Pre-cast for " + skillId);
            tickCount+=1;
            System.out.println( "前摇 " + getTickCount());
            if (getTickCount() >= getMaxTickCount()) {
                cast(entity);
            }
        }
    }
    public void cast(Entity entity) {
        for (Consumer callback : this.callbacks) {
            try {
                callback.accept(this);
            } catch (Exception e) {
                System.err.println("Skill callback failed for " + skillId);
                e.printStackTrace();
            }
        }
        afterCast(entity);
    }
    public void afterCast(Entity entity) {
        this.setTickCount(0);
        this.setCasting(false);
        this.setCurrentCooldown(this.getMaxCooldown());
    }
    public boolean canCast () {
        return currentCooldown <= 0;
    }

    public BossSkill addCallback(Consumer<BossSkill> callback) {
        this.callbacks.add(Objects.requireNonNull(callback, "Callback cannot be null"));
        return this;
    }

    // 添加回调移除方法
    public boolean removeCallback(Consumer<BossSkill> callback) {
        return this.callbacks.remove(callback);
    }

    public void updateCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }
    public abstract BossSkill copy();
    public void serializeInto(CompoundTag nbt) {
        nbt.putString("id", this.skillId);
        nbt.putInt("cooldown", this.currentCooldown);
        nbt.putBoolean("isEnabled", this.isEnabled);
        nbt.putInt("charge", this.chargeCount);
        nbt.putInt("stacks", this.effectStacks);
        // 其他需要保存的属性...
    }

    public void deserializeFrom(CompoundTag nbt) {
        this.skillId = nbt.getString("id");
        this.currentCooldown = nbt.getInt("cooldown");
        this.isEnabled = nbt.getBoolean("isEnabled");
        this.chargeCount = nbt.getInt("charge");
        this.effectStacks = nbt.getInt("stacks");
        // 其他需要加载的属性...
    }

    public void setOwner (LivingEntity entity) {
        this.owner = entity;
    }

    public LivingEntity getOwner () {
        return this.owner;
    }

    public int getMaxCooldown() {
        return maxCooldown;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void setMaxTickCount(int maxTickCount) {
        this.maxTickCount = maxTickCount;
    }

    public int getMaxTickCount() {
        return maxTickCount;
    }

    public void setMaxCooldown(int maxCooldown) {
        this.maxCooldown = maxCooldown;
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    public boolean isCasting() {
        return casting;
    }

    public void setCasting(boolean casting) {
        this.casting = casting;
    }
}
