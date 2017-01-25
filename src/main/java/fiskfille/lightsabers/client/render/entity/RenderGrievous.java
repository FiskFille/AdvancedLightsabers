package fiskfille.lightsabers.client.render.entity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;
import fiskfille.lightsabers.client.model.entity.ModelGrievous;
import fiskfille.lightsabers.client.model.tools.MowzieModelRenderer;
import fiskfille.lightsabers.common.entity.EntityGrievous;

@SideOnly(Side.CLIENT)
public class RenderGrievous extends RenderBiped
{
    private static final ResourceLocation textures = new ResourceLocation(Lightsabers.modid, "textures/models/general_grievous.png");
    private final ModelGrievous model;
    
    public RenderGrievous()
    {
        this(new ModelGrievous(), 0.5F);
    }
    
    public RenderGrievous(ModelGrievous biped, float f)
    {
        super(biped, f);
        model = biped;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity)
    {
        return textures;
    }
    
    @Override
    protected void renderEquippedItems(EntityLiving entity, float partialTicks)
    {
        renderEquippedItems((EntityGrievous) entity, partialTicks);
    }

    protected void renderEquippedItems(EntityGrievous entity, float partialTicks)
    {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        MowzieModelRenderer[] hands = {model.handR1_1, model.handL1_1, model.handR2_1, model.handL2_1};
        
        for (int slot = 0; slot < 4; ++slot)
        {
            ItemStack heldItem = entity.getHeldItem(slot);
            float scale;

            if (heldItem != null && heldItem.getItem() != null)
            {
                Item item = heldItem.getItem();
                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(heldItem, ItemRenderType.EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(ItemRenderType.EQUIPPED, heldItem, ItemRendererHelper.BLOCK_3D));
                boolean right = slot % 2 == 0;
                
                GL11.glPushMatrix();

                if (mainModel.isChild)
                {
                    scale = 0.5F;
                    GL11.glTranslatef(0.0F, 0.625F, 0.0F);
                    GL11.glRotatef(-20.0F, -1.0F, 0.0F, 0.0F);
                    GL11.glScalef(scale, scale, scale);
                }

                hands[slot].postRenderParentChain(0.0625F);
                
                if (right)
                {
                    GL11.glTranslatef(0, 0.035F, 0);
                }
                
                if (item instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.getBlockFromItem(item).getRenderType())))
                {
                    scale = 0.5F;
                    GL11.glTranslatef(0, 0.1875F, -0.3125F);
                    scale *= 0.75F;
                    GL11.glRotatef(20, 1, 0, 0);
                    GL11.glRotatef(45, 0, 1, 0);
                    GL11.glScalef(-scale, -scale, scale);
                }
                else if (item == Items.bow)
                {
                    scale = 0.625F;
                    GL11.glTranslatef(0, 0.125F, 0.3125F);
                    GL11.glRotatef(-20, 0, 1, 0);
                    GL11.glScalef(scale, -scale, scale);
                    GL11.glRotatef(-100, 1, 0, 0);
                    GL11.glRotatef(45, 0, 1, 0);
                }
                else if (item.isFull3D())
                {
                    scale = 0.625F;

                    if (item.shouldRotateAroundWhenRendering())
                    {
                        GL11.glRotatef(180, 0, 0, 1);
                        GL11.glTranslatef(0, -0.125F, 0);
                    }
                    
                    if (right)
                    {
                        GL11.glTranslatef(0.06F, 0, 0);
                    }

                    func_82422_c();
                    GL11.glScalef(scale, -scale, scale);
                    GL11.glRotatef(-100, 1, 0, 0);
                    GL11.glRotatef(41 + 10 * (right ? -1 : 1), 0, 1, 0);
                }
                else
                {
                    scale = 0.375F;
                    GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glRotatef(60, 0, 0, 1);
                    GL11.glRotatef(-90, 1, 0, 0);
                    GL11.glRotatef(20, 0, 0, 1);
                }
                
                GL11.glTranslatef(0.0F, -0.125F, 0.05F * (right ? 1 : -1));

                if (heldItem.getItem().requiresMultipleRenderPasses())
                {
                    for (int pass = 0; pass < heldItem.getItem().getRenderPasses(heldItem.getItemDamage()); ++pass)
                    {
                        int color = heldItem.getItem().getColorFromItemStack(heldItem, pass);
                        float r = (color >> 16 & 255) / 255.0F;
                        float g = (color >> 8 & 255) / 255.0F;
                        float b = (color & 255) / 255.0F;
                        
                        GL11.glColor4f(r, g, b, 1.0F);
                        renderManager.itemRenderer.renderItem(entity, heldItem, pass);
                    }
                }
                else
                {
                    int color = heldItem.getItem().getColorFromItemStack(heldItem, 0);
                    float r = (color >> 16 & 255) / 255.0F;
                    float g = (color >> 8 & 255) / 255.0F;
                    float b = (color & 255) / 255.0F;
                    
                    GL11.glColor4f(r, g, b, 1.0F);
                    renderManager.itemRenderer.renderItem(entity, heldItem, 0);
                }

                GL11.glPopMatrix();
            }
        }
    }
}
