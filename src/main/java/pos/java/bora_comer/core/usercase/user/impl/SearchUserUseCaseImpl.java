package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;


@Service
public class SearchUserUseCaseImpl implements SearchUserUseCase {

    private final UserSearchGateway userSearchGateway;

    public SearchUserUseCaseImpl(UserSearchGateway userSearchGateway) {
        this.userSearchGateway = userSearchGateway;
    }

    @Override
    public User findById(Long id) throws SummerNotFoundException {
        return userSearchGateway.findById(id)
                .orElseThrow(() -> new SummerNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    public Page<User> findAll(int page, int size) throws UserDomainException {
        var pageable = PageRequest.of(page, size);
        return userSearchGateway.findAll(pageable);
    }
}
