package com.wjh.controller;

import com.wjh.dao.NewsDao;
import com.wjh.entity.News;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    NewsDao newsDao;

    @RequestMapping("test")
    public Object test(){
        boolean index = elasticsearchTemplate.createIndex(News.class);
        return index;
    }

    @RequestMapping("testQuery")
    public Object testQuery(String id){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "上5"));
        Page<News> search = newsDao.search(queryBuilder.build());
        return search.getContent();
    }

    @RequestMapping("testInsert")
    public Object testInsert(){
        News news2 = new News(null,"线上5标题哦", "线上5内容哦","线上5标签哦",8l);
        List<News> list = new ArrayList<>();
        list.add(news2);
        Iterable<News> news3 = newsDao.saveAll(list);
        return news3;
    }

    @RequestMapping("testUpdate")
    public Object testUpdate(){
        News news1 = new News("wC0bRW8B1mEsPlfjDC5R","线上2标题哦", "线上22内容哦","线上2标签哦",5l);
        News save = newsDao.save(news1);
        return save;
    }

}
