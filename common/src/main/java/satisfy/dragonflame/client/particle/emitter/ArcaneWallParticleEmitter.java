package satisfy.dragonflame.client.particle.emitter;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import team.lodestar.lodestone.setup.LodestoneScreenParticles;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.rendering.particle.screen.LodestoneScreenParticleTextureSheet;
import team.lodestar.lodestone.systems.rendering.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ArcaneWallParticleEmitter {

    public static void particleTick(HashMap<LodestoneScreenParticleTextureSheet, ArrayList<ScreenParticle>> target, Level world, float tickDelta, ItemStack stack, float x, float y) {
        final Minecraft client = Minecraft.getInstance();
        if (world == null) {
            return;
        }
        float gameTime = world.getGameTime() + client.getFrameTime();
        Color firstColor = new Color(78, 95, 50);
        ScreenParticleBuilder.create(LodestoneScreenParticles.WISP, target)
                .setTransparencyData(GenericParticleData.create(0.02f, 0f)
                        .setEasing(Easing.QUINTIC_IN).build())
                .setLifetime(7)
                .setScaleData(GenericParticleData.create((float) (0.75f + Math.sin(gameTime * 0.015f) * 0.05f), 0).build())
                .setColorData(ColorParticleData.create(firstColor, firstColor).build())
                .setRandomOffset(0.05f)
                .spawnOnStack(0, -1)
                .repeat(x, y, 1)
                .setScaleData(GenericParticleData.create((float) (0.75f - Math.sin(gameTime * 0.065f) * 0.075f), 0).build())
                .setSpinData(SpinParticleData.create(1).setSpinOffset(0.785f-0.01f * gameTime % 6.28f).build())
                .repeat(x, y, 1);
    }
}