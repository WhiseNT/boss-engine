package com.whisent.test.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.whisent.test.client.model.entity.BatModel;
import com.whisent.test.entity.BatEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class BatRenderer extends GeoEntityRenderer<BatEntity> {
    // 正确构造函数签名：只保留Context参数
    public BatRenderer(EntityRendererProvider.Context context) {
        super(context, new BatModel()); // 内部直接初始化模型
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(BatEntity entity) {
        return new ResourceLocation("boss_engine","textures/entity/bat.png");
    }
}
