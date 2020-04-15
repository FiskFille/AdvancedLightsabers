package com.fiskmods.lightsabers.common.force;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fiskmods.lightsabers.common.data.ALData.DataFactory;
import com.fiskmods.lightsabers.common.data.effect.Effect;
import com.fiskmods.lightsabers.common.force.effect.PowerEffect;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectBladeThrow;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectChoke;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectDrain;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectFortify;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectGaze;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectHeal;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectLightning;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectMeditation;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectPush;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectRebound;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectResist;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectSpeed;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectStealth;
import com.fiskmods.lightsabers.common.force.effect.PowerEffectStun;
import com.fiskmods.lightsabers.helper.ALHelper;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.network.ByteBufUtils;
import fiskfille.utils.helper.NBTHelper.ISaveAdapter;
import fiskfille.utils.helper.NBTHelper.ISerializableObject;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.StatCollector;

public class Power implements Comparable<Power>, ISerializableObject<Power>
{
    public static final List<Power> POWERS = Lists.newArrayList();

    public static final Power FORCE_SENSITIVITY = new Power("forceSensitivity", 0, 0, null, new PowerStats(160).setBaseBonus(2).setForceBonus(50).setRegen(5)).setIconIndex(14, 0);
    public static final Power LIGHT_SIDE = new Power("lightSide", 2, 0, FORCE_SENSITIVITY).setIconIndex(13, 1);
    public static final Power DARK_SIDE = new Power("darkSide", -2, 0, FORCE_SENSITIVITY).setIconIndex(13, 2);
    public static final Power NEUTRAL = new Power("neutral", 0, 2, FORCE_SENSITIVITY).setIconIndex(14, 1);
    public static final Power FORCE_LEVEL1 = new Power("level1", 0, -2, FORCE_SENSITIVITY, new PowerStats(160).setBaseBonus(1).setForceBonus(50).setRegen(10, 1)).setIconIndex(11, 1);
    public static final Power FORCE_LEVEL2 = new Power("level2", 0, -1, FORCE_LEVEL1, new PowerStats(247).setBaseBonus(1).setForceBonus(50).setRegen(15, 1)).setIconIndex(12, 1);
    public static final Power FORCE_LEVEL3 = new Power("level3", 0, -1, FORCE_LEVEL2, new PowerStats(394).setBaseBonus(1).setForceBonus(50).setRegen(15, 1)).setIconIndex(10, 2);
    public static final Power FORCE_LEVEL4 = new Power("level4", 0, -1, FORCE_LEVEL3, new PowerStats(679).setBaseBonus(2).setForceBonus(75).setRegen(25, 1)).setIconIndex(11, 2);
    public static final Power FORCE_LEVEL5 = new Power("level5", 0, -1, FORCE_LEVEL4, new PowerStats(1186).setBaseBonus(2).setForceBonus(100).setRegen(30, 1)).setIconIndex(12, 2);
    
    public static final Power HEAL1 = new Power("heal1", 0, -4, LIGHT_SIDE, new PowerStats(320).setBaseReq(1).setUseCost(50)).setIconIndex(0, 0).setEffect(new PowerEffectHeal(4, 0));
    public static final Power HEAL2 = new Power("heal2", 0, -1, HEAL1, new PowerStats(966).setUseCost(125)).setIconIndex(0, 1).setEffect(new PowerEffectHeal(7, 0));
    public static final Power HEAL3 = new Power("heal3", 0, -1, HEAL2, new PowerStats(1440).setUseCost(175)).setIconIndex(0, 2).setEffect(new PowerEffectHeal(13, 7));
    public static final Power FORTIFY1 = new Power("fortify1", 2, -4, LIGHT_SIDE, new PowerStats(320).setBaseReq(1).setUseCost(2.5F).setType(PowerType.PER_SECOND)).setIconIndex(1, 0).setEffect(new PowerEffectFortify(0));
    public static final Power FORTIFY2 = new Power("fortify2", 0, -1, FORTIFY1, new PowerStats(788).setUseCost(2.5F).setType(PowerType.PER_SECOND)).setIconIndex(1, 1).setEffect(new PowerEffectFortify(1));
    public static final Power FORTIFY3 = new Power("fortify3", 0, -1, FORTIFY2, new PowerStats(1100).setUseCost(2.5F).setType(PowerType.PER_SECOND)).setIconIndex(1, 2).setEffect(new PowerEffectFortify(2));
    public static final Power STUN1 = new Power("stun1", 2, 0, LIGHT_SIDE, new PowerStats(320).setBaseReq(1).setUseCost(50)).setIconIndex(2, 0).setEffect(new PowerEffectStun(0, 2, false));
    public static final Power STUN2 = new Power("stun2", 0, -1, STUN1, new PowerStats(788).setUseCost(100)).setIconIndex(2, 1).setEffect(new PowerEffectStun(1, 3.5F, false));
    public static final Power STUN3 = new Power("stun3", 0, -1, STUN2, new PowerStats(1100).setUseCost(150)).setIconIndex(2, 2).setEffect(new PowerEffectStun(2, 4, true));
    
    public static final Power DRAIN1 = new Power("drain1", 0, -4, DARK_SIDE, new PowerStats(460).setBaseReq(1).setUseCost(100)).setIconIndex(3, 0).setEffect(new PowerEffectDrain(0));
    public static final Power DRAIN2 = new Power("drain2", 0, -1, DRAIN1, new PowerStats(1180).setUseCost(150)).setIconIndex(3, 1).setEffect(new PowerEffectDrain(1));
    public static final Power DRAIN3 = new Power("drain3", 0, -1, DRAIN2, new PowerStats(1670).setUseCost(200)).setIconIndex(3, 2).setEffect(new PowerEffectDrain(2));
    public static final Power LIGHTNING1 = new Power("lightning1", -2, -4, DARK_SIDE, new PowerStats(320).setBaseReq(1).setUseCost(5).setType(PowerType.PER_SECOND)).setIconIndex(4, 0).setEffect(new PowerEffectLightning(0));
    public static final Power LIGHTNING2 = new Power("lightning2", 0, -1, LIGHTNING1, new PowerStats(788).setUseCost(10).setType(PowerType.PER_SECOND)).setIconIndex(4, 1).setEffect(new PowerEffectLightning(1));
    public static final Power LIGHTNING3 = new Power("lightning3", 0, -1, LIGHTNING2, new PowerStats(1277).setUseCost(20).setType(PowerType.PER_SECOND)).setIconIndex(4, 2).setEffect(new PowerEffectLightning(2));
    public static final Power WOUND1 = new Power("wound1", -2, 0, DARK_SIDE, new PowerStats(320).setBaseReq(1).setUseCost(50)).setIconIndex(5, 0).setEffect(new PowerEffectChoke(0));
    public static final Power WOUND2 = new Power("wound2", 0, -1, WOUND1, new PowerStats(788).setUseCost(115)).setIconIndex(5, 1).setEffect(new PowerEffectChoke(1));
    public static final Power WOUND3 = new Power("wound3", 0, -1, WOUND2, new PowerStats(1110).setUseCost(170)).setIconIndex(5, 2).setEffect(new PowerEffectChoke(2));
    
    public static final Power STEALTH = new Power("stealth", -3, 0, NEUTRAL, new PowerStats(160).setBaseReq(1).setUseCost(5).setType(PowerType.PER_SECOND)).setIconIndex(11, 0).setEffect(new PowerEffectStealth());
    public static final Power SPEED = new Power("speed", 3, 0, NEUTRAL, new PowerStats(160).setBaseReq(1).setUseCost(30)).setIconIndex(12, 0).setEffect(new PowerEffectSpeed(5, 0));
    public static final Power REBOUND = new Power("rebound", 3, 1, NEUTRAL, new PowerStats(320).setBaseReq(1).setUseCost(3).setType(PowerType.PASSIVE)).setIconIndex(13, 0).setEffect(new PowerEffectRebound());
    public static final Power SIGHT1 = new Power("sight1", -2, 2, NEUTRAL, new PowerStats(160).setBaseReq(1).setUseCost(25)).setIconIndex(6, 0).setEffect(new PowerEffectGaze(10, 0));
    public static final Power SIGHT2 = new Power("sight2", 0, 1, SIGHT1, new PowerStats(394).setUseCost(25)).setIconIndex(6, 1).setEffect(new PowerEffectGaze(15, 1));
    public static final Power SIGHT3 = new Power("sight3", 0, 1, SIGHT2, new PowerStats(550).setUseCost(25)).setIconIndex(6, 2).setEffect(new PowerEffectGaze(25, 2));
    public static final Power MEDITATION1 = new Power("meditation1", -1, 2, NEUTRAL, new PowerStats(320).setBaseReq(1).setUseCost(75)).setIconIndex(7, 0).setEffect(new PowerEffectMeditation(0));
    public static final Power MEDITATION2 = new Power("meditation2", 0, 1, MEDITATION1, new PowerStats(788).setUseCost(100)).setIconIndex(7, 1).setEffect(new PowerEffectMeditation(1));
    public static final Power MEDITATION3 = new Power("meditation3", 0, 1, MEDITATION2, new PowerStats(1100).setUseCost(150)).setIconIndex(7, 2).setEffect(new PowerEffectMeditation(2));
    public static final Power THROW1 = new Power("throw1", 0, 2, NEUTRAL, new PowerStats(160).setBaseReq(1).setUseCost(50)).setIconIndex(10, 0).setEffect(new PowerEffectBladeThrow(0));
    public static final Power THROW2 = new Power("throw2", 0, 1, THROW1, new PowerStats(394).setUseCost(50)).setIconIndex(10, 1).setEffect(new PowerEffectBladeThrow(1));
    public static final Power RESIST1 = new Power("resist1", 1, 2, NEUTRAL, new PowerStats(160).setBaseReq(1).setUseCost(50)).setIconIndex(8, 0).setEffect(new PowerEffectResist(7, 0));
    public static final Power RESIST2 = new Power("resist2", 0, 1, RESIST1, new PowerStats(394).setUseCost(60)).setIconIndex(8, 1).setEffect(new PowerEffectResist(9, 1));
    public static final Power RESIST3 = new Power("resist3", 0, 1, RESIST2, new PowerStats(550).setUseCost(70)).setIconIndex(8, 2).setEffect(new PowerEffectResist(14, 2));
    public static final Power PUSH1 = new Power("push1", 2, 2, NEUTRAL, new PowerStats(320).setBaseReq(1).setUseCost(50)).setIconIndex(9, 0).setEffect(new PowerEffectPush(0));
    public static final Power PUSH2 = new Power("push2", 0, 1, PUSH1, new PowerStats(788).setUseCost(85)).setIconIndex(9, 1).setEffect(new PowerEffectPush(1));
    public static final Power PUSH3 = new Power("push3", 0, 1, PUSH2, new PowerStats(1100).setUseCost(120)).setIconIndex(9, 2).setEffect(new PowerEffectPush(2));
    
    private final String name;
    public final Power parent;
    
    private ForceSide forceSide = ForceSide.NONE;
    private int iconIndex = -1;
    private int xOffset;
    private int yOffset;

    public PowerStats powerStats = new PowerStats(0);
    public PowerEffect powerEffect;
    
    public Set<Power> children = new HashSet();

    public Power(String s, int offsetX, int offsetY, Power power)
    {
        name = s;
        parent = power;

        if (parent != null)
        {
            parent.children.add(this);

            for (ForceSide side : ForceSide.values())
            {
                if (side != ForceSide.NONE && isDescendant(side.getPower()))
                {
                    forceSide = side;
                    break;
                }
            }

            forceSide.powers.add(this);
            xOffset = parent.xOffset + offsetX;
            yOffset = parent.yOffset + offsetY;
        }

        POWERS.add(this);
    }

    public Power(String s, int offsetX, int offsetY, Power power, PowerStats stats)
    {
        this(s, offsetX, offsetY, power);
        powerStats = stats;
    }

    public Power setIconIndex(int x, int y)
    {
        iconIndex = x + y * 16;
        return this;
    }

    public Power setEffect(PowerEffect effect)
    {
        powerEffect = effect;
        return this;
    }

    public boolean hasIcon()
    {
        return iconIndex >= 0;
    }

    public int getIconX()
    {
        return iconIndex % 16;
    }
    
    public int getIconY()
    {
        return iconIndex / 16;
    }
    
    public int getXOffset()
    {
        return xOffset;
    }
    
    public int getYOffset()
    {
        return yOffset;
    }

    public ForceSide getSide()
    {
        return forceSide;
    }

    public String getName()
    {
        return name;
    }

    public String getUnlocalizedName()
    {
        return "forcepower.name." + name;
    }

    public String getLocalizedName()
    {
        return I18n.format(getUnlocalizedName());
    }

    public IChatComponent getFormattedName()
    {
        IChatComponent title = new ChatComponentTranslation(getUnlocalizedName());
        title.getChatStyle().setColor(forceSide.theme);

        IChatComponent desc = title.createCopy();
        List<String> list = Lists.newArrayList();

        if (powerStats.useCost > 0)
        {
            desc.appendText("\n" + EnumChatFormatting.GRAY + StatCollector.translateToLocalFormatted(powerStats.powerType == PowerType.PER_USE ? "forcepower.perUse" : "forcepower.perSecond", ItemStack.field_111284_a.format(powerStats.useCost)) + "\n");
        }

        if (powerEffect != null)
        {
            for (String s : powerEffect.getDesc())
            {
                desc.appendText("\n" + EnumChatFormatting.GRAY + s);
            }
        }

        title.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, desc));

        return title;
    }

    public IChatComponent getChatFormattedName()
    {
        IChatComponent title = getFormattedName().createCopy();
        IChatComponent text = new ChatComponentText("[").appendSibling(title).appendText("]");
        text.setChatStyle(title.getChatStyle());

        return text;
    }

    public int getActualXpCost(EntityPlayer player)
    {
        int cost = powerStats.xpCost;

        if (forceSide.isPolar())
        {
            cost += cost * ALHelper.getCompletion(player, forceSide.getOpposite());
        }

        return cost;
    }
    
    public boolean isUsable()
    {
        return powerEffect != null && powerStats.powerType != PowerType.PASSIVE;
    }

    public float getUseCost(EntityPlayer player)
    {
        return powerEffect.getUseCost(player, this);
    }

    public boolean isSided()
    {
        return forceSide != ForceSide.NONE && this != forceSide.getPower();
    }

    public boolean isDescendant(Power of)
    {
        return isDescendant(this, of);
    }

    private boolean isDescendant(Power power, Power of)
    {
        if (power == of)
        {
            return true;
        }

        if (power.parent != null)
        {
            return isDescendant(power.parent, of);
        }

        return false;
    }

    @Override
    public String toString()
    {
        return String.format("Power[\"%s\"]", getName());
    }

    @Override
    public int compareTo(Power o)
    {
        int result = forceSide.compareTo(o.forceSide);
        return result == 0 ? name.compareTo(o.name) : result;
    }

    static
    {
        Effect.FORTIFY.powers.add(FORTIFY1);
        Effect.FORTIFY.powers.add(FORTIFY2);
        Effect.FORTIFY.powers.add(FORTIFY3);
        Effect.STUN.powers.add(STUN1);
        Effect.STUN.powers.add(STUN2);
        Effect.STUN.powers.add(STUN3);

        Effect.DRAIN.powers.add(DRAIN1);
        Effect.DRAIN.powers.add(DRAIN2);
        Effect.DRAIN.powers.add(DRAIN3);
        Effect.LIGHTNING.powers.add(LIGHTNING1);
        Effect.LIGHTNING.powers.add(LIGHTNING2);
        Effect.LIGHTNING.powers.add(LIGHTNING3);
        Effect.CHOKE.powers.add(WOUND1);
        Effect.CHOKE.powers.add(WOUND2);
        Effect.CHOKE.powers.add(WOUND3);

        Effect.STEALTH.powers.add(STEALTH);
        Effect.SPEED.powers.add(SPEED);
        Effect.GAZE.powers.add(SIGHT1);
        Effect.GAZE.powers.add(SIGHT2);
        Effect.GAZE.powers.add(SIGHT3);
        Effect.MEDITATION.powers.add(MEDITATION1);
        Effect.MEDITATION.powers.add(MEDITATION2);
        Effect.MEDITATION.powers.add(MEDITATION3);
        Effect.RESIST.powers.add(RESIST1);
        Effect.RESIST.powers.add(RESIST2);
        Effect.RESIST.powers.add(RESIST3);
    }

    public static DataFactory<List<Power>> factory()
    {
        return new DataFactory<List<Power>>()
        {
            @Override
            public List<Power> construct()
            {
                return Lists.newArrayList(new Power[3]);
            }
        };
    }

    public static Power getPowerFromName(String s)
    {
        for (Power power : POWERS)
        {
            if (power.name.equals(s))
            {
                return power;
            }
        }

        return null;
    }

    @Override
    public NBTBase writeToNBT()
    {
        return new NBTTagString(getName());
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        ByteBufUtils.writeUTF8String(buf, getName());
    }
    
    public static class Adapter implements ISaveAdapter<Power>
    {
        @Override
        public Power readFromNBT(NBTBase tag)
        {
            if (tag instanceof NBTTagString)
            {
                return Power.getPowerFromName(((NBTTagString) tag).func_150285_a_());
            }

            return null;
        }

        @Override
        public Power fromBytes(ByteBuf buf)
        {
            return Power.getPowerFromName(ByteBufUtils.readUTF8String(buf));
        }
    }
}
