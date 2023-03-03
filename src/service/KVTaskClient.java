package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    URI uri;

    public KVTaskClient(URI uri) {
        this.uri = uri;
    }
    String getToken() throws IOException, InterruptedException {
        URI uri1 = URI.create(uri + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response = client.send(request, handler);
        return response.body();
    }

    public void put(String key, String json) throws IOException, InterruptedException {// сбда должен приходить ключ и джисон и мотод должен загружать все на КВсервер
        URI uri1 = URI.create(uri+ "/save/" + key + "?API_TOKEN=" + getToken());

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri1)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        client.send(request, handler);
    }

    public String load(String key) throws IOException, InterruptedException {

        URI uri1 = URI.create(uri + "/load/" + key + "?API_TOKEN=" + getToken());

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> answer = client.send(request, handler);

        return answer.body();
    }
}

