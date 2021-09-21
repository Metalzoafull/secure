package security.secure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name="user")
@SQLDelete(sql = "UPDATE user SET deleted=true WHERE id = ?")
@Where(clause = "deleted = false")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Column
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Column
    @NotBlank(message = "Name cannot be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String name;

    @Column
    @NotBlank(message = "Lastname cannot be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    private String lastname;

    @JoinColumn(name = "role", referencedColumnName = "id")
    @ManyToOne
    private Role roleId;

    @Column
    private boolean deleted = Boolean.FALSE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roleId.getAuthorities();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !(this.isDeleted());
    }

}
