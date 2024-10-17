package br.com.matheus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean //É um objetro que é montado e gerenciado pelo Spring IOC Container
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Restful Api With Java 18 and Springboot 3")
                        .version("v1")
                        .description("Curso de aprendizado")
                        .termsOfService("http://compass.com.br/termos-servico-teste")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://compass.com.br/licensa-teste")));
    }

}
