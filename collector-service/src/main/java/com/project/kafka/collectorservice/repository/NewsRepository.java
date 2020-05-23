package com.project.kafka.collectorservice.repository;

import com.project.kafka.collectorservice.model.News;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface NewsRepository extends ElasticsearchRepository<News, String> {
}
