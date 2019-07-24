package me.daniel.taskapi.global.model.search;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
@ToString(of = {"value"})
public class Keyword {
    private String value;
}
