package org.tinycloud.oss;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.tinycloud.oss.core.OssConfig;

/**
 * OSS配置类
 * 
 * @author lry 
 */

@ConfigurationProperties(prefix = "oss")
public class OssProperties extends OssConfig {

}