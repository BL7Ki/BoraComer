package pos.java.bora_comer.core.domain;

public class User {

    private Long id;
    private final  String name;
    private final String email;
    private final String username;
    private final String password;
    private final Address address;
    private final UserRoleEnum userRoleEnum;
    private final String lastModifiedDate;


    public static User create(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String lastModifiedDate) {
        return new User(name, email, username, password, address, userRoleEnum, lastModifiedDate);
    }

    public static User create(Long id, String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String lastModifiedDate) {
        User user = new User(name, email, username, password, address, userRoleEnum, lastModifiedDate);
        user.id = id;
        return user;
    }

    private User(String name, String email, String username, String password, Address address, UserRoleEnum userRoleEnum, String lastModifiedDate) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.userRoleEnum = userRoleEnum;
        this.lastModifiedDate = lastModifiedDate;
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

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }
}
