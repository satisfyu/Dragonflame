package satisfy.dragonflame.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.client.ClientUtil;
import satisfy.dragonflame.client.DragonflameClient;

@Mod.EventBusSubscriber(modid = Dragonflame.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public void onCameraSetup(ViewportEvent event) {
        ClientUtil.dragonCamera(event.getCamera());
    }

    @SubscribeEvent
    public void onCameraSetup(ViewportEvent.RenderFog event) {
    }


    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase == TickEvent.Phase.END) {
            DragonflameClient.isYPressed = DragonflameClientForge.EXAMPLE_MAPPING.get().isDown();
        }
    }

    @SubscribeEvent
    public void onPreRenderLiving(RenderLivingEvent.Pre event) {
        if (ClientUtil.shouldCancelRender(event.getEntity())) {
            event.setCanceled(true);
        }
    }
    @SubscribeEvent
    public void onPostRenderLiving(RenderLivingEvent.Post event) {
        if (ClientUtil.shouldCancelRender(event.getEntity())) {
            event.setCanceled(true);
        }
    }
}