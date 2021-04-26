package org.bmn.servlet;

import org.bmn.entity.Item;
import org.bmn.jdbc.service.JdbcItemService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/greeting")
public class Servlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getHeader("user-agent");
        resp.setContentType("text/html");
        List<Item> itemsList = new JdbcItemService().findAll();
        try (PrintWriter writer = resp.getWriter()) {
            for (Item item : itemsList) {
                writer.write("<h1>" + item + "</h1>");
            }
        }
    }
}
