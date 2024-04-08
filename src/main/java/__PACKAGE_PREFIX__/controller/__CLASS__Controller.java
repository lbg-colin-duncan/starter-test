package __PACKAGE_PREFIX__.controller;

import __PACKAGE_PREFIX__.config.Config;
import __PACKAGE_PREFIX__.dto.CustomerDTO;
import com.google.common.flogger.FluentLogger;
import com.google.common.flogger.StackSize;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@RestController
@RequestMapping("/customers")
@Api(value = "/customers")
public class __CLASS__Controller {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private final Config config;


    public __CLASS__Controller(final Config config) {
        this.config = config;
    }

    /**
     * Sample controller
     * @return
     */
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<CustomerDTO>> getAll() {
        List<CustomerDTO> customers = null;

        logger.at(Level.INFO).withStackTrace(StackSize.MEDIUM).log("Logging at Info log level with stacktrace");

        customers = getAllCustomers();

        return ResponseEntity.ok(customers);
    }

    private List<CustomerDTO> getAllCustomers(){
       logger.atInfo().log("Invoking getAllCustomers()");
        List<CustomerDTO> customers;

        CustomerDTO customer1 = new CustomerDTO();
        customer1.setName("Customer 1");
        customer1.setDescription("Customer Description 1");
        customer1.setId("1");

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setName("Customer 2");
        customer2.setDescription("Customer Description 2");
        customer2.setId("2");

        customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);
        return customers;
    }
}
