/*
 * this is techne.hk company
 * Copyright (c) 2018-2018 techne All Rights Reserved.
 * FileName: StoreVO.java
 * @author: maomao993@qq.com
 * @date: 18年10月8日 下午4:46
 * @version: 1.0
 */

package cn.hk.techne.service.es.model;import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;

/**
 * model类
 */
@Document(indexName="alfred_store",type="store",indexStoreType="fs",shards=5,replicas=1,refreshInterval="-1")
public class StoreVO implements Serializable {
    @Id
    private int id;

    private String name;

    /**
     * 地理位置经纬度
     * lat纬度，lon经度 "40.715,-74.011"
     * 如果用数组则相反[-73.983, 40.719]
     */
    @GeoPointField
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
