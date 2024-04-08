package __PACKAGE_PREFIX__.util;

public class Patterns {

    private Patterns() {
    }

    public static final String ALPHA_NUMERICAL = "^[a-zA-Z0-9]*$";

    public static final String ALPHA_NUMERICAL_UNDERSCORE = "^[a-zA-Z0-9_]*$";

    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String ISO_DATE_TIME_FORMAT_WITH_MILLISECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static final String VALID_EMAIL_FORMAT = "^[a-zA-Z]{1}([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)[\\.]{1}([a-zA-Z]{2,5})$";

    public static final String INVALID_EMAIL_FORMAT = "([a-zA-Z0-9_\\-\\.@]*)[_\\.\\-@]{2,}([a-zA-Z0-9\\-\\.@]*)";

    public static final String NINO_IDENTIFIER_VALIDATION_ALLOWED = "^[A-CEGHJ-NOPR-TW-Z]{1}[A-CEGHJ-NPR-TW-Z]{1}[0-9]{6}[A-D]{1}";

    public static final String NINO_IDENTIFIER_VALIDATION_NOT_ALLOWED = "^(GB|BG|NK|KN|TN|NT|ZZ).+";

    public static final String NUMERICAL = "^[0-9]*$";

    public static final String ALPHA_NUMERICAL_SPACE = "^([a-zA-Z0-9]+\\s)*[a-zA-Z0-9]+$";

    public static final String ALPHA_NUM_ADDRESS_LINE = "^[a-zA-Z0-9\\.\\s',-/]*$";

    public static final String CONTINUOUS_SPACE = "([ ]{2,})";

    public static final String DIALLING_CODE = "^[+][0-9]{1,4}$";

    public static final String NAME_FIELDS = "^(?!')(?!-)(?!.*[\\'\\ \\-]{2})(?!.*'$)(?!.*-$)[A-Za-z-' ]*$";
}
