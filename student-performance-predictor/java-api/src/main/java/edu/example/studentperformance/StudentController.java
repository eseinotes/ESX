package edu.example.studentperformance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * STUDENT CONTROLLER
 *
 * Esta clase es el controlador de la API REST de estudiantes.
 *
 * Su función principal es actuar como intermediario entre el cliente
 * (navegador web) y la lógica de negocio (StudentService).
 *
 * RESPONSABILIDADES:
 * 1. Recibir las peticiones HTTP del navegador.
 * 2. Identificar qué operación solicita el cliente.
 * 3. Convertir los datos JSON recibidos en objetos Java.
 * 4. Delegar las operaciones en StudentService.
 * 5. Convertir los resultados de Java en JSON.
 * 6. Enviar las respuestas HTTP al navegador.
 *
 * FLUJO GENERAL:
 *
 * Navegador -> StudentController -> StudentService
 *                                      |
 *                               StudentRepository
 *                                      |
 *                                   HashMap
 *
 * El resultado vuelve siguiendo el camino inverso.
 *
 * IMPORTANTE:
 * Esta clase no almacena directamente estudiantes ni implementa
 * las reglas de negocio. Esas responsabilidades se delegan
 * en StudentService y StudentRepository.
 */
public class StudentController implements HttpHandler {

    // Referencia al servicio que contiene la lógica de negocio.
    // El controlador delegará en él todas las operaciones
    // relacionadas con los estudiantes y sus predicciones.
    private final StudentService service;

    // Gson es una biblioteca que permite transformar:
    //
    // Objeto Java -> JSON
    // JSON -> Objeto Java
    //
    // Esto es necesario porque el navegador se comunica
    // con Java intercambiando datos en formato JSON.
    private final Gson gson;


    /**
     * Constructor de StudentController.
     *
     * Recibimos un objeto StudentService previamente creado.
     *
     * De esta manera, el controlador puede utilizar el servicio
     * sin tener que encargarse de crearlo.
     */
    public StudentController(StudentService service) {

        // Guardamos la referencia al servicio recibido.
        this.service = service;

        // Creamos el objeto Gson para trabajar con JSON.
        gson = new Gson();
    }


    // ============================================================
    // 1. PUNTO DE ENTRADA DE LAS PETICIONES HTTP
    // ============================================================

    /**
     * Este método es ejecutado automáticamente por HttpServer
     * cuando llega una petición HTTP al contexto correspondiente.
     *
     * HttpExchange representa el intercambio HTTP completo.
     * Nos permite acceder a:
     *
     * - Método HTTP: GET, POST, PUT, DELETE...
     * - URL solicitada.
     * - Cabeceras HTTP.
     * - Cuerpo de la petición.
     *
     * También permite construir y enviar la respuesta al cliente.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {

            // PRIMER PASO:
            // Analizamos la ruta solicitada para determinar
            // qué método del controlador debe ejecutarse.
            routeRequest(exchange);

        } catch (JsonParseException exception) {

            // Si el cliente envía un JSON incorrecto,
            // Gson puede lanzar una excepción al interpretarlo.
            //
            // Ejemplo de JSON incorrecto:
            // { "name": "Ana", }
            //
            // Devolvemos HTTP 400 (Bad Request), indicando
            // que los datos enviados por el cliente no son válidos.
            sendError(exchange, 400, "Invalid JSON input");

        } catch (NumberFormatException exception) {

            // Algunas rutas contienen identificadores numéricos.
            //
            // Ejemplo válido:
            // GET /students/1
            //
            // Ejemplo incorrecto:
            // GET /students/abc
            //
            // Si no podemos convertir el identificador a entero,
            // devolvemos HTTP 400 (Bad Request).
            sendError(exchange, 400, "Invalid student identifier");
        }
    }


    // ============================================================
    // 2. IDENTIFICAR LA OPERACIÓN SOLICITADA
    // ============================================================

    /**
     * Recibe una petición HTTP y determina qué operación debe
     * ejecutarse dependiendo de la URL y del método HTTP.
     *
     * Por ejemplo:
     *
     * GET    /students              -> Listar estudiantes
     * POST   /students              -> Crear estudiante
     * GET    /students/1            -> Consultar estudiante 1
     * PUT    /students/1            -> Modificar estudiante 1
     * DELETE /students/1            -> Eliminar estudiante 1
     * GET    /students/1/prediction -> Predecir nota
     */
    private void routeRequest(HttpExchange exchange) throws IOException {

        // Obtenemos el método HTTP de la petición.
        // Por ejemplo: GET, POST, PUT o DELETE.
        String method = exchange.getRequestMethod();

        // Obtenemos la ruta solicitada.
        // Por ejemplo: "/students/1/prediction".
        String path = exchange.getRequestURI().getPath();

        // Dividimos la ruta utilizando "/" como separador.
        //
        // Por ejemplo:
        // "/students/1/prediction"
        //
        // Se convierte en:
        // ["", "students", "1", "prediction"]
        //
        // La primera posición está vacía porque la ruta
        // comienza con el carácter "/".
        String[] parts = path.split("/");

        // CASO 1:
        // La petición se realiza sobre la colección de estudiantes.
        //
        // Ejemplo: /students
        //
        // handleCollection() gestionará las operaciones GET y POST.
        if (parts.length == 2) {
            handleCollection(exchange, method);
            return;
        }

        // CASO 2:
        // La petición se realiza sobre un estudiante concreto.
        //
        // Ejemplo: /students/1
        //
        // El identificador está en parts[2].
        if (parts.length == 3) {

            // Convertimos el identificador de texto a entero.
            int id = Integer.parseInt(parts[2]);

            // Delegamos en el método encargado de gestionar
            // un estudiante individual.
            handleStudent(exchange, method, id);
            return;
        }

        // CASO 3:
        // La petición solicita la predicción de un estudiante.
        //
        // Ejemplo: /students/1/prediction
        if (parts.length == 4) {

            // Comprobamos que el último segmento sea "prediction".
            if ("prediction".equals(parts[3])) {

                // Obtenemos el identificador del estudiante.
                int id = Integer.parseInt(parts[2]);

                // Delegamos en el método de predicciones.
                handlePrediction(exchange, method, id);
                return;
            }
        }

        // Si no reconocemos la ruta solicitada,
        // devolvemos HTTP 404 (Not Found).
        sendError(exchange, 404, "Route not found");
    }


    // ============================================================
    // 3. OPERACIONES SOBRE LA COLECCIÓN DE ESTUDIANTES
    // ============================================================

    /**
     * Gestiona las operaciones sobre todos los estudiantes.
     *
     * GET /students  -> Obtener todos los estudiantes.
     * POST /students -> Crear un estudiante nuevo.
     */
    private void handleCollection(
            HttpExchange exchange,
            String method) throws IOException {

        // --------------------------------------------------------
        // CASO 1: GET /students
        // --------------------------------------------------------

        if ("GET".equals(method)) {

            // Solicitamos a StudentService todos los estudiantes.
            //
            // StudentService delegará en StudentRepository,
            // que recuperará los datos almacenados en el HashMap.
            //
            // gson.toJson() convierte la colección de objetos Java
            // en una cadena de texto JSON.
            //
            // sendJson() envía la respuesta al navegador.
            //
            // HTTP 200 indica que la operación se ha completado.
            sendJson(
                    exchange,
                    200,
                    gson.toJson(service.getAllStudents())
            );

            return;
        }


        // --------------------------------------------------------
        // CASO 2: POST /students
        // --------------------------------------------------------

        if ("POST".equals(method)) {

            // El cliente envía los datos del estudiante en JSON.
            //
            // Por ejemplo:
            //
            // {
            //     "name": "Ana",
            //     "studyHours": 5
            // }
            //
            // readStudent() lee ese JSON y lo transforma
            // en un objeto de la clase Student.
            Student input = readStudent(exchange);

            // Delegamos la creación en StudentService.
            //
            // El servicio se encargará de:
            // 1. Validar los datos.
            // 2. Solicitar al repositorio que guarde el estudiante.
            // 3. Devolver el estudiante creado.
            Student createdStudent = service.createStudent(input);

            // Si el servicio devuelve null, significa
            // que los datos no cumplen las reglas de validación.
            if (createdStudent == null) {

                // Devolvemos HTTP 400 (Bad Request).
                sendError(
                        exchange,
                        400,
                        "Name is required and study hours cannot be negative"
                );
                return;
            }

            // Si el estudiante se ha creado correctamente,
            // devolvemos sus datos convertidos a JSON.
            //
            // HTTP 201 (Created) indica que se ha creado
            // un nuevo recurso en el servidor.
            //
            // Ejemplo:
            //
            // {
            //     "id": 1,
            //     "name": "Ana",
            //     "studyHours": 5
            // }
            sendJson(exchange, 201, gson.toJson(createdStudent));
            return;
        }

        // Si se utiliza un método no permitido para /students,
        // devolvemos HTTP 405 (Method Not Allowed).
        //
        // Por ejemplo: DELETE /students
        sendError(exchange, 405, "Method not allowed");
    }


    // ============================================================
    // 4. OPERACIONES SOBRE UN ESTUDIANTE CONCRETO
    // ============================================================

    /**
     * Gestiona las operaciones sobre un estudiante identificado
     * mediante su identificador numérico.
     *
     * GET    /students/{id} -> Consultar estudiante.
     * PUT    /students/{id} -> Modificar estudiante.
     * DELETE /students/{id} -> Eliminar estudiante.
     *
     * Parámetros:
     * exchange -> Información del intercambio HTTP.
     * method   -> Método HTTP solicitado.
     * id       -> Identificador del estudiante.
     */
    private void handleStudent(
            HttpExchange exchange,
            String method,
            int id) throws IOException {

        // --------------------------------------------------------
        // CASO 1: GET /students/{id}
        // --------------------------------------------------------

        if ("GET".equals(method)) {

            // Solicitamos a StudentService el estudiante
            // cuyo identificador coincide con el recibido.
            //
            // El servicio delegará la búsqueda en el repositorio.
            Student student = service.getStudent(id);

            // Si el repositorio no encuentra al estudiante,
            // el servicio devuelve null.
            if (student == null) {

                // HTTP 404: el recurso solicitado no existe.
                sendError(exchange, 404, "Student not found");
                return;
            }

            // Convertimos el estudiante a JSON
            // y lo devolvemos con HTTP 200 (OK).
            sendJson(exchange, 200, gson.toJson(student));
            return;
        }


        // --------------------------------------------------------
        // CASO 2: PUT /students/{id}
        // --------------------------------------------------------

        if ("PUT".equals(method)) {

            // Primero comprobamos que el estudiante exista.
            Student existingStudent = service.getStudent(id);

            if (existingStudent == null) {

                // No podemos modificar un estudiante inexistente.
                sendError(exchange, 404, "Student not found");
                return;
            }

            // Leemos los nuevos datos del cuerpo de la petición.
            //
            // readStudent() convierte el JSON recibido
            // en un objeto Student.
            //
            // Después, StudentService actualiza al estudiante
            // utilizando el identificador indicado en la URL.
            Student updatedStudent = service.updateStudent(
                    id,
                    readStudent(exchange)
            );

            // Si el servicio devuelve null, los nuevos datos
            // no cumplen las reglas de validación.
            if (updatedStudent == null) {

                sendError(
                        exchange,
                        400,
                        "Name is required and study hours cannot be negative"
                );
                return;
            }

            // Devolvemos HTTP 200 con los datos actualizados.
            sendJson(exchange, 200, gson.toJson(updatedStudent));
            return;
        }


        // --------------------------------------------------------
        // CASO 3: DELETE /students/{id}
        // --------------------------------------------------------

        if ("DELETE".equals(method)) {

            // Solicitamos al servicio que elimine el estudiante.
            //
            // El operador ! significa negación.
            //
            // Por tanto, esta condición se cumple cuando
            // deleteStudent(id) devuelve false.
            //
            // false indica que no se ha podido eliminar
            // porque el estudiante no existe.
            if (!service.deleteStudent(id)) {

                sendError(exchange, 404, "Student not found");
                return;
            }

            // Si la eliminación ha tenido éxito, devolvemos
            // HTTP 204 (No Content).
            //
            // Este código indica que la operación se completó
            // correctamente, pero no devuelve datos en el cuerpo.
            //
            // El valor -1 indica que no enviaremos contenido.
            exchange.sendResponseHeaders(204, -1);

            // Cerramos el intercambio HTTP.
            exchange.close();
            return;
        }

        // Si el método no es GET, PUT o DELETE,
        // devolvemos HTTP 405 (Method Not Allowed).
        sendError(exchange, 405, "Method not allowed");
    }


    // ============================================================
    // 5. SOLICITAR UNA PREDICCIÓN AL MICROSERVICIO PYTHON
    // ============================================================

    /**
     * Gestiona las solicitudes de predicción de un estudiante.
     *
     * GET /students/{id}/prediction
     *
     * FLUJO DE LA PETICIÓN:
     *
     * 1. El navegador solicita la predicción a Java.
     * 2. StudentController recibe la petición HTTP.
     * 3. StudentService busca al estudiante.
     * 4. StudentService utiliza PredictionClient.
     * 5. PredictionClient envía una petición HTTP a Python.
     * 6. Python calcula la nota utilizando la fórmula ficticia.
     * 7. Python devuelve la nota a Java.
     * 8. Java construye un JSON con el resultado.
     * 9. El navegador recibe y muestra la predicción.
     *
     * IMPORTANTE:
     * El navegador nunca se comunica directamente con Python.
     */
    private void handlePrediction(
            HttpExchange exchange,
            String method,
            int id) throws IOException {

        // Para solicitar una predicción únicamente permitimos GET.
        if (!"GET".equals(method)) {

            sendError(exchange, 405, "Method not allowed");
            return;
        }

        // Comprobamos que exista el estudiante solicitado.
        Student student = service.getStudent(id);

        if (student == null) {

            // Si el estudiante no existe, devolvemos HTTP 404.
            sendError(exchange, 404, "Student not found");
            return;
        }

        try {

            // Solicitamos la predicción a StudentService.
            //
            // Internamente, StudentService utilizará
            // PredictionClient para comunicarse con Python.
            //
            // El resultado es un número decimal (double).
            double predictedGrade = service.predictGrade(id);

            // Construimos manualmente el objeto JSON
            // que devolveremos al navegador.
            JsonObject response = new JsonObject();

            // Añadimos los datos del estudiante.
            response.addProperty("id", student.getId());
            response.addProperty("name", student.getName());
            response.addProperty(
                    "studyHours",
                    student.getStudyHours()
            );

            // Añadimos la nota estimada por Python.
            response.addProperty("predictedGrade", predictedGrade);

            // La respuesta tendrá un formato parecido a:
            //
            // {
            //     "id": 1,
            //     "name": "Ana",
            //     "studyHours": 5,
            //     "predictedGrade": 7.5
            // }
            //
            // Convertimos el objeto a JSON y devolvemos HTTP 200.
            sendJson(exchange, 200, gson.toJson(response));

        } catch (InterruptedException exception) {

            // La comunicación con Python puede ser interrumpida
            // mientras esperamos a que termine la operación.
            //
            // Restauramos la marca de interrupción del hilo
            // para que pueda ser detectada posteriormente.
            Thread.currentThread().interrupt();

            // Respondemos al cliente indicando que el servicio
            // de predicción no está disponible.
            //
            // HTTP 503 = Service Unavailable.
            sendError(
                    exchange,
                    503,
                    "Prediction service is unavailable"
            );

        } catch (IOException exception) {

            // Si ocurre un problema de comunicación con Python,
            // por ejemplo porque su servidor está apagado,
            // devolvemos HTTP 503.
            //
            // El controlador transforma el problema interno
            // en una respuesta HTTP comprensible para el navegador.
            sendError(
                    exchange,
                    503,
                    "Prediction service is unavailable"
            );
        }
    }


    // ============================================================
    // 6. CONVERTIR JSON EN OBJETOS JAVA
    // ============================================================

    /**
     * Lee los datos enviados por el cliente en el cuerpo
     * de una petición HTTP y los convierte en un objeto Student.
     *
     * Ejemplo de JSON recibido:
     *
     * {
     *     "name": "Ana",
     *     "studyHours": 5
     * }
     *
     * Resultado:
     * Un objeto Student con esos valores.
     */
    private Student readStudent(HttpExchange exchange) {

        // exchange.getRequestBody() obtiene el cuerpo
        // de la petición HTTP como un flujo de bytes.
        //
        // InputStreamReader transforma esos bytes en caracteres.
        //
        // StandardCharsets.UTF_8 indica la codificación
        // de caracteres utilizada.
        InputStreamReader reader = new InputStreamReader(
                exchange.getRequestBody(),
                StandardCharsets.UTF_8
        );

        // gson.fromJson() convierte el contenido JSON
        // en un objeto Java.
        //
        // Student.class indica la clase Java que queremos crear.
        //
        // Por ejemplo:
        // {"name":"Ana","studyHours":5}
        //
        // Se transforma en un objeto de tipo Student.
        return gson.fromJson(reader, Student.class);
    }


    // ============================================================
    // 7. CONSTRUIR RESPUESTAS DE ERROR
    // ============================================================

    /**
     * Envía una respuesta HTTP de error al navegador.
     *
     * Parámetros:
     * exchange -> Intercambio HTTP actual.
     * status   -> Código de error HTTP (400, 404, 405, 503...).
     * message  -> Descripción del error.
     *
     * Los errores se devuelven en JSON para que JavaScript
     * pueda interpretarlos fácilmente.
     */
    private void sendError(
            HttpExchange exchange,
            int status,
            String message) throws IOException {

        // Creamos un objeto JSON vacío.
        JsonObject error = new JsonObject();

        // Añadimos una propiedad "error" con el mensaje.
        error.addProperty("error", message);

        // Por ejemplo:
        //
        // {
        //     "error": "Student not found"
        // }
        //
        // Delegamos el envío de la respuesta en sendJson().
        sendJson(exchange, status, gson.toJson(error));
    }


    // ============================================================
    // 8. ENVIAR RESPUESTAS HTTP EN FORMATO JSON
    // ============================================================

    /**
     * Construye y envía una respuesta HTTP al navegador.
     *
     * Este método se utiliza tanto para respuestas correctas
     * como para respuestas de error.
     *
     * Parámetros:
     * exchange -> Intercambio HTTP que estamos atendiendo.
     * status   -> Código HTTP de respuesta.
     * json     -> Cadena de texto JSON que queremos enviar.
     *
     * Ejemplo:
     *
     * HTTP/1.1 200 OK
     * Content-Type: application/json
     *
     * {
     *     "id": 1,
     *     "name": "Ana",
     *     "studyHours": 5
     * }
     */
    private void sendJson(
            HttpExchange exchange,
            int status,
            String json) throws IOException {

        // HTTP transmite información mediante bytes.
        //
        // Convertimos el texto JSON en un array de bytes,
        // utilizando la codificación UTF-8.
        byte[] body = json.getBytes(StandardCharsets.UTF_8);

        // Añadimos una cabecera HTTP que indica al navegador
        // que el contenido de la respuesta está en formato JSON.
        //
        // La cabecera Content-Type permite al cliente conocer
        // el tipo de información que está recibiendo.
        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=utf-8"
        );

        // Enviamos las cabeceras de la respuesta HTTP.
        //
        // status: código HTTP (200, 201, 400, 404...).
        // body.length: número de bytes del cuerpo de respuesta.
        exchange.sendResponseHeaders(status, body.length);

        // Obtenemos el flujo de salida del intercambio HTTP.
        //
        // A través de este flujo escribiremos los datos
        // que recibirá el navegador.
        OutputStream output = exchange.getResponseBody();

        // Enviamos el contenido JSON convertido a bytes.
        output.write(body);

        // Cerramos el flujo de salida.
        //
        // Esto indica que hemos terminado de enviar la respuesta.
        output.close();
    }
}