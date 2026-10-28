package edu.example.studentperformance;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Sirve el frontend desde los recursos incluidos en la aplicación Java.
 * Así, navegador y API comparten origen y no es necesario configurar CORS.
 */
public class StaticFileHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method not allowed");
            return;
        }

        String path = exchange.getRequestURI().getPath();
        if ("/".equals(path)) {
            path = "/index.html";
        }

        // La lista cerrada evita servir por accidente otros recursos del classpath.
        if (!isKnownFile(path)) {
            sendText(exchange, 404, "File not found");
            return;
        }

        InputStream input = StaticFileHandler.class.getResourceAsStream("/static" + path);
        if (input == null) {
            sendText(exchange, 404, "File not found");
            return;
        }

        byte[] content = input.readAllBytes();
        input.close();

        // El navegador necesita el tipo correcto para interpretar HTML, CSS y JavaScript.
        exchange.getResponseHeaders().set("Content-Type", contentType(path));
        exchange.sendResponseHeaders(200, content.length);
        OutputStream output = exchange.getResponseBody();
        output.write(content);
        output.close();
    }

    private boolean isKnownFile(String path) {
        if ("/index.html".equals(path)) {
            return true;
        }
        if ("/style.css".equals(path)) {
            return true;
        }
        return "/app.js".equals(path);
    }

    private String contentType(String path) {
        if (path.endsWith(".css")) {
            return "text/css; charset=utf-8";
        }
        if (path.endsWith(".js")) {
            return "text/javascript; charset=utf-8";
        }
        return "text/html; charset=utf-8";
    }

    private void sendText(HttpExchange exchange, int status, String message) throws IOException {
        byte[] body = message.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=utf-8");
        exchange.sendResponseHeaders(status, body.length);
        OutputStream output = exchange.getResponseBody();
        output.write(body);
        output.close();
    }
}
