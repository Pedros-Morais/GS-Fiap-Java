package com.fiap.blackoutservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

import com.fiap.blackoutservice.soap.PowerOutageSOAPServiceImpl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(WebServiceConfig.class);
    private Endpoint endpoint;
    
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    
    @Bean
    public PowerOutageSOAPServiceImpl powerOutageService() {
        return new PowerOutageSOAPServiceImpl();
    }
    
    @PostConstruct
    public void init() {
        try {
            logger.info("Publishing SOAP endpoint...");
            endpoint = Endpoint.publish(
                "http://localhost:8081/ws/poweroutage",  
                powerOutageService()
            );
            logger.info("SOAP endpoint published successfully");
        } catch (Exception e) {
            logger.error("Failed to publish SOAP endpoint: {}", e.getMessage());
        }
    }
    
    @PreDestroy
    public void destroy() {
        if (endpoint != null) {
            try {
                logger.info("Stopping SOAP endpoint...");
                endpoint.stop();
                logger.info("SOAP endpoint stopped successfully");
            } catch (Exception e) {
                logger.error("Error stopping SOAP endpoint: {}", e.getMessage());
            }
        }
    }
}
