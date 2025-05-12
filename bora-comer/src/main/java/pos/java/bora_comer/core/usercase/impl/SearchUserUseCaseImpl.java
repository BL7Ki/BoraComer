package pos.java.bora_comer.core.usercase.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pos.java.bora_comer.core.domain.User;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.mapper.UserMapper;
import pos.java.bora_comer.core.usercase.SearchUserUseCase;
import pos.java.bora_comer.infra.persistence.repository.user.UserRepository;
import pos.java.bora_comer.infra.persistence.repository.user.entity.UserEntity;


@Service
public class SearchUserUseCaseImpl implements SearchUserUseCase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SearchUserUseCaseImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User findById(Long id) throws UserDomainException {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserDomainException("User with ID " + id + " not found"));

        return userMapper.toDomain(userEntity);
    }

    @Override
    public Page<User> findAll(int page, int size) throws UserDomainException {
        var pageable = PageRequest.of(page, size);
        var userPage = userRepository.findAll(pageable);

        return userPage.map(userMapper::toDomain);
    }

}
