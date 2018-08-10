package com.lhl.onlinelearn.onlinelearn.service;

import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import com.lhl.onlinelearn.onlinelearn.entity.Favorites;

public interface FavoritesService {
	Favorites saveFavorites(Collect collect);

	void countFavorites(long id);
}
