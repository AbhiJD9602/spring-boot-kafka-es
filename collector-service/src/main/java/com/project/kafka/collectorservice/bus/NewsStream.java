package com.project.kafka.collectorservice.bus;


import com.project.kafka.collectorservice.model.News;
import com.project.kafka.collectorservice.service.NewsService;
import com.project.kafka.commons.avro.NewsEvent;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableBinding(Processor.class)
public class NewsStream {

    private final NewsService newsService;
    private final MapperFacade mapperFacade;

    public NewsStream(NewsService newsService, MapperFacade mapperFacade) {
        this.newsService = newsService;
        this.mapperFacade = mapperFacade;
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public NewsEvent handleNewsEvent(@Payload NewsEvent newsEvent,
                                     @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                                     @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                                     @Header(KafkaHeaders.OFFSET) Long offset,
                                     @Header(IntegrationMessageHeaderAccessor.DELIVERY_ATTEMPT) Integer deliveryAttempt) {
        log.info("NewsEvent with id '{}' and title '{}' received from bus. topic: {}, partition: {}, offset: {}, deliveryAttempt: {}",
                newsEvent.getId(), newsEvent.getTitle(), topic, partition, offset, deliveryAttempt);

        News news = mapperFacade.map(newsEvent, News.class);
        news = newsService.createNews(news);
        log.info("News with id {} saved in Elasticsearch.", news.getId());

        return newsEvent;
    }

}
