package fiskfille.lightsabers.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import fiskfille.lightsabers.asm.transformers.ClassTransformerColor;
import fiskfille.lightsabers.asm.transformers.ClassTransformerEffectRenderer;
import fiskfille.lightsabers.asm.transformers.ClassTransformerEntityMob;
import fiskfille.lightsabers.asm.transformers.ClassTransformerEntityPlayer;
import fiskfille.lightsabers.asm.transformers.ClassTransformerModelBiped;
import fiskfille.lightsabers.asm.transformers.ClassTransformerModelBipedMultiLayer;

@MCVersion("1.7.10")
@TransformerExclusions("fiskfille.lightsabers.asm")
public class ALLoadingPlugin implements IFMLLoadingPlugin
{
    private static final String[] transformers = new String[]
    {
    	ClassTransformerModelBiped.class.getName(),
    	ClassTransformerModelBipedMultiLayer.class.getName(),
    	ClassTransformerEffectRenderer.class.getName(),
    	ClassTransformerColor.class.getName(),
    	ClassTransformerEntityMob.class.getName(),
    	ClassTransformerEntityPlayer.class.getName()
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
