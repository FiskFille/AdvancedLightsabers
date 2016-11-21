package fiskfille.lightsabers.client.render.tile;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.model.tile.ModelLightsaberForge;
import fiskfille.lightsabers.common.block.BlockLightsaberForge;
import fiskfille.lightsabers.common.block.ModBlocks;
import fiskfille.lightsabers.common.tileentity.TileEntityLightsaberForge;

public class RenderLightsaberForge extends TileEntitySpecialRenderer
{
	private ResourceLocation textureLight = new ResourceLocation(Lightsabers.modid, "textures/models/lightsaber_forge_light.png");
	private ResourceLocation textureDark = new ResourceLocation(Lightsabers.modid, "textures/models/lightsaber_forge_dark.png");
	private ModelLightsaberForge model = new ModelLightsaberForge();

	public void render(TileEntityLightsaberForge tileentity, double x, double y, double z, float partialTicks)
	{
		int metadata = 0;

		if (tileentity.getWorldObj() != null)
		{
			metadata = tileentity.getBlockMetadata();
		}

		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
		GL11.glScalef(1.0F, -1F, -1F);
		GL11.glRotatef(BlockLightsaberForge.getDirection(metadata) * 90 + 180, 0.0F, 1.0F, 0.0F);

		if (!BlockLightsaberForge.isBlockSideOfPanel(metadata))
		{
			bindTexture(tileentity.getBlockType() == ModBlocks.lightsaberForgeDark ? textureDark : textureLight);
			model.render();
		}

		GL11.glPopMatrix();
	}

	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks)
	{
		render((TileEntityLightsaberForge)tileentity, x, y, z, partialTicks);
	} 
}
