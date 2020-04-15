package com.fiskmods.lightsabers.common.event;

import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.fiskmods.lightsabers.ALConstants;
import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.client.sound.ALSounds;
import com.fiskmods.lightsabers.client.sound.SoundAL;
import com.fiskmods.lightsabers.common.config.ModConfig;
import com.fiskmods.lightsabers.common.data.ALData;
import com.fiskmods.lightsabers.common.data.ALPlayerData;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.data.effect.StatusEffect;
import com.fiskmods.lightsabers.common.force.Power;
import com.fiskmods.lightsabers.common.force.PowerManager;
import com.fiskmods.lightsabers.common.force.PowerType;
import com.fiskmods.lightsabers.common.force.effect.PowerEffect;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectActive;
import com.fiskmods.lightsabers.common.item.ModItems;
import com.fiskmods.lightsabers.common.keybinds.ALKeyBinds;
import com.fiskmods.lightsabers.common.lightsaber.LightsaberData;
import com.fiskmods.lightsabers.common.lightsaber.PartType;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.fiskmods.lightsabers.helper.ALRenderHelper;
import com.google.common.collect.Maps;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent17;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

public class ClientEventHandler
{
    private Minecraft mc = Minecraft.getMinecraft();
    public static float renderTick;

    private Map<String, ItemStack> prevLightsaber1 = Maps.newHashMap();
    private Map<String, Boolean> hasPlayedSound = Maps.newHashMap();

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equals(Lightsabers.MODID))
        {
            ModConfig.load(ModConfig.configFile);
            ModConfig.configFile.save();
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event)
    {
        EntityLivingBase entity = event.entityLiving;
        ItemStack itemstack = entity.getHeldItem();
        World world = entity.worldObj;

//        if (prevLightsaber1.containsKey(entity.getUniqueID().toString()))
//        {
//            ItemStack itemstack1 = prevLightsaber1.get(entity.getUniqueID().toString());
//
//            // if (entity.ticksExisted % 2 == 0)
//            {
//                if (itemstack != null && itemstack1 != null && itemstack.getItem() instanceof ItemLightsaberBase && itemstack1.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(itemstack) != ItemLightsaberBase.isActive(itemstack1) || itemstack != null && itemstack1 == null && itemstack.getItem() instanceof ItemLightsaberBase || itemstack == null && itemstack1 != null && itemstack1.getItem() instanceof ItemLightsaberBase)
//                {
//                    onChangeState(entity, itemstack, itemstack1, ItemLightsaberBase.isActive(itemstack));
//                    prevLightsaber1.put(entity.getUniqueID().toString(), itemstack);
//                }
//            }
//        }
//
//        prevLightsaber1.put(entity.getUniqueID().toString(), itemstack);
//        
//        if (itemstack != null && itemstack.getItem() instanceof ItemLightsaberBase && ItemLightsaberBase.isActive(itemstack))
//        {
//            if (!ASMHooksClient.hasHumSound(entity))
//            {
//                mc.getSoundHandler().playSound(new MovingSoundHum(entity, "heavy"));
//                ASMHooksClient.humSounds.add(entity.getUniqueID().toString());
//            }
//        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent event)
    {
        EntityPlayer player = event.player;
        World world = player.worldObj;
        ALPlayerData data = ALPlayerData.getData(player);

        if (event.phase == TickEvent.Phase.END)
        {
            if (player == mc.thePlayer)
            {
                InventoryPlayer inventory = player.inventory;
                ItemStack lightsaber = null;

                for (int i = 0; i < inventory.mainInventory.length; ++i)
                {
                    if (inventory.mainInventory[i] != null && inventory.mainInventory[i].getItem() == ModItems.lightsaber)
                    {
                        lightsaber = inventory.mainInventory[i];
                        break;
                    }
                }

                ALData.LIGHTSABER.set(player, LightsaberData.get(lightsaber));
                
                Power power = PowerManager.getSelectedPower(player);
                boolean flag = false;

                if (power != null && ALData.USE_POWER_COOLDOWN.get(player) == 0 && ALHelper.getForcePowerMax(player) > 0 && power.powerStats.powerType == PowerType.PER_SECOND)
                {
                    if (mc.currentScreen == null && GameSettings.isKeyDown(ALKeyBinds.ACTIVATE_POWER))
                    {
                        flag = ALData.FORCE_POWER.get(player) >= power.getUseCost(player);
                    }
                }

                if (!flag && ALData.USING_POWER.get(player))
                {
                    ALData.USE_POWER_COOLDOWN.set(player, ALConstants.FORCE_POWER_COOLDOWN);
                }

                ALData.USING_POWER.set(player, flag);
                flag = false;

                if (ModConfig.enableShaders)
                {
                    if (StatusEffect.get(player, Effect.GAZE) != null)
                    {
                        ALRenderHelper.startShaders(ALRenderHelper.SHADER_BLUE);
                        flag = true;
                    }
                    else if (StatusEffect.get(player, Effect.STEALTH) != null)
                    {
                        ALRenderHelper.startShaders(ALRenderHelper.SHADER_GRAY);
                        flag = true;
                    }
                    else if (StatusEffect.get(player, Effect.SPEED) != null)
                    {
                        ALRenderHelper.startShaders(ALRenderHelper.SHADER_BLUR);
                        flag = true;
                    }
                }

                if (!flag && mc.entityRenderer.getShaderGroup() != null)
                {
                    String s = mc.entityRenderer.getShaderGroup().getShaderGroupName();
                    ResourceLocation[] shaders = {ALRenderHelper.SHADER_BLUE, ALRenderHelper.SHADER_GRAY, ALRenderHelper.SHADER_BLUR};

                    for (ResourceLocation shader : shaders)
                    {
                        if (s.equals(shader.toString()))
                        {
                            ALRenderHelper.stopShaders();
                            continue;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event)
    {
        EntityPlayer player = event.entityPlayer;
        LightsaberData data = ALData.LIGHTSABER.get(player);

        if (data != null && data != LightsaberData.EMPTY && (player.getHeldItem() == null || player.getHeldItem().getItem() != ModItems.lightsaber))
        {
            GL11.glPushMatrix();
            event.renderer.modelBipedMain.bipedBody.postRender(0.0625F);
            GL11.glRotatef(180, 1, 0, 0);
            GL11.glTranslatef(0.2F, -0.55F, 0.15F);
            GL11.glRotatef(15, 0, 0, 1);
            GL11.glRotatef(10, 1, 0, 0);

            float scale = 0.15F;
            GL11.glScalef(scale, scale, scale);
            GL11.glTranslatef(0, -(data.getPart(PartType.BODY).height + data.getPart(PartType.POMMEL).height / 2) * 0.0625F, 0);
            ALRenderHelper.renderLightsaberHilt(data);
            GL11.glPopMatrix();
        }

        for (StatusEffect status : StatusEffect.get(player))
        {
            Power power = status.effect.getPower(status.amplifier);
            PowerEffect powerEffect = power.powerEffect;

            if (powerEffect instanceof PowerEffectActive)
            {
                ((PowerEffectActive) powerEffect).render(player, event.partialRenderTick);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderLivingSpecialsPre(RenderLivingEvent.Pre event)
    {
        EntityLivingBase entity = event.entity;
        StatusEffect effectStun = StatusEffect.get(entity, Effect.STUN);

        if (effectStun != null)
        {
            boolean prevLighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
            boolean prevTex2D = GL11.glGetBoolean(GL11.GL_TEXTURE_2D);
            boolean prevBlend = GL11.glGetBoolean(GL11.GL_BLEND);
            float prevWidth = GL11.glGetFloat(GL11.GL_LINE_WIDTH);

            GL11.glPushMatrix();
            GL11.glTranslated(event.x, event.y + entity.height / 2, event.z);
            Tessellator tessellator = Tessellator.instance;

            ALRenderHelper.setLighting(61680);
            GL11.glLineWidth(5);
            GL11.glDepthMask(false);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.99F);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);

            float f = entity.ticksExisted + renderTick;
            float range = Math.max(entity.width, entity.height) * 0.75F;
            float angleIncr = 10;
            float coverage = 90;
            int amount = 8;
            float spread = 8;

            for (int i = 0; i < 2; ++i)
            {
                for (int j = -amount; j <= amount; ++j)
                {
                    for (int k = -amount; k <= amount; ++k)
                    {
                        GL11.glPushMatrix();

                        if (i == 0)
                        {
                            GL11.glScalef(-1, -1, -1);
                        }

                        tessellator.startDrawing(3);
                        tessellator.setColorRGBA(54, 84, 181, (int) (50 * Math.min((effectStun.duration - renderTick) / 20, 1)));

                        for (int l = 0; l <= coverage / angleIncr; ++l)
                        {
                            Vec3 vec3 = Vec3.createVectorHelper(0, range, 0);
                            float pitch = l * angleIncr;
                            float yaw = 180 + j * spread + MathHelper.sin(f / 10) * 100;
                            float roll = k * spread + MathHelper.cos(f / 10) * 100;
                            vec3.rotateAroundX(-pitch * (float) Math.PI / 180.0F);
                            vec3.rotateAroundY(-yaw * (float) Math.PI / 180.0F);
                            vec3.rotateAroundZ(-roll * (float) Math.PI / 180.0F);
                            tessellator.addVertex(vec3.xCoord, vec3.yCoord, vec3.zCoord);
                        }

                        tessellator.draw();
                        GL11.glPopMatrix();
                    }
                }
            }

            GL11.glLineWidth(prevWidth);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glDepthMask(true);

            if (prevLighting)
            {
                GL11.glEnable(GL11.GL_LIGHTING);
            }

            if (prevTex2D)
            {
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            if (!prevBlend)
            {
                GL11.glDisable(GL11.GL_BLEND);
            }

            ALRenderHelper.resetLighting();
            GL11.glPopMatrix();
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderLivingPre(RenderLivingEvent.Pre event)
    {
        EntityLivingBase entity = event.entity;
        StatusEffect effectGaze = StatusEffect.get(mc.thePlayer, Effect.GAZE);
        StatusEffect effectMeditation = StatusEffect.get(entity, Effect.MEDITATION);
        StatusEffect effectEnergyResist = StatusEffect.get(entity, Effect.RESIST);

        if (StatusEffect.get(entity, Effect.STEALTH) != null && (effectGaze == null || !ALRenderHelper.canGazeEntity(mc.thePlayer, entity, effectGaze.amplifier)))
        {
            event.setCanceled(true);
            return;
        }

        boolean flag1 = false;
        float f5 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * renderTick;

        if (entity != mc.thePlayer && entity != mc.thePlayer.ridingEntity && effectGaze != null && ALRenderHelper.canGazeEntity(mc.thePlayer, entity, effectGaze.amplifier))
        {
            if (entity.stepHeight > 0)
            {
                int i = effectGaze.duration;
                float f = i > 100 ? 1.0F : 0.2F + MathHelper.sin((i - renderTick) * (float) Math.PI * 0.2F) * 0.2F;
                boolean prevLighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
                boolean flag = entity.isInvisible();
                entity.stepHeight = -entity.stepHeight;
                entity.setInvisible(false);

                ALRenderHelper.setLighting(61680);

                if (!flag1)
                {
                    RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                    flag1 = true;
                }

                GL11.glPushMatrix();
                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (flag)
                {
                    GL11.glColor4f(1, 0, 1, f);
                }
                else
                {
                    GL11.glColor4f(1, 0, 0, f);
                }

                ALRenderHelper.overrideColor(true);
                RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                ALRenderHelper.overrideColor(false);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                GL11.glDepthMask(true);
                entity.setInvisible(flag);
                ALRenderHelper.resetLighting();

                if (prevLighting)
                {
                    GL11.glEnable(GL11.GL_LIGHTING);
                }

                GL11.glPopMatrix();
                entity.stepHeight = -entity.stepHeight;
                event.setCanceled(true);
            }
        }

        if (effectMeditation != null && !entity.isInvisibleToPlayer(mc.thePlayer))
        {
            if (entity.stepHeight > 0)
            {
                int i = effectMeditation.duration;
                float f = 0.7F + MathHelper.sin((i - renderTick) * (float) Math.PI * 0.1F) * 0.3F;
                boolean prevLighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
                entity.stepHeight = -entity.stepHeight;

                ALRenderHelper.setLighting(61680);

                if (!flag1)
                {
                    RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                    flag1 = true;
                }

                GL11.glPushMatrix();
                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glColor4f(1, 0.5F, 0, f);
                ALRenderHelper.overrideColor(true);
                RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                ALRenderHelper.overrideColor(false);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                ALRenderHelper.resetLighting();

                if (prevLighting)
                {
                    GL11.glEnable(GL11.GL_LIGHTING);
                }

                GL11.glPopMatrix();
                entity.stepHeight = -entity.stepHeight;
                event.setCanceled(true);
            }
        }

        if (effectEnergyResist != null && !entity.isInvisibleToPlayer(mc.thePlayer))
        {
            if (entity.stepHeight > 0)
            {
                int i = effectEnergyResist.duration;
                float f = 0.7F + MathHelper.sin((i - renderTick) * (float) Math.PI * 0.1F) * 0.3F;
                boolean prevLighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
                entity.stepHeight = -entity.stepHeight;

                ALRenderHelper.setLighting(61680);

                if (!flag1)
                {
                    RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                    flag1 = true;
                }

                GL11.glPushMatrix();
                GL11.glDepthMask(false);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.01F);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_CONSTANT_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);
                GL11.glColor4f(0, 0.5F, 1, f);
                ALRenderHelper.overrideColor(true);
                RenderManager.instance.renderEntityWithPosYaw(entity, event.x, event.y + (entity == mc.thePlayer ? entity.yOffset : 0), event.z, f5, renderTick);
                ALRenderHelper.overrideColor(false);
                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                ALRenderHelper.resetLighting();

                if (prevLighting)
                {
                    GL11.glEnable(GL11.GL_LIGHTING);
                }

                GL11.glPopMatrix();
                entity.stepHeight = -entity.stepHeight;
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundEvent17 event)
    {
        if (mc.thePlayer != null)
        {
            String name = Lightsabers.MODID + ":" + event.name;

            if (!name.equals(ALSounds.player_force_stealth_on) && !name.equals(ALSounds.player_force_stealth_off) && !name.startsWith(ALSounds.ambient_stealth) && StatusEffect.get(mc.thePlayer, Effect.STEALTH) != null)
            {
                ISound sound = event.sound;
                event.result = new SoundAL(sound.getPositionedSoundLocation(), sound.getVolume() * 0.05F, sound.getPitch(), sound.canRepeat(), sound.getRepeatDelay(), sound.getAttenuationType(), sound.getXPosF(), sound.getYPosF(), sound.getZPosF());
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent event)
    {
        renderTick = event.renderTickTime;
    }
}
