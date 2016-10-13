package me.itache.web.command;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;
import me.itache.utils.imagesource.CloudinaryImageSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 *
 */
public class GetImageCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(GetImageCommand.class);

    public GetImageCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        String filename = URLDecoder.decode(request.getPathInfo().substring(1), "UTF-8");
        byte[] bytes = IOUtils.toByteArray(new CloudinaryImageSource().get(filename));
        response.setHeader("Content-Type", request.getServletContext().getMimeType(filename));
        response.setHeader("Content-Length", String.valueOf(bytes.length));
        response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
        response.getOutputStream().write(bytes);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
