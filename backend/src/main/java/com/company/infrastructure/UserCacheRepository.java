package com.company.infrastructure;

import com.company.domain.User;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import com.google.gson.Gson;

public class UserCacheRepository {
    private Jedis jedis; // the Redis connection (cache)

    public UserCacheRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    public User findUser(String path) {
        System.out.println("Searching cache for user");
        User user = new Gson().fromJson(jedis.get(path), User.class);

        if (user != null) {
            System.out.println("User found in cache");
        } else {
            System.out.println("User not found in cache");
        }

        return user;
    }

    public void addUser(String path, User user) {
        System.out.println("Adding user to cache");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        jedis.set(path, gson.toJson(user));
    }
}
