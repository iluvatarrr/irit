package com.example.demo.repository;

import com.example.demo.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupEntityRepository extends JpaRepository<GroupEntity, Integer> {
    Optional<GroupEntity> findByTitle(String name);
}
