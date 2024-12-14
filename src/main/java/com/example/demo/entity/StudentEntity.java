package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name = "student")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    String name;

    @Column
    Date birthday;

    @ManyToOne
    @JoinColumn(name = "group_id")
    GroupEntity groupEntity;

    @Column(name = "ulearn_id")
    String ulearn;

    @JsonIgnore
    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<ThemeEntity> themeList;
}