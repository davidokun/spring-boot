package features;
import com.singletonapps.demo.dto.GameDTO;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GameCreationDefs extends SpringIntegrationTest {

    private String endpoint;
    private String name;
    private Long year;
    private Response response;
    private GameDTO.GameDTOBuilder gameBuilder;


    @Given("^An endpoint \"([^\"]*)\"$")
    public void anEndpoint(String endpoint) throws Throwable {
        this.endpoint = endpoint;
    }

    @And("^a game with name \"([^\"]*)\" and year published (\\d+)$")
    public void aGameWithNameAndYearPublished(String arg0, int arg1) throws Throwable {
        this.name = name;
        this.year = year;

        gameBuilder = GameDTO.builder()
            .name(this.name)
            .yearPublished(this.year);
    }

    @And("^id is null$")
    public void idIsNull() throws Throwable {
        gameBuilder.id(null);
    }

    @And("^createdOn is null$")
    public void createdonIsNull() throws Throwable {
        gameBuilder.createOn(null);
    }

    @When("^user send a request to \"([^\"]*)\"$")
    public void anUserSendARequestTo(String endpoint) throws Throwable {
        response = RestAssured.post(endpoint);
    }

    @Then("^response should be wit status (\\d+)$")
    public void responseShouldBeWitStatus(int status) throws Throwable {
        response.then().statusCode(status);
    }
}
