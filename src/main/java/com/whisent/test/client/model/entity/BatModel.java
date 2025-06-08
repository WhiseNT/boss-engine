package com.whisent.test.client.model.entity;

import com.whisent.test.entity.BatEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.GeoModel;

public class BatModel extends DefaultedEntityGeoModel<BatEntity> {
    public BatModel() {
        super(new ResourceLocation("boss_engine", "bat"), true);

    }

//    @Override
//    public ResourceLocation getModelResource(BatEntity batEntity) {
//        return new ResourceLocation("boss_engine", "geo/entity/bat.geo.json");
//    }
//
//    @Override
//    public ResourceLocation getTextureResource(BatEntity batEntity) {
//        return  new ResourceLocation("boss_engine", "textures/entity/bat.png");
//    }
//
//    @Override
//    public ResourceLocation getAnimationResource(BatEntity batEntity) {
//        return new ResourceLocation("boss_engine", "animations/entity/bat.animation.json");
//    }
}
