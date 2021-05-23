package jsr269;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.util.ListBuffer;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Set;

public class ProcessUtil {
    static final String THIS = "this";
    private static final String SET = "set";
    private static final String GET = "get";

    static final String BUILDER_STATIC_METHOD_NAME = "builder";

    static final String BUILD_METHOD_NAME = "build";

    static final String CONSTRUCTOR_NAME = "<init>";

    static JCTree.JCVariableDecl cloneJCVariableAsParam(TreeMaker treeMaker, JCTree.JCVariableDecl prototypeJCVariable){
        return  treeMaker.VarDef(treeMaker.Modifiers(Flags.PARAMETER),
                prototypeJCVariable.name, prototypeJCVariable.vartype,null);
    }
    static List<JCTree.JCVariableDecl> cloneJCVariablesAsParams(TreeMaker treeMaker, List<JCTree.JCVariableDecl> prototypeJCVariables) {
        ListBuffer<JCTree.JCVariableDecl> jcVariables = new ListBuffer<>();
        for (JCTree.JCVariableDecl jcVariable : prototypeJCVariables) {
            jcVariables.append(cloneJCVariableAsParam(treeMaker, jcVariable));
        }
        return jcVariables.toList();
    }
    private static boolean isValidField(JCTree jcTree) {
        if (jcTree.getKind().equals(JCTree.Kind.VARIABLE)) {
            JCTree.JCVariableDecl jcVariable = (JCTree.JCVariableDecl) jcTree;

            Set<Modifier> flagSets = jcVariable.mods.getFlags();
            return (!flagSets.contains(Modifier.STATIC)
                    && !flagSets.contains(Modifier.FINAL));
        }

        return false;
    }
    static List<JCTree.JCVariableDecl> getJCVariables(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCVariableDecl> jcVariables = new ListBuffer<>();

        //遍历jcClass的所有内部节点，可能是字段，方法等等
        for (JCTree jcTree : jcClass.defs) {
            //找出所有set方法节点，并添加
            if (isValidField(jcTree)) {
                //注意这个com.sun.tools.javac.util.List的用法，不支持链式操作，更改后必须赋值
                jcVariables.append((JCTree.JCVariableDecl) jcTree);
            }
        }

        return jcVariables.toList();
    }
    private static boolean isSetJCMethod(JCTree jcTree) {
        if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
            JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
            return jcMethod.name.toString().startsWith(SET)
                    && jcMethod.params.size() == 1
                    && !jcMethod.mods.getFlags().contains(Modifier.STATIC);
        }
        return false;
    }
    static List<JCTree.JCMethodDecl> getSetJCMethods(JCTree.JCClassDecl jcClass) {
        ListBuffer<JCTree.JCMethodDecl> setJCMethods = new ListBuffer<>();

        //遍历jcClass的所有内部节点，可能是字段，方法等等
        for (JCTree jcTree : jcClass.defs) {
            //找出所有set方法节点，并添加
            if (isSetJCMethod(jcTree)) {
                //注意这个com.sun.tools.javac.util.List的用法，不支持链式操作，更改后必须赋值
                setJCMethods.append((JCTree.JCMethodDecl) jcTree);
            }
        }

        return setJCMethods.toList();
    }
    static boolean hasNoArgsConstructor(JCTree.JCClassDecl jcClass) {
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (CONSTRUCTOR_NAME.equals(jcMethod.name.toString())) {
                    if (jcMethod.params.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    static boolean hasAllArgsConstructor(List<JCTree.JCVariableDecl> jcVariables, JCTree.JCClassDecl jcClass) {
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (CONSTRUCTOR_NAME.equals(jcMethod.name.toString())) {
                    if (jcVariables.size() == jcMethod.params.size()) {
                        boolean isEqual = true;
                        for (int i = 0; i < jcVariables.size(); i++) {
                            if (!jcVariables.get(i).vartype.type.equals(jcMethod.params.get(i).vartype.type)) {
                                isEqual = false;
                                break;
                            }
                        }
                        if (isEqual) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    static boolean hasSetMethod(JCTree.JCVariableDecl jcVariable, JCTree.JCClassDecl jcClass) {
        String setMethodName = fromPropertyNameToSetMethodName(jcVariable.name.toString());
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (setMethodName.equals(jcMethod.name.toString())
                        && jcMethod.params.size() == 1
                        && jcMethod.params.get(0).vartype.type.equals(jcVariable.vartype.type)) {
                    return true;
                }
            }
        }
        return false;
    }
    static boolean hasGetMethod(JCTree.JCVariableDecl jcVariable, JCTree.JCClassDecl jcClass) {
        String getMethodName = fromPropertyNameToGetMethodName(jcVariable.name.toString());
        for (JCTree jcTree : jcClass.defs) {
            if (jcTree.getKind().equals(JCTree.Kind.METHOD)) {
                JCTree.JCMethodDecl jcMethod = (JCTree.JCMethodDecl) jcTree;
                if (getMethodName.equals(jcMethod.name.toString())
                        && jcMethod.params.size() == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    static String fromPropertyNameToSetMethodName(String propertyName) {
        return SET + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }
    static String fromPropertyNameToGetMethodName(String propertyName) {
        return GET + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }
}
