package features.games;
import com.singletonapps.demo.dto.GameDTO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class GameRetrievalDefs extends GameSpringIntegrationTest {

    private String endpoint;
    private GameDTO.GameDTOBuilder gameDTOBuilder;
    private GameDTO gameDTO;
    private ResponseEntity<GameDTO> response;

    @Autowired
    private TestRestTemplate restTemplate;
    

    @Given("^an endpoint \"([^\"]*)\"$")
    public void anEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @And("^a game created with name \"([^\"]*)\"$")
    public void aGameCreatedWithName(String name) {
        gameDTOBuilder = GameDTO.builder()
            .name(name);
    }

    @And("^year published \"([^\"]*)\"$")
    public void yearPublishedIs(String year) throws SQLException {
        gameDTO = gameDTOBuilder
            .yearPublished(Long.parseLong(year))
            .build();
    }

    @When("^user sends a GET request with parameter \"([^\"]*)\"$")
    public void userSendsAGETRequestWithParameter(String param) {
        this.response = restTemplate.getForEntity(this.endpoint + "/" + param, GameDTO.class);
    }

    @Then("^a status code \"([^\"]*)\" is received$")
    public void aStatusCodeIsReceived(int code) {
        assertThat(response.getStatusCode().value())
            .isEqualTo(code);
    }

    @And("^content type is \"([^\"]*)\"$")
    public void contentTypeIs(String contentType) {
        assertThat(response.getHeaders().getContentType().toString())
            .isEqualTo(contentType);
    }

    @And("^game name is \"([^\"]*)\"$")
    public void gameNameIs(String name) {
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    @And("^year published is \"([^\"]*)\"$")
    public void yearPublished(String year) throws SQLException {
        assertThat(response.getBody().getYearPublished())
            .isEqualTo(Long.parseLong(year));
    }
    
    // All Games
    
    @Given("^an endpoint for all games \"([^\"]*)\"$")
    public void anEndpointEndpoint(String endpoint) throws SQLException {
        this.endpoint = endpoint;
    }

    @When("^I send a GET request$")
    public void iSendAGETRequest()  {
        this.response = restTemplate.getForEntity(this.endpoint, GameDTO.class);
    }

    @Then("^I get a status \"([^\"]*)\"$")
    public void iGetAStatusStatus(int code)  {
        assertThat(response.getStatusCode().value())
            .isEqualTo(code);
    }

    @And("^a game with name \"([^\"]*)\"$")
    public void aGameWithNameName(String name)  {
        assertThat(response.getBody().getName()).isEqualTo(name);
    }

    @And("^year published is equal to \"([^\"]*)\"$")
    public void yearPublishedYear(Long year)  {
        assertThat(response.getBody().getYearPublished())
            .isEqualTo(year);
    }

    @And("^id greater than \"([^\"]*)\"$")
    public void idGreaterThenId(int id) throws SQLException {
        assertThat(response.getBody().getId())
            .isGreaterThan(id);
    }
}
