package com.lhl.onlinelearn.onlinelearn.controller;

import com.lhl.onlinelearn.onlinelearn.entity.Favorites;
import com.lhl.onlinelearn.onlinelearn.entity.User;
import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.repository.FavoritesRepository;
import com.lhl.onlinelearn.onlinelearn.service.CollectService;
import com.lhl.onlinelearn.onlinelearn.service.UrlLibraryService;
import com.lhl.onlinelearn.onlinelearn.common.Constants;
import com.lhl.onlinelearn.onlinelearn.controller.result.ExceptionMsg;
import com.lhl.onlinelearn.onlinelearn.controller.result.Response;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collect")
public class CollectController extends BaseController{

    @Autowired
    private UrlLibraryService urlLibraryService;

    @Resource
    private CollectService collectService;

    @Autowired
    private CollectRepository collectRepository;

    /** 收藏夹 */
    @Autowired
    private FavoritesRepository favoritesRepository;

    @GetMapping("")
    public ModelAndView toCollectPage(HttpServletRequest rest) {
        // 取得当前登录用户
        User user = UserUtils.getCurrentUser();
        Long currentUserId = user.getId();
        Map data = new HashMap<String,Object>();
        data.put("favoritesList",favoritesRepository.findByUserId(currentUserId));
        return new ModelAndView("collect",data);
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
            // 取得当前登录用户
            User user = UserUtils.getCurrentUser();
            Long currentUserId = user.getId();
            collect.setUserId(currentUserId);
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


    @GetMapping("/getFavorites")
    @ResponseBody
    public List<Map<String,Object>> getFavorites(HttpServletRequest rest) {

        // 取得当前登录用户
        User user = UserUtils.getCurrentUser();
        Long currentUserId = user.getId();
        List<Favorites> result = favoritesRepository.findByUserId(currentUserId);
        List<Map<String,Object>> favoritesLsit = new ArrayList<>();
        for (Favorites favorites : result) {
            Map<String,Object> node = new HashMap<>();
            node.put("id",favorites.getId());
            node.put("pId",favorites.getParentId());
            node.put("name",favorites.getName());
            node.put("open",true);
            favoritesLsit.add(node);
        }
        return  favoritesLsit;
    }
}
