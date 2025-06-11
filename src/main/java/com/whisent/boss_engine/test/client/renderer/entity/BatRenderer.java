package com.whisent.boss_engine.test.client.renderer.entity;

import com.whisent.boss_engine.test.client.model.entity.BatModel;
import com.whisent.boss_engine.test.entity.BatEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

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
