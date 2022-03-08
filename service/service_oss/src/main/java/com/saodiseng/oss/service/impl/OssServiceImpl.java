package com.saodiseng.oss.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.saodiseng.oss.service.OssService;
import com.saodiseng.oss.utils.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantProperties.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantProperties.ACCESS_KEY_ID;
        String accessKeySecret = ConstantProperties.ACCESS_KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantProperties.BUCKET_NAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath = "D:\\localpath\\examplefile.txt";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String originalFilename = file.getOriginalFilename();

            //文件名添加唯一值，使名字不重复
            //aiydu-asda-3141-asfd
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String filename = uuid + originalFilename;

            //文件按日期分类    2019/2/22/1.jpg
            //获取当前日期
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            filename = datePath+"/"+filename;

            // 创建PutObject请求。
            //第二个参数 上传到oss文件路径和文件名称   aa/bb/1.jpg
            ossClient.putObject(bucketName, filename, inputStream);

            //返回上传之后的路径 需手动拼接
            // https://guli-edu-yt.oss-cn-chengdu.aliyuncs.com/%E8%AF%BE%E7%A8%8B%E8%A1%A8.png
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }
}
