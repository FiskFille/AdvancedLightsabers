package fiskfille.utils.reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

public class GenericMethod<C, T>
{
    private final Method theMethod;
    
    public GenericMethod(Method method)
    {
        theMethod = method;
    }
    
    public T invoke(C instance, Object... args)
    {
        try
        {
            return (T) theMethod.invoke(instance, args);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    public T invokeStatic(Object... args)
    {
        return invoke(null, args);
    }
    
    public static class Builder<C, T>
    {
        private final Class<C> parentClass;
        private final Class<T> returnType;
        
        private Class[] parameters;
        
        private Builder(Class<C> parent, Class<T> type)
        {
            parentClass = parent;
            returnType = type;
        }
        
        public static <C, T> Builder in(Class<C> parent, Class<T> type)
        {
            return new Builder(parent, type);
        }
        
        public Builder with(Class... params)
        {
            parameters = params;
            return this;
        }
        
        public GenericMethod find(String... names)
        {
            for (String name : names)
            {
                for (Method method : parentClass.getDeclaredMethods())
                {
                    if (method.getName().equals(name) && method.getReturnType() == returnType)
                    {
                        if (parameters != null)
                        {
                            if (method.getParameters().length != parameters.length)
                            {
                                continue;
                            }
                            
                            for (int i = 0; i < parameters.length; ++i)
                            {
                                if (method.getParameterTypes()[i] != parameters[i])
                                {
                                    continue;
                                }
                            }
                        }
                        
                        method.setAccessible(true);
                        return new GenericMethod(method);
                    }
                }
            }
            
            throw new RuntimeException(String.format("Unable to locate method of type %s in %s: %s", returnType.getName(), parentClass.getName(), Arrays.asList(names)));
        }
    }
}
