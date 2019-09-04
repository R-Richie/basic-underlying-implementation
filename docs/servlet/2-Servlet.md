# 2 Servlet协议实现
这部分只是实现servlet，不是实现容器，将实现的servlet放入容器中就可以运行servlet了
## 2.1 xml实现协议
实现类 [XmlServlet](/src/main/java/servlet/XmlServlet.java)，[web.xml](/src/main/resources/web.xml)，

继承HttpServlet，覆盖doGet,doPost方法
```
 @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        System.out.println("XmlServlet 在处理 get() 请求....");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charset=utf-8");
        out.println("<strong>My Servlet!</strong><br>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("MyServlet在处理 post（）请求...");
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html;charaset=utf-8");
        out.println("<strong>My Servlet</strong><br>");
    }
```
web.xml 写2条，相同的servlet-name，一个是servlet-class代码运行的类，一个是servlet-mapping，代表访问的地址

```
    <servlet>
        <servlet-name>xmlServlet</servlet-name>
        <servlet-class>servlet.XmlServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>xmlServlet</servlet-name>
        <url-pattern>/xmlservlet</url-pattern>
    </servlet-mapping>
 ```

### 运行方法
 XmlServlet放在tomcat的目录下，webapps/{you apps name}/WEB-INF/classes/{package path} 路径下，
 web.xml 放在与WEB-INF同级目录内


## 2.2 注解实现协议
实现类 [AnnotationServlet](/src/main/java/servlet/AnnotationServlet.java)，
 
 实现2.1相同，要在类上添加 @WebServlet("/myAnnotationServlet")注解
 ### 运行方法 
 AnnotationServlet与XmlServlet目录位置相同，只是不需要web.xml