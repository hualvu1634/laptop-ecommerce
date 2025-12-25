package e_commerce.project.config;





import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  
    private final UserDetailsService userDetailsService; 
    private final JwtFilter jwtFilter;

    // PUBLIC
    public static final String[] PUBLIC_GET = {"/products/**", "/categories/**"};
    public static final String[] PUBLIC_POST = {"/login", "/users/register"};
    // USER
    public static final String[] ROLE_USER = {"/users","/carts/**", "/orders/**"}; 

    // ADMIN

    public static final String[] ROLE_ADMIN = {"/users/**","/products/**", "/categories/**"};
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
     
            .csrf(csrf -> csrf.disable()) 
            
            // Cấu hình ủy quyền (Authorization)
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()
            .requestMatchers(HttpMethod.POST, PUBLIC_POST).permitAll()

            // User
            .requestMatchers(ROLE_USER).hasRole("USER")

            // Admin
       
            .requestMatchers(ROLE_ADMIN).hasRole("ADMIN")
                
                .anyRequest().authenticated()
                 
            );
            http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  
        return http.build();
    }
@Bean
    AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }
@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();

}
}
