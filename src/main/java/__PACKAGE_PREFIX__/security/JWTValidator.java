package __PACKAGE_PREFIX__.security;

import __PACKAGE_PREFIX__.config.Config;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

public interface JWTValidator {
    UsernamePasswordAuthenticationToken getJWTAuthentication(HttpServletRequest request, Config config);
}
