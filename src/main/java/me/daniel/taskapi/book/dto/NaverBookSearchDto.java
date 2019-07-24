package me.daniel.taskapi.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.util.HtmlUtils;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class NaverBookSearchDto {


    /**
     * [KEY]    [TYPE]  [필수여부] [기본값]                 [설명]
     * query	string	 -	       -	                    검색을 원하는 문자열로서 UTF-8로 인코딩한다. 상세검색시 생략가능
     * display	integer	 N	       10(기본값), 100(최대)	검색 결과 출력 건수 지정
     * start	integer	 N	       1(기본값), 1000(최대)	검색 시작 위치로 최대 1000까지 가능
     * sort	    string	 N	       sim(기본값), date	    정렬 옵션: sim(유사도순), date(출간일순), count(판매량순)
     */
    @Getter
    @Setter
    public static class SearchReq {
        private String query;
        private int start = 1;
        private int display = 10;
        private String sort = null;

        public static SearchReq of(String query, int start, int display, String sort) {
            SearchReq req = new SearchReq();
            req.query = query;
            req.start = start;
            req.display = display;
            req.sort = sort;
            return req;
        }

        public static SearchReq of(BookDto.SearchReq searchReq) {
            SearchReq req = new SearchReq();
            Integer page = searchReq.getPage();
            req.query = searchReq.getQuery();
            req.start = (page - 1 < 0 ? 0 : page) * searchReq.getSize() + 1;
            req.display = searchReq.getSize();
            return req;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchRes {
        @JsonIgnore
        @Setter
        private boolean success = true;

        private List<Item> items;
        private int display;
        private int start;
        private int total;
        private String lastBuildDate;

        @Builder
        public SearchRes(boolean success, List<Item> items, int display, int start, int total, String lastBuildDate) {
            this.success = success;
            this.items = items;
            this.display = display;
            this.start = start;
            this.total = total;
            this.lastBuildDate = lastBuildDate;
        }

        private BookDto.Book convertItem(Item item) {
            Pattern regexForRemovingTag = Pattern.compile("<[^>]*>");
            return BookDto.Book
                    .builder()
                    .isbn(item.isbn)
                    // .title(item.title.replaceAll(regex, ""))
                    .title(
                        regexForRemovingTag.matcher(item.title).replaceAll("")
                    )
                    .price(item.price)
                    .salePrice(item.salePrice)
                    .description(
                        regexForRemovingTag.matcher(item.description).replaceAll("")
                    )
                    .publishedAt(item.publishedAt)
                    .publisher(item.publisher)
                    .author(Collections.singletonList(item.author))
                    .link(item.link)
                    .image(item.image)
                    .provider(BookProvider.NAVER)
                    .build();
        }

        public Page<BookDto.Book> toPageResult(int page, int size) {
            List<BookDto.Book> list = this.items
                    .stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
            Pageable pageInfo = PageRequest.of(page, size);
            return new PageImpl<>(list, pageInfo, total);
        }
    }

    @Getter
    public static class Item {
        private String isbn;
        private String title;
        private int price;

        @JsonProperty("discount")
        private int salePrice;
        private String description;

        @JsonProperty("pubdate")
        private String publishedAt;
        private String publisher;
        private String author;
        private String link;
        private String image;
    }
}
