package security.secure.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import security.secure.dto.UserDTO;
import security.secure.model.User;

import java.util.List;

public interface UserService {

    //String create(UserDTO userDTO);
    String create(UserDTO userDTO);

    List<String> listCategories();

    //ResponseEntity<String> edit(Long id, UserDTO userDTO);

    User findByUsername(String username);

    User findById(Long id);

    void delete (Long id);
}
