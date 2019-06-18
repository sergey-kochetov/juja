package com.juja.servlet.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TrimResponseFilter_var3 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse originalResponse = (HttpServletResponse) servletResponse;
        TrimResponse response = new TrimResponse(originalResponse);
        filterChain.doFilter(servletRequest, response);
        PrintWriter pw = originalResponse.getWriter();
        String content = response.getTrimContent();
        pw.write(content);
        originalResponse.setContentLength(content.length());
        pw.flush();
        pw.close();
    }

    @Override
    public void destroy() {

    }

    private static class TrimResponse extends HttpServletResponseWrapper {
        private StringWriter sw = new StringWriter();

        public TrimResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return new PrintWriter(sw);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new ServletOutputStream() {

                @Override
                public void write(int b) throws IOException {
                    sw.write(b);
                }
            };
        }

        private String getTrimContent() {
            return trim(sw.toString());
        }
    }

    private static String trim(String text) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch != '\t' && ch != '\r' && ch != '\n') {
                res.append(ch);
            }
        }
        return res.toString();
    }
}
