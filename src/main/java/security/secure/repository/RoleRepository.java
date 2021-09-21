package security.secure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import security.secure.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (String name);

}
