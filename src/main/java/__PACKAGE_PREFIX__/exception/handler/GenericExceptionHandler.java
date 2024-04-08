package __PACKAGE_PREFIX__.exception.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.StackSize;

import __PACKAGE_PREFIX__.exception.ErrorCode;
import __PACKAGE_PREFIX__.exception.ErrorData;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GenericExceptionHandler {

	private static final FluentLogger flogger = FluentLogger.forEnclosingClass();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorData> handleGenericException(Exception ex, WebRequest request) {
        var errorData = ErrorCode.INTERNAL_SERVER_ERROR.getErrorData();
        var txnCorrelationId = ExceptionHandlerUtil.getTxnCorrelationId(request);
		flogger.atSevere().withStackTrace(StackSize.FULL).withCause(ex).log("CorelationId : %s message : %s",
				txnCorrelationId, ex.getMessage());
        return new ResponseEntity<>(errorData, errorData.getStatusCode());
    }
    

}
