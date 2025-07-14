package pos.java.bora_comer.core.usercase.userType.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import pos.java.bora_comer.core.domain.UserType;
import pos.java.bora_comer.core.errors.UserDomainException;
import pos.java.bora_comer.core.gateway.userType.UserTypeSearchGateway;
import pos.java.bora_comer.factory.user.UserTypeFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SearchUserTypeUseCaseImplTest {

    private UserTypeSearchGateway userTypeSearchGateway;
    private SearchUserTypeUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        userTypeSearchGateway = mock(UserTypeSearchGateway.class);
        useCase = new SearchUserTypeUseCaseImpl(userTypeSearchGateway);
    }

    @Test
    void deveBuscarTodosUserTypesComPaginacao() throws UserDomainException {
        int page = 0;
        int size = 2;
        List<UserType> userTypes = List.of(
                UserTypeFactory.createUserType(),
                UserTypeFactory.createUserType()
        );
        Page<UserType> pageResult = new PageImpl<>(userTypes, PageRequest.of(page, size), userTypes.size());

        when(userTypeSearchGateway.findAll(PageRequest.of(page, size))).thenReturn(pageResult);

        Page<UserType> result = useCase.findAll(page, size);

        assertEquals(pageResult, result);
        verify(userTypeSearchGateway).findAll(PageRequest.of(page, size));
    }

    @Test
    void devePropagarExcecaoDoGateway() throws UserDomainException {
        int page = 0;
        int size = 1;
        when(userTypeSearchGateway.findAll(PageRequest.of(page, size)))
                .thenThrow(new UserDomainException("erro"));

        assertThrows(UserDomainException.class, () -> useCase.findAll(page, size));
        verify(userTypeSearchGateway).findAll(PageRequest.of(page, size));
    }

}