package fiskfille.lightsabers.common.event;

import mods.battlegear2.api.RenderPlayerEventChild.PreRenderSheathed;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fiskfille.lightsabers.common.item.ModItems;

public class ClientEventHandlerBG
{
    @SubscribeEvent
    public void onPreRenderSheathed(PreRenderSheathed event)
    {
        ItemStack itemstack = event.element;

        if (itemstack != null)
        {
            if (itemstack.getItem() == ModItems.lightsaber || itemstack.getItem() == ModItems.doubleLightsaber)
            {
                event.setCanceled(true);
            }
        }
    }
}
