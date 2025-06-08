package com.whisent.core.caps;

import com.whisent.BossEngine;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BossSkillCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation ID =
            new ResourceLocation(BossEngine.MODID, "boss_skills");

    private final LazyOptional<IBossSkills> instance =
            LazyOptional.of(this::createBossSkills);
    private final LivingEntity owner;

    public BossSkillCapabilityProvider(LivingEntity owner) {
        this.owner = owner;

    }
    private IBossSkills createBossSkills() {
        BossSkills skills = new BossSkills(this.owner);
        skills.initialize(); // 显式初始化
        return skills;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == BossEngineCapabilities.BOSS_SKILLS_CAPABILITY) {
            return BossEngineCapabilities.BOSS_SKILLS_CAPABILITY
                    .orEmpty(cap, instance.cast());
        }
        return LazyOptional.empty();
    }


    @Override
    public CompoundTag serializeNBT() {
        return instance.map(IBossSkills::serializeNBT).orElse(new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.ifPresent(c -> c.deserializeNBT(nbt));
    }
}
