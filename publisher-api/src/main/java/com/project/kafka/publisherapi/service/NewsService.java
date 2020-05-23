package com.project.kafka.publisherapi.service;


import com.project.kafka.publisherapi.exception.NewsNotFoundException;
import com.project.kafka.publisherapi.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {

    News validateAndGetNewsById(String id) throws NewsNotFoundException, NewsNotFoundException;

    Page<News> listAllNewsByPage(Pageable pageable);

    Page<News> search(String text, Pageable pageable);

}
