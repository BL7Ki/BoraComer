package pos.java.bora_comer.infra.persistence.repository.user.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.infra.persistence.repository.userType.entity.UserTypeEntity;

@Entity
@Table(name = "tb_usuarios")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "login", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "senha", nullable = false, length = 10)
    private String password;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "data_alteracao")
    private LocalDateTime lastModifiedDate;

    @Embedded
    private AddressEntity address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRoleEntityEnum role;

    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id")
    private UserTypeEntity userTypeEntity;

    // Construtor padrão (necessário para o JPA)
    public UserEntity() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    public static UserEntity create(String name, String email, String username, String password, AddressEntity address, UserRoleEntityEnum role, Long userTypeId) {
        return new UserEntity(name, email, username, password, address, role, userTypeId);
    }

    public static UserEntity create(Long id, String name, String email, String username, String password, AddressEntity address, UserRoleEntityEnum role, Long userTypeId) {
        return new UserEntity(id, name, email, username, password, address, role, userTypeId);
    }

    // Construtor completo
    private UserEntity(String name, String email, String username, String password, AddressEntity address, UserRoleEntityEnum role, Long userTypeId) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdDate = LocalDateTime.now();
        if (userTypeId != null) {
            this.userTypeEntity = new UserTypeEntity();
            this.userTypeEntity.setId(userTypeId);
        } else {
            this.userTypeEntity = null;
        }
    }

    private UserEntity(Long id, String name, String email, String username, String password, AddressEntity address, UserRoleEntityEnum role, Long userTypeId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.role = role;
        this.createdDate = LocalDateTime.now();
        if (userTypeId != null) {
            this.userTypeEntity = new UserTypeEntity();
            this.userTypeEntity.setId(userTypeId);
        } else {
            this.userTypeEntity = null;
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public UserRoleEntityEnum getRole() {
        return role;
    }

    public UserTypeEntity getUserTypeEntity() {
        return userTypeEntity;
    }

    public void setUserTypeEntity(UserTypeEntity userTypeEntity) {
        this.userTypeEntity = userTypeEntity;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void upadatePassword(String password) {
        this.password = password;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void updateAddress(Address address) {
        this.address.setStreet(address.getStreet());
        this.address.setCity(address.getCity());
        this.address.setState(address.getState());
        this.address.setZipCode(address.getZipCode());
    }

    // Atualizar data de alteração
    public void updateLastModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

}

