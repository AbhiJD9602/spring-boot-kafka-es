package com.project.kafka.newsclient.client;


import com.project.kafka.newsclient.client.dto.MyPage;
import com.project.kafka.newsclient.client.dto.News;
import com.project.kafka.newsclient.client.dto.SearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("publisher-api")
public interface PublisherApiClient {

    @GetMapping("/api/news")
    MyPage<News> listNewsByPage(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
                                @RequestParam("sort") String sort);     //feign client requires parameter names in @RequireParams

    @PutMapping("/api/news/search")
    MyPage<News> searchNewsByPage(@RequestBody SearchDto searchDto, @RequestParam("page") Integer page,
                                  @RequestParam("size") Integer size, @RequestParam("sort") String sort); //feign client requires parameter names in @RequireParams

}
