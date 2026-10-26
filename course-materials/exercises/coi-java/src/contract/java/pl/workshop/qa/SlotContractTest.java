package pl.workshop.qa;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class SlotContractTest {
    @Test void exposesSlotsWithStableTypesAndOrder() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            given().baseUri(server.baseUri().toString()).when().get("/api/slots")
                .then().statusCode(200).contentType("application/json")
                .body("slots", hasSize(2))
                .body("zone", equalTo("Europe/Warsaw"))
                .body("slots[0].id", equalTo("SYN-01"))
                .body("slots[0].remaining", equalTo(2))
                .body("slots[1].id", equalTo("SYN-02"))
                .body("slots[1].remaining", equalTo(0));
        }
    }

    @Test void exposesEmptyReservationArrayOnFreshFixture() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            given().baseUri(server.baseUri().toString()).when().get("/api/reservations")
                .then().statusCode(200).contentType("application/json").body("$", hasSize(0));
        }
    }
}
