package mails.application;

import mails.domain.MailConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PreDestroy;
import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

@ConfigurationPropertiesScan
@EnableConfigurationProperties(MailConfig.class)
public class GetMessageScheduler implements CommandLineRunner {
    @Autowired
    MailConfig mailConfiguration;

    private final Logger log = LoggerFactory.getLogger(GetMessageScheduler.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(GetMessageScheduler.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting service");
        log.info(mailConfiguration.toString());

        log.info(log.isDebugEnabled()+"");

        Properties sysProperties = System.getProperties();
        sysProperties.setProperty("mail.store.protocol", "imap");

        log.debug("Creation session");
        Session session = Session.getDefaultInstance(sysProperties, null);
        log.debug("getting Store");
        Store store = session.getStore();

        log.debug("connecting...");
        store.connect(mailConfiguration.getHostname(), 3143, mailConfiguration.getUsername(), mailConfiguration.getPassword());

        log.debug("getting inbox");
        Folder inbox = store.getFolder("Inbox");
        inbox.open(Folder.READ_ONLY);
        log.info(inbox.getMessageCount()+"");
    }

    @PreDestroy
    public void onExit() {
        //Todo: Send shutdown message to matrix
    }
}
