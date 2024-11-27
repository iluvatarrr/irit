package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@Entity
@Table(name = "group_studing")
@ToString(exclude = "studentEntities")
public class GroupEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   Integer id;

   @Column
   String title;

   @JsonIgnore
   @Builder.Default
   @OneToMany(mappedBy = "groupEntity", orphanRemoval = true)
   List<StudentEntity> studentEntities = new ArrayList<>();
}
