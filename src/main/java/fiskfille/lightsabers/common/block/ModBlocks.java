package fiskfille.lightsabers.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemSlab;
import fiskfille.lightsabers.common.item.ItemCrystal;
import fiskfille.lightsabers.common.item.ItemLightsaberForge;
import fiskfille.lightsabers.common.item.ItemSithCoffin;
import fiskfille.lightsabers.common.item.ItemSithStoneCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntityCrystal;
import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;
import fiskfille.lightsabers.common.tileentity.TileEntitySithCoffin;
import fiskfille.lightsabers.common.tileentity.TileEntitySithStoneCoffin;

public class ModBlocks
{
	public static Block lightsaberCrystal;
	public static Block lightsaberForge;
	public static Block sithCoffin;
	public static Block sithStoneCoffin;
	public static Block lightForcestone;
	public static Block lightActivatedForcestone;
	public static Block lightForcestoneStairs;
	public static Block darkForcestone;
	public static Block darkActivatedForcestone;
	public static Block darkForcestoneStairs;
	public static BlockSlab forcestoneDoubleSlab;
	public static BlockSlab forcestoneSlab;
	
	public static Block test;
	
	public static void register()
	{
		lightsaberCrystal = new BlockCrystal();
		lightsaberForge = new BlockLightsaberForge();
		sithCoffin = new BlockSithCoffin();
		sithStoneCoffin = new BlockSithStoneCoffin();
		lightForcestone = new BlockForcestone().setHardness(5.0F).setResistance(10.0F);
		lightActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(5.0F).setResistance(10.0F);
		lightForcestoneStairs = new BlockModStairs(lightForcestone, 0);
		darkForcestone = new BlockForcestone().setHardness(5.0F).setResistance(10.0F);
		darkActivatedForcestone = new BlockPillar().setLightLevel(1.0F).setHardness(5.0F).setResistance(10.0F);
		darkForcestoneStairs = new BlockModStairs(darkForcestone, 0);
		forcestoneDoubleSlab = (BlockSlab)new BlockModSlab(true).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston);
		forcestoneSlab = (BlockSlab)new BlockModSlab(false).setHardness(5.0F).setResistance(10.0F).setStepSound(Block.soundTypePiston);
//		test = new BlockStructureSpawner();
		
		
		BlockRegistry.registerItemBlockAsTileEntity(lightsaberCrystal, "Lightsaber Crystal", TileEntityCrystal.class, ItemCrystal.class);
		BlockRegistry.registerItemBlockAsTileEntity(lightsaberForge, "Lightsaber Forge", TileEntityLightsaberForge.class, ItemLightsaberForge.class);
		BlockRegistry.registerItemBlockAsTileEntity(sithCoffin, "Sith Coffin", TileEntitySithCoffin.class, ItemSithCoffin.class);
		BlockRegistry.registerItemBlockAsTileEntity(sithStoneCoffin, "Sith Stone Coffin", TileEntitySithStoneCoffin.class, ItemSithStoneCoffin.class);
		BlockRegistry.registerItemBlock(lightForcestone, "Light Forcestone", new ItemMultiTexture(lightForcestone, lightForcestone, BlockForcestone.nameStrings));
		BlockRegistry.registerBlock(lightActivatedForcestone, "Light Activated Forcestone Pillar");
		BlockRegistry.registerBlock(lightForcestoneStairs, "Light Forcestone Stairs");
		BlockRegistry.registerItemBlock(darkForcestone, "Dark Forcestone", new ItemMultiTexture(darkForcestone, darkForcestone, BlockForcestone.nameStrings));
		BlockRegistry.registerBlock(darkActivatedForcestone, "Dark Activated Forcestone Pillar");
		BlockRegistry.registerBlock(darkForcestoneStairs, "Dark Forcestone Stairs");
		BlockRegistry.registerItemBlock(forcestoneDoubleSlab, "Forcestone Double Slab", new ItemSlab(forcestoneDoubleSlab, forcestoneSlab, forcestoneDoubleSlab, true));
		BlockRegistry.registerItemBlock(forcestoneSlab, "Forcestone Slab", new ItemSlab(forcestoneSlab, forcestoneSlab, forcestoneDoubleSlab, false));
//		BlockRegistry.registerBlock(test, "Test");
	}
}
