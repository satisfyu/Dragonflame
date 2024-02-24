package satisfy.dragonflame.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.client.model.DragonWhelplingModel;
import satisfy.dragonflame.client.model.FieryWarhorseModel;
import satisfy.dragonflame.entity.DragonWhelpling;
import satisfy.dragonflame.entity.FieryWarhorse;
import satisfy.dragonflame.util.DragonflameIdentifier;

@Environment(value= EnvType.CLIENT)
public class DragonWhelplingRenderer<T extends DragonWhelpling> extends MobRenderer<T, DragonWhelplingModel<T>> {
    private static final ResourceLocation TEXTURE = new DragonflameIdentifier("textures/entity/dragon_whelpling.png");

    public DragonWhelplingRenderer(EntityRendererProvider.Context context) {
        super(context, new DragonWhelplingModel<>(context.bakeLayer(DragonWhelplingModel.LAYER_LOCATION)), 1.1f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DragonWhelpling entity) {
        return TEXTURE;
    }

}
