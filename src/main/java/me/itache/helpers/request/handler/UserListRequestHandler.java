package me.itache.helpers.request.handler;

import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.DefaultRequestParameter;
import me.itache.model.meta.UserMeta;
import me.itache.validation.rule.OneOfRule;

import javax.servlet.http.HttpServletRequest;

public class UserListRequestHandler extends FilterRequestHandler {

    public UserListRequestHandler(HttpServletRequest request) {
        super(request);
    }

    @Override
    protected DefaultRequestParameter getSortParameter() {
        return new DefaultRequestParameter(request.getParameter(Parameter.SORT), UserMeta.LOGIN.getFieldName(),
                new OneOfRule(UserMeta.LOGIN, UserMeta.EMAIL));
    }
}
