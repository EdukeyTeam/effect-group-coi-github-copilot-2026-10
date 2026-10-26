package pl.workshop.qa;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApiSmokeTest {
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3)).build();

    private HttpResponse<String> send(FixtureServer server, String path, String method) throws Exception {
        return client.send(HttpRequest.newBuilder(server.baseUri().resolve(path))
                .timeout(Duration.ofSeconds(5)).method(method, HttpRequest.BodyPublishers.noBody()).build(),
                HttpResponse.BodyHandlers.ofString());
    }

    @Test void listsSyntheticSlotsOverRealHttp() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            HttpResponse<String> response = send(server, "/api/slots", "GET");
            assertEquals(200, response.statusCode());
            assertEquals("application/json; charset=utf-8", response.headers().firstValue("Content-Type").orElseThrow());
            assertTrue(response.body().contains("\"SYN-01\""));
            assertTrue(response.body().contains("\"SYN-02\""));
            assertTrue(response.body().contains("\"Europe/Warsaw\""));
        }
    }

    @Test void startsWithNoReservations() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            HttpResponse<String> response = send(server, "/api/reservations", "GET");
            assertEquals(200, response.statusCode());
            assertEquals("[]", response.body().strip());
        }
    }

    @Test void returnsNotFoundForUnknownPath() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            HttpResponse<String> response = send(server, "/api/unknown", "GET");
            assertEquals(404, response.statusCode());
            assertEquals("{\"error\":\"NOT_FOUND\"}", response.body());
        }
    }

    @Test void rejectsWriteToReadOnlySlotsRoute() throws Exception {
        try (FixtureServer server = FixtureServer.start(0)) {
            HttpResponse<String> response = send(server, "/api/slots", "POST");
            assertEquals(405, response.statusCode());
            assertEquals("GET", response.headers().firstValue("Allow").orElseThrow());
            assertEquals("{\"error\":\"METHOD_NOT_ALLOWED\"}", response.body());
        }
    }
}
