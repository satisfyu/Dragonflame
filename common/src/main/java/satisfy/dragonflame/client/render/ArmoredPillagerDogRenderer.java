package satisfy.dragonflame.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfy.dragonflame.client.model.ArmoredPillagerDogModel;
import satisfy.dragonflame.entity.ArmoredPillagerDog;
import satisfy.dragonflame.util.DragonflameIdentifier;


@Environment(value = EnvType.CLIENT)
public class ArmoredPillagerDogRenderer extends MobRenderer<ArmoredPillagerDog, ArmoredPillagerDogModel<ArmoredPillagerDog>> {
    private static final ResourceLocation TEXTURE = new DragonflameIdentifier("textures/entity/pillager_dog.png");

    public ArmoredPillagerDogRenderer(EntityRendererProvider.Context context) {
        super(context, new ArmoredPillagerDogModel(context.bakeLayer(ArmoredPillagerDogModel.LAYER_LOCATION)), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(ArmoredPillagerDog entity) {
        return TEXTURE;
    }
}

