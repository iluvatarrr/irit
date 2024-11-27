package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name="theme")
@ToString(exclude = "studentEntity")
public class ThemeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String title;

    @OneToOne(mappedBy = "themeEntity", cascade = CascadeType.ALL)
    ItemDataEntity fullScoreByPerson;

    @OneToOne(mappedBy = "themeEntity", cascade = CascadeType.ALL)
    ItemDataTaskEntity fullScoreByItem;

    @JsonIgnore
    @OneToMany(mappedBy = "themeEntity", cascade = CascadeType.ALL)
    List<TaskEntity> tasks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    StudentEntity studentEntity;
}