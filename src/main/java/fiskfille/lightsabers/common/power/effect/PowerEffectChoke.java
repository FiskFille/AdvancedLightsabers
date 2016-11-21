package fiskfille.lightsabers.common.power.effect;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.VectorHelper;
import fiskfille.lightsabers.common.power.PowerDesc;

public class PowerEffectChoke extends PowerEffect
{
	@Override
	public boolean execute(EntityPlayer player, Side side, Object... args)
	{
		World world = player.worldObj;
		int amplifier = (Integer)args[0];
		double range = 16;

		Vec3 src = VectorHelper.getOffsetCoords(player, 0, 0, 0);
		Vec3 dest = VectorHelper.getOffsetCoords(player, 0, 0, range);
		Vec3 hitVec = null;
		MovingObjectPosition rayTrace = world.rayTraceBlocks(VectorHelper.copy(src), VectorHelper.copy(dest));

		if (rayTrace == null)
		{
			hitVec = dest;
		}
		else
		{
			hitVec = rayTrace.hitVec;
		}

		double distance = player.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);

		for (double point = 0; point <= distance; point += 0.15D)
		{
			Vec3 particleVec = VectorHelper.getOffsetCoords(player, 0, 0, point);

			for (EntityLivingBase entity : VectorHelper.getEntitiesNear(world, particleVec.xCoord, particleVec.yCoord, particleVec.zCoord, 0.5F))
			{
				if (entity != null && entity != player && player.ridingEntity != entity)
				{
					hitVec.xCoord = entity.posX;
					hitVec.yCoord = entity.posY;
					hitVec.zCoord = entity.posZ;
					rayTrace = new MovingObjectPosition(entity, hitVec);
					distance = player.getDistance(hitVec.xCoord, hitVec.yCoord, hitVec.zCoord);
					break;
				}
			}
		}

		if (rayTrace != null)
		{
			if (rayTrace.typeOfHit == MovingObjectType.ENTITY && rayTrace.entityHit instanceof EntityLivingBase)
			{
				EntityLivingBase entity = (EntityLivingBase) rayTrace.entityHit;
				DataManager.addEffect(entity, player, Effect.choke.id, 60, amplifier);

				return true;
			}
		}

		return false;
	}

	@Override
	public String[] getDesc(Object... args)
	{
		return new String[]
		{
			PowerDesc.create("effect", PowerDesc.format("%s %s%s", 2 + (Integer)args[0] * 2, PowerDesc.DAMAGE, EnumChatFormatting.GRAY + "/"), PowerDesc.TARGET),
			PowerDesc.create("effect", PowerDesc.format("%s %s%s", Effect.stun, EnumChatFormatting.GRAY, getStunDuration((Integer)args[0])), PowerDesc.TARGET)
		};
	}
	
	public static float getStunDuration(int amplifier)
	{
		float f = 1.5F;
		
		if (amplifier > 0)
		{
			f += 1 + (amplifier - 1) * 0.5F;
		}
		
		return f;
	}
}
