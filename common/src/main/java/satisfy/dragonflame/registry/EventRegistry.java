package satisfy.dragonflame.registry;

import dev.architectury.event.events.common.TickEvent;
import satisfy.dragonflame.event.DelayedDestructionManager;

public class EventRegistry {
    public static void init() {
        TickEvent.SERVER_PRE.register(server -> {
            DelayedDestructionManager.tick();
        });
    }
}
