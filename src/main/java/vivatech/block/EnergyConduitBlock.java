package vivatech.block;

import alexiil.mc.lib.attributes.SearchOption;
import alexiil.mc.lib.attributes.SearchOptions;
import io.github.cottonmc.energy.api.EnergyAttribute;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderLayer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.state.StateFactory;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BooleanBiFunction;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import vivatech.Vivatech;

public class EnergyConduitBlock extends Block {
    public static final Identifier ID = new Identifier(Vivatech.MODID, "energy_conduit");

    public static final BooleanProperty CONNECTED_UP = BooleanProperty.create("connected_up");
    public static final BooleanProperty CONNECTED_DOWN = BooleanProperty.create("connected_down");
    public static final BooleanProperty CONNECTED_NORTH = BooleanProperty.create("connected_north");
    public static final BooleanProperty CONNECTED_EAST = BooleanProperty.create("connected_east");
    public static final BooleanProperty CONNECTED_SOUTH = BooleanProperty.create("connected_south");
    public static final BooleanProperty CONNECTED_WEST = BooleanProperty.create("connected_west");

    public EnergyConduitBlock() {
        super(Vivatech.METALLIC_BLOCK_SETTINGS);

        setDefaultState(getStateFactory().getDefaultState()
                .with(CONNECTED_UP, false)
                .with(CONNECTED_DOWN, false)
                .with(CONNECTED_NORTH, false)
                .with(CONNECTED_EAST, false)
                .with(CONNECTED_SOUTH, false)
                .with(CONNECTED_WEST, false));
    }

    // Block
    @Override
    protected void appendProperties(StateFactory.Builder<Block, BlockState> builder) {
        builder.add(CONNECTED_UP, CONNECTED_DOWN, CONNECTED_NORTH, CONNECTED_EAST, CONNECTED_SOUTH, CONNECTED_WEST);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView blockView, BlockPos pos, EntityContext context) {
        double startOffset = 0.3125;
        double endOffset = 0.6875;

        VoxelShape shape = VoxelShapes.cuboid(startOffset, startOffset, startOffset, endOffset, endOffset, endOffset);

        if (state.get(CONNECTED_UP)) {
            VoxelShape modifier = VoxelShapes.cuboid(startOffset, startOffset, startOffset, endOffset, 1, endOffset);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        if (state.get(CONNECTED_DOWN)) {
            VoxelShape modifier = VoxelShapes.cuboid(startOffset, 0, startOffset, endOffset, endOffset, endOffset);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        if (state.get(CONNECTED_NORTH)) {
            VoxelShape modifier = VoxelShapes.cuboid(startOffset, startOffset, 0, endOffset, endOffset, endOffset);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        if (state.get(CONNECTED_EAST)) {
            VoxelShape modifier = VoxelShapes.cuboid(startOffset, startOffset, startOffset, 1, endOffset, endOffset);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        if (state.get(CONNECTED_SOUTH)) {
            VoxelShape modifier = VoxelShapes.cuboid(startOffset, startOffset, startOffset, endOffset, endOffset, 1);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        if (state.get(CONNECTED_WEST)) {
            VoxelShape modifier = VoxelShapes.cuboid(0, startOffset, startOffset, endOffset, endOffset, endOffset);
            shape = VoxelShapes.combineAndSimplify(shape, modifier, BooleanBiFunction.OR);
        }

        return shape;
    }

    @Override
    public void onBlockAdded(BlockState stateFrom, World world, BlockPos pos, BlockState stateTo, boolean b) {
        world.updateNeighbors(pos, this);
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block otherBlock, BlockPos otherPos, boolean b) {
        super.neighborUpdate(state, world, pos, otherBlock, otherPos, b);

        calcConnectionProperty(state, pos, Direction.UP, world, CONNECTED_UP);
        calcConnectionProperty(state, pos, Direction.DOWN, world, CONNECTED_DOWN);
        calcConnectionProperty(state, pos, Direction.NORTH, world, CONNECTED_NORTH);
        calcConnectionProperty(state, pos, Direction.EAST, world, CONNECTED_EAST);
        calcConnectionProperty(state, pos, Direction.SOUTH, world, CONNECTED_SOUTH);
        calcConnectionProperty(state, pos, Direction.WEST, world, CONNECTED_WEST);
    }

    private void calcConnectionProperty(BlockState state, BlockPos pos, Direction direction, World world, BooleanProperty property) {
        boolean dirty = false;

        boolean sideState = state.get(property);
        BlockPos offsetPos = pos.offset(direction);
        SearchOption option = SearchOptions.inDirection(direction);

        if (EnergyAttribute.ENERGY_ATTRIBUTE.getFirstOrNull(world, offsetPos, option) != null
                || world.getBlockState(offsetPos).getBlock() instanceof EnergyConduitBlock) {
            if (!sideState) {
                dirty = true;
            }
            state = state.with(property, true);
        } else {
            if (sideState) {
                dirty = true;
            }
            state = state.with(property, false);
        }

        if (dirty) {
            world.setBlockState(pos, state);
            world.updateNeighbors(pos, this);
        }
    }
}