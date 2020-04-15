package fiskfille.utils.common.damage;

import net.minecraft.util.DamageSource;

public class FiskDamageSource extends DamageSource implements IExtendedDamage
{
    private int flags;

    public FiskDamageSource(String domain, String name)
    {
        super(domain + "." + name);
    }
    
    public FiskDamageSource with(DamageType... types)
    {
        flags = DamageType.with(flags, types);
        return this;
    }

    @Override
    public int getFlags()
    {
        return flags;
    }
}
