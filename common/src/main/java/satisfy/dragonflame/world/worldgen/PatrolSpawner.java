package satisfy.dragonflame.world.worldgen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.entity.IPatrollingMob;
import satisfy.dragonflame.registry.EntityRegistry;

public class PatrolSpawner implements CustomSpawner { // TODO
    private int nextTick;

    public int tick(@NotNull ServerLevel serverLevel, boolean bl, boolean bl2) {
        if (!bl) {
            return 0;
        } else if (!serverLevel.getGameRules().getBoolean(GameRules.RULE_DO_PATROL_SPAWNING)) {
            return 0;
        } else {
            RandomSource randomSource = serverLevel.random;
            --this.nextTick;
            if (this.nextTick > 0) {
                return 0;
            } else {
                this.nextTick += 12000 + randomSource.nextInt(1200);

                int players = serverLevel.players().size();
                if (players < 1) {
                    return 0;
                } else {
                    Player player = serverLevel.players().get(randomSource.nextInt(players));
                    if (player.isSpectator()) {
                        return 0;
                    } else {
                        int x = (24 + randomSource.nextInt(24)) * (randomSource.nextBoolean() ? -1 : 1);
                        int z = (24 + randomSource.nextInt(24)) * (randomSource.nextBoolean() ? -1 : 1);
                        BlockPos.MutableBlockPos mutableBlockPos = player.blockPosition().mutable().move(x, 0, z);
                        if (!serverLevel.hasChunksAt(mutableBlockPos.getX() - 10, mutableBlockPos.getZ() - 10, mutableBlockPos.getX() + 10, mutableBlockPos.getZ() + 10)) {
                            return 0;
                        } else {
                            Holder<Biome> holder = serverLevel.getBiome(mutableBlockPos);
                            if (holder.is(BiomeTags.WITHOUT_PATROL_SPAWNS)) {
                                return 0;
                            } else {
                                return spawnPatrol(serverLevel, mutableBlockPos, randomSource);
                            }
                        }
                    }
                }
            }
        }
    }

    private int spawnPatrol(ServerLevel serverLevel, BlockPos.MutableBlockPos blockPos, RandomSource randomSource) {
        int i = 0;
        int amount = (int) Math.ceil(serverLevel.getCurrentDifficultyAt(blockPos).getEffectiveDifficulty());

        for (; i < amount; i++) {
            blockPos.setY(serverLevel.getHeightmapPos(Types.MOTION_BLOCKING_NO_LEAVES, blockPos).getY());
            if (i == 0) {
                if (!this.spawnPatrolMember(EntityRegistry.ARMORED_PILLAGER_DOG.get(), serverLevel, blockPos, randomSource, true)) {
                    return 0;
                }
            } else {
                this.spawnPatrolMember(EntityRegistry.ARMORED_PILLAGER_DOG.get(), serverLevel, blockPos, randomSource, false);
            }

            blockPos.setX(blockPos.getX() + randomSource.nextInt(5) - randomSource.nextInt(5));
            blockPos.setZ(blockPos.getZ() + randomSource.nextInt(5) - randomSource.nextInt(5));
        }

        this.spawnPatrolMember(EntityRegistry.ARMORED_PILLAGER.get(), serverLevel, blockPos, randomSource, false);
        this.spawnPatrolMember(EntityRegistry.ARMORED_VINDICATOR.get(), serverLevel, blockPos, randomSource, false);

        return i;
    }

    private <T extends Mob & IPatrollingMob> boolean spawnPatrolMember(EntityType<T> entity, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, boolean bl) {
        BlockState blockState = serverLevel.getBlockState(blockPos);
        if (!NaturalSpawner.isValidEmptySpawnBlock(serverLevel, blockPos, blockState, blockState.getFluidState(), EntityType.PILLAGER)) {
            return false;
        } else if (!IPatrollingMob.checkPatrolSpawnRules(entity, serverLevel, MobSpawnType.PATROL, blockPos, randomSource)) {
            return false;
        } else {
            T patrollingMonster = entity.create(serverLevel);
            if (patrollingMonster != null) {
                if (bl) {
                    patrollingMonster.setPatrolLeader(true);
                    patrollingMonster.findPatrolTarget();
                }

                patrollingMonster.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                patrollingMonster.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(blockPos), MobSpawnType.PATROL, null, null);
                serverLevel.addFreshEntityWithPassengers(patrollingMonster);
                return true;
            } else {
                return false;
            }
        }
    }
}