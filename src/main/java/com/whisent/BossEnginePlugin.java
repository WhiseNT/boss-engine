package com.whisent;

import com.whisent.core.BossSkill;
import com.whisent.core.BossSkillBuilder;
import com.whisent.core.registries.BossSkillRegistry;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.registry.RegistryInfo;
import dev.latvian.mods.kubejs.script.BindingsEvent;

public class BossEnginePlugin extends KubeJSPlugin {
    public static final RegistryInfo<BossSkill> BOSS_SKILL = RegistryInfo.of
            (BossSkillRegistry.BOSS_SKILLS, BossSkill.class);
    @Override
    public void init() {
        BOSS_SKILL.addType("basic",
                BossSkillBuilder.class,BossSkillBuilder::new);
        super.init();
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        super.registerBindings(event);
    }
}
