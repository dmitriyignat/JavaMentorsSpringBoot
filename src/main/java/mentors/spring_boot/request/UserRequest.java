package mentors.spring_boot.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mentors.spring_boot.model.Role;

import java.util.List;

@Getter
@Setter
public class UserRequest {
    private String login;
    private String name;
    private String password;
    private long id;
    private List<String> roles;
}
