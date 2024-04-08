package __PACKAGE_PREFIX__.security;


import __PACKAGE_PREFIX__.config.Config;
import __PACKAGE_PREFIX__.util._APP_NAME_Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@Configuration
public class __CLASS__AppSecurity {

    @Autowired
    private Config config;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().httpStrictTransportSecurity().includeSubDomains(true)
                .maxAgeInSeconds(31536000).and().contentTypeOptions().and().frameOptions();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/v2/**", "/refresh", "/health", "/info", "/entities/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**").permitAll();

        http = addFiltersToRequest(http);

        http.csrf().disable();
        return http.build();
    }

    private HttpSecurity addFiltersToRequest(HttpSecurity http) throws Exception {
        if(config.getJwtFilterType().equals(_APP_NAME_Constants.JWT_LIB_AUTH_FILTER_TYPE)) {
            http.authorizeRequests().anyRequest().authenticated()
                    .and()
                    .addFilterBefore(new JWTAuthFilter(config), BasicAuthenticationFilter.class);
        }
        return http;
    }

}