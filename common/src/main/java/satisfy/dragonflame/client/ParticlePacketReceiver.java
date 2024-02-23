package satisfy.dragonflame.client;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.networking.DragonflameNetworking;
import team.lodestar.lodestone.setup.LodestoneParticles;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;
import java.util.function.Consumer;

public interface ParticlePacketReceiver {
    static void init() {
        bindParticle(DragonflameNetworking.HEARTHSTONE_TP_PACKET_ID, (particlePacket -> {
                        for (int twinkleIndex = 1; twinkleIndex <= 50; twinkleIndex++) {
                            WorldParticleBuilder.create(LodestoneParticles.TWINKLE_PARTICLE)
                                    .setScaleData(GenericParticleData.create(0.07f, 0).setEasing(Easing.LINEAR).build())
                                    .setLifetime(45)
                                    .setSpinData(SpinParticleData.create(0.1f, 0.1f).build())
                                    .setRandomOffset(1.4D, 1.5D, 1.4D)
                                    .setColorData(ColorParticleData.create(new Color(0xA4AAB6), new Color(0x344d9e))
                                            .setEasing(Easing.CIRC_OUT).build())
                                    .setRandomMotion(0.005D)
                                    .setGravity(0.005f)
                                    .enableNoClip()
                                    .spawn(particlePacket.client.level, particlePacket.xPos, particlePacket.yPos + 1, particlePacket.zPos);
                        }
        }));
    }

    static void bindParticle(ResourceLocation packetId, Consumer<ParticlePacket> consumer) {
        ClientPlayNetworking.registerGlobalReceiver(packetId, (client, handler, buf, responseSender) -> {
            double destinationX = buf.readDouble();
            double destinationY = buf.readDouble();
            double destinationZ = buf.readDouble();
            ChunkPos destinationChunk = new ChunkPos((int) destinationX, (int) destinationZ);
            client.execute(() -> {
                assert client.level != null;
                if (client.level.hasChunk(destinationChunk.x, destinationChunk.z)) {
                    consumer.accept(new ParticlePacket(client, handler, buf, responseSender, destinationX, destinationY, destinationZ));
                }
            });
        });
    }

    class ParticlePacket {
        Minecraft client;
        ClientPacketListener handler;
        FriendlyByteBuf buf;
        PacketSender responseSender;

        double xPos;
        double yPos;
        double zPos;
        public ParticlePacket(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender, double destinationX, double destinationY, double destinationZ) {
            this.client = client;
            this.handler = handler;
            this.buf = buf;
            this.responseSender = responseSender;
            this.xPos = destinationX;
            this.yPos = destinationY;
            this.zPos = destinationZ;
        }
    }
}
