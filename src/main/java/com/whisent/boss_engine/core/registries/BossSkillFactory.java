package com.whisent.boss_engine.core.registries;

import com.whisent.boss_engine.core.BossSkill;

public interface BossSkillFactory {
    BossSkill create(String id);
}
