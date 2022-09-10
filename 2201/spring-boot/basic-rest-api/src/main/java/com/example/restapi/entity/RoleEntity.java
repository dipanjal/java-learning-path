package com.example.restapi.entity;

import com.example.restapi.enums.UserRoleEnum;
import java.util.Collection;
import java.util.HashSet;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Collection<UserEntity> users = new HashSet<>();

    public RoleEntity(UserRoleEnum userRoleEnum) {
        this.id = userRoleEnum.getId();
        this.name = userRoleEnum.name();
    }
}
