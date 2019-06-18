package com.juja.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

//@WebFilter({"/trim", "/trim-params.html"})
public class TrimResponseFilter_var2 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse originalResponse = (HttpServletResponse) servletResponse;
        TrimWriter trimWriter = new TrimWriter();
        TrimResponse response = new TrimResponse(originalResponse, trimWriter);
        filterChain.doFilter(servletRequest, response);
        PrintWriter pw = originalResponse.getWriter();
        pw.write(trimWriter.getContent());
        originalResponse.setContentLength(trimWriter.getLength());
        pw.flush();
        pw.close();
    }

    @Override
    public void destroy() {

    }

    private static class TrimResponse extends HttpServletResponseWrapper {
        private TrimWriter trimWriter;

        public TrimResponse(HttpServletResponse response, TrimWriter trimWriter) {
            super(response);
            this.trimWriter = trimWriter;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(trimWriter);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {

                @Override
                public void write(int b) throws IOException {
                    trimWriter.write(b);
                }
            };
        }
    }

    private static class TrimWriter extends Writer {
        private final StringBuilder buf = new StringBuilder();

        @Override
        public void write(char[] cbuf, int off, int len) throws IOException {
            for (int i = off; i < len; i++) {
                processChar(cbuf[i]);
            }
        }

        @Override
        public void write(int c) throws IOException {
            processChar((char) c);
        }

        @Override
        public void write(String str, int off, int len) throws IOException {
            write(str.toCharArray(), off, len);
        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void close() throws IOException {

        }

        private void processChar(char ch) {
            if (ch != '\t' && ch != '\r' && ch != '\n') {
                buf.append(ch);
            }
        }

        private String getContent() {
            return buf.toString();
        }

        private int getLength() {
            return buf.length();
        }
    }
}
