package pos.java.bora_comer.core.domain.user;

import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;

public class User {

    private Long id;
    private final String name;
    private final String email;
    private final String username;
    private String password;
    private final Address address;
    private final UserRoleEnum userRoleEnum;
    private final String createdDate;
    private final String lastModifiedDate;
    private final UserTypeNameEnum userTypeNameEnum;


    // MÉTODO DE FÁBRICA EXISTENTE (1/2) - USADO PELA PERSISTÊNCIA
    public static User create(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String createdDate, String lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
        return new User(name, email, username, password, address, userRoleEnum, createdDate, lastModifiedDate, userTypeNameEnum);
    }

    // MÉTODO DE FÁBRICA EXISTENTE (2/2) - USADO PELA PERSISTÊNCIA E ID
    public static User create(Long id, String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String createdDate, String lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
        User user = new User(name, email, username, password, address, userRoleEnum, createdDate, lastModifiedDate, userTypeNameEnum);
        user.id = id;
        return user;
    }

    // MÉTODO DE FÁBRICA PARA CRIAÇÃO VIA GraphQL (7 ARGUMENTOS)
    public static User create(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, UserTypeNameEnum userTypeNameEnum) {
        // Passa null para createdDate e lastModifiedDate, mantendo a assinatura original
        return new User(name, email, username, password, address, userRoleEnum, null, null, userTypeNameEnum);
    }

    // CONSTRUTOR PRIVADO
    private User(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String createdDate, String lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.userRoleEnum = userRoleEnum;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.userTypeNameEnum = userTypeNameEnum;
    }

    // --- GETTERS EXISTENTES ---
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

    public Address getAddress() {
        return address;
    }

    public Long getId() {
        return id;
    }

    public UserRoleEnum getUserRoleEnum() {
        return userRoleEnum;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public UserTypeNameEnum getUserTypeNameEnum() {
        return userTypeNameEnum;
    }

    public void updatePassward(String newPassword) {
        this.password = newPassword;
    }

    // Método toBuilder para o Resolver de Update
    public UserBuilder toBuilder() {
        return new UserBuilder(this);
    }

    // Classe Builder para suportar a atualização imutável no Resolver
    public static class UserBuilder {
        private final User user;
        private String name;
        private String email;
        private Address address;

        public UserBuilder(User user) {
            this.user = user;
            this.name = user.name;
            this.email = user.email;
            this.address = user.address;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder address(Address address) {
            this.address = address;
            return this;
        }

        public User build() {
            return User.create(
                    user.id,
                    this.name,
                    this.email,
                    user.username,
                    user.password,
                    this.address,
                    user.userRoleEnum,
                    user.createdDate,
                    user.lastModifiedDate,
                    user.userTypeNameEnum
            );
        }
    }
}
