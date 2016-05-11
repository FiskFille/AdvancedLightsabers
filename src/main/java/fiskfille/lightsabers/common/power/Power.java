package fiskfille.lightsabers.common.power;

import java.util.List;

import net.minecraft.util.StatCollector;

import com.google.common.collect.Lists;

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
	public static final Power heal1 = new Power("heal1", new PowerStats().setXpCost(320).setBasePowerRequirement(1).setForcePowerUseCost(50)).setParent(lightSide).setParentOffset(0, -4).setIconOffset(0, 0);
	public static final Power heal2 = new Power("heal2", new PowerStats().setXpCost(966).setForcePowerUseCost(125)).setParent(heal1).setParentOffset(0, -1).setIconOffset(0, 1);
	public static final Power heal3 = new Power("heal3", new PowerStats().setXpCost(1440).setForcePowerUseCost(175)).setParent(heal2).setParentOffset(0, -1).setIconOffset(0, 2);
	public static final Power fortify1 = new Power("fortify1").setParent(lightSide).setParentOffset(2, -4).setIconOffset(1, 0);
	public static final Power fortify2 = new Power("fortify2").setParent(fortify1).setParentOffset(0, -1).setIconOffset(1, 1);
	public static final Power fortify3 = new Power("fortify3").setParent(fortify2).setParentOffset(0, -1).setIconOffset(1, 2);
	public static final Power stun1 = new Power("stun1").setParent(lightSide).setParentOffset(2, 0).setIconOffset(2, 0);
	public static final Power stun2 = new Power("stun2").setParent(stun1).setParentOffset(0, -1).setIconOffset(2, 1);
	public static final Power stun3 = new Power("stun3").setParent(stun2).setParentOffset(0, -1).setIconOffset(2, 2);
	public static final Power drain1 = new Power("drain1").setParent(darkSide).setParentOffset(0, -4).setIconOffset(3, 0);
	public static final Power drain2 = new Power("drain2").setParent(drain1).setParentOffset(0, -1).setIconOffset(3, 1);
	public static final Power drain3 = new Power("drain3").setParent(drain2).setParentOffset(0, -1).setIconOffset(3, 2);
	public static final Power lightning1 = new Power("lightning1").setParent(darkSide).setParentOffset(-2, -4).setIconOffset(4, 0);
	public static final Power lightning2 = new Power("lightning2").setParent(lightning1).setParentOffset(0, -1).setIconOffset(4, 1);
	public static final Power lightning3 = new Power("lightning3").setParent(lightning2).setParentOffset(0, -1).setIconOffset(4, 2);
	public static final Power wound1 = new Power("wound1").setParent(darkSide).setParentOffset(-2, 0).setIconOffset(5, 0);
	public static final Power wound2 = new Power("wound2").setParent(wound1).setParentOffset(0, -1).setIconOffset(5, 1);
	public static final Power wound3 = new Power("wound3").setParent(wound2).setParentOffset(0, -1).setIconOffset(5, 2);
	public static final Power stealth = new Power("stealth").setParent(neutral).setParentOffset(-3, 0).setIconOffset(11, 0);
	public static final Power speed = new Power("speed").setParent(neutral).setParentOffset(3, 0).setIconOffset(12, 0);
	public static final Power sight1 = new Power("sight1").setParent(neutral).setParentOffset(-2, 2).setIconOffset(6, 0);
	public static final Power sight2 = new Power("sight2").setParent(sight1).setParentOffset(0, 1).setIconOffset(6, 1);
	public static final Power sight3 = new Power("sight3").setParent(sight2).setParentOffset(0, 1).setIconOffset(6, 2);
	public static final Power meditation1 = new Power("meditation1").setParent(neutral).setParentOffset(-1, 2).setIconOffset(7, 0);
	public static final Power meditation2 = new Power("meditation2").setParent(meditation1).setParentOffset(0, 1).setIconOffset(7, 1);
	public static final Power meditation3 = new Power("meditation3").setParent(meditation2).setParentOffset(0, 1).setIconOffset(7, 2);
	public static final Power throw1 = new Power("throw1").setParent(neutral).setParentOffset(0, 2).setIconOffset(10, 0);
	public static final Power throw2 = new Power("throw2").setParent(throw1).setParentOffset(0, 1).setIconOffset(10, 1);
	public static final Power resist1 = new Power("resist1").setParent(neutral).setParentOffset(1, 2).setIconOffset(8, 0);
	public static final Power resist2 = new Power("resist2").setParent(resist1).setParentOffset(0, 1).setIconOffset(8, 1);
	public static final Power resist3 = new Power("resist3").setParent(resist2).setParentOffset(0, 1).setIconOffset(8, 2);
	public static final Power push1 = new Power("push1").setParent(neutral).setParentOffset(2, 2).setIconOffset(9, 0);
	public static final Power push2 = new Power("push2").setParent(push1).setParentOffset(0, 1).setIconOffset(9, 1);
	public static final Power push3 = new Power("push3").setParent(push2).setParentOffset(0, 1).setIconOffset(9, 2);
	
	private final String name;
	public Power parent;
	public int xOffset;
	public int yOffset;
	public int iconX;
	public int iconY;
	public PowerStats powerStats;
	
	public Power(String s)
	{
		name = s;
		powerStats = new PowerStats();
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
}
