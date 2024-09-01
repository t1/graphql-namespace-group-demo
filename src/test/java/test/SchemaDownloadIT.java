package test;

import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusIntegrationTest;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.assertj.core.api.BDDAssertions.then;

@QuarkusIntegrationTest
public class SchemaDownloadIT {
    private static final HttpClient HTTP = HttpClient.newHttpClient();

    @TestHTTPResource("/graphql/schema.graphql")
    URI uri;

    @Test
    void shouldDownloadSchema() throws Exception {
        var response = HTTP.send(HttpRequest.newBuilder().uri(uri).GET().build(), ofString());

        then(response.statusCode()).isEqualTo(200);
        then(response.body()).isEqualTo(Files.readString(Paths.get("schema.graphql")));
    }
}
