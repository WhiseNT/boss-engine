package com.whisent.boss_engine.core.caps;

import com.whisent.boss_engine.BossEngine;
import com.whisent.boss_engine.test.entity.BatEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BossEngine.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BossEngineCapabilities {
    // 定义Capability
    public static final Capability<IBossSkills> BOSS_SKILLS_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>(){});
    // 注册Capability
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IBossSkills.class);
    }

    // 附加Capability到实体
    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof BatEntity) {
            System.out.println("附加Capability到实体");
            event.addCapability(
                    BossSkillCapabilityProvider.ID,
                    new BossSkillCapabilityProvider((LivingEntity) event.getObject())
            );
        }
    }
}
