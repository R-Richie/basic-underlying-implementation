package jsr269;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.code.TypeTag;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.List;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static jsr269.ProcessUtil.CONSTRUCTOR_NAME;
import static jsr269.ProcessUtil.hasNoArgsConstructor;

@SupportedAnnotationTypes("jsr269.NoArgsConstructor")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class NoArgsConstructorProcessor extends BaseProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(NoArgsConstructor.class);
        set.forEach(element -> {
            JCTree jcTree = trees.getTree(element);
            jcTree.accept(new TreeTranslator(){
                @Override
                public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                    messager.printMessage(Diagnostic.Kind.NOTE, "@NoArgsConstructor process [" + jcClassDecl.name.toString() + "] begin!");
                    //添加无参构造方法
                    if (!hasNoArgsConstructor(jcClassDecl)) {
                        jcClassDecl.defs = jcClassDecl.defs.append(
                                createNoArgsConstructor()
                        );
                    }

                    messager.printMessage(Diagnostic.Kind.NOTE, "@NoArgsConstructor process [" + jcClassDecl.name.toString() + "] end!");
                }
            });

        });
        return false;
    }
    private JCTree.JCMethodDecl createNoArgsConstructor(){
        JCTree.JCBlock jcBlock = treeMaker.Block(0, List.nil());
        return treeMaker.MethodDef(
                treeMaker.Modifiers(Flags.PUBLIC),
                names.fromString(CONSTRUCTOR_NAME),
                treeMaker.TypeIdent(TypeTag.VOID), //返回类型
                List.nil(), //泛型形参列表
                List.nil(), //参数列表
                List.nil(), //异常列表
                jcBlock, //方法体
                null //默认方法（可能是interface中的那个default）
        );
    }
}
