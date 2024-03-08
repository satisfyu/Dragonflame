package satisfy.dragonflame.util.api;

import de.cristelknight.doapi.api.DoApiAPI;
import de.cristelknight.doapi.api.DoApiPlugin;
import de.cristelknight.doapi.client.render.feature.CustomArmorManager;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import satisfy.dragonflame.registry.ArmorRegistry;

import java.util.Map;
import java.util.Set;

@DoApiPlugin
public class DragonFlameDoAPI implements DoApiAPI {
    @Override
    public void registerBlocks(Set<Block> set) {
    }

    @Override
    public <T extends LivingEntity> void registerHat(Map<Item, EntityModel<T>> models, EntityModelSet modelLoader) {

    }

    @Override
    public <T extends LivingEntity> void registerArmor(CustomArmorManager<T> customArmorManager, EntityModelSet entityModelSet) {
        ArmorRegistry.registerArmorModels(customArmorManager, entityModelSet);
    }
}
