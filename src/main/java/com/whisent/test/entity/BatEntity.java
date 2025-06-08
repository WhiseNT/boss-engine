package com.whisent.test.entity;

import com.google.common.collect.ImmutableList;
import com.whisent.BossEngine;
import com.whisent.condition.MaxHealthCondition;
import com.whisent.condition.SimpleCondition;
import com.whisent.core.BossSkillManager;
import com.whisent.core.caps.IBossSkills;
import com.whisent.core.caps.BossEngineCapabilities;
import com.whisent.core.registries.BossSkillRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BatEntity extends PathfinderMob implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean isFlying = false;
    private BossSkillManager manager;

    public BatEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
        this.manager = new BossSkillManager(this);

        this.manager.addTriggerCondition(new MaxHealthCondition("snowball"));
        this.manager.addTriggerCondition(new SimpleCondition("melee_attack"));
    }
    protected void registerGoals() {
        //this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 12.0F));
        super.registerGoals();
    }
    public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            this.isFlying = !this.isFlying;
        }
        this.getPersistentData().putString("test","111");
        System.out.println(BossSkillRegistry
                .getSkill(new ResourceLocation(BossEngine.MODID,"snowball")).get());
        return super.interactAt(player, hitPos, hand);
    }

    public static AttributeSupplier setAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 16.0) // 必须添加此属性
                .add(Attributes.ATTACK_KNOCKBACK, 0.5) // 可选但推荐
                .build();
    }
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller",0,this::predicate));
    }
    private <T extends GeoAnimatable>PlayState predicate(AnimationState<T> state) {
        state.getController().setAnimation(RawAnimation.begin());
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

    }

    // 辅助方法
    public LazyOptional<IBossSkills> getBossSkills() {
        return getCapability(BossEngineCapabilities.BOSS_SKILLS_CAPABILITY);
    }

    @Override
    public void tick() {
        super.tick();
        this.manager.tick();
    }
}
