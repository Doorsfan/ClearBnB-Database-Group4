package com.company.infrastructure;

import com.company.domain.User;
import com.google.gson.GsonBuilder;
import redis.clients.jedis.Jedis;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

public class UserCacheRepository {
    private final Jedis jedis; // the Redis connection (cache)
    private static final int ttl = 3600; // "Time to Live" for cache (in seconds)

    public UserCacheRepository(Jedis jedis) {
        this.jedis = jedis;
    }

    public User find(String path) {
        System.out.println("Searching cache for user");
        User user = new Gson().fromJson(jedis.get(path), User.class);

        if (user != null) {
            System.out.println("User found in cache");
        } else {
            System.out.println("User not found in cache");
        }

        return user;
    }

    public void add(String path, User user) {
        System.out.println("Adding user to cache");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        jedis.set(path, gson.toJson(user));
        jedis.expire(path, ttl);
    }

    public void remove(String path) {
        System.out.println("Removing user from cache");
        jedis.del(path);
    }
}
