package com.lhl.onlinelearn.onlinelearn.service.impl;

import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.repository.FavoritesRepository;
import com.lhl.onlinelearn.onlinelearn.service.CollectService;
import com.lhl.onlinelearn.onlinelearn.service.FavoritesService;
import com.lhl.onlinelearn.onlinelearn.common.enums.CollectType;
import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.entity.Favorites;
import com.lhl.onlinelearn.onlinelearn.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("collectService")
public class CollectServiceImpl implements CollectService {
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	/** 收藏夹 */
	@Autowired
	private FavoritesRepository favoritesRepository;
	/** 收藏 */
	@Autowired
	private CollectRepository collectRepository;
	@Autowired
	private FavoritesService favoritesService;
	/**
	 * 收藏文章
	 * @param collect
	 */
	@Transactional
	public void saveCollect(Collect collect) {
		if (collect.getType()==null) {
			collect.setType(CollectType.PUBLIC);
		}else{
			collect.setType(CollectType.PRIVATE);
		}
		if(StringUtils.isNotBlank(collect.getNewFavorites())){
			collect.setFavoritesId(createfavorites(collect));
		}
		if(StringUtils.isBlank(collect.getDescription())){
			collect.setDescription(collect.getTitle());
		}
		if(collect.getUrl().contains("?")){
			collect.setUrl(collect.getUrl().substring(0,collect.getUrl().indexOf("?")));
		}
		collect.setIsDelete(IsDelete.NO);
		collect.setCreateTime(DateUtils.getCurrentTime());
		collect.setLastModifyTime(DateUtils.getCurrentTime());
		collectRepository.save(collect);
	}

    /**
     *  修改文章
     * @author neo
     * @date 2016年8月24日
     * @param newCollect
     */
    @Transactional
    public void updateCollect(Collect newCollect) {
        Collect collect=collectRepository.findById(newCollect.getId().longValue());
        if(StringUtils.isNotBlank(newCollect.getNewFavorites())){
            collect.setFavoritesId(createfavorites(collect));
        }else if(!collect.getFavoritesId().equals(newCollect.getFavoritesId()) && !IsDelete.YES.equals(collect.getIsDelete())){
            favoritesService.countFavorites(collect.getFavoritesId());
            favoritesService.countFavorites(newCollect.getFavoritesId());
            favoritesRepository.reduceCountById(collect.getFavoritesId(), DateUtils.getCurrentTime());
            collect.setFavoritesId(newCollect.getFavoritesId());
        }
        if(IsDelete.YES.equals(collect.getIsDelete())){
            collect.setIsDelete(IsDelete.NO);
            if(StringUtils.isBlank(newCollect.getNewFavorites())){
                favoritesService.countFavorites(newCollect.getFavoritesId());
                collect.setFavoritesId(newCollect.getFavoritesId());
            }
        }
        if (newCollect.getType()==null) {
            collect.setType(CollectType.PUBLIC);
        }else{
            collect.setType(CollectType.PRIVATE);		}
        collect.setTitle(newCollect.getTitle());
        collect.setDescription(newCollect.getDescription());
        collect.setLogoUrl(newCollect.getLogoUrl());
        collect.setRemark(newCollect.getRemark());
        collect.setLastModifyTime(DateUtils.getCurrentTime());
        collectRepository.save(collect);
    }

    @Transactional
	public boolean checkCollect(Collect collect) {
        if(StringUtils.isNotBlank(collect.getNewFavorites())){
            // url+favoritesId+userId
            Favorites favorites = favoritesRepository.findByUserIdAndName(collect.getUserId(), collect.getNewFavorites());
            if(null == favorites){
                return true;
            }else{
                List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserIdAndIsDelete(favorites.getId(), collect.getUrl(), collect.getUserId(), IsDelete.NO);
                if(null != list && list.size() > 0){
                    return false;
                }else{
                    return true;
                }
            }
        }else{
            if(collect.getId() != null){
                Collect c = collectRepository.findById(collect.getId().longValue());
                if(c.getFavoritesId().equals(collect.getFavoritesId())){
                    return true;
                }else{
                    List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserIdAndIsDelete(collect.getFavoritesId(), collect.getUrl(), collect.getUserId(),IsDelete.NO);
                    if(null != list && list.size() > 0){
                        return false;
                    }else{
                        return true;
                    }
                }
            }else{
                List<Collect> list = collectRepository.findByFavoritesIdAndUrlAndUserIdAndIsDelete(collect.getFavoritesId(), collect.getUrl(), collect.getUserId(),IsDelete.NO);
                if(null != list && list.size() > 0){
                    return false;
                }else{
                    return true;
                }
            }
        }
	}

    @Transactional
	public void otherCollect(Collect collect) {

	}

	/**
	 * @author neo
	 * @date 2016年9月8日
	 * @return
	 */
	private Long  createfavorites(Collect collect){
		Favorites favorites = favoritesRepository.findByUserIdAndName(collect.getUserId(), collect.getNewFavorites());
		if (null == favorites) {
			favorites =favoritesService.saveFavorites(collect);
		}
		return favorites.getId();

	}
}