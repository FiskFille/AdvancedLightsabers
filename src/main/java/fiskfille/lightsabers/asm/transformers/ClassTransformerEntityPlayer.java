package fiskfille.lightsabers.asm.transformers;

import java.util.List;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import fiskfille.lightsabers.asm.ALTranslator;
import fiskfille.lightsabers.asm.ASMHooks;

public class ClassTransformerEntityPlayer extends ClassTransformerBase
{
    public static String varPlayer;
    public static String varEntity;

    public ClassTransformerEntityPlayer()
    {
        super("net.minecraft.entity.player.EntityPlayer");
    }

    @Override
    public boolean processMethods(List<MethodNode> methods)
    {
        boolean flag = false;

        for (MethodNode method : methods)
        {
            if (method.name.equals(ALTranslator.getMappedName("r", "attackTargetEntityWithCurrentItem")) && method.desc.equals("(L" + varEntity + ";)V"))
            {
                InsnList list = new InsnList();

                for (int i = 0; i < method.instructions.size(); ++i)
                {
                    AbstractInsnNode node = method.instructions.get(i);

                    if (node instanceof MethodInsnNode)
                    {
                        MethodInsnNode methodNode = (MethodInsnNode) node;

                        if (methodNode.name.equals(ALTranslator.getMappedName("a", "attackEntityFrom")) && methodNode.desc.equals(ALTranslator.getMappedName("(Lro;F)Z", "(Lnet/minecraft/util/DamageSource;F)Z")))
                        {
                            list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ASMHooks.class), "attackEntityFrom", ALTranslator.getMappedName("(L" + varEntity + ";Lro;F)Z", "(L" + varEntity + ";Lnet/minecraft/util/DamageSource;F)Z"), false));
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
        varPlayer = ALTranslator.getMappedName("yz", "net/minecraft/entity/player/EntityPlayer");
        varEntity = ALTranslator.getMappedName("sa", "net/minecraft/entity/Entity");
    }
}
