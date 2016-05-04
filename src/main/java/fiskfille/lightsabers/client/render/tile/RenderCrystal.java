package fiskfille.lightsabers.client.render.tile;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.client.model.tile.ModelCrystal1;
import fiskfille.lightsabers.client.model.tile.ModelCrystal2;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.LightsaberColors;
import fiskfille.lightsabers.common.tileentity.TileEntityCrystal;
 
public class RenderCrystal extends TileEntitySpecialRenderer
{
	private ItemRenderer itemRenderer = new ItemRenderer(Minecraft.getMinecraft());
	private ModelCrystal1 model1 = new ModelCrystal1();
	private ModelCrystal2 model2 = new ModelCrystal2();
       
    public void renderAModelAt(TileEntityCrystal tile, double x, double y, double z, float partialTicks)
    {
    	int metadata = 0;
    	
    	if (tile.getWorldObj() != null)
    	{
    		metadata = tile.getBlockMetadata();
    	}
    	
    	GL11.glPushMatrix();
    	GL11.glTranslatef((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
    	GL11.glScalef(1.0F, -1F, -1F);
    	adjustRotation(tile, metadata);
    	
    	float[] afloat = LightsaberColors.getRGB(LightsaberColors.getColors()[tile.colorId]);
		GL11.glColor4f(afloat[0], afloat[1], afloat[2], 0.75F);
    	GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		ALRenderHelper.setLighting(61680);
    	model2.renderAll();
    	GL11.glEnable(GL11.GL_TEXTURE_2D);
    	GL11.glDisable(GL11.GL_BLEND);
    	
    	GL11.glPopMatrix();
    }
    
    public void adjustRotation(TileEntityCrystal tile, int metadata)
    {
    	int[] aint = {0, 2, 1, 3};
    	
    	if (metadata > 0 && metadata < 5)
    	{
    		GL11.glRotatef(aint[metadata - 1] * 90, 0.0F, 1.0F, 0.0F);
    	}
    	
    	if (metadata == 6)
    	{
    		GL11.glTranslatef(0, 2, 0);
    		GL11.glRotatef(180, 0.0F, 0.0F, 1.0F);
    	}
    	
    	if (metadata != 5 && metadata != 6)
    	{
    		GL11.glTranslatef(1, 1, 0);
    		GL11.glRotatef(90, 0, 0, 1);
    	}
    	
    	Random rand = new Random(tile.xCoord + tile.yCoord + tile.zCoord);
    	GL11.glRotatef(rand.nextInt(360), 0, 1, 0);
    	GL11.glTranslatef(0, (float)rand.nextInt(10) / 40, 0);
    }
    
    public void renderTileEntityAt(TileEntity tileentity, double d, double d1, double d2, float f)
    {
    	renderAModelAt((TileEntityCrystal)tileentity, d, d1, d2, f);
    } 
}
