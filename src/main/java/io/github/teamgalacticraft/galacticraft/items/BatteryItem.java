package io.github.teamgalacticraft.galacticraft.items;

import io.github.cottonmc.energy.impl.SimpleEnergyAttribute;
import io.github.teamgalacticraft.galacticraft.energy.GalacticraftEnergy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.Style;
import net.minecraft.text.TextComponent;
import net.minecraft.text.TextFormat;
import net.minecraft.text.TranslatableTextComponent;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author <a href="https://github.com/teamgalacticraft">TeamGalacticraft</a>
 */
public class BatteryItem extends Item {

    public static final SimpleEnergyAttribute energy = new SimpleEnergyAttribute(15000, GalacticraftEnergy.GALACTICRAFT_JOULES);

    public BatteryItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void buildTooltip(ItemStack stack, World world, List<TextComponent> lines, TooltipContext context) {

        CompoundTag tag = stack.getTag();

        if (tag != null) {
            if (tag.containsKey("Energy")) {

                int energyTag = tag.getInt("Energy");

                if (energyTag < energy.getMaxEnergy() / 3) {
                    lines.add(new TranslatableTextComponent("tooltip.galacticraft-rewoven.energy-remaining", energyTag).setStyle(new Style().setColor(TextFormat.DARK_RED)));
                } else if (energyTag < (energy.getMaxEnergy() / 3) * 2) {
                    lines.add(new TranslatableTextComponent("tooltip.galacticraft-rewoven.energy-remaining", energyTag).setStyle(new Style().setColor(TextFormat.YELLOW)));
                } else {
                    lines.add(new TranslatableTextComponent("tooltip.galacticraft-rewoven.energy-remaining", energyTag).setStyle(new Style().setColor(TextFormat.GREEN)));
                }
                super.buildTooltip(stack, world, lines, context);
            }
        }
    }

    @Override
    public void onCrafted(ItemStack stack, World world, PlayerEntity playerEntity) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Energy", 0);
        tag.putInt("MaxEnergy", energy.getMaxEnergy());
        tag.putInt("Harm", 0);
        stack.setTag(tag);
    }

    // Put this here in case it comes in handy later
    public static int getMaxEnergy() {
        return energy.getMaxEnergy();
    }
}