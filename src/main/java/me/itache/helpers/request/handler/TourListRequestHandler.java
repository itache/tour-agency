package me.itache.helpers.request.handler;

import me.itache.constant.Common;
import me.itache.constant.Parameter;
import me.itache.dao.modifier.EntityCondition;
import me.itache.helpers.request.parameter.DefaultRequestParameter;
import me.itache.helpers.request.parameter.FilterRequestParameter;
import me.itache.model.entity.Tour;
import me.itache.model.meta.TourDescriptionMeta;
import me.itache.model.meta.TourMeta;
import me.itache.validation.rule.*;

import javax.servlet.http.HttpServletRequest;

public class TourListRequestHandler extends FilterRequestHandler {

    public TourListRequestHandler(HttpServletRequest request) {
        super(request);
        addConditionalParameter(Parameter.SEARCH,
                new FilterRequestParameter(request.getParameter(Parameter.SEARCH),
                        EntityCondition.like(TourDescriptionMeta.TITLE, request.getParameter(Parameter.SEARCH)),
                        new NotEmptyRule()));
        addConditionalParameter(Parameter.TOUR_MIN_PRICE,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_MIN_PRICE),
                        EntityCondition.greaterThan(TourMeta.PRICE, request.getParameter(Parameter.TOUR_MIN_PRICE)),
                        new MoreThanInclusivelyRule(0)));
        addConditionalParameter(Parameter.TOUR_MAX_PRICE,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_MAX_PRICE),
                        EntityCondition.lessThan(TourMeta.PRICE, request.getParameter(Parameter.TOUR_MAX_PRICE)),
                        new MoreThanInclusivelyRule(0)));
        addConditionalParameter(Parameter.TOUR_PERSONS_FROM,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_PERSONS_FROM),
                        EntityCondition.greaterThan(TourMeta.PERSONS, request.getParameter(Parameter.TOUR_PERSONS_FROM)),
                        new MoreThanInclusivelyRule(0)));
        addConditionalParameter(Parameter.TOUR_PERSONS_TO,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_PERSONS_TO),
                        EntityCondition.lessThan(TourMeta.PERSONS, request.getParameter(Parameter.TOUR_PERSONS_TO)),
                        new MoreThanInclusivelyRule(0)));
        addConditionalParameter(Parameter.TOUR_HOTEL_LEVEL,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_HOTEL_LEVEL),
                        EntityCondition.eq(TourMeta.HOTEL_LEVEL, request.getParameter(Parameter.TOUR_HOTEL_LEVEL)),
                        new EnumValueRule<>(Tour.HotelLevel.class)));
        addConditionalParameter(Parameter.TOUR_TYPE,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_TYPE),
                        EntityCondition.eq(TourMeta.TYPE, request.getParameter(Parameter.TOUR_TYPE)),
                        new EnumValueRule<>(Tour.Type.class)));
        addConditionalParameter(Parameter.TOUR_START_FROM,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_START_FROM),
                        EntityCondition.greaterThan(TourMeta.START_DATE, request.getParameter(Parameter.TOUR_START_FROM)),
                        new DateRule(Common.DATE_PATTERN)));
        //TODO Parameter builder?
        addConditionalParameter(Parameter.TOUR_START_TO,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_START_TO),
                        EntityCondition.lessThan(TourMeta.START_DATE, request.getParameter(Parameter.TOUR_START_TO)),
                        new DateRule(Common.DATE_PATTERN)));
        addConditionalParameter(Parameter.TOUR_COUNTRY_CODE,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_COUNTRY_CODE),
                        EntityCondition.eq(TourMeta.COUNTRY_CODE, request.getParameter(Parameter.TOUR_COUNTRY_CODE)),
                        new NotEmptyRule()));
        addConditionalParameter(Parameter.TOUR_DURATION_FROM,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_DURATION_FROM),
                        EntityCondition.greaterThan(TourMeta.DURATION, request.getParameter(Parameter.TOUR_DURATION_FROM)),
                        new MoreThanInclusivelyRule(1)));
        addConditionalParameter(Parameter.TOUR_DURATION_TO,
                new FilterRequestParameter(request.getParameter(Parameter.TOUR_DURATION_TO),
                        EntityCondition.lessThan(TourMeta.DURATION, request.getParameter(Parameter.TOUR_DURATION_TO)),
                        new MoreThanInclusivelyRule(1)));
    }

    @Override
    protected DefaultRequestParameter getSortParameter() {
        return new DefaultRequestParameter(request.getParameter(Parameter.SORT), TourDescriptionMeta.TITLE.getColumnName(),
                new OneOfRule(TourDescriptionMeta.TITLE, TourMeta.PRICE, TourMeta.START_DATE, TourMeta.DURATION));
    }
}
