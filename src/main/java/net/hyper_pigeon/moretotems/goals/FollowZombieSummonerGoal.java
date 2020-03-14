package net.hyper_pigeon.moretotems.goals;

import net.hyper_pigeon.moretotems.SummonedZombieEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.LandPathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class FollowZombieSummonerGoal extends Goal {

    private final SummonedZombieEntity minion;
    private LivingEntity summoner;
    private final WorldView world;
    private final double field_6442;
    private final EntityNavigation navigation;
    private int field_6443;
    private final float maxDistance;
    private final float minDistance;
    private float field_6447;
    private final boolean field_21078;

    public FollowZombieSummonerGoal(SummonedZombieEntity minion, LivingEntity summoner, WorldView world, double speed, EntityNavigation navigation,  float maxDistance, float minDistance,  boolean field_21078) {
        this.minion = minion;
        this.summoner = summoner;
        this.world = world;
        this.field_6442 = speed;
        this.navigation = navigation;
        this.maxDistance = maxDistance;
        this.minDistance = minDistance;
        this.field_21078 = field_21078;
    }


    @Override
    public boolean canStart() {

        LivingEntity livingEntity = (LivingEntity) this.minion.getSummoner();

        if (livingEntity == null) {
            return false;
        }
        else if (livingEntity.isSpectator()) {
            return false;
        }
        else if (this.minion.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        }
        else {
            this.summoner = livingEntity;
            return true;
        }

    }


    @Override
    public boolean shouldContinue() {

        if (this.navigation.isIdle()) {
            return false;
        } else {
            return this.minion.squaredDistanceTo(this.summoner) > (double)(this.maxDistance * this.maxDistance);
        }


    }


    public void tick(){
        this.minion.getLookControl().lookAt(this.summoner, 10.0F, (float)this.minion.getLookPitchSpeed());
        if (--this.field_6443 <= 0) {
            this.field_6443 = 10;
            if (!this.minion.hasVehicle()) {
                if (this.minion.squaredDistanceTo(this.summoner) >= 144.0D) {
                    this.method_23345();
                } else {
                    this.navigation.startMovingTo(this.summoner, this.field_6442);
                }

            }
        }



    }


    private void method_23345() {
        BlockPos blockPos = new BlockPos(this.summoner);

        for(int i = 0; i < 10; ++i) {
            int j = this.method_23342(-3, 3);
            int k = this.method_23342(-1, 1);
            int l = this.method_23342(-3, 3);
            boolean bl = this.method_23343(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
            if (bl) {
                return;
            }
        }

    }


    private boolean method_23343(int i, int j, int k) {
        if (Math.abs((double)i - this.summoner.getX()) < 2.0D && Math.abs((double)k - this.summoner.getZ()) < 2.0D) {
            return false;
        } else if (!this.method_23344(new BlockPos(i, j, k))) {
            return false;
        } else {
            this.navigation.stop();
            return true;
        }
    }


    private boolean method_23344(BlockPos blockPos) {
        PathNodeType pathNodeType = LandPathNodeMaker.getPathNodeType(this.world, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (pathNodeType != PathNodeType.WALKABLE) {
            return false;
        } else {
            BlockState blockState = this.world.getBlockState(blockPos.down());
            if (!this.field_21078 && blockState.getBlock() instanceof LeavesBlock) {
                return false;
            } else {
                BlockPos blockPos2 = blockPos.subtract(new BlockPos(this.minion));
                return this.world.doesNotCollide(this.minion, this.minion.getBoundingBox().offset(blockPos2));
            }
        }
    }


    private int method_23342(int i, int j) {
        return this.minion.getRandom().nextInt(j - i + 1) + i;
    }





}
