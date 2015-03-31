package com.arc.bloodarsenal.block;

import WayofTime.alchemicalWizardry.*;
import WayofTime.alchemicalWizardry.ModBlocks;
import WayofTime.alchemicalWizardry.api.event.RitualActivatedEvent;
import WayofTime.alchemicalWizardry.api.event.RitualRunEvent;
import WayofTime.alchemicalWizardry.api.rituals.*;
import WayofTime.alchemicalWizardry.common.tileEntity.TEMasterStone;
import com.arc.bloodarsenal.BloodArsenal;
import com.arc.bloodarsenal.tileentity.TileCompacter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class BlockCompacter extends BlockContainer
{
    public BlockCompacter()
    {
        super(Material.iron);
        setBlockName("compacter");
        setCreativeTab(BloodArsenal.BA_TAB);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are)
    {
        TileEntity tile = world.getTileEntity(x, y + 1, z);

        if (tile instanceof TEMasterStone)
        {
            TEMasterStone ritualStone = (TEMasterStone) tile;
            String test = Rituals.checkValidRitual(world, x, y + 1, z);

            if (!test.equals(""))
            {
                player.addChatMessage(new ChatComponentText("It worked"));

                if (ritualStone.getCurrentRitual().equals(test))
                {
                    player.addChatMessage( new ChatComponentText(test));
                }
            }
            else
            {
                player.addChatMessage(new ChatComponentText("It failed"));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileCompacter();
    }
}
