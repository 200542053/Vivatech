package vivatech.init;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import vivatech.Vivatech;
import vivatech.block.BaseBlock;
import vivatech.block.CoalGeneratorBlock;
import vivatech.block.CrusherBlock;
import vivatech.block.ElectricFurnaceBlock;
import vivatech.block.EnergyBankBlock;
import vivatech.block.EnergyConduitBlock;
import vivatech.block.PressBlock;
import vivatech.util.MachineTeirs;

public class VivatechBlocks implements Initializable {
    public static final Identifier MACHINE_CHASSIS_ID = new Identifier(Vivatech.MODID, "machine_chassis");

    public static final BaseBlock MACHINE_CHASSIS;
    public static final EnergyConduitBlock ENERGY_CONDUIT;
    public static final CoalGeneratorBlock COAL_GENERATOR;
    public static final CrusherBlock CRUSHER;
    public static final ElectricFurnaceBlock[] ELECTRIC_FURNACE;
    public static final EnergyBankBlock ENERGY_BANK;
    public static final PressBlock PRESS;

    static {
        MACHINE_CHASSIS = new BaseBlock(Vivatech.METALLIC_BLOCK_SETTINGS);
        ENERGY_CONDUIT = new EnergyConduitBlock();
        COAL_GENERATOR = new CoalGeneratorBlock();
        CRUSHER = new CrusherBlock();
        ENERGY_BANK = new EnergyBankBlock();
        PRESS = new PressBlock();
        
        ELECTRIC_FURNACE = new ElectricFurnaceBlock[MachineTeirs.values().length];
		ELECTRIC_FURNACE[0] = new ElectricFurnaceBlock(MachineTeirs.MINIMAL);
		ELECTRIC_FURNACE[1] = new ElectricFurnaceBlock(MachineTeirs.NORMAL);
		ELECTRIC_FURNACE[2] = new ElectricFurnaceBlock(MachineTeirs.ADVANCED);
    }

    @Override
    public void initialize() {
        Registry.register(Registry.BLOCK, MACHINE_CHASSIS_ID, MACHINE_CHASSIS);
        Registry.register(Registry.BLOCK, EnergyConduitBlock.ID, ENERGY_CONDUIT);
        Registry.register(Registry.BLOCK, CoalGeneratorBlock.ID, COAL_GENERATOR);
        Registry.register(Registry.BLOCK, CrusherBlock.ID, CRUSHER);
        Registry.register(Registry.BLOCK, new Identifier(ElectricFurnaceBlock.ID.getNamespace(), ElectricFurnaceBlock.ID.getPath() + MachineTeirs.values()[0].getAppend()), ELECTRIC_FURNACE[0]);
        Registry.register(Registry.BLOCK, new Identifier(ElectricFurnaceBlock.ID.getNamespace(), ElectricFurnaceBlock.ID.getPath() + MachineTeirs.values()[1].getAppend()), ELECTRIC_FURNACE[1]);
        Registry.register(Registry.BLOCK, new Identifier(ElectricFurnaceBlock.ID.getNamespace(), ElectricFurnaceBlock.ID.getPath() + MachineTeirs.values()[2].getAppend()), ELECTRIC_FURNACE[2]);
        Registry.register(Registry.BLOCK, CrusherBlock.ID, CRUSHER);
        Registry.register(Registry.BLOCK, EnergyBankBlock.ID, ENERGY_BANK);
        Registry.register(Registry.BLOCK, PressBlock.ID, PRESS);
    }
}
