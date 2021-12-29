package com.barauna.DEVinHouse.security;

import com.barauna.DEVinHouse.security.filters.JWTAuthenticationFilter;
import com.barauna.DEVinHouse.service.UserDetailsServiceImpl;
import com.barauna.DEVinHouse.utils.JWTTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_MATCHERS_POST = { "/login/**" };
    private final JWTTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;

    public WebSecurityConfigAdapter(JWTTokenUtils jwtUtil, UserDetailsServiceImpl userDetailsService){
        this.jwtTokenUtils = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(userDetailsService)
            .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST) // Todos post para as rotas contidas em public_matchers_post
            .permitAll() // permita.
            .anyRequest() // Qualquer outra req
            .authenticated(); // precisa estar autenticada

        http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtTokenUtils)); //sobrescrever rota de autenticação padrão e outras coisas
//        http.addFilter(new JWTAuthorizationFilter(authenticationManager(),
//                jwtUtil, userService));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }



    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
//        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
