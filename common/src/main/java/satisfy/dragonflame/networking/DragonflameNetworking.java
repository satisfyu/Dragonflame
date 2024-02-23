package satisfy.dragonflame.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.phys.Vec3;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.block.arcanetorch.ArcaneTorchItem;
import satisfy.dragonflame.client.ParticlePacketReceiver;
import satisfy.dragonflame.util.DragonflameIdentifier;
import team.lodestar.lodestone.setup.LodestoneParticles;
import team.lodestar.lodestone.systems.rendering.particle.Easing;
import team.lodestar.lodestone.systems.rendering.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.rendering.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.rendering.particle.data.SpinParticleData;

import java.awt.*;
import java.util.Objects;

public interface DragonflameNetworking {
    ResourceLocation SWITCH_TORCH_PACKET_ID = new DragonflameIdentifier("switch_torch_packet");

    ResourceLocation HEARTHSTONE_TP_PACKET_ID = new DragonflameIdentifier("hearthstone_tp_particle_packet");

    static void serverInit() {
        ServerPlayNetworking.registerGlobalReceiver(SWITCH_TORCH_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            ItemStack oldTorchItem = buf.readItem();
            server.execute(() -> {
                Dragonflame.LOGGER.debug("Torch itemstack switch packet recieved");
                if (!player.isCreative()) {
                    Dragonflame.LOGGER.warn("Player {} attempted to change item nbt without creative mode!", player.getName().getString());
                    return;
                }
                int itemSlot = player.getInventory().findSlotMatchingItem(oldTorchItem);
                CompoundTag previousTag = oldTorchItem.getTag();
                if (Objects.isNull(previousTag)) previousTag = ArcaneTorchItem.getDefaultTag();
                previousTag.putBoolean("should_drop", !previousTag.getBoolean("should_drop"));
                oldTorchItem.setTag(previousTag);
                player.getInventory().setItem(itemSlot, oldTorchItem);

                Dragonflame.LOGGER.debug("Torch itemstack switch packet acted upon");
            });
        });
    }

    static void clientInit() {
        ParticlePacketReceiver.init();
    }

    static void commonInit() {
        serverInit();
        clientInit();
    }
}
