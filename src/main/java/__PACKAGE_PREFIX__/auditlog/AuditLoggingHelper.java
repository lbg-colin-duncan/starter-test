package __PACKAGE_PREFIX__.auditlog;

import __PACKAGE_PREFIX__.constants.CommonConstants;
import __PACKAGE_PREFIX__.exception.AuditLoggingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.cloud.Timestamp;
import com.google.common.flogger.FluentLogger;
import com.google.protobuf.InvalidProtocolBufferException;
import com.lbg.audit.AuditPublisher;
import __PACKAGE_PREFIX__.exception.ErrorCode;
import com.lbg.filter.LoggingFilter;
import __PACKAGE_PREFIX__.exception.AuditLoggingException;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import __PACKAGE_PREFIX__.auditlog.AuditCodesEnum;

import java.util.logging.Level;

import static __PACKAGE_PREFIX__.constants.CommonConstants.LOGGED_IN_USER_ID;
import static __PACKAGE_PREFIX__.constants.CommonConstants.LOGGED_IN_USER_TYPE;

@Component
public class AuditLoggingHelper {

    @Autowired
    AuditPublisher auditPublisher;

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public void publishMessage(String customerId, AuditCodesEnum auditEnum, String channel) {

        AuditEvent event =
                AuditEvent
                        .builder()
                        //sample Audit event builder
                        .eventDateTime(Timestamp.now())
                        .authCode(getAuthCode())
                        .loggedinId(LOGGED_IN_USER_ID)
                        .loggedInUserType(LOGGED_IN_USER_TYPE)
                        .eventDateTime(Timestamp.now())
                        .auditCode(auditEnum.getCode())
                        .auditText(auditEnum.getLogText())
                        .channel(channel)
                        .process("CustomerOnboardingAndResources")
                        .build();

        try {
            auditPublisher.publish(event);

        } catch (InvalidProtocolBufferException | JsonProcessingException e) {
            logger.at(Level.SEVERE).withCause(e).log("Some error occurred while processing the request.");
            throw new AuditLoggingException(
                    ErrorCode.INTERNAL_SERVER_ERROR.getErrorData(
                            "Some error occurred while processing the request."));
        }
    }

    private String getAuthCode() {
        String authCode = ThreadContext.get(LoggingFilter.AUTH_CODE_LOGGING);
        if (null == authCode) {
            return "Auth Code Absent";
        } else {
            return authCode;
        }
    }
}
