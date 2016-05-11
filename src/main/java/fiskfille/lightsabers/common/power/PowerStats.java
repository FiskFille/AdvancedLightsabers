package fiskfille.lightsabers.common.power;

public class PowerStats
{
	public int xpCost;
	public int basePowerRequirement;
	public int basePowerBonus;
	public int forcePowerBonus;
	public int forcePowerRegen;
	public int forcePowerRegenOperation;
	public int forcePowerUseCost;
	public EnumPowerType powerType = EnumPowerType.PER_USE;
	
	public PowerStats setXpCost(int i)
	{
		xpCost = i;
		return this;
	}

	public PowerStats setBasePowerRequirement(int i)
	{
		basePowerRequirement = i;
		return this;
	}

	public PowerStats setBasePowerBonus(int i)
	{
		basePowerBonus = i;
		return this;
	}

	public PowerStats setForcePowerBonus(int i)
	{
		forcePowerBonus = i;
		return this;
	}

	public PowerStats setForcePowerRegen(int i)
	{
		forcePowerRegen = i;
		return this;
	}
	
	public PowerStats setForcePowerRegen(int i, int j)
	{
		forcePowerRegen = i;
		forcePowerRegenOperation = j;
		return this;
	}

	public PowerStats setForcePowerUseCost(int i)
	{
		forcePowerUseCost = i;
		return this;
	}

	public PowerStats setPowerType(EnumPowerType type)
	{
		powerType = type;
		return this;
	}
}
