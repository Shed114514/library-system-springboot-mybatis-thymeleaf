package com.shed.springboot.utils.handler;

import com.shed.springboot.utils.exception.*;
import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常捕获处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(Exception.class)
    @RequestMapping("/error500")
    public String exceptionHandler(Exception e, Model model) {
        model.addAttribute("error",e.getMessage());
        return "500" ;
    }

    /**
     * 捕获404错误页面,并跳转到相应的错误提示页面
     */
    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> createWebServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/error404"));
            }
        };
    }
}
