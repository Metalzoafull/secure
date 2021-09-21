package security.secure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import security.secure.dto.UserDTO;
import security.secure.model.User;
import security.secure.security.JwtService;
import security.secure.service.Impl.UserServiceImpl;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody UserDTO userDTO) throws Exception {
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        User user = userService.findByUsername(userDTO.getUsername());
        String token = jwtService.createToken(user);
        return ResponseEntity.status(HttpStatus.OK).body(token);

        //return new AuthenticationResponse(token);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(userService.create(userDTO));
        } catch (Exception e) {
            throw new Exception("Exploto algo", e);
        }
    }

    @GetMapping("/login")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> createTokeno(@RequestBody UserDTO userDTO){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(authentication);

    }

    @GetMapping("/testing")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTokenoa(@RequestBody UserDTO userDTO){
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(authenticationManager.authenticate(authentication));

    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> editar(@RequestBody UserDTO userDTO) throws Exception {
        try{
            return ResponseEntity.status(HttpStatus.OK).body("authentication");
        }catch (Exception e){
            throw new Exception("Exploto algo", e);
        }
    }

    @GetMapping("/panqueque/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<?> panqueque(@RequestBody UserDTO userDto, @PathVariable Long id){
        User boby = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.equals(id, boby.getId())){
            return ResponseEntity.status(HttpStatus.OK).body(userService.edit(id, userDto));

        }else {
            return ResponseEntity.status(HttpStatus.OK).body("fracaso");
        }
    }



}
