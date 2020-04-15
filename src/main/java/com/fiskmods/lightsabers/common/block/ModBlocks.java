package com.fiskmods.lightsabers.common.block;

import com.fiskmods.lightsabers.common.item.ItemCrystal;
import com.fiskmods.lightsabers.common.item.ItemDisassemblyTable;
import com.fiskmods.lightsabers.common.item.ItemForcestone;
import com.fiskmods.lightsabers.common.item.ItemHolocron;
import com.fiskmods.lightsabers.common.item.ItemLightsaberForge;
import com.fiskmods.lightsabers.common.item.ItemSithCoffin;
import com.fiskmods.lightsabers.common.item.ItemSithStoneCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntityCrystal;
import com.fiskmods.lightsabers.common.tileentity.TileEntityDisassemblyStation;
import com.fiskmods.lightsabers.common.tileentity.TileEntityHolocron;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberForge;
import com.fiskmods.lightsabers.common.tileentity.TileEntityLightsaberStand;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithCoffin;
import com.fiskmods.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;

public class ModBlocks
{
    public static Block lightsaberCrystal;
//    public static Block lightsaberCrystalGen;
    public static Block lightsaberForgeLight;
    public static Block lightsaberForgeDark;
    public static Block lightsaberStand;
    public static Block disassemblyStation;
    public static Block sithCoffin;
    public static Block sithStoneCoffin;
    public static Block holocron;

    public static Block lightForcestone;
    public static Block lightActivatedForcestone;
    public static Block lightForcestoneStairs;
    public static Block darkForcestone;
    public static Block darkActivatedForcestone;
    public static Block darkForcestoneStairs;
    public static BlockSlab forcestoneDoubleSlab;
    public static BlockSlab forcestoneSlab;

//    public static Block test;

    public static void register()
    {
        lightsaberCrystal = new BlockCrystal();
//        lightsaberCrystalGen = new BlockCrystalGen();
        lightsaberStand = new BlockLightsaberStand();
        disassemblyStation = new BlockDisassemblyStation();
        sithCoffin = new BlockSithCoffin();
        sithStoneCoffin = new BlockSithStoneCoffin();
        holocron = new BlockHolocron();

        lightForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
        lightActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
        lightForcestoneStairs = new BlockModStairs(lightForcestone, 0);
        darkForcestone = new BlockForcestone().setHardness(3.0F).setResistance(100.0F);
        darkActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(3.0F).setResistance(100.0F);
        darkForcestoneStairs = new BlockModStairs(darkForcestone, 0);
        forcestoneDoubleSlab = (BlockSlab) new BlockModSlab(true).setHardness(3.0F).setResistance(100.0F);
        forcestoneSlab = (BlockSlab) new BlockModSlab(false).setHardness(3.0F).setResistance(100.0F);

        lightsaberForgeLight = new BlockLightsaberForge(lightForcestone);
        lightsaberForgeDark = new BlockLightsaberForge(darkForcestone);
//        test = new BlockStructureSpawner();

        BlockRegistry.registerItemBlockAsTileEntity(lightsaberCrystal, "Lightsaber Crystal", TileEntityCrystal.class, ItemCrystal.class);
        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeLight, "Lightsaber Forge Light", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
        BlockRegistry.registerItemBlockAsTileEntity(lightsaberForgeDark, "Lightsaber Forge Dark", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
        BlockRegistry.registerTileEntity(lightsaberStand, "Lightsaber Stand", TileEntityLightsaberStand.class);
        BlockRegistry.registerItemBlockAsTileEntity(disassemblyStation, "Disassembly Station", TileEntityDisassemblyStation.class, ItemDisassemblyTable.class);
        BlockRegistry.registerItemBlockAsTileEntity(sithCoffin, "Sith Coffin", TileEntitySithCoffin.class, ItemSithCoffin.class);
        BlockRegistry.registerItemBlockAsTileEntity(sithStoneCoffin, "Sith Stone Coffin", TileEntitySithStoneCoffin.class, ItemSithStoneCoffin.class);
        BlockRegistry.registerItemBlockAsTileEntity(holocron, "Holocron", TileEntityHolocron.class, ItemHolocron.class);

        BlockRegistry.registerItemBlock(lightForcestone, "Light Forcestone", new ItemForcestone(lightForcestone));
        BlockRegistry.registerBlock(lightActivatedForcestone, "Light Activated Forcestone Pillar");
        BlockRegistry.registerBlock(lightForcestoneStairs, "Light Forcestone Stairs");
        BlockRegistry.registerItemBlock(darkForcestone, "Dark Forcestone", new ItemForcestone(darkForcestone));
        BlockRegistry.registerBlock(darkActivatedForcestone, "Dark Activated Forcestone Pillar");
        BlockRegistry.registerBlock(darkForcestoneStairs, "Dark Forcestone Stairs");
        BlockRegistry.registerItemBlock(forcestoneDoubleSlab, "Forcestone Double Slab", new ItemSlab(forcestoneDoubleSlab, forcestoneSlab, forcestoneDoubleSlab, true));
        BlockRegistry.registerItemBlock(forcestoneSlab, "Forcestone Slab", new ItemSlab(forcestoneSlab, forcestoneSlab, forcestoneDoubleSlab, false));

//        BlockRegistry.registerBlock(lightsaberCrystalGen, "Lightsaber Crystal Gen");
//		BlockRegistry.registerBlock(test, "Test");
    }
}
