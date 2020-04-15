package com.fiskmods.lightsabers.common.force;

public class PowerStats
{
    public final int xpCost;
    
    public int baseRequirement;
    public int baseBonus;
    public int forceBonus;
    public int regen;
    public int regenOperation;
    public float useCost;
    
    public PowerType powerType = PowerType.PER_USE;
    
    public PowerStats(int cost)
    {
        xpCost = cost;
    }

    public PowerStats setBaseReq(int amount)
    {
        baseRequirement = amount;
        return this;
    }

    public PowerStats setBaseBonus(int amount)
    {
        baseBonus = amount;
        return this;
    }

    public PowerStats setForceBonus(int amount)
    {
        forceBonus = amount;
        return this;
    }

    public PowerStats setRegen(int amount)
    {
        regen = amount;
        return this;
    }

    public PowerStats setRegen(int amount, int operation)
    {
        regen = amount;
        regenOperation = operation;
        return this;
    }

    public PowerStats setUseCost(float cost)
    {
        useCost = cost;
        return this;
    }

    public PowerStats setType(PowerType type)
    {
        powerType = type;
        return this;
    }
}
