package __PACKAGE_PREFIX__.auditlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuditCodesEnum {
    //Enums for application specific audit logs
    private String code;
    private String logText;
    private String activity;
}
