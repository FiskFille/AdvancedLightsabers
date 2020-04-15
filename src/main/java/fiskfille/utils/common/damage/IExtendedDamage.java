package fiskfille.utils.common.damage;

import net.minecraft.util.DamageSource;

public interface IExtendedDamage
{
    int getFlags();
    
    public enum DamageType
    {
        KINETIC(new DamageTypeCallback.Impl()
        {
            @Override
            public boolean isPresent(DamageType type, DamageSource source)
            {
                return source == DamageSource.fall || super.isPresent(type, source);
            }
        }),
        INTERNAL(new DamageTypeCallback.Impl()
        {
            @Override
            public boolean isPresent(DamageType type, DamageSource source)
            {
                return source == DamageSource.drown || source == DamageSource.starve || super.isPresent(type, source);
            }
        }),
        COLD,
        ENERGY,
        SOUND,
        LIGHTNING;
        
        private final DamageTypeCallback hook;
        
        private DamageType(DamageTypeCallback callback)
        {
            hook = callback;
        }
        
        private DamageType()
        {
            this(new DamageTypeCallback.Impl());
        }
        
        public int getFlag()
        {
            return 1 << ordinal();
        }
        
        public boolean isPresent(DamageSource source)
        {
            return hook.isPresent(this, source);
        }
        
        public static int with(int flags, DamageType... types)
        {
            for (DamageType type : types)
            {
                flags |= type.getFlag();
            }
            
            return flags;
        }
    }
    
    public static interface DamageTypeCallback
    {
        boolean isPresent(DamageType type, DamageSource source);
        
        public static class Impl implements DamageTypeCallback
        {
            @Override
            public boolean isPresent(DamageType type, DamageSource source)
            {
                return source instanceof IExtendedDamage && (((IExtendedDamage) source).getFlags() & type.getFlag()) == type.getFlag();
            }
        }
    }
}
