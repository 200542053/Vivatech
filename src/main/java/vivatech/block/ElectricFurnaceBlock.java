package vivatech.block;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import vivatech.Vivatech;
import vivatech.entity.ElectricFurnaceEntity;
import vivatech.util.MachineTeirs;

public class ElectricFurnaceBlock extends AbstractTeiredMachineBlock {
    public static final Identifier ID = new Identifier(Vivatech.MODID, "electric_furnace");

    public ElectricFurnaceBlock(MachineTeirs teir) {
        super(Vivatech.MACHINE_BLOCK_SETTINGS, teir);
    }

    // Block
    @Override
    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (!world.isClient) {
            ContainerProviderRegistry.INSTANCE.openContainer(ID, player, buf -> buf.writeBlockPos(pos));
        }

        return true;
    }

    // BlockEntityProvider
    @Override
    public BlockEntity createBlockEntity(BlockView blockView) {
        return new ElectricFurnaceEntity(TEIR);
    }
}
