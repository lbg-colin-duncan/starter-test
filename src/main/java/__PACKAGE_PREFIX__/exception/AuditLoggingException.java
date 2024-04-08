package __PACKAGE_PREFIX__.exception;

import __PACKAGE_PREFIX__.exception.BaseException;
import __PACKAGE_PREFIX__.exception.ErrorData;

public class AuditLoggingException extends BaseException {
    public AuditLoggingException(ErrorData errorData) {
        super(errorData);
    }
}

