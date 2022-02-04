package com.barauna.DEVinHouse.security;

import com.barauna.DEVinHouse.security.filters.JWTAuthenticationFilter;
import com.barauna.DEVinHouse.security.filters.JWTAuthorizationFilter;
import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.utils.JWTTokenUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS_POST = { "/login/**" };
    private final JWTTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private Environment environment;

    public WebSecurityConfigAdapter(JWTTokenUtils jwtUtil, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder, Environment environment){
        this.jwtTokenUtils = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.environment = environment;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] profiles = environment.getActiveProfiles();

        if (!Set.of(profiles).contains("prod")) {
            http.cors().disable();
            http.csrf().disable();
        }

        http.authorizeRequests()
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST) // Todos post para as rotas contidas em public_matchers_post
            .permitAll() // permita.
            .anyRequest() // Qualquer outra req
            .authenticated(); // precisa estar autenticada

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtTokenUtils)); //sobrescrever rota de autenticação padrão e outras coisas
        http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtTokenUtils, userDetailsService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
