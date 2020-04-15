package fiskfille.utils.registry;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

import fiskfille.utils.helper.FiskPredicates;
import net.minecraft.util.RegistrySimple;

public class FiskSimpleRegistry<T extends FiskRegistryEntry<T>> extends RegistrySimple implements Iterable<T>
{
    protected final Map nameLookup;

    private final String defaultDomain;
    private final String defaultKey;
    private T defaultValue;

    public FiskSimpleRegistry(String domain, String key)
    {
        nameLookup = ((BiMap) registryObjects).inverse();
        defaultDomain = domain;
        defaultKey = namespace(key);
    }

    public T getDefaultValue()
    {
        return defaultValue;
    }

    public void putObject(String key, T value)
    {
        key = namespace(key);

        if (containsKey(key))
        {
            throw new IllegalArgumentException(String.format("Duplicate key '%s'", key));
        }

        if (defaultKey != null && key.equals(defaultKey))
        {
            defaultValue = value;
        }

        value.setRegistryName(key);
        super.putObject(key, value);
    }

    @Override
    public void putObject(Object key, Object value)
    {
        putObject((String) key, (T) value);
    }

    @Override
    protected Map createUnderlyingMap()
    {
        return HashBiMap.create();
    }

    public T getObject(String key)
    {
        return castDefault((T) super.getObject(namespace(key)));
    }

    public String getNameForObject(T value)
    {
        return (String) nameLookup.get(value);
    }

    public boolean containsKey(String key)
    {
        return super.containsKey(namespace(key));
    }

    @Override
    public Iterator<T> iterator()
    {
        return registryObjects.values().iterator();
    }

    protected String namespace(String key)
    {
        return key != null && key.indexOf(':') == -1 ? defaultDomain + ":" + key : key;
    }

    @Override
    public boolean containsKey(Object key)
    {
        return containsKey((String) key);
    }

    public boolean containsValue(T value)
    {
        return registryObjects.values().contains(value);
    }

    @Override
    public T getObject(Object key)
    {
        return getObject((String) key);
    }

    @Override
    public Set<String> getKeys()
    {
        return super.getKeys();
    }

    public Set<String> getKeys(Predicate<T> p)
    {
        return Maps.filterEntries(registryObjects, FiskPredicates.filterValues(p)).keySet();
    }

    public T castDefault(T value)
    {
        if (value == null)
        {
            return getDefaultValue();
        }

        return value;
    }

    public T getRandom(Random rand)
    {
        return Iterables.get(this, rand.nextInt(getKeys().size()));
    }

    public T getRandom()
    {
        return getRandom(new Random());
    }
}
