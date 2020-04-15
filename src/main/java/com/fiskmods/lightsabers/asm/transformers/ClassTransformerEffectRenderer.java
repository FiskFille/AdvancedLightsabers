package com.fiskmods.lightsabers.asm.transformers;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.IincInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.fiskmods.lightsabers.client.particle.ALParticles;

public class ClassTransformerEffectRenderer extends ClassTransformerBase
{
    public static String varPlayer;
    public static String varEntity;

    public ClassTransformerEffectRenderer()
    {
        super("net.minecraft.client.particle.EffectRenderer");
    }

    public static int plus(int i)
    {
        ++i;

        if (i == 3)
        {
            ++i;
        }

        return i;
    }

    @Override
    public boolean processMethods(List<MethodNode> methods)
    {
        String[] names = {"<init>", getMappedName("a", "updateEffects"), getMappedName("a", "renderParticles"), getMappedName("b", "renderLitParticles"), getMappedName("a", "clearEffects")};
        String[] descs = {getMappedName("(Lahb;Lbqf;)V", "(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;)V"), "()V", getMappedName("(Lsa;F)V", "(Lnet/minecraft/entity/Entity;F)V"), getMappedName("(Lsa;F)V", "(Lnet/minecraft/entity/Entity;F)V"), getMappedName("(Lahb;)V", "(Lnet/minecraft/world/World;)V")};
        boolean flag = false;

        for (MethodNode method : methods)
        {
            for (int i = 0; i < names.length; ++i)
            {
                String name = names[i];
                String desc = descs[i];

                if (method.name.equals(name) && method.desc.equals(desc))
                {
                    InsnList list = new InsnList();

                    for (int j = 0; j < method.instructions.size(); ++j)
                    {
                        AbstractInsnNode node = method.instructions.get(j);

                        if (node instanceof InsnNode)
                        {
                            InsnNode insnNode = (InsnNode) node;

                            if (insnNode.getOpcode() == ICONST_3)
                            {
                                if (method.name.equals(names[2]) && method.desc.equals(descs[2]))
                                {
                                    list.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(ALParticles.class), "fxLayersSize", "I"));
                                    continue;
                                }
                            }
                            else if (insnNode.getOpcode() == ICONST_4)
                            {
                                list.add(new FieldInsnNode(GETSTATIC, Type.getInternalName(ALParticles.class), "fxLayersSize", "I"));
                                continue;
                            }
                        }

                        list.add(node);
                    }

                    method.instructions.clear();
                    method.instructions.add(list);
                    flag = true;
                }
            }

            if (method.name.equals(names[2]) && method.desc.equals(descs[2]))
            {
                InsnList list = new InsnList();
                int line = 0;

                for (int j = 0; j < method.instructions.size(); ++j)
                {
                    AbstractInsnNode node = method.instructions.get(j);

                    if (node instanceof LineNumberNode)
                    {
                        LineNumberNode lineNode = (LineNumberNode) node;
                        line = lineNode.line;
                    }

                    if (node instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodNode = (MethodInsnNode) node;

                        if (node.getOpcode() == INVOKEVIRTUAL && methodNode.owner.equals(getMappedName("bmh", "net/minecraft/client/renderer/Tessellator")) && methodNode.name.equals(getMappedName("b", "startDrawingQuads")) && methodNode.desc.equals("()V"))
                        {
                            list.add(node);

                            LabelNode labelNode = new LabelNode();
                            list.add(labelNode);
                            list.add(new LineNumberNode(line + 1, labelNode));
                            list.add(new VarInsnNode(ILOAD, 9));
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ALParticles.class), "bindParticleTextures", "(I)V", false));
                            continue;
                        }
                    }

                    if (node instanceof IincInsnNode)
                    {
                        IincInsnNode iincNode = (IincInsnNode) node;

                        if (iincNode.var == 8 && iincNode.incr == 1)
                        {
                            list.add(new VarInsnNode(ILOAD, 8));
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ClassTransformerEffectRenderer.class), "plus", "(I)I", false));
                            list.add(new VarInsnNode(ISTORE, 8));
                            continue;
                        }
                    }

                    list.add(node);
                }

                method.instructions.clear();
                method.instructions.add(list);
                flag = true;
            }

            if (method.name.equals(getMappedName("b", "getStatistics")) && method.desc.equals("()Ljava/lang/String;"))
            {
                InsnList list = new InsnList();
                boolean open = false;

                for (int i = 0; i < method.instructions.size(); ++i)
                {
                    AbstractInsnNode node = method.instructions.get(i);

                    if (node.getOpcode() == ARETURN)
                    {
                        open = false;
                    }

                    if (open)
                    {
                        continue;
                    }

                    if (node instanceof TypeInsnNode)
                    {
                        TypeInsnNode typeNode = (TypeInsnNode) node;

                        if (node.getOpcode() == NEW && typeNode.desc.equals("java/lang/StringBuilder"))
                        {
                            open = true;
                            list.add(new VarInsnNode(ALOAD, 0));
                            list.add(new FieldInsnNode(GETFIELD, getMappedName("bkn", "net/minecraft/client/particle/EffectRenderer"), getMappedName("c", "fxLayers"), "[Ljava/util/List;"));
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ALParticles.class), "getParticlesInWorld", "([Ljava/util/List;)I", false));
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(String.class), "valueOf", "(I)Ljava/lang/String;", false));
                            continue;
                        }
                    }

                    list.add(node);
                }

                method.instructions.clear();
                method.instructions.add(list);
                method.visitMaxs(1, 1);
                flag = true;
            }
        }

        return flag;
    }

    @Override
    public boolean processFields(List<FieldNode> fields)
    {
        return true;
    }

    @Override
    public void setupMappings()
    {
        varPlayer = getMappedName("yz", "net/minecraft/entity/player/EntityPlayer");
        varEntity = getMappedName("sa", "net/minecraft/entity/Entity");
    }
}
