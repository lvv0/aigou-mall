package com.cskaoyan.config;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.io.InputStream;

@Component
@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunComponent {
    String accessKeyId;
    String accessKeySecret;
    Oss oss;
    Sms sms;

    public OSSClient getOssClient(){
        OSSClient ossClient = new OSSClient(oss.getEndPoint(),accessKeyId,accessKeySecret);
        return ossClient;
    }

    public PutObjectResult fileUpload(String fileName, InputStream inputStream){
        OSSClient ossClient = getOssClient();
        PutObjectResult putObjectResult = ossClient.putObject(oss.getBucket(), fileName, inputStream);
        return putObjectResult;
    }

    public void sendMsg(String phoneNumber, String code){
        DefaultProfile profile = DefaultProfile.getProfile("cn-qingdao", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("SignName", sms.getSignName());
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("TemplateCode", sms.getTemplateCode());
        request.putQueryParameter("TemplateParam",  "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
