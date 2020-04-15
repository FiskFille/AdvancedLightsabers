package com.fiskmods.lightsabers.asm.transformers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.fiskmods.lightsabers.asm.ASMHooksClient;

import net.minecraft.launchwrapper.IClassTransformer;

public class ClassTransformerColor implements IClassTransformer, Opcodes
{
    @Override
    public byte[] transform(String name, String transformedName, byte[] bytes)
    {
        try
        {
            ClassReader cr = new ClassReader(bytes);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);

            boolean success = processMethods(cn.methods);

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cn.accept(cw);

//			if (success)
//			{
//				writeClassFile(cw, transformedName.substring(transformedName.lastIndexOf('.') + 1) + " (" + name + ")");
//			}

            return cw.toByteArray();
        }
        catch (Exception e)
        {
            if (!(e instanceof NullPointerException))
            {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    public boolean processMethods(List<MethodNode> methods)
    {
        boolean flag = false;

        for (MethodNode method : methods)
        {
            InsnList list = new InsnList();

            for (int j = 0; j < method.instructions.size(); ++j)
            {
                AbstractInsnNode node = method.instructions.get(j);

                if (node instanceof MethodInsnNode)
                {
                    MethodInsnNode methodNode = (MethodInsnNode) node;

                    if (node.getOpcode() == INVOKESTATIC && methodNode.owner.equals("org/lwjgl/opengl/GL11") && (methodNode.name.startsWith("glColor3") || methodNode.name.startsWith("glColor4")))
                    {
                        list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ASMHooksClient.class), methodNode.name, methodNode.desc, false));
                        flag = true;
                        continue;
                    }
                }

                list.add(node);
            }

            method.instructions.clear();
            method.instructions.add(list);
        }

        return flag;
    }

    public static void writeClassFile(ClassWriter cw, String name)
    {
        try
        {
            File outDir = new File("debug/glColor/");
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
