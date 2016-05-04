package fiskfille.lightsabers.asm.transformers;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import fiskfille.lightsabers.asm.ALTranslator;
import fiskfille.lightsabers.common.helper.ModelHelper;

public class ClassTransformerModelBiped extends ClassTransformerMethodProcess
{
	public ClassTransformerModelBiped()
	{
		super("net.minecraft.client.model.ModelBiped", "a", "render", "(Lsa;FFFFFF)V", "(Lnet/minecraft/entity/Entity;FFFFFF)V");
	}
	
	@Override
	public void processMethod(MethodNode method)
	{
		InsnList list = new InsnList();

		for (int i = 0; i < method.instructions.size(); ++i)
		{
			AbstractInsnNode node = method.instructions.get(i);

			if (node instanceof MethodInsnNode)
			{
				MethodInsnNode methodNode = (MethodInsnNode)node;
				
				if (methodNode.name.equals(ALTranslator.getMappedName("a", "setRotationAngles")) && methodNode.desc.equals(ALTranslator.getMappedName("(FFFFFFLsa;)V", "(FFFFFFLnet/minecraft/entity/Entity;)V")))
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
					list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModelHelper.class), "renderBipedPre", ALTranslator.getMappedName("(Lbhm;Lsa;FFFFFF)V", "(Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/Entity;FFFFFF)V"), false));
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
				list.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModelHelper.class), "renderBipedPost", ALTranslator.getMappedName("(Lbhm;Lsa;FFFFFF)V", "(Lnet/minecraft/client/model/ModelBiped;Lnet/minecraft/entity/Entity;FFFFFF)V"), false));
			}

			list.add(node);
		}

		method.instructions.clear();
		method.instructions.add(list);
	}
}
