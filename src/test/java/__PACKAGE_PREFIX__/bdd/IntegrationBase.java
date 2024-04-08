package __PACKAGE_PREFIX__.bdd;

import __PACKAGE_PREFIX__.__CLASS__Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@SpringBootTest(classes = __CLASS__Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("INTEGRATION")
@ContextConfiguration
public class IntegrationBase {

    @Autowired
    protected TestRestTemplate template;

}
