package pos.java.bora_comer.infra.graphql.input.user;

import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;
import pos.java.bora_comer.core.domain.user.UserRoleEnum;

/**
 * DTO de entrada para a criação de um novo User.
 * Corresponde ao 'CreateUserInput' no schema GraphQL.
 */
public record CreateUserInput(
        String name,
        String email,
        String username,
        String password,
        AddressInput address,
        UserRoleEnum userRoleEnum,
        UserTypeNameEnum userTypeNameEnum
) {}