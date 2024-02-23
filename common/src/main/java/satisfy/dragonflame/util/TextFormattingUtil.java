package satisfy.dragonflame.util;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TextFormattingUtil {

    static Component withColor(@NotNull Component Component, int color) {
        return repack(Component.toFlatList(Component.getStyle().withColor(color)));
    }

    static Component withItalics(@NotNull Component Component, boolean italics) {
        return repack(Component.toFlatList(Component.getStyle().withItalic(italics)));
    }

    static Component withBold(@NotNull Component Component, boolean bold) {
        return repack(Component.toFlatList(Component.getStyle().withBold(bold)));
    }

    static Component withUnderline(@NotNull Component Component, boolean underline) {
        return repack(Component.toFlatList(Component.getStyle().withUnderlined(underline)));
    }

    static Component withStrikethrough(@NotNull Component Component, boolean strikethrough) {
        return repack(Component.toFlatList(Component.getStyle().withStrikethrough(strikethrough)));
    }

    static Component withObfuscated(@NotNull Component Component, boolean obfuscated) {
        return repack(Component.toFlatList(Component.getStyle().withObfuscated(obfuscated)));
    }

    static Component withInsertion(@NotNull Component Component, String insertion) {
        return repack(Component.toFlatList(Component.getStyle().withInsertion(insertion)));
    }

    static Component repack(@NotNull List<Component> ComponentList) {
        if (ComponentList.isEmpty()) {
            return Component.literal("");
        } else {
            var first = ComponentList.get(0);
            for (var i = 1; i < ComponentList.size(); i++) {
                first = first.copy().append(ComponentList.get(i));
            }
            return first;
        }
    }
}
