package com.whisent.test.register;

import com.whisent.test.entity.BatEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "boss-engine");
    public static final RegistryObject<EntityType<BatEntity>> BAT = ENTITIES.register("bat",
            ()-> EntityType.Builder.of(BatEntity::new, MobCategory.CREATURE)
                    .sized(1f,2f)
                    .build(new ResourceLocation("boss_engine:bat").toString()));


    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

}
