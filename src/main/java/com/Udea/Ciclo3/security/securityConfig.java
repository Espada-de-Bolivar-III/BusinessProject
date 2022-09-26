package com.Udea.Ciclo3.security;

import com.Udea.Ciclo3.handler.CustomSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class securityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder autenticacion) throws Exception {
        autenticacion.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource)
                .usersByUsernameQuery("select email,password,estado from employee where email=?")
                .authoritiesByUsernameQuery("select email, rol from employee where email=?");


    }


    @Override
    protected void configure (HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    //.anyRequest().authenticated()
                    .antMatchers("/", "/ViewEnterprises/**").hasRole("ADMIN")
                    .antMatchers("/ViewEmployees/**").hasRole("ADMIN")
                    .antMatchers("/Enterprise/**").hasRole("ADMIN")
                    .antMatchers("/Employee/**").hasRole("ADMIN")
                    .antMatchers("/ViewTransactions/**").hasAnyRole("ADMIN", "USER") //loguean ambos
                    .antMatchers("/AddTransaction/**").hasAnyRole("ADMIN", "USER") //loguean ambos
                    .antMatchers("/EditTransaction/**").hasAnyRole("ADMIN", "USER") //loguean ambos
                    //controlador de acceso denegado
                    .and().formLogin().successHandler(customSuccessHandler)
                    .and().exceptionHandling().accessDeniedPage("/Denegado")
                    .and().logout().permitAll().and().csrf();

        }
    }

