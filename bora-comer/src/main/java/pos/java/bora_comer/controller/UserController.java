package pos.java.bora_comer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pos.java.bora_comer.model.User;
import pos.java.bora_comer.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Construtor manual sem Lombok e sem @Autowired (boa prática)
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<User>> listarTodos() {
        List<User> usuarios = userService.listarTodos();
        return ResponseEntity.ok(usuarios);
    }

    // Buscar usuário por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        User usuario = userService.buscarPorId(id);
        return ResponseEntity.ok(usuario);
    }

    // Criar novo usuário
    @PostMapping
    public ResponseEntity<User> criarUsuario(@RequestBody User user) {
        User novoUsuario = userService.criarUsuario(user);
        // Retorna Location com o URI do novo recurso criado
        URI location = URI.create("/users/" + novoUsuario.getId());
        return ResponseEntity.created(location).body(novoUsuario);
    }

    // Atualizar usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<User> atualizarUsuario(@PathVariable Long id, @RequestBody User userAtualizado) {
        User usuarioAtualizado = userService.atualizarUsuario(id, userAtualizado);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {
        userService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Validar login (recebe login e senha como query params)
    @PostMapping("/login")
    public ResponseEntity<String> validarLogin(@RequestParam String login, @RequestParam String senha) {
        boolean valido = userService.validarLogin(login, senha);
        if (valido) {
            return ResponseEntity.ok("Login válido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha inválidos.");
        }
    }
}
