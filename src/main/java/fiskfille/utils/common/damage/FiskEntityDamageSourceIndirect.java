package fiskfille.utils.common.damage;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSourceIndirect;

public class FiskEntityDamageSourceIndirect extends EntityDamageSourceIndirect implements IExtendedDamage
{
    private int flags;

    public FiskEntityDamageSourceIndirect(String domain, String name, Entity entity, Entity indirectEntity)
    {
        super(domain + "." + name, entity, indirectEntity);
    }
    
    public FiskEntityDamageSourceIndirect with(DamageType... types)
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
