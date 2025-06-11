package com.whisent.boss_engine.core;

import com.whisent.boss_engine.BossEnginePlugin;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;

public class BossSkillBuilder  extends BuilderBase<BossSkill> {
    public String skillId = "default";
    public PreCastCallback preCastCallback;
    public CastOnCallback castOnCallback;
    public CastCallback castCallback;
    public AfterCastCallback afterCastCallback;

    public BossSkillBuilder(ResourceLocation i) {
        super(i);
    }
    public void preCast(PreCastCallback callback) {
        preCastCallback = callback;
    }
    public void castOn(CastOnCallback callback) {
        castOnCallback = callback;
    }
    public void cast(CastCallback callback) {
        castCallback = callback;
    }
    public void afterCast(AfterCastCallback callback) {
        afterCastCallback = callback;
    }

    @Override
    public RegistryInfo getRegistryType() {
        return BossEnginePlugin.BOSS_SKILL;
    }

    @Override
    public BossSkill createObject() {
        return new CustomBossSkill(skillId,preCastCallback,
                castOnCallback,castCallback,afterCastCallback) ;
    }

    @FunctionalInterface
    public interface PreCastCallback {
        void preCast(BossSkill skill);
    }
    @FunctionalInterface
    public interface CastOnCallback {
        void castOn(BossSkill skill);
    }
    @FunctionalInterface
    public interface CastCallback {
        void cast(BossSkill skill);
    }
    @FunctionalInterface
    public interface AfterCastCallback {
        void afterCast(BossSkill skill);
    }
}

