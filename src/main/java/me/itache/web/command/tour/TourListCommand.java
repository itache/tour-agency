package me.itache.web.command.tour;

import me.itache.constant.Common;
import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.helpers.request.parameter.FilterRequestParameter;
import me.itache.model.bean.TourBean;
import org.apache.log4j.Logger;
import me.itache.constant.Parameter;
import me.itache.constant.View;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.handler.FilterRequestHandler;
import me.itache.helpers.request.handler.TourListRequestHandler;
import me.itache.model.entity.User;
import me.itache.model.meta.TourMeta;
import me.itache.service.TourService;
import me.itache.service.UserService;
import me.itache.web.command.AbstractCommand;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 */
public class TourListCommand extends AbstractCommand {
    private static final Logger LOG = Logger.getLogger(TourListCommand.class);

    public TourListCommand(User.Role... permissions) {
        super(permissions);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info(this.getClass().getSimpleName() + " starts");
        FilterRequestHandler requestHandler = new TourListRequestHandler(request);
        TourService tourService = new TourService(LocaleManager.instance().getCurrentLocale());
        Pagination pagination = requestHandler.getPagination();
        List<TourBean> tours = tourService.get(requestHandler.getSorter(), pagination, getConditions(request, requestHandler));
        request.setAttribute("tours", tours);
        request.setAttribute("pagination", pagination);
        request.getRequestDispatcher(View.TOUR_LIST_PAGE).forward(request, response);
        LOG.info(this.getClass().getSimpleName() + " ends");
    }

    private EntityCondition[] getConditions(HttpServletRequest request, FilterRequestHandler requestHandler) {
        List<EntityCondition> conditions = new ArrayList<>();
        UserService userService = new UserService(request.getSession());
        //Filter outdated tours
        if (userService.getCurrentUser().getRole() != User.Role.ADMIN) {
            formStartDateFromCondition(requestHandler, conditions);
        }
        conditions.addAll(Arrays.asList(requestHandler.getValidConditions()));
        return conditions.toArray(new EntityCondition[]{});
    }

    private void formStartDateFromCondition(FilterRequestHandler requestHandler, List<EntityCondition> conditions) {
        DateFormat df = new SimpleDateFormat(Common.DATE_PATTERN);
        FilterRequestParameter startDateFrom = requestHandler.getParameter(Parameter.TOUR_START_FROM);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 1);
        Date tomorrow = c.getTime();
        if (!startDateFrom.isValid()) {
            conditions.add(EntityCondition.greaterThan(TourMeta.START_DATE, df.format(tomorrow)));
        } else {
            try {
                Date startDate = df.parse(startDateFrom.getValue());
                if (tomorrow.after(startDate)) {
                    conditions.add(EntityCondition.greaterThan(TourMeta.START_DATE, df.format(tomorrow)));
                }
            } catch (ParseException e) {
                conditions.add(EntityCondition.greaterThan(TourMeta.START_DATE, df.format(tomorrow)));
            }
        }
    }
}
