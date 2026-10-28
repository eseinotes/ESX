package edu.example.studentperformance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;

/**
 * Encapsula la comunicación HTTP con el servicio de predicción escrito en
 * Python.
 */
public class PredictionClient {
    private final String serviceUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    public PredictionClient(String serviceUrl) {
        this.serviceUrl = serviceUrl;
        httpClient = HttpClient.newHttpClient();
        gson = new Gson();
    }

    public double requestPrediction(double studyHours) throws IOException, InterruptedException {
        // El nombre study_hours forma parte del contrato JSON de la API de Python.
        JsonObject requestData = new JsonObject();
        requestData.addProperty("study_hours", studyHours);

        // El constructor se configura paso a paso para que cada parte sea visible.
        URI predictionUri = URI.create(serviceUrl + "/predict");
        String requestBody = gson.toJson(requestData);
        Builder requestBuilder = HttpRequest.newBuilder();
        requestBuilder.uri(predictionUri);
        requestBuilder.header("Content-Type", "application/json");
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
        HttpRequest request = requestBuilder.build();

        // send realiza la llamada de forma síncrona y devuelve la respuesta completa.
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new IOException("Prediction service returned an error");
        }

        // Gson transforma la respuesta JSON para poder obtener predicted_grade.
        JsonObject responseData = gson.fromJson(response.body(), JsonObject.class);
        return responseData.get("predicted_grade").getAsDouble();
    }
}
