package org.tinycloud.oss;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tinycloud.oss.core.OssClient;

/**
 * <p>
 * oss自动配置类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-30 10:54
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 ossClient(OssProperties properties) {
        OssClient ossClient = new OssClient();
        return ossClient.ossClient(properties);
    }

    @Bean
    @ConditionalOnBean(AmazonS3.class)
    public OssTemplate ossTemplate(AmazonS3 amazonS3) {
        return new OssTemplateImpl(amazonS3);
    }

}
