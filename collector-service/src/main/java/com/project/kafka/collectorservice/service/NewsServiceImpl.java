package com.project.kafka.collectorservice.service;


import com.project.kafka.collectorservice.model.News;
import com.project.kafka.collectorservice.repository.NewsRepository;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public News createNews(News news) {
        return newsRepository.save(news);
    }

}
