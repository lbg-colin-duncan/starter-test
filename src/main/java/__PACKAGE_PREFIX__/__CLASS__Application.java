package __PACKAGE_PREFIX__;


import com.google.common.flogger.FluentLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.lbg"})
public class __CLASS__Application {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    public static void main(String[] args) throws InterruptedException {
        // Register a Flogger back-end factory for Log4j
        System.setProperty("flogger.backend_factory", "com.google.common.flogger.backend.slf4j.Slf4jBackendFactory#getInstance");
        long startTime = System.currentTimeMillis();
        SpringApplication.run(__CLASS__Application.class, args);
        long totalTime = System.currentTimeMillis() - startTime;
        logger.atInfo().log("Startup time in seconds :" + totalTime / 1000);

    }
}
