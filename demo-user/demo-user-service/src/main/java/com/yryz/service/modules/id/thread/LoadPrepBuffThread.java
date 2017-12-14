package com.yryz.service.modules.id.thread;


import com.yryz.service.modules.id.entity.KeyInfo;
import com.yryz.service.modules.id.service.PrimaryKeyService;

public class LoadPrepBuffThread extends Thread {

	private PrimaryKeyService primaryKeyService;
	private final KeyInfo keyInfo;

	public LoadPrepBuffThread(PrimaryKeyService primaryKeyService,
			final KeyInfo keyInfo) {
		this.primaryKeyService = primaryKeyService;
		this.keyInfo = keyInfo;
	}

	@Override
	public void run() {
		primaryKeyService.loadPrepBuff(keyInfo);
	}
	
}
