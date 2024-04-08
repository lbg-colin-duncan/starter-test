package __PACKAGE_PREFIX__.exception;
import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ErrorCode {
    // 400
    MISSING_OR_EMPTY_HEADER(
            "_API_NAME_API_ERR_10003",
            "Missing or Empty request header ''{0}''",
            HttpStatus.BAD_REQUEST),
    // 404
    // 500 
    INTERNAL_SERVER_ERROR(
            "_API_NAME_API_ERR_10003",
            "Error Occoured in _API_NAME_API",
            HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;

    private final String description;

    private final HttpStatus httpStatus;

    private ErrorCode(String code, String description, HttpStatus httpStatus) {
        this.code = code;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public ErrorData getErrorData() {
        return new ErrorData(this.httpStatus, this.code, this.description);
    }

    public ErrorData getErrorData(Object... v) {
        return new ErrorData(this.httpStatus, this.code, this.getDescription(v));
    }

    public String getDescription(Object... v) {
        return MessageFormat.format(description, v);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public static ErrorCode fromCode(String text) {
        for (ErrorCode b : ErrorCode.values()) {
            if (b.code.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}