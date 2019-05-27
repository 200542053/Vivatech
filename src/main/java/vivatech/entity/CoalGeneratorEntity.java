package vivatech.entity;

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.container.PropertyDelegate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.math.Direction;
import vivatech.energy.IEnergyHolder;
import vivatech.energy.IEnergyStorage;
import vivatech.energy.SimpleEnergyGenerator;
import vivatech.init.VivatechEntities;

import javax.annotation.Nullable;

public class CoalGeneratorEntity extends BlockEntity implements SidedInventory, IEnergyHolder, PropertyDelegateHolder {

    private final int invSize = 1;
    private DefaultedList<ItemStack> inventory = DefaultedList.create(invSize, ItemStack.EMPTY);
    private IEnergyStorage energyStorage = new SimpleEnergyGenerator(100);
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int propertyId) {
            switch (propertyId) {
                case 0:
                    return energyStorage.getCurrentEnergy();
                case 1:
                    return energyStorage.getMaxEnergy();
                default:
                    return 0;
            }
        }

        @Override
        public void set(int propertyId, int value) {
            switch (propertyId) {
                case 0:
                case 1:
                default:
                    break;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };


    public CoalGeneratorEntity() {
        super(VivatechEntities.COAL_GENERATOR);
    }

    // BlockEntity
    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        inventory = DefaultedList.create(invSize, ItemStack.EMPTY);
        Inventories.fromTag(tag, inventory);
        energyStorage.readEnergyFromTag(tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, inventory);
        energyStorage.writeEnergyToTag(tag);
        return tag;
    }

    // SidedInventory
    @Override
    public int[] getInvAvailableSlots(Direction direction) {
        return new int[] {0};
    }

    @Override
    public boolean canInsertInvStack(int slot, ItemStack itemStack, @Nullable Direction direction) {
        return slot == 0 && FurnaceBlockEntity.canUseAsFuel(itemStack);
    }

    @Override
    public boolean canExtractInvStack(int slot, ItemStack itemStack, Direction direction) {
        return true;
    }

    @Override
    public int getInvSize() {
        return invSize;
    }

    @Override
    public boolean isInvEmpty() {
        for (ItemStack itemStack : inventory) if (!itemStack.isEmpty()) return false;
        return true;
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int parts) {
        return Inventories.splitStack(inventory, slot, parts);
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public void setInvStack(int slot, ItemStack itemStack) {
        inventory.set(slot, itemStack);
        if (itemStack.getAmount() > getInvMaxStackAmount()) {
            itemStack.setAmount(getInvMaxStackAmount());
        }
        markDirty();
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        if (world.getBlockEntity(pos) != this) {
            return false;
        } else {
            return player.squaredDistanceTo(
                    (double) pos.getX() + 0.5D,
                    (double) pos.getY() + 0.5D,
                    (double) pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clear() {
        inventory.clear();
    }


    // IEnergyHolder
    @Override
    public IEnergyStorage getEnergyStorage() {
        return energyStorage;
    }

    // PropertyDelegateHolder
    @Override
    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }
}
