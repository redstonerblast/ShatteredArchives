package tally.shattered_archive.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandHandler {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("motion")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(argument("type", StringArgumentType.string())
                            .then(argument("entity", EntityArgumentType.entity())
                                    .then(argument("xmot", DoubleArgumentType.doubleArg())
                                            .then(argument("ymot", DoubleArgumentType.doubleArg())
                                                    .then(argument("zmot", DoubleArgumentType.doubleArg())
                                                            .executes(context ->
                                                                    createMotion(StringArgumentType.getString(context, "type"), EntityArgumentType.getEntity(context, "entity"), new Vec3d(DoubleArgumentType.getDouble(context, "xmot"), DoubleArgumentType.getDouble(context, "ymot"), DoubleArgumentType.getDouble(context, "zmot")), context)
                                                            )
                                                    )
                                            )
                                    )
                                    .then(literal("toward")
                                            .then(argument("location", Vec3ArgumentType.vec3())
                                                    .then(argument("power", DoubleArgumentType.doubleArg())
                                                            .then(literal("relative")
                                                                    .executes(context ->
                                                                            createMotionLocRel(StringArgumentType.getString(context, "type"), EntityArgumentType.getEntity(context, "entity"), Vec3ArgumentType.getVec3(context, "location"), DoubleArgumentType.getDouble(context, "power"), context)
                                                                    )
                                                            )
                                                            .executes(context ->
                                                                    createMotionLoc(StringArgumentType.getString(context, "type"), EntityArgumentType.getEntity(context, "entity"), Vec3ArgumentType.getVec3(context, "location"), DoubleArgumentType.getDouble(context, "power"), context)
                                                            )
                                                    )
                                            )
                                    )
                            )
                    )
            );
        });
    }

    private static int createMotion(String type, Entity entity, Vec3d vec3d, CommandContext<ServerCommandSource> context) {
        if(entity != null) {
            if(Objects.equals(type, "set") || Objects.equals(type, "add")) {
                if(Objects.equals(type, "set")) {
                    entity.setVelocity(vec3d);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                } else {
                    entity.addVelocity(vec3d);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private static int createMotionLocRel(String type, Entity entity, Vec3d vec3d, Double power, CommandContext<ServerCommandSource> context) {
        if(entity != null) {
            if(Objects.equals(type, "set") || Objects.equals(type, "add")) {
                Vec3d vel = vec3d.subtract(entity.getPos()).multiply(power);
                if(Objects.equals(type, "set")) {
                    entity.setVelocity(vel);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                } else {
                    entity.addVelocity(vel);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    private static int createMotionLoc(String type, Entity entity, Vec3d vec3d, Double power, CommandContext<ServerCommandSource> context) {
        if(entity != null) {
            if(Objects.equals(type, "set") || Objects.equals(type, "add")) {
                Vec3d vel = vec3d.subtract(entity.getPos()).normalize().multiply(power);
                if(Objects.equals(type, "set")) {
                    entity.setVelocity(vel);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                } else {
                    entity.addVelocity(vel);
                    entity.velocityModified = Boolean.TRUE;
                    return 1;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
