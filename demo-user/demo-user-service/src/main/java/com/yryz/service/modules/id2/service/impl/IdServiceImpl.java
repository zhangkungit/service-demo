package com.yryz.service.modules.id2.service.impl;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.distributed.lock.DistributedLockUtils;
import com.yryz.common.exception.QsourceException;;
import com.yryz.service.modules.id2.entity.CodeModel;
import com.yryz.service.modules.id2.dao.persistence.IdDao;
import com.yryz.service.modules.id2.service.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/8/14
 * @description
 */
@Service("idService2")
public class IdServiceImpl implements IdService {
    private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);

    private static final int DEFAULT_CODE_LENGTH = 5;
    private static final String ID_LOCK_NAME = "idGenerator";

    @Autowired
    private IdDao idDao;

//    @Autowired
//    private IdGenerator idGenerator;


    @Transactional
    @Override
    public Long getId(String type) {
        Long result = null;
        String lock = null;
        long start = System.currentTimeMillis();
        try {
            lock = DistributedLockUtils.lock(ID_LOCK_NAME, type);
            CodeModel codeModel = idDao.selectByType(type);
            if (codeModel != null) {
                // 已经配置，获取新的code
                long current = codeModel.getCurrent();
                current++;
                result = current;
                int flag = idDao.updateByType(type, current);
                //logger.info("updateByType from db:{}", flag);
            } else {
                logger.info("generate id for new type: {}", type);
                CodeModel model = buildNewCodeModel(type);
                result = model.getCurrent();
                int flag = idDao.insertCodeModel(model);
                //logger.info("insertCodeModel from db:{}", flag);
            }
        } catch (Exception e) {
            logger.error("getId error", e);
            throw new QsourceException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getMsg(),
                    "getId error for type: " + type);
        } finally {
            DistributedLockUtils.unlock(ID_LOCK_NAME, lock);
        }
        logger.info("getId cost time: {}", System.currentTimeMillis() - start);
        return result;
    }

    /**
     * 如果type为未指定类型，则默认发号范围为5位
     * @param type
     * @return
     */
    private CodeModel buildNewCodeModel(String type) {
        CodeModel model = new CodeModel();
        model.setType(type);
        model.setCodeLength(DEFAULT_CODE_LENGTH);
        model.setCurrent(getSetInitial(DEFAULT_CODE_LENGTH));
        Date now = new Date();
        model.setCreateDate(now);
        model.setLastUpdateDate(now);
        return model;
    }

    private Long getInitial() {
        return (long)(Math.random() * 10000 + 10000);
    }

    @Override
    public String getId() {
        return null;
    }

    private CodeModel buildInitialCodeModel(String type, Integer length) {
        CodeModel model = new CodeModel();
        model.setType(type);
        model.setCodeLength(length);
        //新配置发号开始值
        model.setCurrent(getSetInitial(length));
        Date now = new Date();
        model.setCreateDate(now);
        model.setLastUpdateDate(now);
        return model;
    }

    private long getSetInitial(int index) {
        int pow = (int) Math.pow(10, index - 1);
        return (long) (Math.random() * pow + pow);
    }

}
