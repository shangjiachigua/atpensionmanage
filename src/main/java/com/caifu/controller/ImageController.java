package com.caifu.controller;


import net.coobird.thumbnailator.Thumbnails;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * ClassName :ImageController
 *
 * @author :
 * @description ：
 * @date : 2020-08-011 10:34
 */
@Controller
@RequestMapping(value = "/img")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Value("${file.upload.result.path}")
    private String imageUrlPath;
    @Value("${file.upload.path}")
    private String imgSocialPath;

    public static final int IMAGE_SIZE_7380 = 7380;
    public static final int IMAGE_SIZE_4096 = 4096;
    public static final int IMAGE_SIZE_1024 = 1024;
    public static final int IMAGE_SIZE_600 = 600;

    /**
     * 图片上传
     *
     * @return 图片url
     */
    @PostMapping("uploadImage")
    @ResponseBody
    public String ajaxUploadImage(@RequestParam(value = "file", required = false) MultipartFile uploadFile) {
        JSONObject result = new JSONObject();
        try {
            String fileName = System.currentTimeMillis() + ".jpg";
            //测试用 老人档案
            String filePath = imgSocialPath + "/pensionUserInfo";
            String imgPath = imageUrlPath + "/pensionUserInfo";
            // String savePath = "C:\\Users\\hml\\Desktop" + "/" + fileName;
            String savePath = filePath + "/" + fileName;

            File tempFile = new File(savePath);
            createMkdirs(filePath);
//            uploadFile.transferTo(tempFile);
            //压缩图片
            //获取文件大小 KB
            long size = uploadFile.getSize() / 1024;
            //判断文件大小对图片质量进行压缩,尺寸不变，范围0.01~1.0,值越低压缩效率越高。图片低于600K不进行压缩
//            if (size >= IMAGE_SIZE_7380) {
//                Thumbnails.of(uploadFile.getInputStream()).scale(0.1f).outputQuality(0.01f).toFile(savePath);
//            } else if (size >= IMAGE_SIZE_4096 ) {
//                Thumbnails.of(uploadFile.getInputStream()).scale(0.2f).outputQuality(0.1f).toFile(savePath);
//            } else if (size >= IMAGE_SIZE_1024 ) {
//                Thumbnails.of(uploadFile.getInputStream()).scale(0.3f).outputQuality(0.2f).toFile(savePath);
//            } else if (size >= IMAGE_SIZE_600 ) {
//                Thumbnails.of(uploadFile.getInputStream()).scale(0.3f).outputQuality(0.3f).toFile(savePath);
//            } else {
//                Thumbnails.of(uploadFile.getInputStream()).scale(0.3f).outputQuality(0.3f).toFile(savePath);
//            }
            //Thumbnails.of(uploadFile.getInputStream()).scale(0.3f).outputQuality(0.3f).toFile(savePath);

            result.put("code", "0000");
            result.put("msg", imgPath + "/" + fileName);

            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "9999");
            result.put("msg", "图片上传失败");
            return result.toString();
        }
    }


    private static void createMkdirs(String filePath) {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            boolean isTrue = targetFile.mkdirs();
            logger.info("是否创建了文件夹：" + isTrue);
        }
    }
//
//    private static void uploadFile(byte[] file, String filePath, String fileName)   {
//        //uploadFile(uploadFile.getBytes(), filePath, fileName);
//       try{
//           File targetFile = new File(filePath);
//           if (!targetFile.exists()) {
//               targetFile.mkdirs();
//           }
//           FileOutputStream out = new FileOutputStream(filePath + "/" + fileName);
//           out.write(file);
//           out.flush();
//           out.close();
//       } catch(Exception e){
//           e.printStackTrace();
//       }
//    }


}
