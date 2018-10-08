/*
 * this is techne.hk company
 * Copyright (c) 2018-2018 techne All Rights Reserved.
 * FileName: StoreEsController.java
 * @author: maomao993@qq.com
 * @date: 18年10月8日 下午4:49
 * @version: 1.0
 */

package cn.hk.techne.service.es.controller;

import cn.hk.techne.service.es.model.StoreVO;
import cn.hk.techne.service.es.service.StoreRepository;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class StoreEsController {
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @GetMapping("/es/store/add")
    public Object add() {
        double lat = 39.929986;
        double lon = 116.395645;

        List<StoreVO> personList = new ArrayList<>(900000);
        for (int i = 100000; i < 1000000; i++) {
            double max = 0.00001;
            double min = 0.000001;
            Random random = new Random();
            double s = random.nextDouble() % (max - min + 1) + max;
            DecimalFormat df = new DecimalFormat("######0.000000");
            // System.out.println(s);
            String lons = df.format(s + lon);
            String lats = df.format(s + lat);
            Double dlon = Double.valueOf(lons);
            Double dlat = Double.valueOf(lats);

            StoreVO store = new StoreVO();

            store.setId(i);
            store.setName("商店名字" + i);
            store.setAddress(dlat + "," + dlon);
            storeRepository.save(store);

        }


       SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("商店名字")).build();
        List<StoreVO> articles = elasticsearchTemplate.queryForList(searchQuery, StoreVO.class);
        for (StoreVO store : articles) {
            System.out.println(store.toString());
        }

        return "ok";
    }

    @GetMapping("/es/store/list")
    public Object query() {
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.queryStringQuery("商店名字")).build();
        List<StoreVO> articles = elasticsearchTemplate.queryForList(searchQuery, StoreVO.class);
        return articles;
    }

    @GetMapping("/es/store/query")
    public Object query( @RequestParam(value = "pageNo", required = false,defaultValue = "1") Integer pageNo,
                         @RequestParam(value = "PageSize", required = false,defaultValue = "10") Integer PageSize) {
        double lat = 39.929986;
        double lon = 116.395645;

        Long nowTime = System.currentTimeMillis();
        //查询某经纬度100米范围内
        GeoDistanceQueryBuilder builder = QueryBuilders.geoDistanceQuery("address").point(lat, lon)
                .distance(100, DistanceUnit.METERS);

        GeoDistanceSortBuilder sortBuilder = SortBuilders.geoDistanceSort("address",lat,lon)
                .point(lat, lon)
                .unit(DistanceUnit.METERS)
                .order(SortOrder.ASC);

        Pageable pageable = new PageRequest(pageNo, PageSize);

        NativeSearchQueryBuilder builder1 = new NativeSearchQueryBuilder().withFilter(builder).withPageable(pageable).withSort(sortBuilder);
        SearchQuery searchQuery = builder1.build();

        //queryForList默认是分页，走的是queryForPage，默认10个
        List<StoreVO> personList = elasticsearchTemplate.queryForList(searchQuery, StoreVO.class);

        System.out.println("耗时：" + (System.currentTimeMillis() - nowTime));
        return personList;
    }
}
