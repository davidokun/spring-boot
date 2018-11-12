package features.games;
import com.singletonapps.demo.dto.GameDTO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class GameCreationDefs extends GameSpringIntegrationTest {

    private String endpoint;
    private ResponseEntity<GameDTO>  response;
    private GameDTO.GameDTOBuilder gameBuilder;
    private GameDTO game;

    @Autowired
    private TestRestTemplate restTemplate;

    @Given("^An endpoint \"([^\"]*)\"$")
    public void anEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @And("^a game with name \"([^\"]*)\" and year published (\\d+)$")
    public void aGameWithNameAndYearPublished(String name, long year) throws Throwable {

        gameBuilder = GameDTO.builder()
            .name(name)
            .yearPublished(year);
    }

    @And("^id is null$")
    public void idIsNull() {
        gameBuilder.id(null);
    }

    @And("^createdOn is null$")
    public void createdonIsNull() {
        game = gameBuilder.createOn(null)
            .build();
    }

    @When("^user send a request to \"([^\"]*)\"$")
    public void anUserSendARequestTo(String endpoint) throws Throwable {
        //response = RestAssured.post(endpoint);
        response = restTemplate.postForEntity(endpoint, game, GameDTO.class);
    }

    @Then("^response should be wit status (\\d+)$")
    public void responseShouldBeWitStatus(int status) throws Throwable {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @And("^id is greater than (\\d+)$")
    public void idIsGreaterThan(int arg0) throws Throwable {
        assertThat(response.getBody().getId()).isGreaterThan(arg0);
    }

    @And("^createOn is not null$")
    public void createonIsNotNull() throws Throwable {
        assertThat(response.getBody().getCreateOn()).isNotNull();
    }

    @And("^name is \"([^\"]*)\"$")
    public void nameIs(String name) throws Throwable {
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    @And("^year is (\\d+)$")
    public void yearIs(int year) throws Throwable {
        assertThat(response.getBody().getYearPublished()).isEqualTo(year);
    }
}
