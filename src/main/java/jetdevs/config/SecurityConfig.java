package jetdevs.config;


import jetdevs.constants.ErrorCodes;
import jetdevs.model.User;
import jetdevs.utils.UserHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Function to encode the password
    // Assign to the particular roles.
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        List<User> users = UserHelper.getUsers();
        for (User user : users) {
            auth
                    .inMemoryAuthentication()
                    .withUser(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRole());
        }
    }

    // Configuring the api
    // according to the roles.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/file/upload")
                .hasRole(ErrorCodes.ADMIN_ROLE)
                .antMatchers("/file/status/{fileId}")
                .hasRole(ErrorCodes.ADMIN_ROLE)
                .antMatchers("/file/getAll")
                .hasAnyRole(ErrorCodes.ADMIN_ROLE, ErrorCodes.USER_ROLE)
                .antMatchers("/file/delete/{fileId}")
                .hasAnyRole(ErrorCodes.ADMIN_ROLE)
                .antMatchers("/file/{fileId}")
                .hasAnyRole(ErrorCodes.ADMIN_ROLE, ErrorCodes.USER_ROLE)
                .antMatchers("/file/reviewedUserInfo/{fileId}")
                .hasAnyRole(ErrorCodes.ADMIN_ROLE, ErrorCodes.USER_ROLE)
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

}
