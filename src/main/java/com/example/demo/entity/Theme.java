package com.example.demo.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class Theme {
    Integer id;
    String title;
    ItemData fullScoreByPerson;
    ItemData fullScoreByItem;
    List<Task> tasks;

    @Override
    public String toString() {
        return "Тема: %s.\nМаксимальные баллы за эту тему: %s\nЗадания:\n%s".formatted(title, fullScoreByItem, tasks.stream().map(a -> a.toString() + "\n").collect(Collectors.joining()));
    }
}