package com.lhl.onlinelearn.onlinelearn.repository;

import com.lhl.onlinelearn.onlinelearn.common.enums.CollectType;
import com.lhl.onlinelearn.onlinelearn.common.enums.IsDelete;
import com.lhl.onlinelearn.onlinelearn.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectRepository extends JpaRepository<Collect, Long> {

	Collect findByIdAndUserId(Long id, Long userId);

	@Transactional
    void deleteById(Long id);

	List<Collect> findByFavoritesIdAndUrlAndUserIdAndIsDelete(Long favoritesId, String url, Long userId, IsDelete isDelete);

	Collect findById(long  id);

	Long countByFavoritesIdAndIsDelete(Long favoritesId,IsDelete isDelete);

	Long countByFavoritesIdAndTypeAndIsDelete(Long favoritesId, CollectType type, IsDelete isDelete);

	List<Collect> findByUserIdAndIsDelete(Long userId, IsDelete isDelete);

}