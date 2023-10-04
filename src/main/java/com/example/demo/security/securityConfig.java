package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class securityConfig  {
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/users/**","/SignIn").permitAll();
//                    auth.requestMatchers("/cart/**").authenticated();
//                })
//                .formLogin(Customizer.withDefaults())
//                .logout(logout -> {
//                    logout.logoutUrl("/logout");
//                    logout.deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
//                })
//                .build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer :: disable)
                //set up the apis that can be a accessed by all (permit all())
                .authorizeHttpRequests( auth -> auth
//                                .requestMatchers("/visitor-api", "/save","/home").permitAll()
//                                .requestMatchers("/**").hasAnyRole("admin","user")
//                                .requestMatchers("/**").permitAll()

                        .requestMatchers("/get-orders").authenticated()
                )

                //login
                .formLogin(login->{
                    login.loginPage("/SignIn").loginProcessingUrl("/login").permitAll();
                    login.defaultSuccessUrl("/get-orders");
                }
                )
                .logout(logout -> {
                    logout.logoutUrl("/logout");
                    logout.logoutSuccessUrl("/visitor-api");
                    logout.deleteCookies("auth_code", "JSESSIONID").invalidateHttpSession(true);
                })
                .build();
    }
//the commented method can be used alone along with passencoder (without userInfodetails ...)
    //this can only be done manually and saved in the memory(will be rermoved as soon as you rerun and all passwords are shown in the code -->less secure)
    //to let the security mapp between the user details in the data base and the
//    @Bean
//    public UserDetailsService userDetailsService(){
    //user details is a class found in security the bellow are setting the usename ,password,role manuall
    //also we are encoding (نشفر) the pass for security
//        UserDetails ADMIN = User.withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails USER = User.withUsername("user1")
//                .password(passwordEncoder().encode("user1"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(ADMIN,USER);
//    }
//so the next method
// ( userdetails service will overload the one manually writen to connect the user info details to the ones in memmory of security)
// are ben used to map the data base with the security

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoDetailsService();
    }
    //increpting the pass by using
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}