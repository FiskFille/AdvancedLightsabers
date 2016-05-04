package fiskfille.lightsabers.common.entity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityForce extends EntityThrowable
{	
	private String type;
	
    public EntityForce(World par1World)
    {
        super(par1World);
    }

    public EntityForce(World par1World, EntityLivingBase par2EntityLivingBase, String par3Str)
    {
        super(par1World, par2EntityLivingBase);
        this.setSize(1.2F, 1.2F);
        this.type = par3Str;
    }

    public EntityForce(World par1World, double par2, double par4, double par6)
    {
        super(par1World, par2, par4, par6);
    }
    
    protected float getGravityVelocity()
    {
        return 5.0F;
    }

    protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
    {    	
    	if (!this.worldObj.isRemote)
        {
    		if (this.type.equals("strafing"))
    		{
    			AxisAlignedBB axisalignedbb = this.boundingBox.expand(0.5D, 1.0D, 0.5D);
    			List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityLivingBase entitylivingbase = (EntityLivingBase)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            if (entitylivingbase instanceof EntityPlayer == false)
                            {
                                entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(Minecraft.getMinecraft().thePlayer), 10.0F);
                            }
                        }
                    }
                }
    		}
    		
    		
    		if (this.type.equals("blocking"))
    		{
    			AxisAlignedBB axisalignedbb = this.boundingBox.expand(0.2D, 0.2D, 0.2D);
    			List list1 = this.worldObj.getEntitiesWithinAABB(EntityArrow.class, axisalignedbb);

                if (list1 != null && !list1.isEmpty())
                {
                    Iterator iterator = list1.iterator();

                    while (iterator.hasNext())
                    {
                        EntityArrow entitylivingbase = (EntityArrow)iterator.next();
                        double d0 = this.getDistanceSqToEntity(entitylivingbase);

                        if (d0 < 16.0D)
                        {
                            double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                            entitylivingbase.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2.5F, 1.0F);
                            Minecraft.getMinecraft().thePlayer.swingItem();
                        }
                    }
                }
    		}
    		
    		
    		
    		
    		
    		
    		
    		
    		
    		
		}
    }
}
