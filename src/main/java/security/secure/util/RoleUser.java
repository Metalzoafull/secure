package security.secure.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import security.secure.enums.RoleEnum;
import security.secure.model.Role;
import security.secure.repository.RoleRepository;

import java.util.NoSuchElementException;

@Component
@Slf4j
public class RoleUser {

    @Autowired
    RoleRepository roleRepository;


    @EventListener
    public void roleLoader(ApplicationReadyEvent event){
        /*try {
            roleRepository.findByName("ROLE_USER");
        } catch (NoSuchElementException e){
            log.warn("Role User: " + e.getMessage() + ". Creating User role..");
            roleRepository.save(new Role("ROLE_USER","User Role"));
        }

         */
        try {
            roleRepository.findByName(RoleEnum.ROLE_USER.name());
        } catch (NoSuchElementException e){
            log.warn("Role User: " + e.getMessage() + ". Creating User role..");
            roleRepository.save(new Role(RoleEnum.ROLE_USER.name(),"User Role"));
        }

        try {
            roleRepository.findByName(RoleEnum.ROLE_ADMIN.name());
        } catch (NoSuchElementException e){
            log.warn("Role Admin: " + e.getMessage() + ". Creating Admin role..");
            roleRepository.save(new Role(RoleEnum.ROLE_ADMIN.name(),"Admin Role"));
        }
    }
}
