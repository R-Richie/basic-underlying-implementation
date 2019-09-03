# 4 Servlet容器实现

## 4.1 HttpServer1 实现
之前的HttpServer只是访问静态资源，现在要实现Servlet的接口，继续处理动态资源。

使用的类，[HttpServer1](/src/main/java/servlet/HttpServer1.java)，[Request1](/src/main/java/servlet/Request1.java)，
[Response1](/src/main/java/servlet/Response1.java),[ServletProcessor1](/src/main/java/servlet/ServletProcessor1.java),
[StaticResourceProcessor](/src/main/java/servlet/StaticResourceProcessor.java),[PrimitiveServlet](/src/main/java/servlet/PrimitiveServlet.java)


以下代码似乎处理动态与静态资源的地方，通过ServletProcessor1处理动态资源，StaticResourceProcessor处理静态资源
```
if(request.getUri().startsWith("/servlet/")){
   ServletProcessor1 processor = new ServletProcessor1();
   processor.process(request,response);
}else{
   StaticResourceProcessor processor = new StaticResourceProcessor();
   processor.process(request, response);
}
```

要动态加载servlet，首先通过加载servlet当前目录的URLCLassLoader，通过loadClass加载servlet类
```
 try {
            URL[] urls = new URL[1];
            URLStreamHandler urlStreamHandler = null;
            File classPath = new File(Constants.CLASS_ROOT);
            String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
            urls[0] = new URL(null,repository, urlStreamHandler);
            loader = new URLClassLoader(urls);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class myClass = null;
        try {
            myClass = loader.loadClass(Constants.PACKAGE + "." + servletName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
```
然后根据地址里的servlet名字，实例化servlet，然后调用service方法，实现结果返回。

```
  Servlet servlet = null;
        try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
```


### 运行方法
1. 运行HttpServer1, run main方法
2. 浏览器访问 http://localhost:8080/servlet/PrimitiveServlet,会运行PrimitveServlet的service方法
3. 浏览器访http://localhost:8080/index.html,会加载resource下的index.html

## 4.2 HttpServer2 实现

使用的类，[HttpServer2](/src/main/java/servlet/HttpServer2.java)，[Request2](/src/main/java/servlet/Request2.java)，
[Response2](/src/main/java/servlet/Response2.java),[ServletProcessor2](/src/main/java/servlet/ServletProcessor2.java),
[StaticResourceProcessor](/src/main/java/servlet/StaticResourceProcessor.java),[PrimitiveServlet](/src/main/java/servlet/PrimitiveServlet.java)
[RequestFacade](/src/main/java/servlet/RequestFacade.java),[ResponseFacade](/src/main/java/servlet/ResponseFacade.java)
HttpServer2实现与HttpServer相似，主要的不同在ServletProcessor2的

```
 try {
            RequestFacade requestFacade = new RequestFacade(request);
            ResponseFacade responseFacade = new ResponseFacade(response);
            servlet = (Servlet) myClass.newInstance();
            servlet.service(requestFacade,responseFacade);
        } catch (Exception e) {
            e.printStackTrace();
        }
```

```
 try {
            servlet = (Servlet) myClass.newInstance();
            servlet.service(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
```
之前需要将Request与Response向上转型到ServletRequest，和ServletResponse，现在是用门面方法RequestFacade和ResoonseFacade
原来方法调用将servletRequest转成Request调用，这样就暴露了servlet之外的方法，不太安全，所以使用门面方法包装一下，只暴露Servlet的方法，给别人调用更安全。

### 运行方法
1. 运行HttpServer1, run main方法
2. 浏览器访问 http://localhost:8080/servlet/PrimitiveServlet,会运行PrimitveServlet的service方法
3. 浏览器访http://localhost:8080/index.html,会加载resource下的index.html
