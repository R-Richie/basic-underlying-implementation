package templateengine;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkerUtils {
    private String ftl_n;

    private Configuration configuration;

    public FreeMarkerUtils(String path,String ftl_name) throws IOException {
        this.ftl_n = ftl_name;
        configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(getClass(), path));
        configuration.setDirectoryForTemplateLoading(new File(path));

    }

    public String setValue(Map<String, Object> context) {
        JsonObject returnData = null;
        try {
            Template template = configuration.getTemplate(ftl_n + ".ftl");
            StringWriter writer = new StringWriter();
            template.process(context, writer);
            String result = writer.toString();
            returnData = new JsonParser().parse(result).getAsJsonObject();//先将模板文件转为json对象，再转为json字符串
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return returnData.toString();
    }
}
