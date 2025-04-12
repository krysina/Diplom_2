package praktikum.user;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Credentials {

    private String email;
    private String password;
    private String name;

    public static Credentials fromUser(User user) {
        return new Credentials(user.getEmail(), user.getPassword(), user.getName());
    }

}
