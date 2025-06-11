package com.whisent.boss_engine;

import com.whisent.boss_engine.core.BossSkill;
import com.whisent.boss_engine.core.BossSkillBuilder;
import com.whisent.boss_engine.core.registries.BossSkillRegistry;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.Lazy;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class BossEnginePlugin extends KubeJSPlugin {
    public static final RegistryInfo<BossSkill> BOSS_SKILL =
            RegistryInfo.of(BossSkillRegistry.BOSS_SKILLS,BossSkill.class);
    @Override
    public void init() {
        BOSS_SKILL.addType("basic",
                BossSkillBuilder.class,BossSkillBuilder::new);
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
    }
}
