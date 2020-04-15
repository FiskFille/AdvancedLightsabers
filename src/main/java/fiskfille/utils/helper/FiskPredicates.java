package fiskfille.utils.helper;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class FiskPredicates
{
    public static final Map<Class, Map<String, Predicate>> METHODS = Maps.newHashMap();

    public static final Predicate<Entity> ON_GROUND = new Predicate<Entity>()
    {
        @Override
        public boolean apply(Entity input)
        {
            return input.onGround;
        }
    };

    public static final Predicate<Entity> ALIVE = new Predicate<Entity>()
    {
        @Override
        public boolean apply(Entity input)
        {
            return input.isEntityAlive();
        }
    };

    public static final Predicate<Entity> IS_FLYING = new Predicate<Entity>()
    {
        @Override
        public boolean apply(Entity input)
        {
            return input instanceof EntityPlayer && ((EntityPlayer) input).capabilities.isFlying;
        }
    };

    public static <T> Predicate<T> forInput(Class<T> clazz, String input)
    {
        List<Predicate<T>> list = Lists.newArrayList();
        String[] astring = input.split(" && ");

        for (String s : astring)
        {
            boolean inverted = false;

            while (s.startsWith("!"))
            {
                s = s.substring(1);
                inverted = !inverted;
            }

            Predicate<T> p = getHook(clazz, s);

            if (p == null)
            {
                continue;
            }

            if (inverted)
            {
                p = Predicates.not(p);
            }

            list.add(p);
        }

        return Predicates.and(list);
    }

    public static <T> void addHook(String key, Class<T> clazz, Predicate<T> p)
    {
        getHooks(clazz).put(key, p);
    }

    public static Map<String, Predicate> getHooks(Class clazz)
    {
        Map<String, Predicate> map = METHODS.get(clazz);

        if (map == null)
        {
            METHODS.put(clazz, map = Maps.newHashMap());
        }

        return map;
    }

    public static Predicate getHook(Class clazz, String key)
    {
        Predicate p = getHooks(clazz).get(key);

        return p == null ? Predicates.alwaysFalse() : p;
    }

    public static <K, V> Predicate<Entry<K, V>> filterKeys(final Predicate<K> p)
    {
        return new Predicate<Entry<K, V>>()
        {
            @Override
            public boolean apply(Entry<K, V> input)
            {
                return p.apply(input.getKey());
            }
        };
    }

    public static <K, V> Predicate<Entry<K, V>> filterValues(final Predicate<V> p)
    {
        return new Predicate<Entry<K, V>>()
        {
            @Override
            public boolean apply(Entry<K, V> input)
            {
                return p.apply(input.getValue());
            }
        };
    }

//    public static <T extends Entity> Predicate<T> cast(final Class<T> clazz, final Predicate p)
//    {
//        if (p == null)
//        {
//            return null;
//        }
//
//        return new Predicate<T>()
//        {
//            @Override
//            public boolean apply(T input)
//            {
//                return clazz.isInstance(input) && p.apply(input);
//            }
//        };
//    }

    public static Predicate<Object> ofType(final Class<?> type)
    {
        return new Predicate<Object>()
        {
            @Override
            public boolean apply(Object input)
            {
                return type.isInstance(input);
            }
        };
    }

    static
    {
        addHook("hasTab", Item.class, new Predicate<Item>()
        {
            @Override
            public boolean apply(Item input)
            {
                return input.getCreativeTab() != null;
            }
        });

        for (final ModContainer mod : Loader.instance().getModList())
        {
            addHook("fromMod_" + mod.getModId(), Item.class, new Predicate<Item>()
            {
                @Override
                public boolean apply(Item input)
                {
                    return input.delegate.name().startsWith(mod.getModId() + ":");
                }
            });
        }
    }
}
