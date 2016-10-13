package me.itache.helpers.request.handler;

import me.itache.validation.rule.OneOfRule;
import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.DefaultRequestParameter;
import me.itache.model.meta.OrderMeta;
import me.itache.model.meta.UserMeta;

import javax.servlet.http.HttpServletRequest;

public class OrderListRequestHandler extends FilterRequestHandler {

    public OrderListRequestHandler(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected DefaultRequestParameter getSortParameter() {
        return new DefaultRequestParameter(request.getParameter(Parameter.SORT), OrderMeta.DATE.getColumnName(),
                new OneOfRule(OrderMeta.STATUS, OrderMeta.DATE, UserMeta.LOGIN));
    }
}
