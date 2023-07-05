package org.tinycloud.oss.core;


import java.io.Serializable;

/**
 * <p>
 * oss配置实体类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-30 10:54
 */
public class OssConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 对象存储服务的URL
     */
    private String endpoint;

    /**
     * 区域
     */
    private String region;

    /**
     * true path-style nginx 反向代理和S3默认支持 pathStyle模式 {http://endpoint/bucketname}
     * false supports virtual-hosted-style 阿里云等需要配置为 virtual-hosted-style 模式{http://bucketname.endpoint}
     * 只是url的显示不一样
     */
    private Boolean pathStyleAccess = true;

    /**
     * Access key
     */
    private String accessKey;

    /**
     * Secret key
     */
    private String secretKey;

    /**
     * 最大线程数，默认： 100
     */
    private Integer maxConnections = 100;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Boolean getPathStyleAccess() {
        return pathStyleAccess;
    }

    public void setPathStyleAccess(Boolean pathStyleAccess) {
        this.pathStyleAccess = pathStyleAccess;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(Integer maxConnections) {
        this.maxConnections = maxConnections;
    }
}
