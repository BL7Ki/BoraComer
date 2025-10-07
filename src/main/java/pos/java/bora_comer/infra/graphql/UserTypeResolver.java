package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import pos.java.bora_comer.core.domain.userType.UserType;
import pos.java.bora_comer.core.usercase.userType.CreateUserTypeUseCase;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;
import pos.java.bora_comer.infra.graphql.dto.PageResponse;
import pos.java.bora_comer.infra.graphql.input.userType.CreateUserTypeInput;

@Controller
public class UserTypeResolver {

    private final CreateUserTypeUseCase createUseCase;
    private final SearchUserTypeUseCase searchUseCase;

    public UserTypeResolver(CreateUserTypeUseCase createUseCase, SearchUserTypeUseCase searchUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
    }

    // --- QUERIES ---

    @QueryMapping
    public UserType userType(@Argument Long id) {
        return searchUseCase.findById(id);
    }

    @QueryMapping
    public PageResponse<UserType> userTypes(@Argument int page, @Argument int size) {
        return PageResponse.fromPage(searchUseCase.findAll(page, size));
    }

    // --- MUTATIONS ---

    @MutationMapping
    public UserType createUserType(@Argument CreateUserTypeInput input) {
        UserType newUserType = UserType.create(input.name());
        return createUseCase.execute(newUserType);
    }
}
