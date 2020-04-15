package fiskfille.utils.common.entity.attribute;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.minecraft.entity.ai.attributes.IAttribute;

public class AttributeWrapper
{
    private final List<AttributePair> modifiers = Lists.newArrayList();
    public final IAttribute attribute;

    public AttributeWrapper(IAttribute attribute)
    {
        this.attribute = attribute;
    }

    protected List<Double> getModifiers(int operation)
    {
        List<Double> list = Lists.newArrayList();

        for (AttributePair pair : modifiers)
        {
            if (operation == pair.operation)
            {
                list.add(pair.amount);
            }
        }

        return list;
    }

    public ImmutableList<AttributePair> getModifiers()
    {
        return ImmutableList.copyOf(modifiers);
    }

    public void apply(double amount, int operation)
    {
        modifiers.add(new AttributePair(amount, operation));
    }

    public boolean isEmpty()
    {
        return modifiers.isEmpty();
    }

    public double getValue(double baseValue)
    {
        List<Double> list = Lists.newArrayList(getModifiers(1));
        list.addAll(getModifiers(2));

        double amount;

        for (Iterator<Double> iter = list.iterator(); iter.hasNext(); baseValue *= 1 + amount)
        {
            amount = iter.next();
        }

        for (Iterator<Double> iter = getModifiers(0).iterator(); iter.hasNext(); baseValue += amount)
        {
            amount = iter.next();
        }

        return attribute.clampValue(baseValue);
    }

    public float getValue(float baseValue)
    {
        return (float) getValue((double) baseValue);
    }
}
