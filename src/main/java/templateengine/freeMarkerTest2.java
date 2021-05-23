package templateengine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class freeMarkerTest2 {
    public static void main(String[] args) throws IOException {
        FreeMarkerUtils utl = new FreeMarkerUtils("/Users/ljr/Documents/work/privateSpace/basic-underlying-implementation/src/main/resources/templates/","eventflow");

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("size", 0);
        context.put("gte_val", "111111497283200000");
        context.put("lte_val", "1497928996980");
        context.put("min_val", "1497283200000");
        context.put("max_val", "1497928996980");
        context.put("interval", "21526566ms");

        String json = utl.setValue(context);

        System.out.println(json);

    }
}
