package hellgate.admin.config;

import hellgate.common.util.Roles;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties(SecurityProperties.class)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

    private static final String ADMIN_PATH = "/admin/**";
    private static final String USER_PATH = "/user/**";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AccessDecisionVoter<?> hierarchyVoter() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy(Roles.HIERARCHY);
        return new RoleHierarchyVoter(hierarchy);
    }

    @Configuration
    static class WebsiteSecurityAdapter extends WebSecurityConfigurerAdapter {

        private final SecurityProperties securityProperties;
        private final UserDetailsService userDetailsService;

        public WebsiteSecurityAdapter(SecurityProperties securityProperties,
                                      UserDetailsService userDetailsService) {
            this.securityProperties = securityProperties;
            this.userDetailsService = userDetailsService;
        }

        @Override
        public void configure(WebSecurity web) {
            web.ignoring()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                    .antMatchers(securityProperties.getIgnorePath());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.userDetailsService(userDetailsService)
                    .authorizeRequests()
                    .antMatchers(securityProperties.getPublicPath()).permitAll()
                    .antMatchers(USER_PATH).hasRole(Roles.RAW_USER)
                    .antMatchers(ADMIN_PATH).hasRole(Roles.RAW_ADMIN)
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage(securityProperties.getLoginPath()).permitAll()
                    .defaultSuccessUrl(securityProperties.getDefaultSuccessUrl())
                    .and().logout().permitAll();
            if (securityProperties.getRememberMe()) {
                http.rememberMe();
            }
        }
    }
}
