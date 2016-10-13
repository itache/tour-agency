package me.itache.validation.rule;

import com.captcha.botdetect.web.servlet.Captcha;

import javax.servlet.http.HttpServletRequest;


public class CaptchaRule implements Rule {
    private HttpServletRequest request;

    public CaptchaRule(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean validate(String data) {
        Captcha captcha = Captcha.load(request, "exampleCaptcha");
        return captcha.validate(data);
    }
}
