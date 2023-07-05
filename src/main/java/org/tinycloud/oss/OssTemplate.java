package org.tinycloud.oss;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

/**
 * <p>
 * oss操作模板
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-30 10:54
 */
public interface OssTemplate {

    /**
     * 创建bucket
     * @param bucketName bucket名称
     */
    void createBucket(String bucketName);

    /**
     * 获取所有的bucket
     * @return Bucket列表
     */
    List<Bucket> getAllBuckets();

    /**
     * 通过bucket名称删除bucket
     * @param bucketName bucket名称
     */
    void removeBucket(String bucketName);

    /**
     * 上传文件
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream 文件流
     * @param contextType 文件类型
     * @return true 成功，false 失败
     */
    boolean putObject(String bucketName, String objectName, InputStream stream, String contextType);

    /**
     * 上传文件
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream 文件流
     * @return true 成功，false 失败
     */
    boolean putObject(String bucketName, String objectName, InputStream stream);

    /**
     * 获取文件
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return S3Object（可以通过InputStream inputStream = s3Object.getObjectContent()来获取InputStream）
     */
    S3Object getObject(String bucketName, String objectName);

    /**
     * 获取对象的url链接
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires 过期时间
     * @return 链接
     */
    String getObjectURL(String bucketName, String objectName, Integer expires);

    /**
     * 通过bucketName和objectName删除对象
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return true 成功，false 失败
     */
    boolean removeObject(String bucketName, String objectName);

    /**
     * 根据文件前置查询文件
     * @param bucketName bucket名称
     * @param prefix 前缀
     * @param recursive 是否递归查询
     * @return S3ObjectSummary 列表
     */
    List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive);
}