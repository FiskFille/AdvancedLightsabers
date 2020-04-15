package com.fiskmods.lightsabers.common.force;

import java.util.List;

import com.fiskmods.lightsabers.common.data.ALData;

import net.minecraft.entity.player.EntityPlayer;

public class PowerManager
{
    private final EntityPlayer thePlayer;

    public PowerManager(EntityPlayer player)
    {
        thePlayer = player;
    }

    public int getHierarchy(Power power)
    {
        if (hasPowerUnlocked(power))
        {
            return 0;
        }
        else
        {
            int i = 0;

            for (Power parent = power.parent; parent != null && !hasPowerUnlocked(parent); ++i)
            {
                parent = parent.parent;
            }

            return i;
        }
    }

    public boolean hasPowerUnlocked(Power power)
    {
        return hasPowerUnlocked(thePlayer, power);
    }

    public boolean canUnlockPower(Power power)
    {
        return canUnlockPower(thePlayer, power);
    }

    public PowerData getPowerData(Power power)
    {
        return getPowerData(thePlayer, power);
    }

    public static boolean unlockPower(EntityPlayer player, Power power)
    {
        if (!hasPowerUnlocked(player, power))
        {
            PowerData data = getPowerData(player, power);

            if (data != null)
            {
                data.setUnlocked(player, true);
                data.xpInvested = power.getActualXpCost(player);
                
                ALData.BASE_POWER.incrWithoutNotify(player, (byte) (power.powerStats.baseBonus - power.powerStats.baseRequirement));

                if (power == Power.FORCE_SENSITIVITY)
                {
                    for (ForceSide side : ForceSide.values())
                    {
                        unlockPower(player, side.getPower());
                    }
                }

                unlockPower(player, power.parent);

                return true;
            }
        }

        return false;
    }

    public static boolean removePower(EntityPlayer player, Power power)
    {
        if (hasPowerUnlocked(player, power))
        {
            PowerData data = getPowerData(player, power);

            if (data != null)
            {
                data.setUnlocked(player, false);
                data.xpInvested = 0;
                
                ALData.BASE_POWER.incrWithoutNotify(player, (byte) (power.powerStats.baseRequirement - power.powerStats.baseBonus));

                for (Power child : power.children)
                {
                    removePower(player, child);
                }

                return true;
            }
        }

        return false;
    }

    public static boolean hasPowerUnlocked(EntityPlayer player, Power power)
    {
        PowerData data = getPowerData(player, power);
        return data != null && data.isUnlocked();
    }

    public static boolean canUnlockPower(EntityPlayer player, Power power)
    {
        return power.parent == null || hasPowerUnlocked(player, power.parent);
    }

    public static PowerData getPowerData(EntityPlayer player, Power power)
    {
        return ALData.POWERS.get(player).get(power);
    }

    public static Power getSelectedPower(EntityPlayer player)
    {
        List<Power> selectedPowers = ALData.SELECTED_POWERS.get(player);

        if (!selectedPowers.isEmpty())
        {
            int index = ALData.SELECTED_POWER.get(player);

            if (index >= 0 && index < selectedPowers.size())
            {
                return selectedPowers.get(index);
            }
        }

        return null;
    }
}
