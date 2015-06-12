package com.arc.bloodarsenal;

import com.arc.bloodarsenal.block.ModBlocks;
import com.arc.bloodarsenal.entity.EntityBloodTNT;
import com.arc.bloodarsenal.entity.mob.EntityBloodHound;
import com.arc.bloodarsenal.entity.projectile.EntityBloodBall;
import com.arc.bloodarsenal.entity.projectile.EntityGatlingProjectile;
import com.arc.bloodarsenal.items.ModItems;
import com.arc.bloodarsenal.items.sigil.SigilUtils;
import com.arc.bloodarsenal.misc.VersionChecker;
import com.arc.bloodarsenal.renderer.block.*;
import com.arc.bloodarsenal.renderer.entity.RenderEntityBloodHound;
import com.arc.bloodarsenal.renderer.item.ItemRenderGatling;
import com.arc.bloodarsenal.renderer.item.RenderBow;
import com.arc.bloodarsenal.renderer.projectile.RenderEntityGatlingProjectile;
import com.arc.bloodarsenal.renderer.block.TilePortableAltarRenderer;
import com.arc.bloodarsenal.renderer.block.item.TilePortableAltarItemRenderer;
import com.arc.bloodarsenal.tileentity.TileLifeInfuser;
import com.arc.bloodarsenal.tileentity.TilePortableAltar;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
    @Override
    public void init()
    {
        registerRenders();
        registerEvents();
        getClientWorld();
        initRendering();
    }

    @Override
    public void registerRenders()
    {
        MinecraftForgeClient.registerItemRenderer(ModItems.energy_gatling, new ItemRenderGatling());
        MinecraftForgeClient.registerItemRenderer(ModItems.bound_bow, new RenderBow());

        RenderingRegistry.registerEntityRenderingHandler(EntityGatlingProjectile.class, new RenderEntityGatlingProjectile());
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodTNT.class, new RenderTNTPrimed());
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodBall.class, new RenderSnowball(ModItems.blood_ball));
        RenderingRegistry.registerEntityRenderingHandler(EntityBloodHound.class, new RenderEntityBloodHound(new ModelWolf(), new ModelWolf(), 0.5F));

        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.life_infuser), new RenderTileLifeInfuser());

        ClientRegistry.bindTileEntitySpecialRenderer(TilePortableAltar.class, new TilePortableAltarRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileLifeInfuser.class, new TileLifeInfuserRenderer());

        ShaderHelper.initShaders();
    }

    @Override
    public void registerEvents()
    {
        if (BloodArsenalConfig.versionCheckingAllowed)
        {
            new VersionChecker().init();
        }

        MinecraftForge.EVENT_BUS.register(new SigilUtils());
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
    }
}
