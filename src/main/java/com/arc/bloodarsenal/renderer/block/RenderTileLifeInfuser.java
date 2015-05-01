package com.arc.bloodarsenal.renderer.block;

import com.arc.bloodarsenal.tileentity.TileLifeInfuser;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

public class RenderTileLifeInfuser implements ISimpleBlockRenderingHandler
{
    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glPushMatrix();
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        GL11.glScalef(1.0F, 1.0F, 1.0F);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(new TileLifeInfuser(), 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glPopMatrix();
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public int getRenderId() {
        return -1;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }
}
