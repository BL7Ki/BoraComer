package pos.java.bora_comer.core.domain;

public class UserType {
    private Long id;
    private UserTypeNameEnum name;

    private UserType(Long id, UserTypeNameEnum name) {
        this.id = id;
        this.name = name;
    }

    public static UserType create(Long id, UserTypeNameEnum name) {
        return new UserType(id, name);
    }

    public static UserType create(UserTypeNameEnum name) {
        return new UserType(null, name);
    }

    public Long getId() {
        return id;
    }

    public UserTypeNameEnum getNome() {
        return name;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
