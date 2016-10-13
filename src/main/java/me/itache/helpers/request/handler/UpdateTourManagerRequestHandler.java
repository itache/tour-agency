package me.itache.helpers.request.handler;

import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.DefaultRequestParameter;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.validation.rule.LessThanOrEqualsRule;
import me.itache.validation.rule.MoreThanInclusivelyRule;

import javax.servlet.http.HttpServletRequest;

public class UpdateTourManagerRequestHandler extends RequestHandler {
    public UpdateTourManagerRequestHandler(HttpServletRequest request) {
        super(request);
        addParameter(Parameter.TOUR_DISCOUNT_STEP,
                new RequestParameter(request.getParameter(Parameter.TOUR_DISCOUNT_STEP),
                        new MoreThanInclusivelyRule(0),
                        new LessThanOrEqualsRule(request.getParameter(Parameter.TOUR_MAX_DISCOUNT))));
        addParameter(Parameter.TOUR_MAX_DISCOUNT,
                new RequestParameter(request.getParameter(Parameter.TOUR_MAX_DISCOUNT),
                        new MoreThanInclusivelyRule(0)));
        addParameter(Parameter.TOUR_HOT,
                new DefaultRequestParameter(request.getParameter(Parameter.TOUR_HOT), "false"));
    }
}
