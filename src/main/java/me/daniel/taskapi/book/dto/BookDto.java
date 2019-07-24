package me.daniel.taskapi.book.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class BookDto {
    @Getter
    @Setter
    public static class SearchReq {
        @NotBlank(message = "검색어(`query`)는 필수 값이며 빈 문자열을 허용하지 않습니다.")
        private String query;
        @Min(value = 0, message = "페이지 번호의 최소 값은 `0`입니다.")
        private Integer page = 0;

        @Min(value = 1, message = "페이지 사이즈(`limit`)의 최소 값은 `1`입니다.")
        @Max(value = 100, message = "페이지 사이즈(`limit`)의 최대 값은 `100`입니다.")
        private Integer size = 10;
        private String sort = "accuracy";
    }

    @Getter
    public static class Book {
        private String isbn;
        private String title;
        private int price;
        private int salePrice;
        private String description;
        private String publishedAt;
        private String publisher;
        private List<String> author;
        private String link;
        private String image;
        private BookProvider provider;

        @Builder
        public Book(String isbn, String title, int price, int salePrice, String description, String publishedAt, String publisher, List<String> author, String link, String image, BookProvider provider) {
            this.isbn = isbn;
            this.title = title;
            this.price = price;
            this.salePrice = salePrice;
            this.description = description;
            this.publishedAt = publishedAt;
            this.publisher = publisher;
            this.author = author;
            this.link = link;
            this.image = image;
            this.provider = provider;
        }
    }


}
