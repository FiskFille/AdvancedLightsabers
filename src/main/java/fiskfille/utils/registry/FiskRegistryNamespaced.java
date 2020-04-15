package fiskfille.utils.registry;

import java.util.Iterator;

import net.minecraft.util.ObjectIntIdentityMap;

public class FiskRegistryNamespaced<T extends FiskRegistryEntry<T>> extends FiskSimpleRegistry<T>
{
    protected ObjectIntIdentityMap underlyingIntegerMap = new ObjectIntIdentityMap();
    private int maxId = 0;
    private int nextId = 0;

    public FiskRegistryNamespaced(String domain, String key)
    {
        super(domain, key);
    }

    public FiskRegistryNamespaced<T> setMaxId(int max)
    {
        maxId = max;
        return this;
    }

    @Override
    public void putObject(String key, T value)
    {
        addObject(nextId, key, value);
        ++nextId;
    }

    public void addObject(int id, String key, T value)
    {
        if (id < 0 || maxId > 0 && id > maxId)
        {
            throw new IndexOutOfBoundsException(String.format("Index: %s, Max: %s", id, maxId));
        }

        super.putObject(key, value);
        underlyingIntegerMap.func_148746_a(value, id);
    }

    public int getIDForObject(T value)
    {
        return underlyingIntegerMap.func_148747_b(value);
    }

    public T getObjectById(int id)
    {
        return castDefault((T) underlyingIntegerMap.func_148745_a(id));
    }

    public boolean containsId(int id)
    {
        return underlyingIntegerMap.func_148744_b(id);
    }

    @Override
    public Iterator iterator()
    {
        return underlyingIntegerMap.iterator();
    }

    public T lookup(String key)
    {
        if (containsKey(key))
        {
            return getObject(key);
        }

        try
        {
            int id = Integer.parseInt(key);

            if (containsId(id))
            {
                return getObjectById(id);
            }
        }
        catch (NumberFormatException e)
        {
        }

        return null;
    }
}
