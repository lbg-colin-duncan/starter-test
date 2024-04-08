package __PACKAGE_PREFIX__.auditlog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;
import com.lbg.audit.BaseAuditEvent;
import __PACKAGE_PREFIX__.util.GoogleTimestampDeserializer;
import __PACKAGE_PREFIX__.util.GoogleTimestampSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AuditEvent  extends BaseAuditEvent {

    @JsonSerialize(using = GoogleTimestampSerializer.class)
    @JsonDeserialize(using = GoogleTimestampDeserializer.class)
    Timestamp eventDateTime;

    String authCode;
    String loggedinId;
    String loggedInUserType;

}
