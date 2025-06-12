package tally.shattered_archive.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tally.shattered_archive.ShatteredArchive;

@Mixin(ActiveTargetGoal.class)
public class ActiveTargetGoalMixin {
    @Redirect(method = "findClosestTarget()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;getClosestPlayer(Lnet/minecraft/entity/ai/TargetPredicate;Lnet/minecraft/entity/LivingEntity;DDD)Lnet/minecraft/entity/player/PlayerEntity;"))
    private PlayerEntity inject(World instance, TargetPredicate targetPredicate, LivingEntity livingEntity, double v, double vb, double vc) {
        PlayerEntity player = livingEntity.getWorld().getClosestPlayer(targetPredicate, livingEntity, v, vb, vc);
        if (player != null) {
            if (player.getCommandTags().contains(livingEntity.getType().getName().getString() + "_ignore")) {
                return null;
            }
        }
        return player;
    }
}
