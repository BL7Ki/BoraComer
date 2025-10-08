package pos.java.bora_comer.infra.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.domain.user.Address;
import pos.java.bora_comer.core.usercase.user.CreateUserUseCase;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;
import pos.java.bora_comer.core.usercase.user.UpdateUserUseCase;
import pos.java.bora_comer.core.usercase.user.DeleteUserUseCase;
import pos.java.bora_comer.infra.graphql.dto.PageResponse;
import pos.java.bora_comer.infra.graphql.input.user.CreateUserInput;
import pos.java.bora_comer.infra.graphql.input.user.UpdateUserInput;
import pos.java.bora_comer.infra.graphql.input.user.AddressInput;

@Controller
public class UserResolver {

    private final CreateUserUseCase createUseCase;
    private final SearchUserUseCase searchUseCase;
    private final UpdateUserUseCase updateUseCase;
    private final DeleteUserUseCase deleteUseCase;

    public UserResolver(CreateUserUseCase createUseCase,
                        SearchUserUseCase searchUseCase,
                        UpdateUserUseCase updateUseCase,
                        DeleteUserUseCase deleteUseCase) {
        this.createUseCase = createUseCase;
        this.searchUseCase = searchUseCase;
        this.updateUseCase = updateUseCase;
        this.deleteUseCase = deleteUseCase;
    }

    // --- QUERIES ---

    @QueryMapping
    public User user(@Argument Long id) {
        return searchUseCase.findById(id);
    }

    @QueryMapping
    public PageResponse<User> users(@Argument int page, @Argument int size) {
        return PageResponse.fromPage(searchUseCase.findAll(page, size));
    }

    // --- MUTATIONS ---

    @MutationMapping
    public User createUser(@Argument CreateUserInput input) {
        User newUser = mapInputToUser(input);
        return createUseCase.execute(newUser);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UpdateUserInput input) {
        User existingUser = searchUseCase.findById(id);
        User updatedUser = applyUpdateInput(existingUser, input);
        return updateUseCase.execute(updatedUser);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        deleteUseCase.execute(id);
        return true;
    }

    @MutationMapping
    public User changePassword(@Argument Long userId,
                               @Argument String currentPassword,
                               @Argument String newPassword) {
        updateUseCase.changeUserPassword(userId, currentPassword, newPassword);
        // Busca o usuário novamente para retornar o objeto atualizado
        return searchUseCase.findById(userId);
    }

    @MutationMapping
    public User associateUserType(@Argument Long userId, @Argument Long userTypeId) {
        return updateUseCase.userAssociate(userId, userTypeId);
    }

    // --- LÓGICA DE MAPEAMENTO INTERNA ---

    //Converte o CreateUserInput em um objeto de domínio User.
    private User mapInputToUser(CreateUserInput input) {
        Address domainAddress = mapAddressInputToDomain(input.address());

        return User.create(
                input.name(),
                input.email(),
                input.username(),
                input.password(),
                domainAddress,
                input.userRoleEnum(),
                input.userTypeNameEnum()
                // createdDate e lastModifiedDate são gerados pelo sistema
        );
    }

    //Aplica os campos não nulos de UpdateUserInput ao objeto User existente.
    private User applyUpdateInput(User existingUser, UpdateUserInput input) {

        // O UseCase de Update irá persistir e retornar este objeto.
        return existingUser.toBuilder()
                .name(input.name() != null ? input.name() : existingUser.getName())
                .email(input.email() != null ? input.email() : existingUser.getEmail())
                .address(input.address() != null ? mapAddressInputToDomain(input.address()) : existingUser.getAddress())
                .build();
    }

    // Converte o AddressInput em um objeto de domínio Address.
    private Address mapAddressInputToDomain(AddressInput input) {
        return Address.create(
                input.street(),
                input.neighborhood(),
                input.city(),
                input.state(),
                input.zipCode()
        );
    }
}
