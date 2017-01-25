package net.ilexiconn.llibrary.client.model;

import java.util.HashMap;

import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.MathHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fiskfille.lightsabers.Lightsabers;

/**
 * @author iLexiconn
 * @since 1.0.0
 */
@SideOnly(Side.CLIENT)
public class ModelAnimator
{
    private int tempTick;
    private int prevTempTick;
    private boolean correctAnimation;
    private IAnimatedEntity entity;
    private HashMap<ModelRenderer, Transform> transformMap;
    private HashMap<ModelRenderer, Transform> prevTransformMap;

    private ModelAnimator()
    {
        tempTick = 0;
        correctAnimation = false;
        transformMap = new HashMap<>();
        prevTransformMap = new HashMap<>();
    }

    /**
     * @return a new ModelAnimator instance
     */
    public static ModelAnimator create()
    {
        return new ModelAnimator();
    }

    /**
     * @return the {@link IAnimatedEntity} instance. Null if {@link ModelAnimator#update} has never been called.
     */
    public IAnimatedEntity getEntity()
    {
        return entity;
    }

    /**
     * Update the animations of this model.
     * 
     * @param entity the entity instance
     */
    public void update(IAnimatedEntity entity)
    {
        tempTick = prevTempTick = 0;
        correctAnimation = false;
        this.entity = entity;
        transformMap.clear();
        prevTransformMap.clear();
    }

    /**
     * Start an animation
     * 
     * @param animation the animation instance
     * @return true if it's the current animation
     */
    public boolean setAnimation(Animation animation)
    {
        tempTick = prevTempTick = 0;
        correctAnimation = entity.getAnimation() == animation;
        return correctAnimation;
    }

    /**
     * Start a keyframe for the current animation.
     * 
     * @param duration the keyframe duration
     */
    public void startKeyframe(int duration)
    {
        if (!correctAnimation)
        {
            return;
        }
        prevTempTick = tempTick;
        tempTick += duration;
    }

    /**
     * Add a static keyframe with a specific duration to the animation.
     * 
     * @param duration the keyframe duration
     */
    public void setStaticKeyframe(int duration)
    {
        startKeyframe(duration);
        this.endKeyframe(true);
    }

    /**
     * Reset this keyframe to its original state
     * 
     * @param duration the keyframe duration
     */
    public void resetKeyframe(int duration)
    {
        startKeyframe(duration);
        this.endKeyframe();
    }

    /**
     * Rotate a box in the current keyframe. All the values are relative.
     * 
     * @param box the box to rotate
     * @param x the x rotation
     * @param y the y rotation
     * @param z the z rotation
     */
    public void rotate(ModelRenderer box, float x, float y, float z)
    {
        if (!correctAnimation)
        {
            return;
        }
        getTransform(box).addRotation(x, y, z);
    }

    /**
     * Move a box in the current keyframe. All the values are relative.
     * 
     * @param box the box to move
     * @param x the x offset
     * @param y the y offset
     * @param z the z offset
     */
    public void move(ModelRenderer box, float x, float y, float z)
    {
        if (!correctAnimation)
        {
            return;
        }
        getTransform(box).addOffset(x, y, z);
    }

    private Transform getTransform(ModelRenderer box)
    {
        Transform transform = transformMap.get(box);
        if (transform == null)
        {
            transform = new Transform();
            transformMap.put(box, transform);
        }
        return transform;
    }

    /**
     * End the current keyframe. this will reset all box transformations to their original state.
     */
    public void endKeyframe()
    {
        this.endKeyframe(false);
    }

    private void endKeyframe(boolean stationary)
    {
        if (!correctAnimation)
        {
            return;
        }
        int animationTick = entity.getAnimationTick();

        if (animationTick >= prevTempTick && animationTick < tempTick)
        {
            if (stationary)
            {
                for (ModelRenderer box : prevTransformMap.keySet())
                {
                    Transform transform = prevTransformMap.get(box);
                    box.rotateAngleX += transform.getRotationX();
                    box.rotateAngleY += transform.getRotationY();
                    box.rotateAngleZ += transform.getRotationZ();
                    box.rotationPointX += transform.getOffsetX();
                    box.rotationPointY += transform.getOffsetY();
                    box.rotationPointZ += transform.getOffsetZ();
                }
            }
            else
            {
                float tick = (animationTick - prevTempTick + Lightsabers.proxy.getPartialTicks()) / (tempTick - prevTempTick);
                float inc = MathHelper.sin((float) (tick * Math.PI / 2.0F)), dec = 1.0F - inc;
                for (ModelRenderer box : prevTransformMap.keySet())
                {
                    Transform transform = prevTransformMap.get(box);
                    box.rotateAngleX += dec * transform.getRotationX();
                    box.rotateAngleY += dec * transform.getRotationY();
                    box.rotateAngleZ += dec * transform.getRotationZ();
                    box.rotationPointX += dec * transform.getOffsetX();
                    box.rotationPointY += dec * transform.getOffsetY();
                    box.rotationPointZ += dec * transform.getOffsetZ();
                }
                for (ModelRenderer box : transformMap.keySet())
                {
                    Transform transform = transformMap.get(box);
                    box.rotateAngleX += inc * transform.getRotationX();
                    box.rotateAngleY += inc * transform.getRotationY();
                    box.rotateAngleZ += inc * transform.getRotationZ();
                    box.rotationPointX += inc * transform.getOffsetX();
                    box.rotationPointY += inc * transform.getOffsetY();
                    box.rotationPointZ += inc * transform.getOffsetZ();
                }
            }
        }

        if (!stationary)
        {
            prevTransformMap.clear();
            prevTransformMap.putAll(transformMap);
            transformMap.clear();
        }
    }
}
