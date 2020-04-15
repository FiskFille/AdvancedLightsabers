package fiskfille.utils.common.keybinds;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import fiskfille.utils.common.interaction.InteractionHandler;
import fiskfille.utils.common.interaction.InteractionHandler.InteractionType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public enum FiskKeyHandler
{
    INSTANCE;
    
    public static final List<FiskKeyBinding> KEYS = new ArrayList();
    private Minecraft mc = Minecraft.getMinecraft();

    private int pressed;
    private int[] timePressed;

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        EntityPlayer player = mc.thePlayer;

        if (mc.currentScreen == null)
        {
            int prevPressed = pressed;
            pressed = 0;
            
            for (int i = 0; i < KEYS.size(); ++i)
            {
                if (KEYS.get(i).getIsKeyPressed())
                {
                    pressed |= 1 << i;
                }
            }
            
            if (pressed != prevPressed)
            {
                InteractionHandler.interact(mc.thePlayer, InteractionType.KEY_PRESS, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.boundingBox.minY), MathHelper.floor_double(player.posZ));
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(ClientTickEvent event)
    {
        EntityPlayer player = mc.thePlayer;

        if (mc.currentScreen == null && event.phase == Phase.END)
        {
            if (timePressed == null)
            {
                timePressed = new int[KEYS.size()];
            }
            
            for (int i = 0; i < KEYS.size(); ++i)
            {
                if (KEYS.get(i).getIsKeyPressed())
                {
                    if (++timePressed[i] == 5)
                    {
                        InteractionHandler.interact(mc.thePlayer, InteractionType.KEY_HOLD, MathHelper.floor_double(player.posX), MathHelper.floor_double(player.boundingBox.minY), MathHelper.floor_double(player.posZ));
                    }
                }
                else
                {
                    timePressed[i] = 0;
                }
            }
        }
    }
    
    public static void register()
    {
        FMLCommonHandler.instance().bus().register(INSTANCE);
    }
}
