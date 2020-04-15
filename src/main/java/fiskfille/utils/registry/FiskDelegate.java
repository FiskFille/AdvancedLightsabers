package fiskfille.utils.registry;

import com.google.common.base.Objects;

import cpw.mods.fml.common.registry.RegistryDelegate;

public class FiskDelegate<T> implements RegistryDelegate<T>
{
    private T referant;
    private String name;
    private final Class<T> type;

    public FiskDelegate(T referant, Class<T> type)
    {
        this.referant = referant;
        this.type = type;
    }

    @Override
    public T get()
    {
        return referant;
    }

    @Override
    public String name()
    {
        return name;
    }

    @Override
    public Class<T> type()
    {
        return type;
    }

    void changeReference(T newTarget)
    {
        this.referant = newTarget;
    }

    void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Delegate)
        {
            Delegate<?> other = (Delegate<?>) obj;

            return Objects.equal(other.name(), name);
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }
}
