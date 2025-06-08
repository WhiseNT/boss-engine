package com.whisent.core.registries;

import com.whisent.core.BossSkill;

public interface BossSkillFactory {
    BossSkill create(String id);
}
