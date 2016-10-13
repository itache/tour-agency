package me.itache.web;

import me.itache.web.command.Command;
import me.itache.web.command.CommandRegistry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandRegistry.getCommand(request.getRequestURI());
        if (command == null) {
            response.sendError(404);
            return;
        }
        command.execute(request, response);
    }
}
