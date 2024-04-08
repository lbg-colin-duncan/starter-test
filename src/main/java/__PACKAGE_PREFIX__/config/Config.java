package __PACKAGE_PREFIX__.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${jwtsecret}")
    private String jwtSecret;
    
    @Value("${lbg.jwt.validate.jwks.url}")
    private String jwksUrl;

    @Value("${jwt.filter.type}")
    private String jwtFilterType;

    public String getJwtSecret() {
        return jwtSecret;
    }
    
    public String getJwksUrl() { 
        return jwksUrl; 
    }

    public String getJwtFilterType() {
        return jwtFilterType;
    }
}
