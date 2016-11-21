package fiskfille.lightsabers.common.data;

import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;

public class StatusEffect
{
	public int id;
	public int duration;
	public int amplifier;
	public UUID casterUUID;
	
	public StatusEffect(int id, int duration, int amplifier)
	{
		this.id = id;
		this.duration = duration;
		this.amplifier = amplifier;
	}
	
	public StatusEffect(int id, int duration, int amplifier, UUID uuid)
	{
		this(id, duration, amplifier);
		this.casterUUID = uuid;
	}
	
	public StatusEffect(int id, int duration, int amplifier, EntityLivingBase entity)
	{
		this(id, duration, amplifier, entity != null ? entity.getUniqueID() : null);
	}
}
