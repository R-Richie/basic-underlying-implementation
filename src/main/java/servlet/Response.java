package servlet;

import java.io.*;

public class Response {
    private static final int BUFER_SIZE = 1024;
    Request request;
    OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    public void sendStaticResource() throws IOException{
        byte[] bytes = new byte[BUFER_SIZE];
        FileInputStream fis = null;
        try {
            if(request.getUri() == null){
                return;
            }
            File file = new File(Constants.WEB_ROOT, request.getUri());
            if(file.exists()){

                fis = new FileInputStream(file);
                int ch = fis.read(bytes,0 ,BUFER_SIZE);
                while(ch != -1){
                    outputStream.write(bytes, 0 ,ch);
                    ch = fis.read(bytes,0 ,BUFER_SIZE);
                }

            }else{
                String errorMessage = "HTTP/1.1 404 FILE NOT FOUND\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 28 "+
                        "<h1>File not find</h1>";
                outputStream.write(errorMessage.getBytes());
            }
        } catch (Exception e) {
                e.printStackTrace();
        } finally {

            if(fis!= null){
                fis.close();
            }
        }

    }
}
