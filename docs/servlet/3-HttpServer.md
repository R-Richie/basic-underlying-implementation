# 3 http 服务器
实现一个简单的http服务器，可以访问静态资源
使用类，[HttpServer](/src/main/java/servlet/HttpServer.java)，[Request](/src/main/java/servlet/Request.java)，[Response](/src/main/java/servlet/Response.java)

通过ServerSocket的accept方法监听某个端口，等待请求。
当某个请求过来的时候，accept方法会返回socket对象，
`input = socket.getInputStream();`
 `output = socket.getOutputStream();`
 获取输入输出，分别读入输入的url，在某个路径下面查找文件，读出对应文件，输出到输出流
 
 这样就可以做一个简单的http服务器
 
 ## 例子运行方法
 运行 HttpServer，浏览器访问 localhost:8080/index.html,这时候访问的是resource下面的index.html
 代码可以访问某个路径下的资源。
 
               