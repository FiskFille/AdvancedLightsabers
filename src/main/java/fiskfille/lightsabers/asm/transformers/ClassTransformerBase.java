package fiskfille.lightsabers.asm.transformers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import net.minecraft.launchwrapper.IClassTransformer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

public abstract class ClassTransformerBase implements IClassTransformer, Opcodes
{
    public Logger logger = LogManager.getLogger("Advanced Lightsabers");

    public static final String SIMPLEST_METHOD_DESC = "()V";
    protected final String classPath;
    protected final String unobfClass;

    public ClassTransformerBase(String classPath)
    {
        this.classPath = classPath;
        unobfClass = classPath.substring(classPath.lastIndexOf('.') + 1);
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        try
        {
            if (transformedName.equals(classPath))
            {
                logger.log(Level.INFO, "Advanced Lightsabers - Patching Class " + unobfClass + " (" + name + ")");

                ClassReader cr = new ClassReader(bytes);
                ClassNode cn = new ClassNode();
                cr.accept(cn, 0);

                setupMappings();
                boolean success = processFields(cn.fields) && processMethods(cn.methods);
                addInterface(cn.interfaces);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                cn.accept(cw);

                logger.log(success ? Level.INFO : Level.ERROR, "Advanced Lightsabers - Patching Class " + unobfClass + (success ? " done" : " FAILED!"));

                writeClassFile(cw, unobfClass + " (" + name + ")");
                return cw.toByteArray();
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
        logger.log(Level.INFO, "\tPatching method " + method + " in " + unobfClass);
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
}
