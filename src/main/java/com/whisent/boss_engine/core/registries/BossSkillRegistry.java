package com.whisent.boss_engine.core.registries;

import com.whisent.boss_engine.BossEngine;
import com.whisent.boss_engine.core.BossSkill;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

import java.util.Optional;

public class BossSkillRegistry {
    // 定义技能注册表键
    public static final ResourceKey<Registry<BossSkill>> BOSS_SKILLS =
            ResourceKey.createRegistryKey(
                    new ResourceLocation(BossEngine.MODID, "boss_skills"));
    public static final DeferredRegister<BossSkill> DEFERRED_REGISTER =
            DeferredRegister.create(BOSS_SKILLS, BossEngine.MODID);
    // 获取注册表实例的便捷方法
    public static ForgeRegistry<BossSkill> getRegistry() {
        return RegistryManager.ACTIVE.getRegistry(BOSS_SKILLS);
    }


    // 在ModSetup中注册自定义注册表
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onNewRegistry(NewRegistryEvent event) {
        event.create(new RegistryBuilder<BossSkill>()
                .setName(BOSS_SKILLS.location())
                .setDefaultKey(new ResourceLocation(BossEngine.MODID, "default"))
                .allowModification()
                .setMaxID(Integer.MAX_VALUE - 1)
        );
        // 注册完成后初始化DEFERRED_REGISTER
        DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // 注册技能工厂的辅助方法
    public static void registerFactory(String id, BossSkillFactory factory) {
        ResourceLocation loc = new ResourceLocation(BossEngine.MODID, id);
        getRegistry().register(loc, factory.create(loc.toString()));
    }

    // 获取技能的方法
    public static Optional<BossSkill> getSkill(ResourceLocation id) {
        return Optional.ofNullable(getRegistry().getValue(id));
    }

    public static BossSkill getOrThrow(ResourceLocation id) {
        return getSkill(id).orElseThrow(() ->
                new IllegalArgumentException("Unknown skill: " + id));
    }
}
