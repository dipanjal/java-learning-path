package com.example.restapi.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Collection<RoleEntity> roles = new HashSet<>();

    public UserEntity(String userName, String password, String name, String email, RoleEntity... roleEntityArgs) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roles.addAll(Set.of(roleEntityArgs));
    }

    public UserEntity(String userName, String password, String name, String email, Set<RoleEntity> roleEntitySet) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.email = email;
        this.roles.addAll(roleEntitySet);
    }
}
