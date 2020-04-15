package com.fiskmods.lightsabers.asm.transformers;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.fiskmods.lightsabers.helper.ModelHelper;

public class ClassTransformerModelBipedMultiLayer extends ClassTransformerBase
{
    public static String varPlayer;
    public static String varEntity;

    public ClassTransformerModelBipedMultiLayer()
    {
        super("fiskfille.heroes.client.model.ModelBipedMultiLayer");
    }

    @Override
    public boolean processMethods(List<MethodNode> methods)
    {
        boolean flag = false;

        for (MethodNode method : methods)
        {
            if (method.name.equals(getMappedName("func_78088_a", "render")) && method.desc.equals("(Lnet/minecraft/entity/Entity;FFFFFF)V"))
            {
                InsnList list = new InsnList();

                for (int i = 0; i < method.instructions.size(); ++i)
                {
                    AbstractInsnNode node = method.instructions.get(i);

                    if (node instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodNode = (MethodInsnNode) node;

                        if (methodNode.name.equals("renderBipedPre") && methodNode.desc.equals("(Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/Entity;FFFFFF)V"))
                        {
                            list.add(node);
                            list.add(new VarInsnNode(ALOAD, 0));
                            list.add(new VarInsnNode(ALOAD, 1));
                            list.add(new VarInsnNode(FLOAD, 2));
                            list.add(new VarInsnNode(FLOAD, 3));
                            list.add(new VarInsnNode(FLOAD, 4));
                            list.add(new VarInsnNode(FLOAD, 5));
                            list.add(new VarInsnNode(FLOAD, 6));
                            list.add(new VarInsnNode(FLOAD, 7));
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModelHelper.class), "renderBipedPre", "(Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/Entity;FFFFFF)V", false));
                            continue;
                        }
                    }

                    if (node.getOpcode() == RETURN)
                    {
                        list.add(new VarInsnNode(ALOAD, 0));
                        list.add(new VarInsnNode(ALOAD, 1));
                        list.add(new VarInsnNode(FLOAD, 2));
                        list.add(new VarInsnNode(FLOAD, 3));
                        list.add(new VarInsnNode(FLOAD, 4));
                        list.add(new VarInsnNode(FLOAD, 5));
                        list.add(new VarInsnNode(FLOAD, 6));
                        list.add(new VarInsnNode(FLOAD, 7));
                        list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModelHelper.class), "renderBipedPost", "(Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/Entity;FFFFFF)V", false));
                    }

                    list.add(node);
                }

                method.instructions.clear();
                method.instructions.add(list);
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
