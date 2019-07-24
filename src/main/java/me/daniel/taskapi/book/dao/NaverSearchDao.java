package me.daniel.taskapi.book.dao;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.book.dto.NaverBookSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;


import static me.daniel.taskapi.book.dto.NaverBookSearchDto.SearchReq;

@Component
@FeignClient(
    name = "naver-api",
    url = "${app.api.naver.host}",
    configuration = NaverSearchDao.Config.class,
    fallback = NaverSearchDao.Fallback.class
)
public interface NaverSearchDao extends BookSearchDao<SearchReq, NaverBookSearchDto.SearchRes> {
    @Override
    @GetMapping("/v1/search/book.json")
    NaverBookSearchDto.SearchRes search(@SpringQueryMap SearchReq req);

    class Config {
        private static final String HEADER_X_NAVER_CLIENT_ID = "X-Naver-Client-Id";
        private static final String HEADER_X_NAVER_CLIENT_SECRET = "X-Naver-Client-Secret";

        @Value(value = "${app.api.naver.clientId}")
        private String clientId;

        @Value(value = "${app.api.naver.clientSecret}")
        private String clientSecret;

        @Bean
        RequestInterceptor requestInterceptor() {
            return template -> {
                template.header(HEADER_X_NAVER_CLIENT_ID, clientId);
                template.header(HEADER_X_NAVER_CLIENT_SECRET, clientSecret);
            };
        }
    }

    @Component
    @Slf4j
    class Fallback implements NaverSearchDao {

        @Override
        public NaverBookSearchDto.SearchRes search(SearchReq req) {
            log.error("naver request failure, throw Exception");
            return NaverBookSearchDto.SearchRes
                    .builder().success(false).build();

        }
    }

}




