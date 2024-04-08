package __PACKAGE_PREFIX__.exception.handler;

import org.springframework.web.context.request.WebRequest;

import __PACKAGE_PREFIX__.util._APP_NAME_Constants;

public class ExceptionHandlerUtil {

    public static String getTxnCorrelationId(WebRequest request) {
        return request.getHeader(_APP_NAME_Constants.CORRELATION_ID);
    }
}
