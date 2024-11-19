package com.example.demo.repository;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.University;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

public class VkRepository {

    private final int USER_ID;
    private final String TOKEN;
    private final VkApiClient vk;
    private final UserActor actor;

    public VkRepository() {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        USER_ID = getUserId();
        TOKEN = getToken();
        actor = new UserActor(USER_ID,TOKEN);
    }

    @SneakyThrows
    private String getToken() {
        return Files.readString(Paths.get("src/main/resources/static/token.txt"));
    }

    @SneakyThrows
    private int getUserId() {
        return Integer.parseInt(Files.readString(Paths.get("src/main/resources/static/appId.txt")));

    }
    public List<UserFull> getUserWithName(String name, Fields... fields) throws ClientException, ApiException {
        return vk.users().search(actor).q(name).fields(fields).execute().getItems();
    }

    public Optional<UserFull> getUserByUniversityName(List<UserFull> users, String universityName) {
        return users.stream().filter(u -> u.getUniversities() != null && u.getUniversities().stream().map(University::getName).toList().contains(universityName)).findAny();
    }

    @SneakyThrows
    public Optional<UserFull> getUserByGroupName(List<UserFull> users, Group group)  {
        return users.stream().filter(u ->  group != null && (isMemberGroup(group, u)) == 1).findFirst();
    }

    @SneakyThrows
    public Group getUserByMainGroup(String groupName) {
        return vk.groups().search(actor, groupName).count(1).execute().getItems().getFirst();
    }

    @SneakyThrows
    public Integer isMemberGroup(Group group, UserFull user) {
       return Integer.parseInt(vk.groups().isMember(actor, group.getId().toString())
                .userId(user.getId())
                .execute().getValue());
    }
}