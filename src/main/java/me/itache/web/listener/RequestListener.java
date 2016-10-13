package me.itache.web.listener;

import me.itache.helpers.LocaleManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 *
 */
public class RequestListener implements ServletRequestListener {

    private static final Logger LOG = Logger.getLogger(RequestListener.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        LOG.debug("Request destroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        LOG.debug("Request initialization");
        HttpServletRequest request = (HttpServletRequest) servletRequestEvent.getServletRequest();
        LOG.debug("Request uri -> " + request.getRequestURI());
        HttpSession session = request.getSession();
        if(!isLocaleSet(session)){
            LOG.trace("Locale not set");
            Locale locale = request.getLocale();
            if(locale == null || !LocaleManager.instance().isSupported(locale)) {
                locale = LocaleManager.instance().getDefault();
                LOG.trace("Get default locale from LocaleManager:" + locale);
            }
            LOG.trace("Locale was set:" + locale);
            session.setAttribute("locale", locale);
            return;
        }
        Locale current = (Locale) session.getAttribute("locale");
        LocaleManager.instance().setCurrentLocale(current);
        LOG.debug("Locale was set:" + session.getAttribute("locale"));
    }

    private boolean isLocaleSet(HttpSession session) {
        return session.getAttribute("locale") != null;
    }


}
