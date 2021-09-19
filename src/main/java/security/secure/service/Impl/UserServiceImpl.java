package security.secure.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.secure.dto.UserDTO;
import security.secure.enums.RoleEnum;
import security.secure.model.User;
import security.secure.repository.RoleRepository;
import security.secure.repository.UserRepository;
import security.secure.security.JwtService;
import security.secure.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public String create(UserDTO userDTO) {
        User user1 = new User();
        user1.setUsername(userDTO.getUsername());
        user1.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user1.setRoleId(roleRepository.findByName(RoleEnum.ROLE_USER.name()));
        //user1.setRoleId();
        userRepository.save(user1);
        return jwtService.createToken(user1) ;
    }

    /*@Override
    public String generateUserToken(String username) {
        //final Collection<? extends GrantedAuthority> authorities = loadUserByUsername(username).getAuthorities();
        return jwtService.createToken(username, authorities);}

     */

    @Override
    public List<String> listCategories() {
        return null;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        Optional<User> category = userRepository.findById(id);
        return category.orElse(null);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findByUsername(s);
    }
}
