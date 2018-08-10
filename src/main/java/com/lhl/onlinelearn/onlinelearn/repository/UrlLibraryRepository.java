package com.lhl.onlinelearn.onlinelearn.repository;

import com.lhl.onlinelearn.onlinelearn.entity.UrlLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface UrlLibraryRepository extends JpaRepository<UrlLibrary, Long> {

    @Transactional
    @Modifying
    @Query("update UrlLibrary u set u.count=u.count+1 where u.id =:id ")
    int increaseCountById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update UrlLibrary u set u.logoUrl = ?2 where u.id = ?1")
    int updateLogoUrlById(Long id,String logoUrl);

}
