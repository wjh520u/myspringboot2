package com.wjh.dao;

import com.wjh.entity.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsDao  extends ElasticsearchRepository<News,String> {
}
