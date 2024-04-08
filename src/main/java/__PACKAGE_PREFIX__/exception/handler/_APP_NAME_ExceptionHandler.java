package __PACKAGE_PREFIX__.exception.handler;

import java.util.function.BiFunction;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.StackSize;

import __PACKAGE_PREFIX__.exception.BaseException;
import __PACKAGE_PREFIX__.exception.ErrorData;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class _APP_NAME_ExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final FluentLogger flogger = FluentLogger.forEnclosingClass();

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorData> handleBaseException(BaseException ex, WebRequest request) {
       return handleError.apply(ex, request);
    }
    
    
    private BiFunction<BaseException, WebRequest, ResponseEntity<ErrorData>> handleError = (ex, request) -> {
        ErrorData errorData = ex.getErrorData();
        var txnCorrelationId = ExceptionHandlerUtil.getTxnCorrelationId(request);
		flogger.atSevere().withStackTrace(StackSize.FULL).withCause(ex).log("CorelationId : %s message : %s",
				txnCorrelationId, ex.getMessage());
        return new ResponseEntity<>(errorData, errorData.getStatusCode());
    };

}
