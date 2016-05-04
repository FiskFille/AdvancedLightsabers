package fiskfille.lightsabers.common.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import fiskfille.lightsabers.common.generator.structure.StructureSithTomb;

public class BlockStructureSpawner extends BlockBasic
{
	public BlockStructureSpawner()
	{
		super(Material.rock);
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ)
	{
		spawnStructure(world, x, y, z);
		return true;
	}

	private void spawnStructure(World world, int x, int y, int z)
	{
		StructureSithTomb structure = new StructureSithTomb(world, x, y, z);
		structure.spawnStructure(new Random());
	}
}
