package mentors.spring_boot.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import mentors.spring_boot.model.Role;
import mentors.spring_boot.model.User;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private User user;
    private List<Role> roles;
    private List<User> users;
//    private boolean redirect;
//    private String redirect_url;
}
