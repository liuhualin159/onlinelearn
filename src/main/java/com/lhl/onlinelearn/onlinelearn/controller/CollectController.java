package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.service.CollectService;
import com.lhl.onlinelearn.onlinelearn.service.UrlLibraryService;
import com.lhl.onlinelearn.onlinelearn.common.Constants;
import com.lhl.onlinelearn.onlinelearn.controller.result.ExceptionMsg;
import com.lhl.onlinelearn.onlinelearn.controller.result.Response;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{

    @Autowired
    private UrlLibraryService urlLibraryService;

    @Resource
    private CollectService collectService;

    @Autowired
    private CollectRepository collectRepository;

    @GetMapping("")
    public ModelAndView toCollectPage(HttpServletRequest rest) {
        return new ModelAndView("collect");
    }

    /**
     * 取得网页图标
     * @param url
     * @return
     */
    @RequestMapping(value="/getCollectLogoUrl",method=RequestMethod.POST)
    public String getCollectLogoUrl(String url){
        if(StringUtils.isNotBlank(url)){
            String logoUrl = urlLibraryService.getMap(url);
            if(StringUtils.isNotBlank(logoUrl)){
                return logoUrl;
            }else{
                return Constants.default_logo;
            }
        }else{
            return Constants.default_logo;
        }
    }

    /**
     * 文章收集
     * @param collect
     * @return
     */
    @RequestMapping(value = "/collect", method = RequestMethod.POST)
    public Response collect(Collect collect) {
        try {
            if(StringUtils.isBlank(collect.getLogoUrl()) || collect.getLogoUrl().length()>300){
                collect.setLogoUrl(Constants.BASE_PATH + Constants.default_logo);
            }
            //TODO: 用户ID
            collect.setUserId(Long.valueOf(1));
            if(collectService.checkCollect(collect)){
                Collect exist=collectRepository.findByIdAndUserId(collect.getId(), collect.getUserId());
                if(collect.getId()==null){
                    collectService.saveCollect(collect);
                }else if(exist==null){//收藏别人的文章
                    collectService.otherCollect(collect);
                }else{
                    collectService.updateCollect(collect);
                }
            }else{
                return result(ExceptionMsg.CollectExist);
            }
        } catch (Exception e) {
            // TODO: handle exception
            logger.error("collect failed, ", e);
            return result(ExceptionMsg.FAILED);
        }
        return result();
    }
}
