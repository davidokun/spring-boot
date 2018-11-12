package features.games;

import com.singletonapps.demo.BottomUpTestingApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = BottomUpTestingApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
@Sql(scripts = "classpath:delete.sql", executionPhase = AFTER_TEST_METHOD)
public class SpringIntegrationTest {

    @Test
    public void dummy() {
      // Dummy test to delete sql data after tests.
    }
}
