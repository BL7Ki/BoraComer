package pos.java.bora_comer.infra.persistence.repository.userType.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import java.util.Objects;

@Entity
@Table(name = "tb_tipo_usuarios")
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, unique = true)
    private UserTypeNameEntityEnum name;


    public UserTypeEntity() {
    }

    private UserTypeEntity(UserTypeNameEntityEnum name) {
        this.name = name;
    }

    public static UserTypeEntity create(UserTypeNameEntityEnum name) {
        return new UserTypeEntity(
             name
        );
    }

    public Long getId() {
        return id;
    }

    public UserTypeNameEntityEnum getName() {
        return name;
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
