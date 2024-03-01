package satisfy.dragonflame.entity.fire_dragon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import satisfy.dragonflame.util.DragonflameIdentifier;

@Environment(value= EnvType.CLIENT)
public class FireDragonRenderer<T extends FireDragon> extends MobRenderer<T, FireDragonModel<T>> {
    private static final ResourceLocation TEXTURE = new DragonflameIdentifier("textures/entity/firedragon.png");

    public FireDragonRenderer(EntityRendererProvider.Context context) {
        super(context, new FireDragonModel<>(context.bakeLayer(FireDragonModel.LAYER_LOCATION)), 1.1f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(FireDragon entity) {
        return TEXTURE;
    }

}
