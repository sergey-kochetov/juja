package com.juja.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class sampleServlet extends HttpServlet {
    //https://www.shortn0tes.com/2017/01/intellij-idea-community-edition-tomcat.html
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String line = "<html>\n" +
                "    <body>\n" +
                "        <h2>\n" +
                "            Hello World!\n" +
                "        </h2>\n" +
                "\n" +
                "\n" +
                "        <a href=\"servlet\">Click here to see servlet</a>\n" +
                "    </body>\n" +
                "</html>";
        resp.getWriter().write(line);
    }
}
