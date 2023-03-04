package client;

import service.ManagerSaveException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private final URI uri;
    private final String token;

    public KVTaskClient(URI uri) {
        this.uri = uri;
        this.token = getToken();
    }

    private String getToken() {
        URI uri1 = URI.create(uri + "/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
        HttpResponse<String> response;
        try {
            response = client.send(request, handler);
            if ((response.statusCode() != 200)) {
                throw new ManagerSaveException("Ошибка");
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Ошибка");
        }
        return response.body();
    }

    public void put(String key, String json) {// сбда должен приходить ключ и джисон и мотод должен загружать все на КВсервер
        URI uri1 = URI.create(uri + "/save/" + key + "?API_TOKEN=" + token);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri1)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<Void> handler = HttpResponse.BodyHandlers.discarding();

        HttpResponse<Void> answer;
        try {
            answer = client.send(request, handler);
            if (answer.statusCode() != 200){
                throw new ManagerSaveException("Статус ответа не 200");
            }
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Ошибка");
        }
    }

    public String load(String key) {
        String body;

        URI uri1 = URI.create(uri + "/load/" + key + "?API_TOKEN=" + token);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri1)
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> answer;
        try {
            answer = client.send(request, handler);
            if (answer.statusCode() != 200){
                throw new ManagerSaveException("Статус ответа не 200");
            }
            body = answer.body();
        } catch (IOException | InterruptedException e) {
            throw new ManagerSaveException("Ошибка");
        }
        return body;
    }
}

