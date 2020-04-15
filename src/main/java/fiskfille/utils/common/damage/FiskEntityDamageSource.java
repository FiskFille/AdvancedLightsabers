package fiskfille.utils.common.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;

public class FiskEntityDamageSource extends EntityDamageSource implements IExtendedDamage
{
    private int flags;

    public FiskEntityDamageSource(String domain, String name, Entity entity)
    {
        super(domain + "." + name, entity);
    }
    
    public FiskEntityDamageSource with(DamageType... types)
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
