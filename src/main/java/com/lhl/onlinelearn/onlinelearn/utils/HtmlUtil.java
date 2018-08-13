package com.lhl.onlinelearn.onlinelearn.utils;

import com.lhl.onlinelearn.onlinelearn.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class HtmlUtil {

    public static Logger logger =  LoggerFactory.getLogger(HtmlUtil.class);

    /**
     * @param url
     * @return
     */
    public static String getImge(String url){
        String logo="";
        logo=getPageImg(url);
        if(StringUtils.isBlank(logo) || logo.length()>300){
            logo=Constants.BASE_PATH + Constants.default_logo;
        }
        return logo;
    }

    /**
     * @param url
     * @return
     */
    public static String getPageImg(String url){
        String imgUrl="";
        Document doc;
        try {
            doc = Jsoup.connect(url).userAgent(Constants.user_Agent).get();
            Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
            for(Element image : images){
                imgUrl=image.attr("src");
                if(StringUtils.isNotBlank(imgUrl) ){
                    if(imgUrl.startsWith("//")){
                        imgUrl = "http:" + imgUrl;
                    }else if(!imgUrl.startsWith("http") && !imgUrl.startsWith("/")){
                        imgUrl=URLUtil.getDomainUrl(url) + "/" + imgUrl;
                    }else if(!imgUrl.startsWith("http")){
                        imgUrl=URLUtil.getDomainUrl(url)+imgUrl;
                    }
                }
                // 判断图片大小
                String fileUrl = download(imgUrl);
                if(fileUrl!=null){
                    File picture = new File(fileUrl);
                    FileInputStream in = new FileInputStream(picture);
                    BufferedImage sourceImg = ImageIO.read(in);
                    String weight = String.format("%.1f",picture.length()/1024.0);
                    int width = sourceImg.getWidth();
                    int height = sourceImg.getHeight();
                    // 删除临时文件
                    if(picture.exists()){
                        in.close();
                        picture.delete();
                    }
                    if(Double.parseDouble(weight) <= 0 || width <=0 || height <= 0){
                        logger.info("当前图片大小为0，继续获取图片链接");
                        imgUrl="";
                    }else{
                        break;
                    }
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.warn("getPageImg  失败,url:"+url,e.getMessage());
        }
        return imgUrl;
    }

    // 图片下载
    private static String download(String url) {
        try {
            String imageName = url.substring(url.lastIndexOf("/") + 1,
                    url.length());

            URL uri = new URL(url);
            InputStream in = uri.openStream();
            String dirName = "/static/temp/";
            File dirFile = new File(dirName);
            if(!dirFile.isDirectory()){
                dirFile.mkdir();
            }
            String fileName = dirName+imageName;
            File file = new File(dirFile,imageName);
            FileOutputStream fo = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = in.read(buf, 0, buf.length)) != -1) {
                fo.write(buf, 0, length);
            }
            in.close();
            fo.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
