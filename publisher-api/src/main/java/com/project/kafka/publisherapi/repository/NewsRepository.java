package com.project.kafka.publisherapi.repository;

import com.project.kafka.publisherapi.model.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsRepository extends ElasticsearchRepository<News, String> {
}
