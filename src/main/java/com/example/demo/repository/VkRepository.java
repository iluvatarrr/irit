package com.example.demo.repository;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.groups.Group;
import com.vk.api.sdk.objects.users.Fields;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Repository
//@PropertySource("classpath:static/.properties")
public class VkRepository {
//    @Value("${USER_ID}")
    private Integer USER_ID;
//    @Value("${TOKEN}")
    private String TOKEN;
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

    @SneakyThrows
    public List<Group> getGroupByName(String groupName) {
        return vk.groups().search(actor, groupName).count(1).execute().getItems();
    }

    @SneakyThrows
    public List<UserFull> getUserByNameAndGroup(String name, Integer subId) {
        return vk.users().search(actor)
                .q(name)
                .fields(Fields.BDATE)
                .groupId(subId)
                .execute().getItems();
    }
}