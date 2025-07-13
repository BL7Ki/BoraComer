package pos.java.bora_comer.core.usercase.userType.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.SearchUserTypeGateway;
import pos.java.bora_comer.core.usercase.userType.SearchUserTypeUseCase;

@Service
public class SearchUserTypeUseCaseImpl implements SearchUserTypeUseCase {

    private final SearchUserTypeGateway searchUserTypeGateway;

    public SearchUserTypeUseCaseImpl(SearchUserTypeGateway searchUserTypeGateway) {
        this.searchUserTypeGateway = searchUserTypeGateway;
    }


    @Override
    public Page<UserType> findAll(int page, int size) throws UserDomainException {

        PageRequest pageable = PageRequest.of(page, size);

        return searchUserTypeGateway.findAll(pageable);
    }
}
