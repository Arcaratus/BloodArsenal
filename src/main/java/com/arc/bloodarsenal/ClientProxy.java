package com.arc.bloodarsenal;

import WayofTime.alchemicalWizardry.client.ClientEventHandler;
import com.arc.bloodarsenal.block.ModBlocks;
import com.arc.bloodarsenal.entity.EntityBloodTNT;
import com.arc.bloodarsenal.entity.projectile.EntityBloodBall;
import com.arc.bloodarsenal.entity.projectile.EntityEnderSigilPearl;
import com.arc.bloodarsenal.entity.projectile.EntityGatlingProjectile;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.renderer.block.TileLifeInfuserRenderer;
import com.arc.bloodarsenal.renderer.block.item.TileLifeInfuserItemRenderer;
import com.arc.bloodarsenal.renderer.item.ItemRenderGatling;
import com.arc.bloodarsenal.renderer.projectile.RenderEntityBloodBall;
import com.arc.bloodarsenal.renderer.projectile.RenderEntityGatlingProjectile;
import com.arc.bloodarsenal.renderer.block.TilePortableAltarRenderer;
import com.arc.bloodarsenal.renderer.block.item.TilePortableAltarItemRenderer;
import com.arc.bloodarsenal.tileentity.TileLifeInfuser;
import com.arc.bloodarsenal.tileentity.TilePortableAltar;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerRenders()
    {
        MinecraftForgeClient.registerItemRenderer(ModItems.energy_gatling, new ItemRenderGatling());

        RenderingRegistry.registerEntityRenderingHandler(EntityGatlingProjectile.class, new RenderEntityGatlingProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodTNT.class, new RenderTNTPrimed());
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodBall.class, new RenderSnowball(ModItems.blood_ball));
        RenderingRegistry.registerEntityRenderingHandler(EntityEnderSigilPearl.class, new RenderSnowball(Items.ender_pearl));

        ClientRegistry.bindTileEntitySpecialRenderer(TilePortableAltar.class, new TilePortableAltarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLifeInfuser.class, new TileLifeInfuserRenderer());
    }

    @Override
    public void registerEvents()
    {
        FMLCommonHandler.instance().bus().register(new ClientEventHandler());
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void initRendering()
    {
        MinecraftForgeClient.registerItemRenderer(ItemBlock.getItemFromBlock(ModBlocks.portable_altar), new TilePortableAltarItemRenderer());
        MinecraftForgeClient.registerItemRenderer(ItemBlock.getItemFromBlock(ModBlocks.life_infuser), new TileLifeInfuserItemRenderer());
    }
}
