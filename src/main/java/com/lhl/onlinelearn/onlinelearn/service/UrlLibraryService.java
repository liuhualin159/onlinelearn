package com.lhl.onlinelearn.onlinelearn.service;

import com.lhl.onlinelearn.onlinelearn.repository.UrlLibraryRepository;
import com.lhl.onlinelearn.onlinelearn.entity.UrlLibrary;
import com.lhl.onlinelearn.onlinelearn.utils.HtmlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UrlLibraryService {

    private Map<String,String> maps = new HashMap<String, String>();
    @Autowired
    private UrlLibraryRepository urlLibraryRepository;


    public String getMap(String key){
        if(maps.isEmpty()){
            List<UrlLibrary> collectLibrarieList = urlLibraryRepository.findAll();
            for(UrlLibrary urlLibrary : collectLibrarieList){
                maps.put(urlLibrary.getUrl(), urlLibrary.getLogoUrl());
            }
        }
        if(null == maps.get(key)){
            this.addMaps(key);
        }
        return maps.get(key);
    }

    public void addMaps(String key){
        if(key.contains("?")){
            key=key.substring(0,key.indexOf("?"));
        }
        String logoUrl = HtmlUtil.getImge(key);
        maps.put(key,logoUrl);
        UrlLibrary urlLibrary = new UrlLibrary();
        urlLibrary.setUrl(key);
        urlLibrary.setLogoUrl(logoUrl);
        urlLibraryRepository.save(urlLibrary);
    }
}
