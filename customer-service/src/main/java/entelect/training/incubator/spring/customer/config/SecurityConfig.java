package entelect.training.incubator.spring.customer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private DataSource securityDataSource;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(securityDataSource);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // !!! Disclaimer: NEVER DISABLE CSRF IN PRODUCTION !!!
            .authorizeRequests()
            .antMatchers(HttpMethod.GET, "/customers/**").hasRole("USER")
            .antMatchers(HttpMethod.POST, "/customers").hasAnyRole("SYSTEM", "ADMIN")
            .anyRequest().denyAll()
            .and()
            .httpBasic();
    }
    
}
