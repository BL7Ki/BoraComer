package pos.java.bora_comer.core.usercase.userType.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.UserTypeSearchGateway;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;

@Service
public class SearchUserTypeUseCaseImpl implements SearchUserTypeUseCase {

    private final UserTypeSearchGateway searchUserTypeGateway;

    public SearchUserTypeUseCaseImpl(UserTypeSearchGateway userTypeSearchGateway) {
        this.searchUserTypeGateway = userTypeSearchGateway;
    }


    @Override
    public Page<UserType> findAll(int page, int size) throws UserDomainException {

        PageRequest pageable = PageRequest.of(page, size);

        return searchUserTypeGateway.findAll(pageable);
    }

    @Override
    public UserType findById(Long id) throws UserDomainException {

        return searchUserTypeGateway.findById(id)
                .orElseThrow(() -> new UserDomainException("Tipo de usuário não encontrado com o ID: " + id));
    }
}
