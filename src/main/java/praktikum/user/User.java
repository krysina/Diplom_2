package praktikum.user;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class User {

    private String email;
    private String password;
    private String name;

    public static User random() {
        String randomEmail = String.format("%s@yandex.ru", RandomStringUtils.randomAlphabetic(5));
        String randomPassword = RandomStringUtils.randomAlphanumeric(8);
        String randomName = RandomStringUtils.randomAlphabetic(6);

        return User.builder()
                .email(randomEmail)
                .password(randomPassword)
                .name(randomName)
                .build();
    }
}
