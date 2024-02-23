package satisfy.dragonflame.util;

import net.minecraft.nbt.CompoundTag;

import java.util.function.Function;

public interface NbtUtil {
        static int getOrDefaultInt(Function<CompoundTag, Integer> getter, int defaultValue, CompoundTag nbt) {
            try {
                return getter.apply(nbt);
            } catch (Exception ignored) {
                return defaultValue;
            }
        }

        static int getOrThrowInt(CompoundTag nbt, String value) {
            if (!nbt.contains(value)) {
                throw new NullPointerException();
            }
            return nbt.getInt(value);
        }
}
