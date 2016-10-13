package me.itache.web;

import me.itache.model.entity.User;
import me.itache.web.command.Command;
import me.itache.web.command.CommandRegistry;
import me.itache.web.command.GetImageCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class FileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
            doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Command command = CommandRegistry.getCommand(req.getRequestURI());
        if(command == null) {
            command = new GetImageCommand(User.Role.all());
        }
        command.execute(req, resp);
    }
}
