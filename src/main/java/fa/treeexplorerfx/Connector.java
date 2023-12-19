package fa.treeexplorerfx;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import Trees.Row;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;



public class Connector {

    private final URI address = URI.create("http://localhost:8080/trees");
    private final ObjectMapper mapper = new ObjectMapper();

    private final HttpClient httpClient;

    public Connector() {
        this.httpClient = HttpClient.newHttpClient();
    }
     public ArrayList<Row> getTrees() throws IOException, InterruptedException {
         TypeReference<ArrayList<Row>> typeRef = new TypeReference<>() {};
         return get(typeRef);
     }

    private <T> T get(TypeReference<T> typeReference) throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder()
                .uri(address)
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(response.body(), typeReference);
    }

    public void post(Object data) throws IOException, InterruptedException {
        var json = mapper.writeValueAsString(data);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(address)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();


        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        response.statusCode();
    }
}
