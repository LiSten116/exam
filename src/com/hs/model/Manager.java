package com.hs.model;

public class Manager {
    private Integer id;

    private String username;

    private String password;

    private Byte modified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Byte getModified() {
        return modified;
    }

    public void setModified(Byte modified) {
        this.modified = modified;
    }
}