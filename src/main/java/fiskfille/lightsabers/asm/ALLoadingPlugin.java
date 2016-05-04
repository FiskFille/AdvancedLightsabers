package fiskfille.lightsabers.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import fiskfille.lightsabers.asm.transformers.ClassTransformerModelBiped;

@MCVersion("1.7.10")
@TransformerExclusions("fiskfille.lightsabers.asm")
public class ALLoadingPlugin implements IFMLLoadingPlugin
{
    private static final String[] transformers = new String[]
    {
    	ClassTransformerModelBiped.class.getName()
    };
    
    @Override
    public String[] getASMTransformerClass()
    {
        return transformers;
    }
    
    @Override
    public String getAccessTransformerClass()
    {
        return null;
    }

    @Override
    public String getModContainerClass()
    {
        return null;
    }

    @Override
    public String getSetupClass()
    {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data)
    {
        ALTranslator.obfuscatedEnv = Boolean.class.cast(data.get("runtimeDeobfuscationEnabled"));
    }
}
