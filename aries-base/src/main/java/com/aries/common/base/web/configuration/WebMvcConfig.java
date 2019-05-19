package com.aries.common.base.web.configuration;

import com.aries.common.base.common.utils.CommonUtil;
import com.aries.common.base.web.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 	1、springboot全局字符编码设置（解决乱码问题）
 *
 * 	2、springboot配置加载静态资源设置
 * @author
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {

        System.out.println("Springboot 编码格式设置为UTF-8---1");
        /**
         * StringHttpMessageConverter是一个请求和响应信息的编码转换器，通过源码我们发现默认编码ISO-8859-1，不是UTF-8，所以我们只要通过上述配置将请求字符串转为UTF-8 即可
         */
        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
    }

    /**
     * 	加载默认转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyConverter());
        // 这里必须加上加载默认转换器，不然bug玩死人，并且该bug目前在网络上似乎没有解决方案
        // 百度，谷歌，各大论坛等。你可以试试去掉。
        addDefaultHttpMessageConverters(converters);
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    /**
     * 	加载拦截器配置信息
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        InterceptorRegistration loginRegistration = registry.addInterceptor(loginInterceptor);
        // 拦截路径
        loginRegistration.addPathPatterns("/**");
        // 排除路径
        loginRegistration.excludePathPatterns(CommonUtil.excludeUrlPath());
        // 排除资源请求
        loginRegistration.excludePathPatterns(CommonUtil.excludeResourcePath());
        System.out.println("Springboot 登录拦截配置： ----> " + CommonUtil.excludeUrlPath() + "<----->" + CommonUtil.excludeResourcePath() );
    }

    /**
     * 	加在静态资源的配置信息
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/META-INF/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }
}
