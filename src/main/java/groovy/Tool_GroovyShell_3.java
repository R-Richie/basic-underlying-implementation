package groovy;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;

import java.util.HashMap;
import java.util.Map;
import groovy.util.Eval;

public class Tool_GroovyShell_3 {
    public static void main(String[] args) throws Exception {
        // GroovyScriptEngine的根路径，如果参数是字符串数组，说明有多个根路径
        GroovyScriptEngine engine = new GroovyScriptEngine("src/main/java/groovy/");
        //GroovyScriptEngine engine = new GroovyScriptEngine(new String[] {"src/main/java/com/juxinli/groovy/shell/"});

        Binding binding = new Binding();
        binding.setVariable("name", "juxinli");
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> map2 = new HashMap<>();
        map.put("12", map2);
        map2.put("567","6778");
        binding.setProperty("title",map);
        TestBean testBean = new TestBean();
        testBean.setId("123");
        testBean.setName("356");
        map.put("677", testBean);

//        Object result1 = engine.run("GroovyShell_3_1.groovy", binding);
//        System.out.println(result1);
//        Object result2 = engine.run("GroovyShell_3_2.groovy", binding);
//        System.out.println(result2);
//        Object result3 = engine.run("GroovyShell_3_3.groovy", binding);
//        System.out.println(result3);

        Object x = Eval.x(map, "x.'12'.'567'");
        System.out.println(x);
        Object x1 = Eval.x(testBean, "x.id");
        System.out.println(x1);
    }


}
