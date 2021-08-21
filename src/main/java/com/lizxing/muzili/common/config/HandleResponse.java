package com.lizxing.muzili.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Long类型的数据直接返回会被js减少精度，必须处理成String
 * @author lizxing
 * @date 2021/8/14
 */
@Configuration
public class HandleResponse {
    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        final JavaTimeModule module = new JavaTimeModule();

        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);

        return builder.createXmlMapper(false).modulesToInstall(module).build();
    }
}
