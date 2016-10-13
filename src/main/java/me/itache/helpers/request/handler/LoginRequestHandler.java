package me.itache.helpers.request.handler;

import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.validation.rule.NotEmptyRule;

import javax.servlet.http.HttpServletRequest;

public class LoginRequestHandler extends RequestHandler {

    public LoginRequestHandler(HttpServletRequest request) {
        super(request);
        addParameter(Parameter.LOGIN,
                new RequestParameter(request.getParameter(Parameter.LOGIN), new NotEmptyRule()));
        addParameter(Parameter.PASSWORD,
                new RequestParameter(request.getParameter(Parameter.PASSWORD), new NotEmptyRule()));
    }


}
