package me.daniel.taskapi.search.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class TopKeywordDto {

    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    public static class Req implements Serializable {
        @Min(value = 1, message = "최소 조회 가능 키워드 수(`limit`)는 1입니다.")
        @Max(value = 10, message = "최대 조회 가능 키워드 수(`limit`)는 10입니다.")
        private int limit = 10;
    }

    @Getter
    @AllArgsConstructor(staticName = "of")
    // @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class Res implements Serializable {
        private LocalDateTime updatedAt;
        private List<TopKeywordResult> list;
    }

    @AllArgsConstructor
    @Getter
    public static class TopKeywordResult {
        private String keyword;
        private long count;
    }

}
