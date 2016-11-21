package fiskfille.lightsabers.common.power;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

import com.google.common.collect.Lists;

import fiskfille.lightsabers.common.data.Effect;
import fiskfille.lightsabers.common.helper.ALHelper;
import fiskfille.lightsabers.common.power.effect.PowerEffect;

public class Power
{
	public static final List<Power> powers = Lists.newArrayList();
	
	public static final Power forceSensitivity = new Power("forceSensitivity", new PowerStats().setXpCost(160).setBasePowerBonus(2).setForcePowerBonus(50).setForcePowerRegen(5)).setOffset(0, 0).setIconOffset(13, 0);
	public static final Power lightSide = new Power("lightSide").setParent(forceSensitivity).setOffset(2, 0).setIconOffset(13, 1);
	public static final Power darkSide = new Power("darkSide").setParent(forceSensitivity).setOffset(-2, 0).setIconOffset(13, 2);
	public static final Power neutral = new Power("neutral").setParent(forceSensitivity).setOffset(0, 2).setIconOffset(14, 0);
	public static final Power forceLevel1 = new Power("level1", new PowerStats().setXpCost(160).setBasePowerBonus(1).setForcePowerBonus(50).setForcePowerRegen(10, 1)).setParent(forceSensitivity).setParentOffset(0, -2).setIconOffset(11, 1);
	public static final Power forceLevel2 = new Power("level2", new PowerStats().setXpCost(247).setBasePowerBonus(1).setForcePowerBonus(50).setForcePowerRegen(15, 1)).setParent(forceLevel1).setParentOffset(0, -1).setIconOffset(12, 1);
	public static final Power forceLevel3 = new Power("level3", new PowerStats().setXpCost(394).setBasePowerBonus(1).setForcePowerBonus(50).setForcePowerRegen(15, 1)).setParent(forceLevel2).setParentOffset(0, -1).setIconOffset(10, 2);
	public static final Power forceLevel4 = new Power("level4", new PowerStats().setXpCost(679).setBasePowerBonus(2).setForcePowerBonus(75).setForcePowerRegen(25, 1)).setParent(forceLevel3).setParentOffset(0, -1).setIconOffset(11, 2);
	public static final Power forceLevel5 = new Power("level5", new PowerStats().setXpCost(1186).setBasePowerBonus(2).setForcePowerBonus(100).setForcePowerRegen(30, 1)).setParent(forceLevel4).setParentOffset(0, -1).setIconOffset(12, 2);
	public static final Power heal1 = new Power("heal1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(lightSide).setParentOffset(0, -4).setIconOffset(0, 0).setEffect(PowerEffect.heal, 4);
	public static final Power heal2 = new Power("heal2", new PowerStats().setXpCost(966).setForcePowerUseCost(125)).setParent(heal1).setParentOffset(0, -1).setIconOffset(0, 1).setEffect(PowerEffect.heal, 7);
	public static final Power heal3 = new Power("heal3", new PowerStats().setXpCost(1440).setForcePowerUseCost(175)).setParent(heal2).setParentOffset(0, -1).setIconOffset(0, 2).setEffect(PowerEffect.heal, 13, 7);
	public static final Power fortify1 = new Power("fortify1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(2.5F).setPowerType(EnumPowerType.PER_SECOND)).setParent(lightSide).setParentOffset(2, -4).setIconOffset(1, 0).setEffect(PowerEffect.fortify, 0);
	public static final Power fortify2 = new Power("fortify2", new PowerStats().setXpCost(788).setForcePowerUseCost(2.5F).setPowerType(EnumPowerType.PER_SECOND)).setParent(fortify1).setParentOffset(0, -1).setIconOffset(1, 1).setEffect(PowerEffect.fortify, 1);
	public static final Power fortify3 = new Power("fortify3", new PowerStats().setXpCost(1100).setForcePowerUseCost(2.5F).setPowerType(EnumPowerType.PER_SECOND)).setParent(fortify2).setParentOffset(0, -1).setIconOffset(1, 2).setEffect(PowerEffect.fortify, 2);
	public static final Power stun1 = new Power("stun1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(lightSide).setParentOffset(2, 0).setIconOffset(2, 0).setEffect(PowerEffect.stun, 0, 2.0F);
	public static final Power stun2 = new Power("stun2", new PowerStats().setXpCost(788).setForcePowerUseCost(100)).setParent(stun1).setParentOffset(0, -1).setIconOffset(2, 1).setEffect(PowerEffect.stun, 1, 3.5F);
	public static final Power stun3 = new Power("stun3", new PowerStats().setXpCost(1100).setForcePowerUseCost(150)).setParent(stun2).setParentOffset(0, -1).setIconOffset(2, 2).setEffect(PowerEffect.stun, 2, 4.0F, true);
	public static final Power drain1 = new Power("drain1", new PowerStats().setXpCost(460).setBasePowerRequirement(1).setForcePowerUseCost(100)).setParent(darkSide).setParentOffset(0, -4).setIconOffset(3, 0).setEffect(PowerEffect.drain, 0);
	public static final Power drain2 = new Power("drain2", new PowerStats().setXpCost(1180).setForcePowerUseCost(150)).setParent(drain1).setParentOffset(0, -1).setIconOffset(3, 1).setEffect(PowerEffect.drain, 1);
	public static final Power drain3 = new Power("drain3", new PowerStats().setXpCost(1670).setForcePowerUseCost(200)).setParent(drain2).setParentOffset(0, -1).setIconOffset(3, 2).setEffect(PowerEffect.drain, 2);
	public static final Power lightning1 = new Power("lightning1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(5).setPowerType(EnumPowerType.PER_SECOND)).setParent(darkSide).setParentOffset(-2, -4).setIconOffset(4, 0).setEffect(PowerEffect.lightning, 0);
	public static final Power lightning2 = new Power("lightning2", new PowerStats().setXpCost(788).setForcePowerUseCost(10).setPowerType(EnumPowerType.PER_SECOND)).setParent(lightning1).setParentOffset(0, -1).setIconOffset(4, 1).setEffect(PowerEffect.lightning, 1);
	public static final Power lightning3 = new Power("lightning3", new PowerStats().setXpCost(1277).setForcePowerUseCost(20).setPowerType(EnumPowerType.PER_SECOND)).setParent(lightning2).setParentOffset(0, -1).setIconOffset(4, 2).setEffect(PowerEffect.lightning, 2);
	public static final Power wound1 = new Power("wound1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(darkSide).setParentOffset(-2, 0).setIconOffset(5, 0).setEffect(PowerEffect.choke, 0);
	public static final Power wound2 = new Power("wound2", new PowerStats().setXpCost(788).setForcePowerUseCost(115)).setParent(wound1).setParentOffset(0, -1).setIconOffset(5, 1).setEffect(PowerEffect.choke, 1);
	public static final Power wound3 = new Power("wound3", new PowerStats().setXpCost(1110).setForcePowerUseCost(170)).setParent(wound2).setParentOffset(0, -1).setIconOffset(5, 2).setEffect(PowerEffect.choke, 2);
	public static final Power stealth = new Power("stealth", new PowerStats().setXpCost(160).setBasePowerRequirement(1).setForcePowerUseCost(5).setPowerType(EnumPowerType.PER_SECOND)).setParent(neutral).setParentOffset(-3, 0).setIconOffset(11, 0).setEffect(PowerEffect.stealth, 0);
	public static final Power speed = new Power("speed", new PowerStats().setXpCost(160).setBasePowerRequirement(1).setForcePowerUseCost(30)).setParent(neutral).setParentOffset(3, 0).setIconOffset(12, 0).setEffect(PowerEffect.speed, 5.0F, 0);
	public static final Power sight1 = new Power("sight1", new PowerStats().setXpCost(160).setBasePowerRequirement(1).setForcePowerUseCost(25)).setParent(neutral).setParentOffset(-2, 2).setIconOffset(6, 0).setEffect(PowerEffect.gaze, 10.0F, 0);
	public static final Power sight2 = new Power("sight2", new PowerStats().setXpCost(394).setForcePowerUseCost(25)).setParent(sight1).setParentOffset(0, 1).setIconOffset(6, 1).setEffect(PowerEffect.gaze, 15.0F, 1);
	public static final Power sight3 = new Power("sight3", new PowerStats().setXpCost(550).setForcePowerUseCost(25)).setParent(sight2).setParentOffset(0, 1).setIconOffset(6, 2).setEffect(PowerEffect.gaze, 25.0F, 2);
	public static final Power meditation1 = new Power("meditation1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(75)).setParent(neutral).setParentOffset(-1, 2).setIconOffset(7, 0).setEffect(PowerEffect.meditation, 0);
	public static final Power meditation2 = new Power("meditation2", new PowerStats().setXpCost(788).setForcePowerUseCost(100)).setParent(meditation1).setParentOffset(0, 1).setIconOffset(7, 1).setEffect(PowerEffect.meditation, 1);
	public static final Power meditation3 = new Power("meditation3", new PowerStats().setXpCost(1100).setForcePowerUseCost(150)).setParent(meditation2).setParentOffset(0, 1).setIconOffset(7, 2).setEffect(PowerEffect.meditation, 2);
	public static final Power throw1 = new Power("throw1", new PowerStats().setXpCost(160).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(neutral).setParentOffset(0, 2).setIconOffset(10, 0).setEffect(PowerEffect.bladeThrow, 0);
	public static final Power throw2 = new Power("throw2", new PowerStats().setXpCost(394).setForcePowerUseCost(50)).setParent(throw1).setParentOffset(0, 1).setIconOffset(10, 1).setEffect(PowerEffect.bladeThrow, 1);
	public static final Power resist1 = new Power("resist1", new PowerStats().setXpCost(160).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(neutral).setParentOffset(1, 2).setIconOffset(8, 0).setEffect(PowerEffect.resist, 7.0F, 0);
	public static final Power resist2 = new Power("resist2", new PowerStats().setXpCost(394).setForcePowerUseCost(60)).setParent(resist1).setParentOffset(0, 1).setIconOffset(8, 1).setEffect(PowerEffect.resist, 9.0F, 1);
	public static final Power resist3 = new Power("resist3", new PowerStats().setXpCost(550).setForcePowerUseCost(70)).setParent(resist2).setParentOffset(0, 1).setIconOffset(8, 2).setEffect(PowerEffect.resist, 14.0F, 2);
	public static final Power push1 = new Power("push1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(neutral).setParentOffset(2, 2).setIconOffset(9, 0).setEffect(PowerEffect.push, 0);
	public static final Power push2 = new Power("push2", new PowerStats().setXpCost(788).setForcePowerUseCost(85)).setParent(push1).setParentOffset(0, 1).setIconOffset(9, 1).setEffect(PowerEffect.push, 1);
	public static final Power push3 = new Power("push3", new PowerStats().setXpCost(1100).setForcePowerUseCost(120)).setParent(push2).setParentOffset(0, 1).setIconOffset(9, 2).setEffect(PowerEffect.push, 2);
	
	private final String name;
	public Power parent;
	public int xOffset;
	public int yOffset;
	public int iconX;
	public int iconY;
	public PowerStats powerStats;
	public PowerEffect powerEffect;
	public Object[] powerEffectArgs;
	
	public Power(String s)
	{
		name = s;
		powerStats = new PowerStats();
		powerEffect = new PowerEffect();
		powers.add(this);
	}
	
	public Power(String s, PowerStats stats)
	{
		this(s);
		powerStats = stats;
	}
	
	public Power setParent(Power power)
	{
		parent = power;
		return this;
	}
	
	public Power setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
		return this;
	}
	
	public Power setParentOffset(int x, int y)
	{
		if (parent != null)
		{
			xOffset = parent.xOffset + x;
			yOffset = parent.yOffset + y;
		}
		
		return this;
	}
	
	public Power setIconOffset(int x, int y)
	{
		iconX = x;
		iconY = y;
		return this;
	}
	
	public Power setEffect(PowerEffect effect, Object... args)
	{
		powerEffect = effect;
		powerEffectArgs = args;
		return this;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNameTranslated()
	{
		return StatCollector.translateToLocal("forcepower.name." + name);
	}
	
	public static Power getPowerFromName(String s)
	{
		for (Power power : powers)
		{
			if (power.name.equals(s))
			{
				return power;
			}
		}
		
		return null;
	}
	
	public int getActualXpCost(EntityPlayer player)
	{
		int i = powerStats.xpCost;
		
		if (ALHelper.isLightPower(this))
		{
			i += i * ALHelper.getDarkPercentage(player);
		}
		else if (ALHelper.isDarkPower(this))
		{
			i += i * ALHelper.getLightPercentage(player);
		}
		
		return i;
	}
	
	public float getUseCost(EntityPlayer player)
	{
		return powerEffect.getUseCost(player, this, powerEffectArgs);
	}

	public String toString()
	{
		return String.format("%s[\"%s\"]", getClass().getName(), getName());
	}
	
	static
	{
		Effect.fortify.powers.add(fortify1);
		Effect.fortify.powers.add(fortify2);
		Effect.fortify.powers.add(fortify3);
		Effect.stun.powers.add(stun1);
		Effect.stun.powers.add(stun2);
		Effect.stun.powers.add(stun3);
		
		Effect.drain.powers.add(drain1);
		Effect.drain.powers.add(drain2);
		Effect.drain.powers.add(drain3);
		Effect.lightning.powers.add(lightning1);
		Effect.lightning.powers.add(lightning2);
		Effect.lightning.powers.add(lightning3);
		Effect.choke.powers.add(wound1);
		Effect.choke.powers.add(wound2);
		Effect.choke.powers.add(wound3);
		
		Effect.stealth.powers.add(stealth);
		Effect.speed.powers.add(speed);
		Effect.gaze.powers.add(sight1);
		Effect.gaze.powers.add(sight2);
		Effect.gaze.powers.add(sight3);
		Effect.meditation.powers.add(meditation1);
		Effect.meditation.powers.add(meditation2);
		Effect.meditation.powers.add(meditation3);
		Effect.resist.powers.add(resist1);
		Effect.resist.powers.add(resist2);
		Effect.resist.powers.add(resist3);
	}
}
