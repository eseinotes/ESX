package edu.example.studentperformance;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Punto de entrada que crea los objetos de la aplicación y arranca el servidor HTTP.
 */
public class Main {
    public static void main(String[] args) throws IOException {

        // Las dependencias se conectan de forma explícita para mostrar la relación entre capas.
        StudentRepository repository = new StudentRepository();

        // en desarrollo local se utiliza localhost.
        String predictionServiceUrl = System.getenv("PYTHON_SERVICE_URL");
        if (predictionServiceUrl == null) {
            predictionServiceUrl = "http://localhost:8000";
        } else if (predictionServiceUrl.isBlank()) {
            predictionServiceUrl = "http://localhost:8000";
        }
        PredictionClient predictionClient = new PredictionClient(predictionServiceUrl);
        StudentService service = new StudentService(repository, predictionClient);
        StudentController controller = new StudentController(service);

        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/students", controller);
        server.createContext("/", new StaticFileHandler());
        server.start();

        System.out.println("Student Performance Predictor is running at http://localhost:8080");
    }
}
