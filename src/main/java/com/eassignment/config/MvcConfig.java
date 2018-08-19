package com.eassignment.config;

import java.util.Locale;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.eassignment.interceptor.BaseInterceptor;
import com.eassignment.validation.EmailValidator;
import com.eassignment.validation.PasswordMatchesValidator;


@Configuration
@ComponentScan(basePackages = { "com.eassignment.web","com.eassignment.interceptor" })
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter {

	public static final String ENCODING = "UTF-8";
	
	
	
    public MvcConfig() {
        super();
    }

    //

    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("/auth/login");
        registry.addViewController("/login").setViewName("/auth/login");
        
        registry.addViewController("/home").setViewName("/index.html");
 
        registry.addViewController("/forgetPassword").setViewName("/auth/forgetPassword");
        registry.addViewController("/updatePassword").setViewName("/auth/updatePassword");
        registry.addViewController("/changePassword").setViewName("/auth/changePassword");
        registry.addViewController("/emailError").setViewName("/auth/emailError");
        
        
        registry.addViewController("/invalidSession").setViewName("/session/invalidSession");

        
        
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/", "classpath:/resources/","classpath:/static/"};
    

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    
    /*@Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/", "/resources/");
    }*/
    
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        registry.addInterceptor(localeChangeInterceptor);
        
        final BaseInterceptor baseInterceptor = new BaseInterceptor();
        registry.addInterceptor(baseInterceptor);
    }

    // beans

    @Bean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return cookieLocaleResolver;
    }

    // @Bean
    // public MessageSource messageSource() {
    // final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    // messageSource.setBasename("classpath:messages");
    // messageSource.setUseCodeAsDefaultMessage(true);
    // messageSource.setDefaultEncoding("UTF-8");
    // messageSource.setCacheSeconds(0);
    // return messageSource;
    // }
    
    @Bean
    public ResourceBundleMessageSource messageSource() {
     ResourceBundleMessageSource source = new ResourceBundleMessageSource();
     source.setBasenames("i18n/messages");  // name of the resource bundle 
     source.setUseCodeAsDefaultMessage(true);
     source.setDefaultEncoding("UTF-8");
     source.setCacheSeconds(0);
     return source;
    }
    
    
    @Override
    public org.springframework.validation.Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }
    

    @Bean
    public EmailValidator usernameValidator() {
        return new EmailValidator();
    }

    @Bean
    public PasswordMatchesValidator passwordMatchesValidator() {
        return new PasswordMatchesValidator();
    }

    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
    

}