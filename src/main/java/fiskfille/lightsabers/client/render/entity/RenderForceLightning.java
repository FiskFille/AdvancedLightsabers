package fiskfille.lightsabers.client.render.entity;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import fiskfille.lightsabers.common.data.DataManager;
import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.data.StatusEffect;
import fiskfille.lightsabers.common.entity.EntityForceLightning;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.helper.ALRenderHelper;
import fiskfille.lightsabers.common.helper.VectorHelper;

public class RenderForceLightning extends Render
{
	public RenderForceLightning()
	{
		super();
		shadowSize = 0.0F;
	}

	public void render(EntityForceLightning forceLightning, double x, double y, double z, float f, float partialTicks)
	{
		EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;
		EntityLivingBase entity = forceLightning.entity;
		Vec3 entityVec = entity.getPosition(partialTicks);

		if (entity != clientPlayer)
		{
			entityVec.yCoord += 1.62F;
		}

		if (forceLightning.isEntityAlive())
		{
			x = entityVec.xCoord - renderManager.renderPosX;
			y = entityVec.yCoord - renderManager.renderPosY;
			z = entityVec.zCoord - renderManager.renderPosZ;

			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			ALRenderHelper.setupRenderLightning();
			renderLightning(entity, ALHelper.getEffectTargets(entity, Effect.drain.id), Vec3.createVectorHelper(1, 0.4F, 0), 2, partialTicks);

			StatusEffect effect = DataManager.getEffect(entity, Effect.lightning.id);

			if (effect != null)
			{
				Random rand = new Random(entity.ticksExisted * 100000);
				EntityLivingBase target = ALHelper.getForceLightningTarget(entity);
				Vec3 color = Vec3.createVectorHelper(0, 0, 1);

				for (int i = 0; i < 2; ++i)
				{
					for (int j = 0; j < 2 + effect.amplifier; ++j)
					{
						Vec3 src = VectorHelper.getOffsetCoords(entity, 0, 0, 0);
						Vec3 dst = Vec3.createVectorHelper(0, 0, 7);
						dst.rotateAroundX(-(entity.rotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks) * (float) Math.PI / 180.0F);
						dst.rotateAroundY(-(entity.rotationYawHead - (entity.rotationYawHead - entity.prevRotationYawHead) * partialTicks) * (float) Math.PI / 180.0F);
						dst = Vec3.createVectorHelper(entity.posX + dst.xCoord, VectorHelper.getAccuratePlayerY(entity) + dst.yCoord, entity.posZ + dst.zCoord);

						Vec3 targetVec = null;
						MovingObjectPosition rayTrace = entity.worldObj.rayTraceBlocks(VectorHelper.copy(src), VectorHelper.copy(dst));

						if (rayTrace == null)
						{
							targetVec = dst;
						}
						else
						{
							targetVec = rayTrace.hitVec;
						}

						if (target != null)
						{
							targetVec = target.getPosition(partialTicks).addVector(0, target.height / 2, 0);

							if (target == clientPlayer)
							{
								targetVec.yCoord -= 1.62F;
							}
						}

						if (entity != clientPlayer)
						{
							targetVec.yCoord -= 1.62F;
						}

						renderLightning(entity, targetVec, color, rand, 1.5F + effect.amplifier * 0.5F, i == 0, partialTicks);
					}
				}
			}

			ALRenderHelper.finishRenderLightning();
			GL11.glPopMatrix();
		}
	}

	public void renderLightning(EntityLivingBase entity, List<EntityLivingBase> targets, Vec3 color, int lightningAmount, float partialTicks)
	{
		Random rand = new Random();
		EntityPlayer clientPlayer = Minecraft.getMinecraft().thePlayer;

		for (EntityLivingBase target : targets)
		{
			rand.setSeed(target.ticksExisted * 100000);
			Vec3 targetVec = target.getPosition(partialTicks).addVector(0, target.height / 2, 0);

			if (entity != clientPlayer)
			{
				targetVec.yCoord -= 1.62F;
			}

			if (target == clientPlayer)
			{
				targetVec.yCoord -= 1.62F;
			}

			for (int j = 0; j < lightningAmount; ++j)
			{
				renderLightning(entity, targetVec, color, rand, 1, partialTicks);
			}
		}
	}

	public void renderLightning(EntityLivingBase caster, Vec3 targetVec, Vec3 color, Random rand, float spreadFactor, float partialTicks)
	{
		renderLightning(caster, targetVec, color, rand, spreadFactor, true, partialTicks);
	}

	public void renderLightning(EntityLivingBase caster, Vec3 targetVec, Vec3 color, Random rand, float spreadFactor, boolean hand, float partialTicks)
	{
		Vec3 src = Vec3.createVectorHelper(-0.275F * (hand ? 1 : -1), -0.25F, 0.8F);
		Vec3 dst = caster.getPosition(partialTicks).subtract(targetVec);
		Vec3 dst1 = VectorHelper.copy(dst);
		Vec3 dst2 = VectorHelper.copy(dst);
		boolean firstPerson = caster == Minecraft.getMinecraft().thePlayer && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
		double amount = Math.min(src.distanceTo(dst) * 0.05D, 1) * (firstPerson ? 0.75D : 1);
		double srcSpread = Math.min(firstPerson ? 0.05D : 0.15D, amount);
		double dstSpread = Math.min(0.2D, amount) * spreadFactor;

		if (firstPerson)
		{
			src = Vec3.createVectorHelper(-0.45F * (hand ? 1 : -1), -0.25F, 0.6F);
		}

		double d = 0.33D;
		double d1 = 0.66D;

		src.rotateAroundX(-ALRenderHelper.median(caster.rotationPitch, caster.prevRotationPitch) * (float) Math.PI / 180.0F);
		src.rotateAroundY(-ALRenderHelper.median(caster.rotationYaw, caster.prevRotationYaw) * (float) Math.PI / 180.0F);
		src.xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
		src.yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
		src.zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * srcSpread;
		dst1.xCoord = dst.xCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst1.yCoord = dst.yCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst1.zCoord = dst.zCoord * d + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst2.xCoord = dst.xCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst2.yCoord = dst.yCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst2.zCoord = dst.zCoord * d1 + MathHelper.getRandomDoubleInRange(rand, -1, 1) * amount;
		dst.xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
		dst.yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
		dst.zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * 0.125F;
		dst1.xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
		dst1.yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
		dst1.zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d;
		dst2.xCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;
		dst2.yCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;
		dst2.zCoord += MathHelper.getRandomDoubleInRange(rand, -1, 1) * dstSpread * d1;

		float opacity = 1;
		float lineWidth = 5 * (firstPerson ? 2 : 1);
		float innerLineWidth = 1 * (firstPerson ? 2 : 1);
		ALRenderHelper.drawLightningLine(src, dst1, lineWidth, innerLineWidth, color, opacity);
		ALRenderHelper.drawLightningLine(dst1, dst2, lineWidth, innerLineWidth, color, opacity);
		ALRenderHelper.drawLightningLine(dst2, dst, lineWidth, innerLineWidth, color, opacity);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float partialTicks)
	{
		render((EntityForceLightning) entity, x, y, z, f, partialTicks);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}
