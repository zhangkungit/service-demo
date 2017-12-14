
package com.yryz.service.modules.id.dao.persistence;

import com.yryz.service.modules.id.entity.PrimaryKey;
import org.springframework.stereotype.Repository;

/**
 * @author
 * @version 1.0
 * @date 2017年7月11日 下午4:49:28
 * @Description id生成
 */
@Repository
public interface PrimarykeyPersistenceDao {
	/**
	 * 
	 * 获取id
	 * @param primaryKeyName
	 * @return
	 */
	PrimaryKey getPrimaryKey(String primaryKeyName);
	
	/**
	 * 
	 * 更新id生成器
	 * @param primaryKey
	 * @return
	 */
	Integer updatePrimaryKey(PrimaryKey primaryKey);

	/**
	 * 新生发号类型
	 * @param primaryKey
	 */
	void insertPrimaryKey(PrimaryKey primaryKey);
}
