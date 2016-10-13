package me.itache.helpers.request.handler;

import me.itache.dao.modifier.EntityCondition;
import me.itache.dao.modifier.Pagination;
import me.itache.dao.modifier.Sorter;
import me.itache.helpers.request.parameter.FilterRequestParameter;
import me.itache.validation.rule.EnumValueRule;
import me.itache.validation.rule.MoreThanInclusivelyRule;
import me.itache.constant.Parameter;
import me.itache.helpers.request.parameter.DefaultRequestParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Checks given request parameters for validity.
 * Ignore parameters that not exist in concrete handler parameter list.
 * Ignore empty and non-valid parameters.
 */
public abstract class FilterRequestHandler {
    protected HttpServletRequest request;
    private DefaultRequestParameter pageParameter;
    private DefaultRequestParameter orderParameter;
    private HashMap<String, FilterRequestParameter> filterParametersMap = new HashMap<>();

    /**
     * Creates new handler extracting parameters from request
     *
     * @param request
     */
    public FilterRequestHandler(HttpServletRequest request) {
        this.request = request;
        pageParameter = new DefaultRequestParameter(request.getParameter(Parameter.PAGE), "1",
                new MoreThanInclusivelyRule(1));
        orderParameter = new DefaultRequestParameter(request.getParameter(Parameter.ORDER), Sorter.Order.ASC.name(),
                new EnumValueRule<>(Sorter.Order.class));
    }

    public FilterRequestParameter getParameter(String name) {
        return filterParametersMap.get(name);
    }

    /**
     * Creates sorter based on 'sort' and 'order' request parameters
     *
     * @return sorter for given request
     */
    public Sorter getSorter() {
        String order = getOrderParameter().getValidValue();
        String sort = getSortParameter().getValidValue();
        return new Sorter(sort, Sorter.Order.valueOf(order.toUpperCase()));
    }

    protected abstract DefaultRequestParameter getSortParameter();

    /**
     * Creates pagination based on 'page' request parameter
     *
     * @return pagination object
     */
    public Pagination getPagination() {
        Pagination pagination = new Pagination();
        DefaultRequestParameter page = getPageParameter();
        pagination.setCurrentPageNumber(Integer.parseInt(page.getValidValue()));
        return pagination;
    }

    protected DefaultRequestParameter getPageParameter() {
        return pageParameter;
    }

    /**
     * Checks given parameters for validity
     * and returns only valid conditions that belong to each parameter
     *
     * @return valid conditions
     */
    public EntityCondition[] getValidConditions() {
        List<EntityCondition> conditions = new ArrayList<>();
        for (FilterRequestParameter parameter : filterParametersMap.values()) {
            if (parameter.isValid()) {
                conditions.add(parameter.getCondition());
            }
        }
        return conditions.toArray(new EntityCondition[]{});
    }

    protected DefaultRequestParameter getOrderParameter() {
        return orderParameter;
    }

    protected void addConditionalParameter(String name, FilterRequestParameter parameter) {
        filterParametersMap.put(name, parameter);
    }
}
