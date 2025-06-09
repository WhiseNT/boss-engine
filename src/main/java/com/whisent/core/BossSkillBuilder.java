package com.whisent.core;

import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import net.minecraft.resources.ResourceLocation;

public class BossSkillBuilder  extends BuilderBase<BossSkill> {


    public BossSkillBuilder(ResourceLocation i) {
        super(i);
    }

    @Override
    public RegistryInfo getRegistryType() {
        return null;
    }

    @Override
    public BossSkill createObject() {
        return null;
    }
}
