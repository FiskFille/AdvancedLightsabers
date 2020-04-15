package com.fiskmods.lightsabers.common.block;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import com.fiskmods.lightsabers.common.generator.structure.Structure;
import com.fiskmods.lightsabers.common.generator.structure.StructureSithTomb;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockStructureSpawner extends BlockBasic
{
    public BlockStructureSpawner()
    {
        super(Material.rock);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        if (Keyboard.isKeyDown(Keyboard.KEY_M))
        {
            spawnStructure(world, x, y, z);
        }

        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float hitX, float hitY, float hitZ)
    {
        spawnStructure(world, x, y, z);
        return true;
    }

    private void spawnStructure(World world, int x, int y, int z)
    {
        Structure structure = new StructureSithTomb(world, x, y, z);
        Random rand = new Random();

//		rand.setSeed(574935734654L);
        structure.spawnStructure(rand);
    }
}
