package com.example.demo.service;

import com.example.demo.entity.GroupEntity;
import com.example.demo.repository.GroupEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GroupEntityService {

    private final GroupEntityRepository groupEntityRepository;

    @Autowired
    public GroupEntityService(GroupEntityRepository groupEntityRepository) {
        this.groupEntityRepository = groupEntityRepository;
    }

    @Transactional
    public GroupEntity save(GroupEntity groupEntity) {
        return groupEntityRepository.save(groupEntity);
    }

    public GroupEntity findOrCreateGroup(String groupName) {
        return groupEntityRepository.findByTitle(groupName)
                .orElseGet(() -> {
                    GroupEntity group = new GroupEntity();
                    group.setTitle(groupName);
                    return save(group);
                });
    }
}
