package me.itache.web.command;

import org.apache.log4j.Logger;
import me.itache.helpers.LocaleManager;
import me.itache.model.entity.User;
import me.itache.constant.Parameter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 *
 */
public class SetLanguageCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(SetLanguageCommand.class);

    public SetLanguageCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(this.getClass().getSimpleName() + " starts");
        Locale locale;
        try {
            locale = Locale.forLanguageTag(request.getParameter(Parameter.LANG));
            LOG.trace("Locale from request:" + locale);
            if (!LocaleManager.instance().isSupported(locale)) {
                LOG.trace("Locale not supported. Use default:" + locale);
                locale = LocaleManager.instance().getDefault();
            }
        } catch (RuntimeException ex) {
            locale = LocaleManager.instance().getDefault();
            LOG.trace("Locale not exists. Use default:" + locale);
        }
        request.getSession().setAttribute("locale", locale);
        LOG.trace("Locale set to session:" + locale);
        response.sendRedirect(request.getHeader("referer"));
        LOG.info(this.getClass().getSimpleName() + " ends");
    }
}
