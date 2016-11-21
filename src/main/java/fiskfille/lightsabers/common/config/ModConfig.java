package fiskfille.lightsabers.common.config;

import java.io.IOException;

import net.minecraftforge.common.config.Configuration;

public class ModConfig
{
	public static final String CATEGORY_DYNAMIC_LIGHTS = "Dynamic Lights";
	public static final String CATEGORY_RENDERING = "Rendering";

	public static boolean dynamicLightsEnabled;
	public static int dynamicLightsUpdateInterval;

	public static float renderGlobalMultiplier;
	public static float renderWidthMultiplier;
	public static float renderOpacityMultiplier;
	public static float renderSmoothingMultiplier;
	public static float renderLightingMultiplier;
	public static boolean enableShaders;

	public static Configuration configFile;

	public static void load(Configuration config)
	{
		configFile = config;

		dynamicLightsEnabled = configFile.getBoolean("Enable Dynamic Lights Support", CATEGORY_DYNAMIC_LIGHTS, true, "Whether support for Dynamic Lights is enabled.");
		dynamicLightsUpdateInterval = configFile.getInt("Dynamic Lights Update Interval", CATEGORY_DYNAMIC_LIGHTS, 1000, 0, Integer.MAX_VALUE, "Update Interval time for all EntityLiving in milliseconds. The lower the better and costlier.");

		renderGlobalMultiplier = configFile.getFloat("Global Multiplier", CATEGORY_RENDERING, 1, 0, Float.MAX_VALUE, "Global multiplier for lightsaber rendering values.");
		renderWidthMultiplier = configFile.getFloat("Width Multiplier", CATEGORY_RENDERING, 1, 0, Float.MAX_VALUE, "Multiplier for lightsaber width.");
		renderOpacityMultiplier = configFile.getFloat("Opacity Multiplier", CATEGORY_RENDERING, 1, 0, Float.MAX_VALUE, "Multiplier for lightsaber opacity.");
		renderSmoothingMultiplier = configFile.getFloat("Smoothing Multiplier", CATEGORY_RENDERING, 1, 0, Float.MAX_VALUE, "Multiplier for lightsaber smooth lighting. (The higher, the prettier, but also more resource-costly.)");
		renderLightingMultiplier = configFile.getFloat("Lighting Multiplier", CATEGORY_RENDERING, 1, 0, Float.MAX_VALUE, "Multiplier for lightsaber lighting.");
		enableShaders = configFile.getBoolean("Enable Shaders", CATEGORY_RENDERING, true, "Enable use of shaders for certain Force powers?");
	}
}
