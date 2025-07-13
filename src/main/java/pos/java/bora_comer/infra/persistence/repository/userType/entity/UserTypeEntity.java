package pos.java.bora_comer.infra.persistence.repository.userType.entity;

import jakarta.persistence.*;

import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;


import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_tipo_usuarios")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, unique = true)
    private UserTypeNameEntityEnum name;

    @OneToMany(mappedBy = "userTypeEntity")
    private Set<UserEntity> users = new HashSet<>();

    public UserTypeEntity() {
    }

    private UserTypeEntity(UserTypeNameEntityEnum name) {
        this.name = name;
    }

    private UserTypeEntity(Long id, UserTypeNameEntityEnum name) {
        this.id = id;
        this.name = name;
    }

    public static UserTypeEntity create(UserTypeNameEntityEnum name) {
        return new UserTypeEntity(
             name
        );
    }

    public static UserTypeEntity create(Long id, UserTypeNameEntityEnum name) {
        return new UserTypeEntity(
                id, name
        );
    }

    public Long getId() {
        return id;
    }

    public UserTypeNameEntityEnum getName() {
        return name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserTypeEntity that = (UserTypeEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
