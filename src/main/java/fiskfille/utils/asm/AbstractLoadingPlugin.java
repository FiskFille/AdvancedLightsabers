package fiskfille.utils.asm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.Side;

public abstract class AbstractLoadingPlugin implements IFMLLoadingPlugin
{
    public static boolean obfuscatedEnv;
    
    public abstract void setupTransformers(Side side, List<Class> list);

    @Override
    public String[] getASMTransformerClass()
    {
        List<Class> classes = new ArrayList();
        setupTransformers(FMLLaunchHandler.side(), classes);
        
        String[] astring = new String[classes.size()];
        
        for (int i = 0; i < astring.length; ++i)
        {
            astring[i] = classes.get(i).getName();
        }
        
        return astring;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        obfuscatedEnv = Boolean.class.cast(data.get("runtimeDeobfuscationEnabled"));
    }
}
