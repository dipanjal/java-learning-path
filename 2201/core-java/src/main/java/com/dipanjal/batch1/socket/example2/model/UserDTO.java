package com.dipanjal.batch1.socket.example2.model;

public class UserDTO {

    private long id;
    private String name;
    private int age;

    public UserDTO(long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
