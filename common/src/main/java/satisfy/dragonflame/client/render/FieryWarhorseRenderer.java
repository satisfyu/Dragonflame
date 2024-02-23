package satisfy.dragonflame.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import satisfy.dragonflame.client.model.FieryWarhorseModel;
import satisfy.dragonflame.entity.FieryWarhorse;
import satisfy.dragonflame.util.DragonflameIdentifier;

@Environment(value= EnvType.CLIENT)
public class FieryWarhorseRenderer<T extends FieryWarhorse> extends MobRenderer<T, FieryWarhorseModel<T>> {
    private static final ResourceLocation TEXTURE = new DragonflameIdentifier("textures/entity/fiery_warhorse.png");

    public FieryWarhorseRenderer(EntityRendererProvider.Context context) {
        super(context, new FieryWarhorseModel<>(context.bakeLayer(FieryWarhorseModel.LAYER_LOCATION)), 1.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(FieryWarhorse entity) {
        return TEXTURE;
    }

}
