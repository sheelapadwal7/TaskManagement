package com.task.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class WebMvcConfig {

	/*
	 * @Bean public FilterRegistrationBean<TokenFilter>
	 * tokenFilterRegistrationBean(TokenFilter tokenFilter) {
	 * FilterRegistrationBean<TokenFilter> registrationBean = new
	 * FilterRegistrationBean<>(); registrationBean.setFilter(tokenFilter);
	 * registrationBean.addUrlPatterns( // "/student/*", // "/admin/*"
	 * 
	 * ); registrationBean.setOrder(1); return registrationBean; }
	 */

    @Bean
    public FilterRegistrationBean<TokenFilter> authFilterRegistrationBean(TokenFilter tokenFilter) {
        FilterRegistrationBean<TokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(tokenFilter);
        registrationBean.addUrlPatterns("/auth/*");
        registrationBean.setOrder(2);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public TokenFilter tokenFilter() {
        return new TokenFilter();
    }
}
