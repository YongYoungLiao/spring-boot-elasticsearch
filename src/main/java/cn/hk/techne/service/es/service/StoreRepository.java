/*
 * this is techne.hk company
 * Copyright (c) 2018-2018 techne All Rights Reserved.
 * FileName: StoreRepository.java
 * @author: maomao993@qq.com
 * @date: 18年10月8日 下午4:46
 * @version: 1.0
 */

package cn.hk.techne.service.es.service;

import cn.hk.techne.service.es.model.StoreVO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface StoreRepository extends ElasticsearchRepository<StoreVO,Integer> {

}
