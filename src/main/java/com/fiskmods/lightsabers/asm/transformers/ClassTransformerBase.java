package com.fiskmods.lightsabers.asm.transformers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import com.fiskmods.lightsabers.Lightsabers;
import com.fiskmods.lightsabers.asm.ALLoadingPlugin;

import net.minecraft.launchwrapper.IClassTransformer;

public abstract class ClassTransformerBase implements IClassTransformer, Opcodes
{
    public static final Logger LOGGER = LogManager.getFormatterLogger(Lightsabers.NAME);

    protected final String classPath;
    protected final String unobfClass;

    public ClassTransformerBase(String path)
    {
        classPath = path;
        unobfClass = path.substring(path.lastIndexOf('.') + 1);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        try
        {
            if (transformedName.equals(classPath))
            {
                LOGGER.info("Patching Class %s (%s)", unobfClass, name);

                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                ClassReader reader = new ClassReader(bytes);
                ClassNode node = new ClassNode();
                reader.accept(node, 0);

                setupMappings();
                boolean success = processFields(node.fields) && processMethods(node.methods);
                addInterface(node.interfaces);
                node.accept(writer);
                
                if (success)
                {
                    LOGGER.debug("Patching Class %s done", unobfClass);
                }
                else
                {
                    LOGGER.error("Patching Class %s FAILED!", unobfClass);
                }

                writeClassFile(writer, String.format("%s (%s)", unobfClass, name));

                return writer.toByteArray();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return bytes;
    }

    public void addInterface(List<String> interfaces)
    {
    }

    public abstract boolean processMethods(List<MethodNode> methods);

    public abstract boolean processFields(List<FieldNode> fields);

    public abstract void setupMappings();

    public void sendPatchLog(String method)
    {
        LOGGER.log(Level.INFO, "\tPatching method %s in %s", method, unobfClass);
    }

    public static void writeClassFile(ClassWriter cw, String name)
    {
        try
        {
            File outDir = new File("debug/");
            outDir.mkdirs();
            DataOutputStream dout = new DataOutputStream(new FileOutputStream(new File(outDir, name + ".class")));
            dout.write(cw.toByteArray());
            dout.flush();
            dout.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getClassName(String className)
    {
        return "net/minecraft/" + className.replace(".", "/");
    }

    public static String getMappedName(String name, String devName)
    {
        return ALLoadingPlugin.obfuscatedEnv ? name : devName;
    }
}
