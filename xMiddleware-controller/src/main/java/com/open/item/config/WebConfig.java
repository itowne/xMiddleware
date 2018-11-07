package com.open.item.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.open.item.interceptor.SessionInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {

    private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Value("${upload.path}")
    private String uploadPath;

    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + uploadPath);
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        multipartResolver.setMaxUploadSize(10485760L);
        return multipartResolver;
    }

    @SuppressWarnings("deprecation")
    public FastJsonHttpMessageConverter fastJson() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        ArrayList<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
        fastJsonHttpMessageConverter.setFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.QuoteFieldNames);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(supportedMediaTypes);
        return fastJsonHttpMessageConverter;
    }

    public StringHttpMessageConverter stringConverter() {
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        stringConverter.setWriteAcceptCharset(false);
        ArrayList<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
        supportedMediaTypes.add(MediaType.valueOf("text/plain;charset=UTF-8"));
        stringConverter.setSupportedMediaTypes(supportedMediaTypes);
        return stringConverter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SessionInterceptor si = new SessionInterceptor();
        registry.addInterceptor(si).addPathPatterns("/**").excludePathPatterns("/").excludePathPatterns("/resources/**")
                .excludePathPatterns("/upload/**").excludePathPatterns("/login").excludePathPatterns("/doLogin")
                .excludePathPatterns("/view/**").excludePathPatterns("/admin");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringConverter());
        converters.add(fastJson());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

}
