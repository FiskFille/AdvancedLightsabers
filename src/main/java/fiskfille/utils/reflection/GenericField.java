package fiskfille.utils.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;

public class GenericField<C, T>
{
    private final Field theField;
    
    public GenericField(Class<C> parent, Class<T> type, String... names)
    {
        for (String name : names)
        {
            for (Field field : parent.getDeclaredFields())
            {
                if (field.getName().equals(name) && field.getType() == type)
                {
                    field.setAccessible(true);
                    theField = field;
                    return;
                }
            }
        }
        
        throw new RuntimeException(String.format("Unable to locate field of type %s in %s: %s", type.getName(), parent.getName(), Arrays.asList(names)));
    }
    
    public T get(C instance)
    {
        try
        {
            return (T) theField.get(instance);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public T getStatic()
    {
        return get(null);
    }
    
    public void set(C instance, T value)
    {
        try
        {
            theField.set(instance, value);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public void setStatic(T value)
    {
        set(null, value);
    }
}
