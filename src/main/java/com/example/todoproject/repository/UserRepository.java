package com.example.todoproject.repository;

public interface UserRepository {
    long addUser(String userName, String password);

    String findPassword(long userId);

    int updateUserName(long userId, String userName);
}
