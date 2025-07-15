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


    public static User create(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String createdDate, String lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
        return new User(name, email, username, password, address, userRoleEnum, createdDate, lastModifiedDate, userTypeNameEnum);
    }

    public static User create(Long id, String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String createdDate, String lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
        User user = new User(name, email, username, password, address, userRoleEnum, createdDate, lastModifiedDate, userTypeNameEnum);
        user.id = id;
        return user;
    }

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

}
