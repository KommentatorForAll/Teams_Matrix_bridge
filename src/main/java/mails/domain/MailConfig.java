package mails.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "mail")
@AllArgsConstructor
@Data
@ConstructorBinding
public class MailConfig {
    private String username;
    private String password;
    private String hostname;
    private int refreshRate;
    private boolean readDirectMessages;
}
