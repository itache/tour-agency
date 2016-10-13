package me.itache.helpers.request.handler;

import me.itache.constant.Common;
import me.itache.validation.rule.*;
import me.itache.constant.Parameter;
import me.itache.helpers.LocaleManager;
import me.itache.helpers.request.parameter.DefaultRequestParameter;
import me.itache.helpers.request.parameter.RequestParameter;
import me.itache.model.entity.Tour;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Locale;

public class UpdateTourRequestHandler extends RequestHandler {

    public UpdateTourRequestHandler(HttpServletRequest request) {
        super(request);
        for (Locale locale : LocaleManager.instance().getAvailableLocales().values()) {
            addParameter(Parameter.TOUR_NAME + "_" + locale.toLanguageTag(),
                    new RequestParameter(request.getParameter(Parameter.TOUR_NAME + "_" + locale.toLanguageTag()),
                            new NotEmptyRule(),
                            new MinLengthRule(Common.TOUR_NAME_MIN_LENGTH),
                            new MaxLengthRule(Common.TOUR_NAME_MAX_LENGTH)));
            addParameter(Parameter.TOUR_DESCRIPTION + "_" + locale.toLanguageTag(),
                    new RequestParameter(request.getParameter(Parameter.TOUR_DESCRIPTION + "_" + locale.toLanguageTag()),
                            new NotEmptyRule(),
                            new MinLengthRule(Common.TOUR_DESCRIPTION_MIN_LENGTH)));
        }
        addParameter(Parameter.TOUR_PRICE,
                new RequestParameter(request.getParameter(Parameter.TOUR_PRICE),
                        new MoreThanInclusivelyRule(1)));
        addParameter(Parameter.TOUR_HOT,
                new DefaultRequestParameter(request.getParameter(Parameter.TOUR_HOT), "false"));
        addParameter(Parameter.TOUR_TYPE,
                new RequestParameter(request.getParameter(Parameter.TOUR_TYPE),
                        new EnumValueRule<>(Tour.Type.class)));
        addParameter(Parameter.TOUR_HOTEL_LEVEL,
                new RequestParameter(request.getParameter(Parameter.TOUR_HOTEL_LEVEL),
                        new EnumValueRule<>(Tour.HotelLevel.class)));
        addParameter(Parameter.TOUR_PERSONS,
                new RequestParameter(request.getParameter(Parameter.TOUR_PERSONS),
                        new MoreThanInclusivelyRule(1)));
        addParameter(Parameter.TOUR_START,
                new RequestParameter(request.getParameter(Parameter.TOUR_START),
                        new DateRule(Common.DATE_PATTERN),
                        new DateAfterRule(LocalDate.now(), Common.DATE_PATTERN)));
        addParameter(Parameter.TOUR_DURATION,
                new RequestParameter(request.getParameter(Parameter.TOUR_DURATION),
                        new MoreThanInclusivelyRule(1)));
        addParameter(Parameter.TOUR_COUNTRY_CODE,
                new RequestParameter(request.getParameter(Parameter.TOUR_COUNTRY_CODE),
                        new NotEmptyRule(),
                        new MinLengthRule(Common.COUNTRY_CODE_MIN_LENGTH),
                        new MaxLengthRule(Common.COUNTRY_CODE_MAX_LENGTH)));
        addParameter(Parameter.TOUR_DISCOUNT_STEP,
                new RequestParameter(request.getParameter(Parameter.TOUR_DISCOUNT_STEP),
                        new MoreThanInclusivelyRule(0),
                        new LessThanOrEqualsRule(request.getParameter(Parameter.TOUR_MAX_DISCOUNT))));
        addParameter(Parameter.TOUR_MAX_DISCOUNT,
                new RequestParameter(request.getParameter(Parameter.TOUR_MAX_DISCOUNT),
                        new MoreThanInclusivelyRule(0)));
    }
}
