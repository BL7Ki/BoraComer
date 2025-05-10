package pos.java.bora_comer.service;

import org.springframework.stereotype.Service;
import pos.java.bora_comer.model.User;
import pos.java.bora_comer.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Construtor manual
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Listar todos os usuários
    public List<User> listarTodos() {
        return userRepository.findAll();
    }

    // Buscar usuário por ID
    public User buscarPorId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    // Criar novo usuário
    public User criarUsuario(User user) {
        user.setDataUltimaAlteracao(LocalDate.now());
        return userRepository.save(user);
    }

    // Atualizar usuário existente
    public User atualizarUsuario(Long id, User userAtualizado) {
        User userExistente = buscarPorId(id);

        userExistente.setNome(userAtualizado.getNome());
        userExistente.setEmail(userAtualizado.getEmail());
        userExistente.setLogin(userAtualizado.getLogin());
        userExistente.setSenha(userAtualizado.getSenha());
        userExistente.setEndereco(userAtualizado.getEndereco());
        userExistente.setDataUltimaAlteracao(LocalDate.now());

        return userRepository.save(userExistente);
    }

    // Deletar usuário
    public void excluirUsuario(Long id) {
        buscarPorId(id); // Valida se existe antes
        userRepository.deleteById(id);
    }

    // Validar login (login e senha)
    public boolean validarLogin(String login, String senha) {
        Optional<User> usuarioOpt = userRepository.findByLogin(login);
        return usuarioOpt.map(user -> user.getSenha().equals(senha)).orElse(false);
    }
}
