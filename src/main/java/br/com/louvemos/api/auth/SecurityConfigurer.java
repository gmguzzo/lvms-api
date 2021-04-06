/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.louvemos.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author gmguzzo
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private LvmsAuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    private final String ENV = System.getenv("ENV");

    private final String[] permittedPaths = {"/v2/auth", "/", "/v2/persons/self", "/v2/passwords/**"};
    private final String[] userPermittedPOSTPUTDELETEPaths = {"/v2/**", "/v2/**/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (ENV.equals("root")) {
            http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers("**")
                    .permitAll()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        } else {
            http
                    .csrf()
                    .disable()
                    .authorizeRequests()
                    .antMatchers(permittedPaths)
                    .permitAll()
                    .antMatchers(HttpMethod.POST, userPermittedPOSTPUTDELETEPaths)
                    .hasAuthority("USER")
                    .antMatchers(HttpMethod.PUT, userPermittedPOSTPUTDELETEPaths)
                    .hasAuthority("USER")
                    .antMatchers(HttpMethod.GET)
                    .hasAuthority("USER")
                    .antMatchers(HttpMethod.DELETE, userPermittedPOSTPUTDELETEPaths)
                    .hasAuthority("USER")
                    .anyRequest()
                    .authenticated()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
