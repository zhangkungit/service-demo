package com.yryz.service.modules.id.provider;

import com.yryz.service.modules.id.api.IdAPI;
import com.yryz.service.modules.id.service.IIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/8
 * @description
 */
@Service("idAPI")
public class IdAPIImpl implements IdAPI {
    @Autowired
    @Qualifier("idService")
    private IIdService idService;

    @Override
    public String getId(String type) {
        String orderId = idService.getId(type);
        return orderId;
    }

    @Override
    public String getUserId() {
        String userId = idService.getUserId();
        return userId;
    }
}
