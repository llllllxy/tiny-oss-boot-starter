package org.tinycloud.oss;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;


/**
 * <p>
 * oss操作模板实现类
 * </p>
 *
 * @author liuxingyu01
 * @since 2023-06-30 10:54
 */
public class OssTemplateImpl implements OssTemplate {

    private final AmazonS3 amazonS3;

    public OssTemplateImpl(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * 创建一个Bucket
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_CreateBucket.html
     * @param bucketName bucket名称
     */
    @Override
    public void createBucket(String bucketName) {
        if (!amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.createBucket((bucketName));
        }
    }

    /**
     * 获取所有的buckets
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListBuckets.html
     * @return bucket列表
     */
    @Override
    public List<Bucket> getAllBuckets() {
        return amazonS3.listBuckets();
    }

    /**
     * 通过Bucket名称删除Bucket
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_DeleteBucket.html
     * @param bucketName bucket名称
     */
    @Override
    public void removeBucket(String bucketName) {
        amazonS3.deleteBucket(bucketName);
    }

    /**
     * 上传对象
     *  AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_PutObject.html
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream 文件流（会自动关闭）
     * @param contextType 文件类型
     * @return true or false
     */
    @Override
    public boolean putObject(String bucketName, String objectName, InputStream stream, String contextType) {
        try {
            PutObjectResult result = putObject(bucketName, objectName, stream, stream.available(), contextType);
            if (result != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 上传对象
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_PutObject.html
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream 文件流（会自动关闭）
     * @return true or false
     */
    @Override
    public boolean putObject(String bucketName, String objectName, InputStream stream) {
        try {
            PutObjectResult result = putObject(bucketName, objectName, stream, stream.available(),
                    "application/octet-stream");
            if (result != null) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 通过bucketName和objectName获取对象
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_GetObject.html
     */
    @Override
    public S3Object getObject(String bucketName, String objectName) {
        return amazonS3.getObject(bucketName, objectName);
    }

    /**
     * 获取对象的url
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires 几天后过期，单位（天）
     * @return
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_GeneratePresignedUrl.html
     */
    @Override
    public String getObjectURL(String bucketName, String objectName, Integer expires) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, expires);
        URL url = amazonS3.generatePresignedUrl(bucketName, objectName, calendar.getTime());
        return url.toString();
    }

    /**
     * 通过bucketName和objectName删除对象
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_DeleteObject.html
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return true or false
     */
    @Override
    public boolean removeObject(String bucketName, String objectName) {
        try {
            amazonS3.deleteObject(bucketName, objectName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据bucketName和prefix获取对象集合
     * @param bucketName bucket名称
     * @param prefix 前缀
     * @param recursive 是否递归查询
     * @return
     * AmazonS3：https://docs.aws.amazon.com/AmazonS3/latest/API/API_ListObjects.html
     */
    @Override
    public List<S3ObjectSummary> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        ObjectListing objectListing = amazonS3.listObjects(bucketName, prefix);
        return objectListing.getObjectSummaries();
    }

    /**
     *
     * @param bucketName 存储桶名
     * @param objectName 对象id
     * @param stream 文件输入流（会自动关闭）
     * @param size 大小
     * @param contextType type
     * @return PutObjectResult
     */
    private PutObjectResult putObject(String bucketName, String objectName, InputStream stream, long size,
                                      String contextType) {
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(size);
            objectMetadata.setContentType(contextType);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            // 开始上传
            return amazonS3.putObject(bucketName, objectName, byteArrayInputStream, objectMetadata);
        } catch (Exception e) {
            return null;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

}