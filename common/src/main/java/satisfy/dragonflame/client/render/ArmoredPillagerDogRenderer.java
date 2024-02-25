package satisfy.dragonflame.client.render;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.client.model.ArmoredPillagerDogModel;
import satisfy.dragonflame.entity.ArmoredPillagerDog;
import satisfy.dragonflame.util.DragonflameIdentifier;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ArmoredPillagerDogRenderer extends MobRenderer<ArmoredPillagerDog, ArmoredPillagerDogModel<ArmoredPillagerDog>> {
    private static final ResourceLocation TEXTURE_1 = new DragonflameIdentifier("textures/entity/pillager_dog.png");
    private static final ResourceLocation TEXTURE_2 = new DragonflameIdentifier("textures/entity/pillager_dog_variant.png");
    private final Map<UUID, ResourceLocation> textureCache = new HashMap<>();

    public ArmoredPillagerDogRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmoredPillagerDogModel<>(context.bakeLayer(ArmoredPillagerDogModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(ArmoredPillagerDog entity) {
        return textureCache.computeIfAbsent(entity.getUUID(), uuid -> {
            boolean useFirstTexture = Math.random() < 0.5;
            return useFirstTexture ? TEXTURE_1 : TEXTURE_2;
        });
    }
}
