package fiskfille.utils.common.entity.attribute;

public class AttributePair
{
    public final Double amount;
    public final Integer operation;

    public AttributePair(double amount, int operation)
    {
        this.amount = amount;
        this.operation = operation;
    }

    @Override
    public String toString()
    {
        return String.format("AttributePair[amt=%s,op=%s]", amount, operation);
    }

    @Override
    public int hashCode()
    {
        return amount.hashCode() ^ operation.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AttributePair)
        {
            AttributePair pair = (AttributePair) obj;

            return pair.amount.equals(amount) && pair.operation.equals(operation);
        }

        return false;
    }
}
