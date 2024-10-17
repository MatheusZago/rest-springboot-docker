package br.com.matheus.config;

import br.com.matheus.serialization.converter.YamlJackson2HttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final MediaType MEDIA_TYPE_APPLICATION_YML = MediaType.valueOf("application/x-yaml");

    @Value("${cors.originPatterns:default}")
    private String corsOriginPatterns = "";

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new YamlJackson2HttpMessageConverter());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        var allowedOrigins = corsOriginPatterns.split(",");
        registry.addMapping("/**") //ta mapeando todas as rotas da api para iss
//                .allowedMethods("GET", "POST", "PUT")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true); //Para liberar authenticação
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer){
        //Vamos usar query param para definir o tipo de dado
        //http://localhost:8080/api/person/v1?mediaType=xml

        /* configurer.favorParameter(true)  //Aceita parametro
                .parameterName("mediaType") //Nome do parametro
                .ignoreAcceptHeader(true)  //Ignorar parametros do heador
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON) //Ta falando quais aceita
                .mediaType("xml", MediaType.APPLICATION_XML); */

        //Via header param:
        configurer.favorParameter(false)  //não aceita parametro de query
                .ignoreAcceptHeader(false)  //aceitar header
                .useRegisteredExtensionsOnly(false)
                .defaultContentType(MediaType.APPLICATION_JSON)
                .mediaType("json", MediaType.APPLICATION_JSON) //Ta falando quais aceita
                .mediaType("xml", MediaType.APPLICATION_XML)
                .mediaType("x-yaml", MEDIA_TYPE_APPLICATION_YML);
    }

}
