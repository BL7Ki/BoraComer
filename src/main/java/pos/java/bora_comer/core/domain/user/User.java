package pos.java.bora_comer.core.domain.user;

import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import java.time.LocalDateTime;

public class User {

    private Long id;
    private final String name;
    private final String email;
    private final String username;
    private String password;
    private Address address;
    private final UserRoleEnum userRoleEnum;
    private final LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private final UserTypeNameEnum userTypeNameEnum;


    public static User create(String name, String email, String username, String password, UserRoleEnum userRoleEnum, UserTypeNameEnum userTypeNameEnum) {

        Address defaultAddress = Address.create("", "", "", "", "");
        LocalDateTime now = LocalDateTime.now();

        return new User(
                name,
                email,
                username,
                password,
                defaultAddress,
                userRoleEnum,
                now,
                now,
                userTypeNameEnum
        );
    }

    public static User reconstruct(
            Long id,
            String name,
            String email,
            String username,
            String password,
            Address address,
            UserRoleEnum userRoleEnum,
            LocalDateTime createdDate,
            LocalDateTime lastModifiedDate,
            UserTypeNameEnum userTypeNameEnum) {

        User user = new User(name, email, username, password, address, userRoleEnum, createdDate, lastModifiedDate, userTypeNameEnum);
        user.id = id;
        return user;
    }

    private User(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, LocalDateTime createdDate, LocalDateTime lastModifiedDate, UserTypeNameEnum userTypeNameEnum) {
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

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public Address getAddress() { return address; }
    public UserRoleEnum getUserRoleEnum() { return userRoleEnum; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate; }
    public UserTypeNameEnum getUserTypeNameEnum() { return userTypeNameEnum; }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
        this.lastModifiedDate = LocalDateTime.now();
    }

    public void updateAddress(Address newAddress) {
        this.address = newAddress;
        this.lastModifiedDate = LocalDateTime.now();
    }
}