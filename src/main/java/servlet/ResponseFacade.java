package servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class ResponseFacade implements ServletResponse {
    private ServletResponse servletResponse = null;

    public ResponseFacade(ServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    @Override
    public String getCharacterEncoding() {
        return servletResponse.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return servletResponse.getContentType();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return servletResponse.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return servletResponse.getWriter();
    }

    @Override
    public void setCharacterEncoding(String charset) {
        servletResponse.setCharacterEncoding(charset);
    }

    @Override
    public void setContentLength(int len) {
        servletResponse.setContentLength(len);
    }

    @Override
    public void setContentLengthLong(long len) {
        servletResponse.setContentLengthLong(len);
    }

    @Override
    public void setContentType(String type) {
        servletResponse.setContentType(type);
    }

    @Override
    public void setBufferSize(int size) {

    }

    @Override
    public int getBufferSize() {
        return servletResponse.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {
        servletResponse.flushBuffer();
    }

    @Override
    public void resetBuffer() {
        servletResponse.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return servletResponse.isCommitted();
    }

    @Override
    public void reset() {
        servletResponse.reset();
    }

    @Override
    public void setLocale(Locale loc) {
        servletResponse.setLocale(loc);
    }

    @Override
    public Locale getLocale() {
        return servletResponse.getLocale();
    }
}
