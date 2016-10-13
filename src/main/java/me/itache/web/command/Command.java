package me.itache.web.command;

import me.itache.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public interface Command {
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    boolean checkPermission(User user);
}
