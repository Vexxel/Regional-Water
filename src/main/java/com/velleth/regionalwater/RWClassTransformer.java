package com.velleth.regionalwater;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.util.Arrays;

import static org.objectweb.asm.Opcodes.*;

public class RWClassTransformer implements IClassTransformer
{
    private static final String[] targetClasses = {
            "net.minecraft.block.BlockDynamicLiquid"
    };

    @Override
    public byte[] transform(String name, String transformedName, byte[] targetClass) {
        boolean isObfuscated = !name.equals(transformedName);
        int index = Arrays.asList(targetClasses).indexOf(transformedName);
        return index != -1 ? transform(index, targetClass, isObfuscated) : targetClass;
    }

    private static byte[] transform(int index, byte[] classBeingTransformed, boolean isObfuscated) {
        RWModContainer.log.info("Transforming: " + targetClasses[index]);
        try {
            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classBeingTransformed);
            classReader.accept(classNode, 0);

            switch(index) {
                case 0:
                    transformBlockFluid(classNode, isObfuscated);
                    break;
            }

            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(classWriter);
            return classWriter.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return classBeingTransformed;
    }

    private static void transformBlockFluid(ClassNode blockFluidClass, boolean isObfuscated) {
        final String UPDATE_TICK = isObfuscated ? "a" : "updateTick";
        final String UPDATE_TICK_DESC = isObfuscated ? "(Lahb;IIILjava/util/Random;)V" : "(Lnet/minecraft/world/World;IIILjava/util/Random;)V";

        for (MethodNode method : blockFluidClass.methods) {
            if (method.name.equals(UPDATE_TICK) && method.desc.equals(UPDATE_TICK_DESC)) {
                AbstractInsnNode targetNode = null;
                for (AbstractInsnNode instruction : method.instructions.toArray()) {
                    if (instruction.getOpcode() == ALOAD) {
                        if (((VarInsnNode) instruction).var == 0 && instruction.getNext().getOpcode() == GETFIELD && instruction.getNext().getNext().getOpcode() == ICONST_2) {
                            targetNode = instruction;
                            break;
                        }
                    }
                }

                if (targetNode != null) {

                    InsnList toInsert = new InsnList();
                    LabelNode label23 = new LabelNode();
                    LabelNode label24 = new LabelNode();

                    toInsert.add(new VarInsnNode(ALOAD, 1));
                    toInsert.add(new VarInsnNode(ILOAD, 2));
                    toInsert.add(new VarInsnNode(ILOAD, 3));
                    toInsert.add(new VarInsnNode(ILOAD, 4));
                    toInsert.add(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(TransformMethods.class), "shouldGenerateSource", isObfuscated ? "(Lahb;III)Z" : "(Lnet/minecraft/world/World;III)Z", false));
                    toInsert.add(new JumpInsnNode(IFNE, label23));
                    toInsert.add(label24);
                    toInsert.add(new VarInsnNode(ALOAD, 0));
                    toInsert.add(new InsnNode(ICONST_0));
                    toInsert.add(new FieldInsnNode(PUTFIELD, isObfuscated ? "akr" : "net/minecraft/block/BlockDynamicLiquid", isObfuscated ? "a" : "field_149815_a", "I"));
                    toInsert.add(label23);
                    toInsert.add(new FrameNode(F_SAME, 0, null, 0, null));

                    method.instructions.insertBefore(targetNode, toInsert);
                }
                else {
                    RWModContainer.log.error("Error transforming BlockDynamicLiquid!");
                }
            }
        }
    }
}