package cz.etyka.exam.pub.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired private SpringUserDetailsService userDetailsService;

    private static final String BARTENDER_ROLE = "BARTENDER";
    private static final String GUEST_ROLE = "GUEST";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable csrf protection for POST requests to work without it. Don't do this in production!
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users").hasAnyAuthority(BARTENDER_ROLE, GUEST_ROLE)
                .antMatchers("/user/**").hasAnyAuthority(BARTENDER_ROLE, GUEST_ROLE)
                .antMatchers("/drink-menu").hasAnyAuthority(BARTENDER_ROLE, GUEST_ROLE)
                .antMatchers(HttpMethod.POST, "/buy").hasAuthority(GUEST_ROLE )
                .antMatchers("/summary/**").hasAuthority(BARTENDER_ROLE)
                .and()
                .httpBasic();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
