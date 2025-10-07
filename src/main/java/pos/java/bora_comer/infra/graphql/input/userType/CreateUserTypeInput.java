package pos.java.bora_comer.infra.graphql.input.userType;

import pos.java.bora_comer.core.domain.userType.UserTypeNameEnum;

public record CreateUserTypeInput(
        UserTypeNameEnum name
) {}