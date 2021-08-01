package ru.maslov.boot.spring_boot_app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Entity

@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)

    private String name;

    @Column(name = "surname")

    private String surName;

    @Column(name = "age")

    private int age;

    @Column(name = "username")
    private String username;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "password")
    private String password;

    public User(String name, String surName, int age, String username, Set<Role> roles, String password) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.username = username;
        this.roles = roles;
        this.password = password;
    }




    public void takeRole(Role role) {
        roles.add(role);
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public User(int id, String name, String surName, int age, String username, String passWord) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.username = username;
        this.password = passWord;
    }

    public User() {
    }

    public User(int id, String name, String surName, int age, String username, Set<Role> roles, String passWord) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.username = username;
        this.roles = roles;
        this.password = passWord;
    }
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }


    public void setPassword(String passWord) {
        this.password = passWord;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
