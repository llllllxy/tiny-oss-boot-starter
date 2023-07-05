package org.tinycloud.oss;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.tinycloud.oss.core.OssConfig;

/**
 * <p>
 * oss配置实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-30 10:54
 */
@ConfigurationProperties(prefix = "tiny-oss")
public class OssProperties extends OssConfig {

}