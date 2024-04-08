package __PACKAGE_PREFIX__.security;

import __PACKAGE_PREFIX__.config.Config;
import __PACKAGE_PREFIX__.util._APP_NAME_Constants;
import org.springframework.stereotype.Component;


@Component
public class JWTFilterFactory {

    public JWTValidator getAuthValidator(Config config){
        switch (config.getJwtFilterType()) {
            case _APP_NAME_Constants.JWT_LIB_AUTH_FILTER_TYPE :
            default:
                return new JWTLibraryValidator();

        }
    }

}
