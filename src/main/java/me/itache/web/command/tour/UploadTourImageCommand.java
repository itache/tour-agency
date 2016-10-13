package me.itache.web.command.tour;

import me.itache.constant.Parameter;
import me.itache.exception.CommandException;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.service.TourService;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.web.command.AbstractCommand;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import me.itache.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
public class UploadTourImageCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(UploadTourImageCommand.class);

    public static final String REQUEST_IS_NOT_MULTIPART = "Request is not multipart, please 'multipart/form-data' enctype for your form.";

    public UploadTourImageCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        RequestParameter tourID = new RequestParameter(request.getParameter(Parameter.TOUR_ID), new MoreThanInclusivelyRule(1));
        TourService service = new TourService();
        if (!tourID.isValid() || service.getByPk(Long.parseLong(tourID.getValue())) == null) {
            LOG.error("Tour ID not valid or tour not exits. ID:" + tourID.getValue());
            response.sendError(404);
        }
        if (!ServletFileUpload.isMultipartContent(request)) {
            LOG.error(REQUEST_IS_NOT_MULTIPART);
            throw new CommandException(REQUEST_IS_NOT_MULTIPART);
        }
        service.uploadTourImage(Long.parseLong(tourID.getValue()), request);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
