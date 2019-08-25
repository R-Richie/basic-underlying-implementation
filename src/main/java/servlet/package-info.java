/**
 * servlet简单实现，理解根本原理
 * 1)XmlServlet ,web.xml 是基于xml 的servlet的是实现
 * XmlServlet放在tomcat的目录下，webapps/{you apps name}/WEB-INF/classes/{package path} 路径下，web.xml 放在与WEB-INF同级目录内
 * 2）AnnotationServlet是基于注解的servlet的实现
 * AnnotationServlet与XmlServlet目录位置相同，只是不需要web.xml
 */
package servlet;
