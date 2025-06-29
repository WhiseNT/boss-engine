package com.whisent.boss_engine.core.caps;

import com.whisent.boss_engine.core.BossSkill;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public interface IBossSkills extends INBTSerializable<CompoundTag> {
    Map<String, BossSkill> getSkills();
    boolean hasSkill(String skillName);
    boolean addSkill(String skillName, BossSkill skill);
    void setSkills(Map<String, BossSkill> skills);
}
