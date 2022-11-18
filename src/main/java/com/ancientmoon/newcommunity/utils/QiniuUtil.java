package com.ancientmoon.newcommunity.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuUtil {
    static Configuration cfg = new Configuration(Region.autoRegion());
    static UploadManager uploadManager = new UploadManager(cfg);
    //...生成上传凭证，然后准备上传
    static String accessKey = "itV3Yk7aAZ3tm3_X6ElSIbABa1kzODOmnAWPrkxz";
    static String secretKey = "6istE9JB6PFXBhlf1v5slgMmyo24kizUORvVTnmP";
    static String bucket = "douyinsingle";

    public static String upload(byte[] bytes, String fileName) throws QiniuException {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        uploadManager.put(bytes,fileName, upToken);
        String str = "http://rkxdf09as.bkt.clouddn.com/";
        return str+fileName;
    }
}
