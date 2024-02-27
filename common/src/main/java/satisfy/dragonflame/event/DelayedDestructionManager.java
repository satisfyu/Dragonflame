package satisfy.dragonflame.event;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

import java.util.Iterator;
import java.util.List;

public class DelayedDestructionManager {
    private static final List<DelayedDestruction> destructions = Lists.newArrayList();

    public static void scheduleDestruction(Level level, BlockPos pos, int delay) {
        destructions.add(new DelayedDestruction(level, pos, delay));
    }

    public static void tick() {
        Iterator<DelayedDestruction> iterator = destructions.iterator();
        while (iterator.hasNext()) {
            DelayedDestruction destruction = iterator.next();
            if (destruction.tick()) {
                iterator.remove();
            }
        }
    }

    private static class DelayedDestruction {
        private final Level level;
        private final BlockPos pos;
        private int delay;

        public DelayedDestruction(Level level, BlockPos pos, int delay) {
            this.level = level;
            this.pos = pos;
            this.delay = delay;
        }

        public boolean tick() {
            if (--delay <= 0) {
                if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                    level.destroyBlock(pos, true);
                    serverLevel.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.05);
                    serverLevel.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 20, 0.5, 0.5, 0.5, 0.05);
                    level.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.BLOCKS, 1.0F, 1.0F);
                }
                return true;
            }
            return false;
        }
    }
}
