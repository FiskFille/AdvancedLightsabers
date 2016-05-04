package fiskfille.lightsabers.asm;

public class ALTranslator
{
    public static boolean obfuscatedEnv;

    public static String getMappedClassName(String className)
    {
        return new StringBuilder("net/minecraft/").append(className.replace(".", "/")).toString();
    }

    public static String getMappedName(String name, String devName)
    {
        return obfuscatedEnv ? name : devName;
    }
}
