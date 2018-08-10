package com.lhl.onlinelearn.onlinelearn.service;

import com.lhl.onlinelearn.onlinelearn.entity.Collect;

public interface CollectService {


	public void saveCollect(Collect collect);

	public void updateCollect(Collect newCollect);

	public boolean checkCollect(Collect collect);

	public void otherCollect(Collect collect);

}
