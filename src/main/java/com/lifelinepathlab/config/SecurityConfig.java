package com.lifelinepathlab.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.lifelinepathlab.security.JwtAuthenticationEntryPoint;
import com.lifelinepathlab.security.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())  
                .cors(cors -> cors.disable())

                .authorizeHttpRequests((auth) -> auth.requestMatchers(HttpMethod.POST,"home/**").authenticated()
 	
                		.requestMatchers("api/user/login").permitAll()
                		.requestMatchers("api/user/regenerate-otp").permitAll()
                		
                		.requestMatchers("api/user/verify-account").permitAll()
                		.requestMatchers("/api/user/**").permitAll()
                		.requestMatchers("/api/feedback/**").permitAll()
                		
                		.requestMatchers("/api/tests/**").permitAll()
                		.requestMatchers("/api/tests/all/**").permitAll()
                		.requestMatchers("/api/tests/create/**").permitAll()
                		.requestMatchers("/api/tests/testName/**").permitAll()
                		.requestMatchers("/api/tests/TestType").permitAll()
                		.requestMatchers("/api/tests/bestOffers").permitAll()
                		
                		//Docter All Api
                		.requestMatchers("/api/doctors/**").permitAll()
                		.requestMatchers("/api/doctors/register/**").permitAll()
                		.requestMatchers("/api/doctors/list/**").permitAll()
                		.requestMatchers("/api/doctors/pending/**").permitAll()
                		.requestMatchers("api/doctors/approved/**").permitAll()
                		.requestMatchers("api/doctors/request/**").permitAll()
                		.requestMatchers("api/doctors/reject/**").permitAll()

                		//All api feedback
                		.requestMatchers("/api/feedback/**").permitAll()
                		.requestMatchers("/api/feedback").permitAll()
                		.requestMatchers("/api/feedback/review/**").permitAll()
                		
                		//All api appointments
                		.requestMatchers("/api/appointments/**").permitAll()
//                		.requestMatchers("/api/appointments/schedule/**").hasRole("USER")
                		.requestMatchers("/api/appointments/schedule/**").permitAll()
                		.requestMatchers("/api/appointments/today/**").permitAll()
                		.requestMatchers("/api/appointments/list/**").permitAll()
                		
                		//All api reports
                		.requestMatchers("/api/reports/**").permitAll()
                		.requestMatchers("/api/reports/user/**").permitAll()
                		.requestMatchers("/api/reports//email/**").permitAll()
                		.requestMatchers("/api/reports/upload/**").permitAll()

                		
                		           
                		
                
                		.requestMatchers("/api/orders/**").permitAll()
                		.requestMatchers("/api/orders/create-order/**").permitAll()
                		.requestMatchers("/api/orders/update-satus/**").permitAll()

                		
                		
                		
                		.requestMatchers("/api/tests/create/**").permitAll()
                		.requestMatchers("/api/orders/addOrder/**").permitAll()
                		.requestMatchers("/api/orders/orderStatus/**").permitAll()
                		.requestMatchers("/api/orders/cartOrders/**").permitAll()
                		.requestMatchers("/api/enquiry/**").permitAll()
                		
                		
                		.requestMatchers("/api/paymentDetails/**").permitAll()
                		           		
                		
                		.anyRequest().authenticated()                		
                )              
                		
                .exceptionHandling(ex ->ex.authenticationEntryPoint(point))
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ; 
                  
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    @Bean
    public DaoAuthenticationProvider doDaoAuthenticationProvider() {
    	DaoAuthenticationProvider daoAuthenticationProvider = new  DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    	return daoAuthenticationProvider;
    }

}