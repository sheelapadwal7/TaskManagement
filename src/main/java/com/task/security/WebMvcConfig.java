/*
 * package com.task.security;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.method.configuration.
 * EnableMethodSecurity; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.http.SessionCreationPolicy; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.authentication.
 * UsernamePasswordAuthenticationFilter;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity
 * 
 * @EnableMethodSecurity public class WebMvcConfig {
 * 
 * // @Autowired // SecurityFilter securityFilter;
 * 
 * // // @Bean // public FilterRegistrationBean<TokenFilter>
 * tokenFilterRegistrationBean(TokenFilter tokenFilter) { //
 * FilterRegistrationBean<TokenFilter> registrationBean = new
 * FilterRegistrationBean<>(); // registrationBean.setFilter(tokenFilter); //
 * registrationBean.addUrlPatterns("/student/*"); //
 * registrationBean.addUrlPatterns("/admin/*"); //
 * registrationBean.addUrlPatterns("/tasks/*"); // registrationBean.setOrder(1);
 * // // // return registrationBean; // } // // @Bean // public
 * FilterRegistrationBean<TokenFilter> authFilterRegistrationBean(TokenFilter
 * tokenFilter) { // FilterRegistrationBean<TokenFilter> registrationBean = new
 * FilterRegistrationBean<>(); // registrationBean.setFilter(tokenFilter); //
 * registrationBean.addUrlPatterns("/auth/*"); // registrationBean.setOrder(2);
 * // registrationBean.setEnabled(false); // return registrationBean; // } //
 * // @Bean // public TokenFilter tokenFilter() { // return new TokenFilter();
 * // } //
 * 
 * @Bean public SecurityFilter securityFilter() { return new SecurityFilter(); }
 * 
 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity http)
 * throws Exception { System.out.println("i am also registering"); //
 * http.csrf(csrf -> csrf.disable()) // .authorizeRequests(). //
 * requestMatchers("/test").authenticated().requestMatchers("auth/student/login"
 * ).permitAll() // .anyRequest() // .authenticated() // .and() //
 * .sessionManagement(session ->
 * session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //
 * http.addFilterBefore(securityFilter,
 * UsernamePasswordAuthenticationFilter.class); // return http.build();
 * 
 * 
 * return http.csrf(csrf -> csrf.disable()) .authorizeHttpRequests(auth ->
 * auth.requestMatchers("/auth/student/login").permitAll())
 * .authorizeHttpRequests(auth ->
 * auth.requestMatchers("/tasks/**").authenticated()) .sessionManagement(sess ->
 * sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 * 
 * .addFilterBefore(securityFilter(),
 * UsernamePasswordAuthenticationFilter.class) .build(); } }
 */