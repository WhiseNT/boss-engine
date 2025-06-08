package com.whisent.core;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class BossStage {
     protected final ResourceLocation resorceLoaction;
     protected int duration;
     protected int elapsedTicks;
     protected boolean isActive;


     public BossStage (ResourceLocation rl) {
        this.resorceLoaction = rl;
     }
     public void setEntity() {
     }
     public ResourceLocation getStageId() {
          return resorceLoaction;
     }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setElapsedTicks(int elapsedTicks) {
        this.elapsedTicks = elapsedTicks;
    }

    public int getElapsedTicks() {
        return elapsedTicks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void init(LivingEntity entity) {

    }


    public void tick(LivingEntity entity) {
     if (isActive) {
         this.elapsedTicks++;
         if (this.elapsedTicks >= this.duration) {
              this.end(entity);
         }
     }
    }

    public void end(LivingEntity entity) {
     isActive = false;
    }
    public BossStage copy() {
         return new BossStage(this.getStageId());
    }
}
