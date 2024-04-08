package __PACKAGE_PREFIX__.security;

import __PACKAGE_PREFIX__.config.Config;
import __PACKAGE_PREFIX__.exception.BaseException;
import __PACKAGE_PREFIX__.exception.ErrorCode;
import __PACKAGE_PREFIX__.exception.ErrorData;
import com.auth0.jwt.JWT;
import com.google.common.flogger.FluentLogger;
import com.lbg.mve.exception.JwtAuthorisationException;
import com.lbg.mve.validation.JwtValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Component
public class JWTLibraryValidator implements JWTValidator {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_BEARER = "Bearer ";
    public static final String EMPTY_STRING = "";
    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    /**
     * Here you can do the custom validation if token is valid
     * For now user is getting set in return object, but it's customizable
     * @param request
     * @return
     */
    @Override
    public UsernamePasswordAuthenticationToken getJWTAuthentication(HttpServletRequest request, Config config) {
        try {
            if (validateToken(request, config)) {
                String user = getClaimFromJWTToken(request, "sub");
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            }
        } catch (JwtAuthorisationException ex) {
            logger.atInfo().log("Exception while validating token : " + ex.getMessage());
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR.getErrorData("Exception while validating token : " + ex.getMessage()));
        } catch (Exception ex) {
            logger.atInfo().log("Unknown Exception while validating token : " + ex.getMessage());
            throw new BaseException(ErrorCode.INTERNAL_SERVER_ERROR.getErrorData("Unknown Exception while validating token : " + ex.getMessage()));
        }
        return null;
    }

    private boolean validateToken(HttpServletRequest request, Config config){
        JwtValidation jwtValidator = new JwtValidation();
        jwtValidator.setJwksUrl(config.getJwksUrl());
        return jwtValidator.authorizeHeader(request);
    }

    private String getClaimFromJWTToken(HttpServletRequest request, String claim) {
        String token = getDecodedJWTToken(request);
        return JWT.decode(token).getClaim(claim).toString();
    }

    private String getDecodedJWTToken(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION_HEADER).replace(AUTHORIZATION_BEARER, EMPTY_STRING);
    }
}