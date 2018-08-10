package com.lhl.onlinelearn.onlinelearn.service.impl;

import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.repository.CollectRepository;
import com.lhl.onlinelearn.onlinelearn.repository.FavoritesRepository;
import com.lhl.onlinelearn.onlinelearn.service.FavoritesService;
import com.lhl.onlinelearn.onlinelearn.common.enums.CollectType;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.entity.Favorites;
import com.lhl.onlinelearn.onlinelearn.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("favoritesService")
public class FavoritesServiceImpl implements FavoritesService {
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FavoritesRepository favoritesRepository;
	@Autowired
	private CollectRepository collectRepository;

	/**
	 * 保存
	 * @return
	 */
	public Favorites saveFavorites(Collect collect){
		Favorites favorites = new Favorites();
		favorites.setName(collect.getNewFavorites());
		favorites.setUserId(collect.getUserId());
		favorites.setCount(1l);
		if(CollectType.PUBLIC.name().equals(collect.getType())){
			favorites.setPublicCount(1l);
		}else {
			favorites.setPublicCount(10l);
		}
		favorites.setCreateTime(DateUtils.getCurrentTime());
		favorites.setLastModifyTime(DateUtils.getCurrentTime());
		favoritesRepository.save(favorites);
		return favorites;
	}

	public void countFavorites(long id){
		Favorites favorite=favoritesRepository.findById(id);
		Long count=collectRepository.countByFavoritesIdAndIsDelete(id, IsDelete.NO);
		favorite.setCount(count);
		Long pubCount=collectRepository.countByFavoritesIdAndTypeAndIsDelete(id, CollectType.PUBLIC, IsDelete.NO);
		favorite.setPublicCount(pubCount);
		favorite.setLastModifyTime(DateUtils.getCurrentTime());
		favoritesRepository.save(favorite);

	}
}
