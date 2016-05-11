package fiskfille.lightsabers.common.helper;

import net.minecraft.entity.player.EntityPlayer;

public class ALHelper
{
	public static int getXpBarCap(int level)
    {
        return level >= 30 ? 62 + (level - 30) * 7 : (level >= 15 ? 17 + (level - 15) * 3 : 17);
    }
	
	public static int getTotalXp(EntityPlayer player)
	{
//		int totalXp = Math.round(player.xpBarCap() * player.experience);
//		int prevLevel = player.experienceLevel;
//
//		for (int k = 0; k < prevLevel; ++k)
//		{
//			player.experienceLevel = k;
//			totalXp += player.xpBarCap();
//		}
//
//		player.experienceLevel = prevLevel;
//		return totalXp;
		
		int totalXp = Math.round(player.xpBarCap() * player.experience);

		for (int i = 0; i < player.experienceLevel; ++i)
		{
			totalXp += getXpBarCap(i);
		}

		return totalXp;
	}
	
	public static void removeExperience(EntityPlayer player, int amount)
	{
        int i = getTotalXp(player);

        if (amount > i)
        {
            amount = i;
        }
        
//        int j = 0;
//        
//        for (int k = 0; k < amount; ++k)
//        {
//        	player.experienceTotal -= 1;
//        }

        player.experience -= (float)amount / (float)player.xpBarCap();

//        for (player.experienceTotal -= amount; player.experience <= 0.0F; player.experience *= (float)player.xpBarCap())
//        {
//            player.experience = (player.experience + 1.0F) * (float)player.xpBarCap();
//            player.addExperienceLevel(-1);
//        }
	}
}
