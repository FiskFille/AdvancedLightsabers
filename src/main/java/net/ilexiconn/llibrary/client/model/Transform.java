package net.ilexiconn.llibrary.client.model;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author iLexiconn
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class Transform
{
    private float rotationX;
    private float rotationY;
    private float rotationZ;
    private float offsetX;
    private float offsetY;
    private float offsetZ;

    /**
     * @return the x rotation
     */
    public float getRotationX()
    {
        return rotationX;
    }

    /**
     * @return the y rotation
     */
    public float getRotationY()
    {
        return rotationY;
    }

    /**
     * @return the z rotation
     */
    public float getRotationZ()
    {
        return rotationZ;
    }

    /**
     * @return the x offset
     */
    public float getOffsetX()
    {
        return offsetX;
    }

    /**
     * @return the y offset
     */
    public float getOffsetY()
    {
        return offsetY;
    }

    /**
     * @return the z offset
     */
    public float getOffsetZ()
    {
        return offsetZ;
    }

    /**
     * Add rotation to this transformation
     * 
     * @param x the x rotation
     * @param y the y rotation
     * @param z the z rotation
     */
    public void addRotation(float x, float y, float z)
    {
        rotationX += x;
        rotationY += y;
        rotationZ += z;
    }

    /**
     * Add offset to this transformation
     * 
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    public void addOffset(float x, float y, float z)
    {
        offsetX += x;
        offsetY += y;
        offsetZ += z;
    }

    /**
     * Reset the rotation of this transformation
     */
    public void resetRotation()
    {
        rotationX = 0.0F;
        rotationY = 0.0F;
        rotationZ = 0.0F;
    }

    /**
     * Reset the offset of this transformation
     */
    public void resetOffset()
    {
        offsetX = 0.0F;
        offsetY = 0.0F;
        offsetZ = 0.0F;
    }

    /**
     * Set the rotation of this transformation
     * 
     * @param x the x rotation
     * @param y the y rotation
     * @param z the z rotation
     */
    public void setRotation(float x, float y, float z)
    {
        resetRotation();
        addRotation(x, y, z);
    }

    /**
     * Set the offset of this transformation
     * 
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    public void setOffset(float x, float y, float z)
    {
        resetOffset();
        addOffset(x, y, z);
    }
}
