package me.itache.helpers.request.handler;

import me.itache.constant.Common;
import me.itache.validation.rule.*;
import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.RequestParameter;

import javax.servlet.http.HttpServletRequest;

public class SingUpRequestHandler extends RequestHandler {

    public SingUpRequestHandler(HttpServletRequest request) {
        super(request);
        addParameter(Parameter.NEW_LOGIN,
                new RequestParameter(request.getParameter(Parameter.NEW_LOGIN),
                        new NotEmptyRule(),
                        new LoginUniqueRule(),
                        new LatinNumericRule(),
                        new MinLengthRule(Common.LOGIN_MIN_LENGTH),
                        new MaxLengthRule(Common.LOGIN_MAX_LENGTH)));
        addParameter(Parameter.NEW_PASSWORD,
                new RequestParameter(request.getParameter(Parameter.NEW_PASSWORD),
                        new NotEmptyRule(),
                        new LatinNumericRule(),
                        new MinLengthRule(Common.PASSWORD_MIN_LENGTH),
                        new MaxLengthRule(Common.PASSWORD_MAX_LENGTH)));
        addParameter(Parameter.EMAIL,
                new RequestParameter(request.getParameter(Parameter.EMAIL),
                        new EmailUniqueRule(),
                        new EmailRule()));
        addParameter(Parameter.PASSWORD_CONFIRMATION,
                new RequestParameter(request.getParameter(Parameter.PASSWORD_CONFIRMATION),
                        new EqualsRule(request.getParameter(Parameter.NEW_PASSWORD))));
        addParameter(Parameter.CAPTCHA,
                new RequestParameter(request.getParameter(Parameter.CAPTCHA),
                        new CaptchaRule(request)));
    }
}
