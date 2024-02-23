package satisfy.dragonflame.block.arcanetorch;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import satisfy.dragonflame.Dragonflame;
import satisfy.dragonflame.registry.ObjectRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SpecialRitualHandler {

    private BlockPos detectedCenter = null;


    public SpecialRitualHandler(Level world, BlockPos startingTorch) {
        Collection<BlockPos> foundTorches = searchForTorchPositions(world, startingTorch);
        if (foundTorches.size() == 4 && Objects.nonNull(detectedCenter)) {
            getLogger().debug("Ritual has been detected!");
            getLogger().info("Found torches collection length: {}", foundTorches.size());
            getLogger().info("Found torches array length: {}", foundTorches.toArray(new BlockPos[0]).length);
            completeRitual(world, detectedCenter, foundTorches.toArray(new BlockPos[0]));
        }
    }

    private Collection<BlockPos> searchForTorchPositions(Level world, BlockPos startingTorch) {
        boolean hasFoundRitual = false;
        Collection<BlockPos> foundTorches = new ArrayList<>();
        foundTorches.add(startingTorch);
        if (areValidTorchPositions(world, startingTorch, startingTorch.north().west(), startingTorch.north(2), startingTorch.north().east())) {
            foundTorches.add(startingTorch.north().west());
            foundTorches.add(startingTorch.north(2));
            foundTorches.add(startingTorch.north().east());
            this.detectedCenter = startingTorch.north();
        }
        if (areValidTorchPositions(world, startingTorch, startingTorch.east().north(), startingTorch.east(2), startingTorch.east().south()) && !hasFoundRitual) {
            foundTorches.add(startingTorch.east().north());
            foundTorches.add(startingTorch.east(2));
            foundTorches.add(startingTorch.east().south());
            this.detectedCenter = startingTorch.east();
        }
        if (areValidTorchPositions(world, startingTorch, startingTorch.south().east(), startingTorch.south(2), startingTorch.south().west()) && !hasFoundRitual) {
            foundTorches.add(startingTorch.south().east());
            foundTorches.add(startingTorch.south(2));
            foundTorches.add(startingTorch.south().west());
            this.detectedCenter = startingTorch.south();
        }
        if (areValidTorchPositions(world, startingTorch, startingTorch.west().south(), startingTorch.west(2), startingTorch.west().north()) && !hasFoundRitual) {
            foundTorches.add(startingTorch.west().south());
            foundTorches.add(startingTorch.west(2));
            foundTorches.add(startingTorch.west().north());
            this.detectedCenter = startingTorch.west();
        }

        return foundTorches;
    }

    private static boolean areValidTorchPositions(Level world, BlockPos startingTorch, BlockPos... possibleCandidates) {
        if (possibleCandidates.length != 3) {
            getLogger().info("Possible ritual block length was unexpectedly a non-3 value");
            return false;
        }
        boolean allSearchedPositionsAreValid = world.getBlockState(startingTorch).getBlock() == ObjectRegistry.ARCANE_GROUND_TORCH.get();

        // just double checking lol
        if (world.getBlockState(possibleCandidates[0]).getBlock() != ObjectRegistry.ARCANE_GROUND_TORCH.get()) allSearchedPositionsAreValid = false;
        if (world.getBlockState(possibleCandidates[1]).getBlock() != ObjectRegistry.ARCANE_GROUND_TORCH.get()) allSearchedPositionsAreValid = false;
        if (world.getBlockState(possibleCandidates[2]).getBlock() != ObjectRegistry.ARCANE_GROUND_TORCH.get()) allSearchedPositionsAreValid = false;
        try {
            if (!world.getBlockState(startingTorch).getValue(ArcaneGroundTorchBlock.SHOULD_DROP))
                allSearchedPositionsAreValid = false; // don't want to give them 5 in one go lolol
            if (!world.getBlockState(possibleCandidates[0]).getValue(ArcaneGroundTorchBlock.SHOULD_DROP))
                allSearchedPositionsAreValid = false;
            if (!world.getBlockState(possibleCandidates[1]).getValue(ArcaneGroundTorchBlock.SHOULD_DROP))
                allSearchedPositionsAreValid = false;
            if (!world.getBlockState(possibleCandidates[2]).getValue(ArcaneGroundTorchBlock.SHOULD_DROP))
                allSearchedPositionsAreValid = false;
        } catch (Exception ignored) {}

        return allSearchedPositionsAreValid;
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(Dragonflame.MOD_ID + "/SpecialRitualHandler");
    }

    public void completeRitual(Level world, BlockPos summoningCenterPos, BlockPos... torches) {
        if (torches.length != 4) {
            throw new RuntimeException("Oh boy, you messed up big time!");
            // this should never print
        }
        world.setBlockAndUpdate(torches[0], world.getBlockState(torches[0]).setValue(ArcaneGroundTorchBlock.SHOULD_DROP, false));
        world.setBlockAndUpdate(torches[1], world.getBlockState(torches[1]).setValue(ArcaneGroundTorchBlock.SHOULD_DROP, false));
        world.setBlockAndUpdate(torches[2], world.getBlockState(torches[2]).setValue(ArcaneGroundTorchBlock.SHOULD_DROP, false));
        world.setBlockAndUpdate(torches[3], world.getBlockState(torches[3]).setValue(ArcaneGroundTorchBlock.SHOULD_DROP, false));
        world.addFreshEntity(new ItemEntity(world, summoningCenterPos.getX(), summoningCenterPos.getY() + 10, summoningCenterPos.getZ(), new ItemStack(ObjectRegistry.TITAN_DUST.get())));
    }
}
