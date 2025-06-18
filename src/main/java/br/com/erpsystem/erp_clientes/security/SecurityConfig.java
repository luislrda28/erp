package br.com.erpsystem.erp_clientes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod; // Importe HttpMethod

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Regras para Clientes (mantidas)
                        .requestMatchers(HttpMethod.DELETE, "/api/clientes/{id}").hasRole("ADMIN") // Apenas ADMIN deleta
                        .requestMatchers(HttpMethod.POST, "/api/clientes").hasRole("ADMIN") // Apenas ADMIN cria
                        .requestMatchers(HttpMethod.PUT, "/api/clientes/{id}").hasRole("ADMIN") // Apenas ADMIN atualiza
                        .requestMatchers(HttpMethod.GET, "/api/clientes/**").hasAnyRole("USER", "ADMIN") // USER ou ADMIN podem ler

                        // Novas Regras para Produtos
                        .requestMatchers(HttpMethod.POST, "/api/produtos").hasRole("ADMIN") // Apenas ADMIN cria produto
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/{id}").hasRole("ADMIN") // Apenas ADMIN atualiza produto
                        .requestMatchers(HttpMethod.DELETE, "/api/produtos/{id}").hasRole("ADMIN") // Apenas ADMIN deleta produto
                        // Operações de estoque específicas
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/{id}/adicionar-estoque").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/produtos/{id}/remover-estoque").hasRole("ADMIN")
                        // Leitura de produtos para USER e ADMIN
                        .requestMatchers(HttpMethod.GET, "/api/produtos/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    // Os usuários em memória permanecem os mesmos
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}