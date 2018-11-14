package features.games;
import com.singletonapps.demo.dto.GameDTO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GameRetrievalDefs extends GameSpringIntegrationTest {

    private String endpoint;
    private GameDTO.GameDTOBuilder gameDTOBuilder;
    private GameDTO gameDTO;
    private ResponseEntity<GameDTO> response;
    private ResponseEntity<List<GameDTO>> responseList;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DataSource ds;
    

    @Given("^an endpoint \"([^\"]*)\"$")
    public void anEndpoint(String endpoint) throws SQLException {
        this.endpoint = endpoint;
        ScriptUtils.
            executeSqlScript(ds.getConnection(), new ClassPathResource("testData.sql"));
    }

    @And("^a game created with name \"([^\"]*)\"$")
    public void aGameCreatedWithName(String name) {
        gameDTOBuilder = GameDTO.builder()
            .name(name);
    }

    @And("^year published \"([^\"]*)\"$")
    public void yearPublishedIs(String year) {
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
        ScriptUtils.
            executeSqlScript(ds.getConnection(), new ClassPathResource("delete.sql"));
    }
    
    // All Games
    
    @Given("^an endpoint for all games \"([^\"]*)\"$")
    public void anEndpointEndpoint(String endpoint) throws SQLException {
        this.endpoint = endpoint;
        ScriptUtils.
            executeSqlScript(ds.getConnection(), new ClassPathResource("testData.sql"));
    }

    @When("^I send a GET request$")
    public void iSendAGETRequest()  {
        this.responseList =
            restTemplate.exchange(
                this.endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<GameDTO>>() {});
    }

    @Then("^I get a status \"([^\"]*)\"$")
    public void iGetAStatusStatus(int code)  {
        assertThat(responseList.getStatusCode().value())
            .isEqualTo(code);
    }

    @And("^games with names \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void aGameWithNameName(String name1, String name2, String name3) {
        assertThat(responseList.getBody().get(0).getName()).isEqualTo(name1);
        assertThat(responseList.getBody().get(1).getName()).isEqualTo(name2);
        assertThat(responseList.getBody().get(2).getName()).isEqualTo(name3);
    }

    @And("^ids greater than \"([^\"]*)\"$")
    public void aGameWithNameName(int id) throws SQLException {
        assertThat(responseList.getBody().get(0).getId()).isGreaterThan(id);
        assertThat(responseList.getBody().get(1).getId()).isGreaterThan(id);
        assertThat(responseList.getBody().get(2).getId()).isGreaterThan(id);

        ScriptUtils.
            executeSqlScript(ds.getConnection(), new ClassPathResource("delete.sql"));
    }

}
