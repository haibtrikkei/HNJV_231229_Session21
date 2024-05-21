package ra.demo_springwebmvc_hibernate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginForm {
    private String username;
    private String password;
    private String save;
}
