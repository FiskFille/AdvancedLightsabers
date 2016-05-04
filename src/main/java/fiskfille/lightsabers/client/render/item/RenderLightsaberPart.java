package fiskfille.lightsabers.client.render.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.LightsaberAPIClient;
import fiskfille.lightsabers.common.helper.LightsaberHelper;
import fiskfille.lightsabers.common.lightsaber.Lightsaber;
import fiskfille.lightsabers.common.lightsaber.Lightsaber.EnumPartType;

public class RenderLightsaberPart implements IItemRenderer
{
	public EnumPartType partType;

	public RenderLightsaberPart(EnumPartType type)
	{
		partType = type;
	}

	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		return true;
	}

	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{
		return type == type.ENTITY || type == type.INVENTORY;
	}

	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		Lightsaber lightsaber = LightsaberHelper.getLightsaberFromPart(item);
		ModelBase model = LightsaberAPIClient.getModelFor(lightsaber, partType);
		float height = partType == EnumPartType.EMITTER ? lightsaber.getEmitter().height : (partType == EnumPartType.SWITCH_SECTION ? lightsaber.getSwitchSection().height : (partType == partType.BODY ? lightsaber.getBody().height : lightsaber.getPommel().height));
		float f = (height / 2 - (partType == EnumPartType.BODY || partType == EnumPartType.POMMEL ? height : 0)) * 0.0625F;
		float scale = 1.0F;
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Lightsabers.modid, "textures/models/lightsaber/" + partType.name().toLowerCase() + "_" + lightsaber.getName().toLowerCase().replace(' ', '_').replace("(", "").replace(")", "") + ".png"));

		if (type == ItemRenderType.EQUIPPED_FIRST_PERSON)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(-100, 0, 1, 0);
			GL11.glRotatef(-150, 1, 0, 0);
			GL11.glRotatef(5, 0, 0, 1);
			GL11.glTranslatef(0.0F, 0.15F, 0.9F);			

			scale /= 2.5F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.EQUIPPED)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(-90, 0, 1, 0);
			GL11.glRotatef(-150, 1, 0, 0);
			GL11.glRotatef(0, 0, 0, 1);
			GL11.glTranslatef(0.1F, 0.15F, 0.475F);
			GL11.glRotatef(90, 0, 1, 0);
			GL11.glRotatef(-20, 0, 1, 1);
			GL11.glRotatef(-10, 1, 0, 0);
			
			scale /= 2.5F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.ENTITY)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(180, 1, 0, 0);

			scale /= 2.5F;
			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
		else if (type == ItemRenderType.INVENTORY)
		{
			GL11.glPushMatrix();
			GL11.glRotatef(-20, 0, 0, 1);
			GL11.glRotatef(-40, 1, 0, 0);
			GL11.glRotatef(180, 1, 0, 0);
			GL11.glTranslatef(0.0F, 0.05F, 0.0F);
			GL11.glRotatef(-110, 0, 1, 0);
			
			if (partType == EnumPartType.POMMEL && height <= 4)
			{
				scale = 2.0F;
			}
			
			while (height * scale > 20)
			{
				scale -= 0.01F;
			}

			GL11.glScalef(scale, scale, scale);
			GL11.glTranslatef(0, f, 0);
			model.render(null, 0, 0, 0, 0, 0, 0.0625F);
			GL11.glPopMatrix();
		}
	}
}
