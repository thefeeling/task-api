package me.daniel.taskapi.book.dao;

import feign.RequestInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.daniel.taskapi.book.dto.KakaoBookSearchDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import static me.daniel.taskapi.book.dto.KakaoBookSearchDto.SearchReq;

@Component
@FeignClient(
    name = "kakao-api",
    url = "${app.api.kakao.host}",
    configuration = KakaoSearchDao.Config.class,
    fallback = KakaoSearchDao.Fallback.class
)
public interface KakaoSearchDao extends BookSearchDao<SearchReq, KakaoBookSearchDto.SearchRes> {
    @Override
    @GetMapping("/v3/search/book")
    KakaoBookSearchDto.SearchRes search(@SpringQueryMap KakaoBookSearchDto.SearchReq req);

    class Config {
        static final String HEADER_SECRET_KEY = "Authorization";

        @Value(value = "${app.api.kakao.apikey}")
        private String apiKey;

        @Bean
        RequestInterceptor requestInterceptor() {
            return template -> template.header(HEADER_SECRET_KEY, "KakaoAK " + apiKey);
        }
    }

    @Component
    @RequiredArgsConstructor
    @Slf4j
    class Fallback implements KakaoSearchDao {
        private final NaverSearchDao naverSearchApiService;

        /**
         * TODO: NAVER/KAKAO 타입정보 매핑 진행 안됨.
         * @param req
         * @return
         */
        @Override
        public KakaoBookSearchDto.SearchRes search(SearchReq req) {
            log.error("kakao request failure, init fallback {}", req);
            return KakaoBookSearchDto.SearchRes.builder().success(false).build();
        }
    }
}




