package com.whisent;

import com.mojang.logging.LogUtils;
import com.whisent.core.registries.BossSkillRegistry;
import com.whisent.skill.ModSkills;
import com.whisent.test.client.renderer.entity.BatRenderer;
import com.whisent.test.entity.BatEntity;
import com.whisent.test.register.EntityRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Optional;
import java.util.function.Predicate;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BossEngine.MODID)
public class BossEngine {

    public static final String MODID = "boss_engine";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    //modEventBus.addListener(this::onAttachCapabilitiesPre);
    public static final DeferredRegister<MemoryModuleType<?>> MEMORIES =
            DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, MODID);

    public static final DeferredRegister<SensorType<?>> SENSORS =
            DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, MODID);
    // 注册一个记忆类型（存储攻击目标）
    public static final RegistryObject<MemoryModuleType<LivingEntity>> TARGET_MEMORY =
            MEMORIES.register("target", () -> new MemoryModuleType<>(Optional.empty()));

    public BossEngine() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(BossSkillRegistry::onNewRegistry);
        modEventBus.addListener(this::commonSetup);
        EntityRegistry.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ModSkills.SKILLS.register(modEventBus);
        MEMORIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        SENSORS.register(FMLJavaModLoadingContext.get().getModEventBus());
        modEventBus.addListener(EventPriority.NORMAL, false, EntityAttributeCreationEvent.class, event -> {
            event.put(EntityRegistry.BAT.get(), BatEntity.setAttributes());
        });

    }
    public static void registerSkills() {

    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(EntityRegistry.BAT.get(), BatRenderer::new);;
        }
    }
}
