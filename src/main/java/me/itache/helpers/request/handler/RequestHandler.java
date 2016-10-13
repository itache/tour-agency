package me.itache.helpers.request.handler;

import me.itache.helpers.request.parameter.Error;
import me.itache.helpers.request.parameter.RequestParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Checks given request parameters for validity.
 */
abstract public class RequestHandler {
    protected HttpServletRequest request;
    private boolean isValidated;
    private HashMap<String, RequestParameter> requestParametersMap = new HashMap<>();
    private HashMap<String, List<Error>> errorsMap = new HashMap<>();

    public RequestHandler(HttpServletRequest request) {
        this.request = request;
    }

    protected void addParameter(String name, RequestParameter parameter) {
        requestParametersMap.put(name, parameter);
    }

    /**
     * Validate provided parameters
     */
    public void validate() {
        for (Map.Entry<String, RequestParameter> entry : requestParametersMap.entrySet()) {
            entry.getValue().validate();
            if (!entry.getValue().isValid()) {
                errorsMap.put(entry.getKey(), entry.getValue().getErrors());
            }
        }
        isValidated = true;
    }

    /**
     * @param name
     * @return parameter by name
     */
    public RequestParameter getParameter(String name) {
        return requestParametersMap.get(name);
    }

    /**
     * @return parameter map. Key - name, value - request parameter
     */
    public HashMap<String, RequestParameter> getParametersMap() {
        return requestParametersMap;
    }

    /**
     * @return true if all parameters are valid.
     */
    public boolean areParametersValid() {
        if(!isValidated){
            validate();
        }
        return errorsMap.size() == 0;
    }

    /**
     * @return map of errors. Key - parameter name, value - lis of Error object
     */
    public Map<String, List<Error>> getErrors() {
        if(!isValidated){
            validate();
        }
        return new HashMap<>(errorsMap);
    }
}
