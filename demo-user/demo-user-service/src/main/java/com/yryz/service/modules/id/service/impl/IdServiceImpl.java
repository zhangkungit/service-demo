package com.yryz.service.modules.id.service.impl;

import com.yryz.service.modules.id.common.PrimaryUtils;
import com.yryz.service.modules.id.service.IIdService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/8
 * @description
 */
@Service("idService")
public class IdServiceImpl implements IIdService {
    @Override
    public String getId(String type) {
        long id = PrimaryUtils.getNextId(type);
        String head = DateFormatUtils.format(new Date(), "yyyyMMdd");
        return head + id;
    }

    @Override
    public String getUserId() {
        return null;
    }
}
