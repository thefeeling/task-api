package me.daniel.taskapi.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class KakaoBookSearchDto {
    @Getter
    @Setter
    @ToString
    public static class SearchReq {
        private String query;
        private Integer page;
        private Integer size;
        private String sort;

        public static SearchReq of(BookDto.SearchReq req) {
            SearchReq kakaoReqDto = new SearchReq();
            kakaoReqDto.query = req.getQuery();
            kakaoReqDto.page = req.getPage() + 1;
            kakaoReqDto.size = req.getSize();
            kakaoReqDto.sort = req.getSort();
            return kakaoReqDto;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SearchRes {
        @JsonIgnore
        private boolean success = true;
        private List<Document> documents;
        private Meta meta;

        @Builder
        public SearchRes(boolean success, List<Document> documents, Meta meta) {
            this.success = success;
            this.documents = documents;
            this.meta = meta;
        }

        private BookDto.Book convertItem(Document document) {
            return BookDto.Book
                    .builder()
                    .isbn(document.isbn)
                    .title(document.title)
                    .price(document.price)
                    .salePrice(document.salePrice)
                    .description(document.contents)
                    .publishedAt(document.datetime)
                    .publisher(document.publisher)
                    .author(document.authors)
                    .link(document.url)
                    .image(document.thumbnail)
                    .provider(BookProvider.KAKAO)
                    .build();
        }

        public Page<BookDto.Book> toPageResult(int page, int size) {
            List<BookDto.Book> list = this.documents
                    .stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
            Pageable pageInfo = PageRequest.of(page, size);
            return new PageImpl<>(list, pageInfo, meta.total_count);
        }


    }

    @Getter
    public static class Meta {
        private Boolean is_end;
        private Integer pageable_count;
        private Integer total_count;
    }

    @Getter
    public static class Document {
        private String url;
        private List<String> translators;
        private String title;
        private String thumbnail;
        private String status;
        private int price;
        @JsonProperty("sale_price")
        private int salePrice;
        private String publisher;
        private String isbn;
        private String datetime;
        private String contents;
        private List<String> authors;
    }


}





