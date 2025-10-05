package pos.java.bora_comer.core.usercase.user.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.user.User;
import pos.java.bora_comer.core.errors.SummerNotFoundException;
import pos.java.bora_comer.core.gateway.user.UserSearchGateway;
import pos.java.bora_comer.core.usercase.user.SearchUserUseCase;


@Service
public class SearchUserUseCaseImpl implements SearchUserUseCase {

    private final UserSearchGateway userSearchGateway;

    public SearchUserUseCaseImpl(UserSearchGateway userSearchGateway) {
        this.userSearchGateway = userSearchGateway;
    }

    @Override
    public User findByUsername(String username) throws SummerNotFoundException {
        return userSearchGateway.findByUsername(username)
                .orElseThrow(() -> new SummerNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public Page<User> findAll(int page, int size) {
        var pageable = PageRequest.of(page, size);
        return userSearchGateway.findAll(pageable);
    }
}