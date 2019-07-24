package me.daniel.taskapi.book.api;


import lombok.RequiredArgsConstructor;
import me.daniel.taskapi.book.application.BookSearchAdapterService;
import me.daniel.taskapi.book.dto.BookDto;
import me.daniel.taskapi.global.auth.AuthHelper;
import me.daniel.taskapi.global.auth.Authorization;
import me.daniel.taskapi.global.auth.TokenPayload;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookSearchAdapterService bookSearchAdapterService;

    @Authorization
    @GetMapping
    public ResponseEntity<Page<BookDto.Book>> search(
        @Valid BookDto.SearchReq searchDto
    ) {
        TokenPayload payload = AuthHelper.getCurrentTokenPayload();
        return new ResponseEntity<>(
            bookSearchAdapterService.doSearch(payload, searchDto),
            HttpStatus.OK
        );
    }
}
