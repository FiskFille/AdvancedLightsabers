package fiskfille.lightsabers.common.helper;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.LightsaberAPI;
import fiskfille.lightsabers.client.sound.ALSounds;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.container.InventoryLightsaberForge;
import fiskfille.lightsabers.common.entity.EntitySithGhost;
import fiskfille.lightsabers.common.item.ItemLightsaberBase;
import fiskfille.lightsabers.common.item.ModItems;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;
import fiskfille.lightsabers.common.lightsaber.LightsaberManager;
import fiskfille.lightsabers.common.network.ALNetworkManager;
import fiskfille.lightsabers.common.network.PacketIgniteLightsaber;

public class LightsaberHelper
{
	public static final float MIN_LENGTH_CM = 22;
	
	public static int getColorId(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("Lightsaber");
		return nbttagcompound == null ? 0 : (nbttagcompound.hasKey("color") ? nbttagcompound.getInteger("color") : 0);
	}
	
	public static int[] getFocusingCrystalIds(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("Lightsaber");
		return nbttagcompound == null ? new int[] {} : (nbttagcompound.hasKey("FocusingCrystals") ? nbttagcompound.getIntArray("FocusingCrystals") : new int[] {});
	}
	
	public static String getPartString(ItemStack itemstack, EnumPartType type)
	{
		NBTTagCompound nbttagcompound = itemstack.getTagCompound();
		String s = type.name().toLowerCase();
		
		if (nbttagcompound == null)
		{
			return LightsaberManager.lightsaberGraflex.getName();
		}
		else
		{
			NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Lightsaber");
			return nbttagcompound1 == null ? LightsaberManager.lightsaberGraflex.getName() : (nbttagcompound1.hasKey(s) ? nbttagcompound1.getString(s) : LightsaberManager.lightsaberGraflex.getName());
		}
	}
	
	public static Lightsaber getPart(ItemStack itemstack, EnumPartType type)
	{
		Lightsaber lightsaber = LightsaberAPI.getLightsaberByName(getPartString(itemstack, type));
		
		if (lightsaber == null)
		{
			lightsaber = LightsaberManager.lightsaberGraflex;
		}
		
		return lightsaber;
	}
	
	public static EnumPartType getPartType(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		return EnumPartType.valueOf(itemstack.getTagCompound().getString("type").toUpperCase());
	}
	
	public static ItemStack createLightsaber(int colorId, Lightsaber emitter, Lightsaber switchSection, Lightsaber body, Lightsaber pommel, int... focusingCrystalIds)
	{
		ItemStack itemstack = new ItemStack(ModItems.lightsaber);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		
		nbttagcompound1.setInteger("color", colorId);
		nbttagcompound1.setString("emitter", emitter.getName());
		nbttagcompound1.setString("switch_section", switchSection.getName());
		nbttagcompound1.setString("body", body.getName());
		nbttagcompound1.setString("pommel", pommel.getName());
		
		if (focusingCrystalIds != null && focusingCrystalIds.length > 0)
		{
			nbttagcompound1.setIntArray("FocusingCrystals", focusingCrystalIds);
		}
		
		nbttagcompound.setTag("Lightsaber", nbttagcompound1);
		itemstack.setTagCompound(nbttagcompound);
		return itemstack;
	}
	
	public static ItemStack createLightsaber(int colorId, Lightsaber lightsaber, int... focusingCrystalIds)
	{
		return createLightsaber(colorId, lightsaber, lightsaber, lightsaber, lightsaber, focusingCrystalIds);
	}
	
	public static ItemStack createRandomLightsaber(Random random, int colorId)
	{
		Lightsaber[] parts = new Lightsaber[4];
		List<Integer> list = Lists.newArrayList();
		int color = colorId;
		
		if (colorId == -1)
		{
			color = random.nextInt(LightsaberColors.getColors().length);
		}
		
		for (int i = 0; i < 4; ++i)
		{
			parts[i] = LightsaberAPI.getLightsabers().get(random.nextInt(LightsaberAPI.getLightsabers().size()));
		}
		
		int j = -1;
		
		for (int i = 0; i < 2; ++i)
		{
			if (random.nextFloat() < (i == 0 ? 0.25F : 0.125F))
			{
				int k = random.nextInt(FocusingCrystals.getFocusingCrystals().length);
				
				if (j != k)
				{
					list.add(k);
				}
				
				j = k;
			}
		}
		
		int[] aint = new int[list.size()];
		
		for (int i = 0; i < list.size(); ++i)
		{
			aint[i] = list.get(i);
		}
		
		ItemStack itemstack = createLightsaber(color, parts[0], parts[1], parts[2], parts[3], aint);
		
		if (getLightsaberHeightCm(itemstack) < MIN_LENGTH_CM)
		{
			return createRandomLightsaber(random, colorId);
		}
		
		return itemstack;
	}
	
	public static ItemStack createRandomLightsaber(Random random)
	{
		return createRandomLightsaber(random, -1);
	}
	
	public static ItemStack createDoubleLightsaber(ItemStack upperLightsaber, ItemStack lowerLightsaber)
	{
		ItemStack itemstack = new ItemStack(ModItems.doubleLightsaber);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("UpperLightsaber", upperLightsaber.getTagCompound().getCompoundTag("Lightsaber"));
		nbttagcompound.setTag("LowerLightsaber", lowerLightsaber.getTagCompound().getCompoundTag("Lightsaber"));
		itemstack.setTagCompound(nbttagcompound);
		return itemstack;
	}
	
	public static ItemStack getDoubleLightsaberUpper(ItemStack itemstack)
	{
		ItemStack itemstack1 = new ItemStack(ModItems.lightsaber);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("Lightsaber", itemstack.getTagCompound().getCompoundTag("UpperLightsaber"));
		nbttagcompound.setBoolean("active", itemstack.getTagCompound().getBoolean("active"));
		itemstack1.setTagCompound(nbttagcompound);
		return itemstack1;
	}
	
	public static ItemStack getDoubleLightsaberLower(ItemStack itemstack)
	{
		ItemStack itemstack1 = new ItemStack(ModItems.lightsaber);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setTag("Lightsaber", itemstack.getTagCompound().getCompoundTag("LowerLightsaber"));
		nbttagcompound.setBoolean("active", itemstack.getTagCompound().getBoolean("active"));
		itemstack1.setTagCompound(nbttagcompound);
		return itemstack1;
	}
	
	public static ItemStack createLightsaberPart(Lightsaber lightsaber, EnumPartType type)
	{
		ItemStack itemstack = new ItemStack(type == type.EMITTER ? ModItems.lightsaberEmitter : (type == type.SWITCH_SECTION ? ModItems.lightsaberSwitchSection : (type == type.BODY ? ModItems.lightsaberBody : ModItems.lightsaberPommel)));
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		
		nbttagcompound.setString("type", type.name().toLowerCase());
		nbttagcompound.setString("lightsaber", lightsaber.getName());
		
		itemstack.setTagCompound(nbttagcompound);
		return itemstack;
	}
	
	public static Lightsaber getLightsaberFromPart(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		return LightsaberAPI.getLightsaberByName(itemstack.getTagCompound().getString("lightsaber"));
	}
	
	public static ItemStack getLightsaberForgeResult(InventoryLightsaberForge inventory, World world)
	{
		ItemStack emitter = inventory.getStackInSlot(0);
		ItemStack switchSection = inventory.getStackInSlot(1);
		ItemStack body = inventory.getStackInSlot(2);
		ItemStack pommel = inventory.getStackInSlot(3);
		ItemStack circuitry = inventory.getStackInSlot(4);
		ItemStack crystal = inventory.getStackInSlot(5);
		ItemStack focusingCrystal1 = inventory.getStackInSlot(6);
		ItemStack focusingCrystal2 = inventory.getStackInSlot(7);
		
		if (emitter != null && emitter.getItem() == ModItems.lightsaberEmitter && switchSection != null && switchSection.getItem() == ModItems.lightsaberSwitchSection && body != null && body.getItem() == ModItems.lightsaberBody && pommel != null && pommel.getItem() == ModItems.lightsaberPommel && circuitry != null && circuitry.getItem() == ModItems.lightsaberCircuitry && crystal != null && crystal.getItem() == Item.getItemFromBlock(ModBlocks.lightsaberCrystal))
		{
			int color = getCrystalColorId(crystal);
			List<Integer> list = Lists.newArrayList();
			
			if (focusingCrystal1 != null)
			{
				list.add(getFocusingCrystalId(focusingCrystal1));
			}
			
			if (focusingCrystal2 != null)
			{
				list.add(getFocusingCrystalId(focusingCrystal2));
			}
			
			int[] aint = new int[list.size()];
			
			for (int i = 0; i < list.size(); ++i)
			{
				aint[i] = list.get(i);
			}
			
			return createLightsaber(color, getLightsaberFromPart(emitter), getLightsaberFromPart(switchSection), getLightsaberFromPart(body), getLightsaberFromPart(pommel), aint);
		}
		
		return null;
	}

	public static ItemStack getEquippedLightsaber(EntityPlayer player)
	{
		InventoryPlayer inventory = player.inventory;
		
		for (int i = 0; i < inventory.mainInventory.length; ++i)
		{
			if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() == ModItems.lightsaber)
			{
				return inventory.mainInventory[i];
			}
		}
		
		return null;
	}
	
	public static int getCrystalColorId(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		return itemstack.getTagCompound().getInteger("color");
	}
	
	public static ItemStack createCrystal(int colorId)
	{
		ItemStack itemstack = new ItemStack(ModBlocks.lightsaberCrystal);
		ItemLightsaberBase.refreshNBT(itemstack);
		itemstack.getTagCompound().setInteger("color", colorId);
		return itemstack;
	}
	
	public static int getCrystalIdFromColor(int color)
	{
		for (int i = 0; i < LightsaberColors.getColors().length; ++i)
		{
			if (color == LightsaberColors.getColors()[i])
			{
				return i;
			}
		}
		
		return 0;
	}
	
	public static int getFocusingCrystalId(ItemStack itemstack)
	{
		ItemLightsaberBase.refreshNBT(itemstack);
		return itemstack.getTagCompound().getInteger("FocusingCrystal");
	}
	
	public static ItemStack createFocusingCrystal(int crystalId)
	{
		ItemStack itemstack = new ItemStack(ModItems.focusingCrystal, 1, crystalId);
		ItemLightsaberBase.refreshNBT(itemstack);
		itemstack.getTagCompound().setInteger("FocusingCrystal", crystalId);
		return itemstack;
	}
	
	public static int getFocusingCrystalIdFromName(String name)
	{
		for (int i = 0; i < FocusingCrystals.getFocusingCrystals().length; ++i)
		{
			if (name.equals(FocusingCrystals.getFocusingCrystals()[i]))
			{
				return i;
			}
		}
		
		return 0;
	}
	
	public static void igniteLightsaberWithoutNotify(EntityLivingBase entity, boolean state)
	{
		ItemStack itemstack = entity.getHeldItem();
		
		if (itemstack != null)
		{
			ItemLightsaberBase.refreshNBT(itemstack);
			itemstack.getTagCompound().setBoolean("active", state);
			
			String[] sounds = {ALSounds.mob_lightsaber_on, ALSounds.mob_lightsaber_off};
			
			if (entity instanceof EntityPlayer)
			{
				sounds = new String[] {ALSounds.player_lightsaber_on, ALSounds.player_lightsaber_off};
			}
			
			entity.playSound(sounds[state ? 0 : 1], 1.0F, 1.0F);
		}
	}
	
	public static void igniteLightsaber(EntityLivingBase entity, boolean state)
	{
		ItemStack itemstack = entity.getHeldItem();
		
		if (itemstack != null)
		{
			ItemLightsaberBase.refreshNBT(itemstack);
			
			if (itemstack.getTagCompound().getBoolean("active") != state)
			{
				if (entity.worldObj.isRemote)
				{
					ALNetworkManager.networkWrapper.sendToServer(new PacketIgniteLightsaber(entity, state));
				}
				else
				{
					ALNetworkManager.networkWrapper.sendToDimension(new PacketIgniteLightsaber(entity, state), entity.dimension);
				}

				igniteLightsaberWithoutNotify(entity, state);
			}
		}
	}
	
	public static boolean isBlockable(Entity entity)
	{
		return entity instanceof IProjectile
			|| entity instanceof EntityPlayer
			|| entity instanceof EntitySithGhost;
	}
	
	public static boolean hasFocusingCrystal(ItemStack itemstack, String id)
	{
		int[] crystalIds = getFocusingCrystalIds(itemstack);
		List<String> focusingCrystals = Lists.newArrayList();
		
		for (int i : crystalIds)
		{
			focusingCrystals.add(FocusingCrystals.getFocusingCrystals()[i]);
		}
		
		return focusingCrystals.contains(id);
	}
	
	public static float getLightsaberHeight(ItemStack itemstack)
    {
    	if (itemstack.getItem() == ModItems.doubleLightsaber)
    	{
    		return getLightsaberHeight(getDoubleLightsaberUpper(itemstack)) + getLightsaberHeight(getDoubleLightsaberLower(itemstack));
    	}
    	
    	return getPart(itemstack, EnumPartType.EMITTER).getEmitter().height + getPart(itemstack, EnumPartType.SWITCH_SECTION).getSwitchSection().height + getPart(itemstack, EnumPartType.BODY).getBody().height + getPart(itemstack, EnumPartType.POMMEL).getPommel().height;
    }
	
	public static float getLightsaberHeightCm(ItemStack itemstack)
    {
		return getLightsaberHeight(itemstack) * 0.575F;
    }
}
