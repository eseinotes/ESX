
/* ============================================================
   STUDENT PERFORMANCE PREDICTOR
   Frontend: app.js

   Este archivo contiene el código JavaScript del navegador.

   Sus principales responsabilidades son:
   1. Recoger los datos introducidos por el usuario.
   2. Enviar peticiones HTTP a la API Java.
   3. Recibir las respuestas del servidor en formato JSON.
   4. Actualizar la interfaz HTML con los resultados.

   IMPORTANTE:
   El navegador solo se comunica con la API Java (puerto 8080).
   Nunca accede directamente al HashMap ni al servicio Python.
   ============================================================ */


// ============================================================
// 1. OBTENER LOS ELEMENTOS DE LA PÁGINA HTML
// ============================================================

// document.getElementById() busca un elemento del HTML
// utilizando su atributo id.
//
// Por ejemplo, document.getElementById("student-form")
// obtiene el elemento <form id="student-form">.

const form = document.getElementById("student-form");
const studentIdInput = document.getElementById("student-id");
const nameInput = document.getElementById("student-name");
const studyHoursInput = document.getElementById("study-hours");
const formTitle = document.getElementById("form-title");
const cancelEditButton = document.getElementById("cancel-edit");
const studentList = document.getElementById("student-list");
const message = document.getElementById("message");

// Array que contiene los estudiantes obtenidos del servidor.
//
// IMPORTANTE: Este array es una copia temporal en el navegador.
// Los datos originales están almacenados en el HashMap de Java.
let currentStudents = [];


// ============================================================
// 2. ASOCIAR EVENTOS A LOS ELEMENTOS HTML
// ============================================================

// addEventListener() permite ejecutar una función cuando
// ocurre un determinado evento sobre un elemento HTML.
//
// Cuando el usuario envía el formulario, llamamos a saveStudent.
form.addEventListener("submit", saveStudent);

// Cuando pulsa el botón de cancelar, restauramos el formulario.
cancelEditButton.addEventListener("click", resetForm);

// Al cargar el JavaScript, solicitamos los estudiantes a Java.
// Esto permite mostrar los estudiantes existentes al abrir la web.
loadStudents();


// ============================================================
// 3. CONSULTAR ESTUDIANTES: GET /students
// ============================================================

/*
 * Obtiene la lista de estudiantes desde la API Java.
 *
 * Flujo:
 * Navegador -> StudentController -> StudentService
 *           -> StudentRepository -> HashMap
 *
 * La respuesta vuelve siguiendo el camino inverso.
 *
 * async indica que la función puede ejecutar operaciones
 * asíncronas mediante await.
 */
async function loadStudents() {

    // try permite controlar posibles errores.
    try {

        // fetch() realiza una petición HTTP al servidor.
        //
        // Al no indicar el método, utiliza GET.
        //
        // La URL es relativa al servidor de nuestra página:
        // http://localhost:8080/students
        //
        // await espera el resultado de la operación asíncrona
        // sin bloquear el navegador.
        const response = await fetch("/students");

        // response.json() lee el cuerpo de la respuesta HTTP
        // e interpreta su contenido JSON.
        //
        // En este caso obtenemos un array de estudiantes.
        const students = await response.json();

        // Guardamos la lista recibida en una variable local.
        currentStudents = students;

        // Llamamos a la función encargada de mostrar
        // los estudiantes en el HTML.
        renderStudents(students);

    } catch (error) {

        // Si ocurre un error de comunicación o de procesamiento,
        // informamos al usuario.
        showMessage("Could not load students.", true);
    }
}


// ============================================================
// 4. CREAR O ACTUALIZAR ESTUDIANTES: POST / PUT
// ============================================================

/*
 * Recoge los datos del formulario y los envía a la API Java.
 *
 * Si estamos creando un estudiante:
 *   POST /students
 *
 * Si estamos modificando uno existente:
 *   PUT /students/{id}
 */
async function saveStudent(event) {

    // Cuando enviamos un formulario HTML, el navegador
    // normalmente recarga la página.
    //
    // preventDefault() evita ese comportamiento para que
    // podamos gestionar el envío desde JavaScript.
    event.preventDefault();

    // Recuperamos el identificador del estudiante.
    //
    // Si está vacío, estamos creando uno nuevo.
    // Si contiene un ID, estamos modificando uno existente.
    const id = studentIdInput.value;

    // Construimos un objeto JavaScript con los datos del formulario.
    const student = {

        // trim() elimina los espacios iniciales y finales.
        name: nameInput.value.trim(),

        // Los campos HTML devuelven texto.
        // Number() convierte ese texto a un número.
        studyHours: Number(studyHoursInput.value)
    };

    // Variables que utilizaremos para construir la petición.
    let url;
    let method;

    // Decidimos qué operación HTTP necesitamos realizar.
    if (id === "") {

        // Crear un nuevo estudiante.
        url = "/students";
        method = "POST";

    } else {

        // Modificar un estudiante existente.
        url = "/students/" + id;
        method = "PUT";
    }

    try {

        // Enviamos una petición HTTP a la API Java.
        const response = await fetch(url, {

            // Método HTTP seleccionado anteriormente.
            method: method,

            // Indicamos que el cuerpo contiene información JSON.
            headers: {
                "Content-Type": "application/json"
            },

            // JSON.stringify() convierte un objeto JavaScript
            // en una cadena de texto JSON.
            //
            // Ejemplo:
            // {"name":"Ana","studyHours":5}
            //
            // Este texto será enviado en el cuerpo de la petición.
            body: JSON.stringify(student)
        });

        // response.ok es true si el servidor devuelve
        // un código HTTP de éxito (entre 200 y 299).
        //
        // Por ejemplo:
        // 201 Created -> estudiante creado correctamente.
        // 400 Bad Request -> los datos no son válidos.
        if (!response.ok) {

            // Si la API devuelve un error, mostramos su mensaje.
            await showApiError(response);
            return;
        }

        // Restauramos el formulario.
        resetForm();

        // Mostramos un mensaje de confirmación.
        showMessage("Student saved.", false);

        // Volvemos a consultar los estudiantes para mostrar
        // la información actualizada en la página.
        await loadStudents();

    } catch (error) {

        // Error de comunicación con el servidor.
        showMessage("Could not save the student.", true);
    }
}


// ============================================================
// 5. MOSTRAR LOS ESTUDIANTES EN LA PÁGINA HTML
// ============================================================

/*
 * Recibe un array de estudiantes y genera los elementos HTML
 * necesarios para mostrarlos en pantalla.
 *
 * Esta función NO se comunica con Java.
 * Solo modifica el contenido de la página web.
 *
 * Esto se conoce como manipulación del DOM.
 */
function renderStudents(students) {

    // Eliminamos los elementos anteriores de la lista.
    studentList.replaceChildren();

    // Comprobamos si el array está vacío.
    if (students.length === 0) {

        // Creamos un elemento HTML <p>.
        const emptyMessage = document.createElement("p");

        // Añadimos su contenido de texto.
        emptyMessage.textContent = "No students yet.";

        // Insertamos el párrafo en la página.
        studentList.appendChild(emptyMessage);
        return;
    }

    // Recorremos todos los estudiantes del array.
    for (let index = 0; index < students.length; index++) {

        // Obtenemos el estudiante actual.
        const student = students[index];

        // Creamos una tarjeta HTML para el estudiante.
        const card = document.createElement("article");

        // Asignamos una clase CSS para definir su apariencia.
        card.className = "student-card";

        // Creamos un elemento <strong> para el nombre.
        const name = document.createElement("strong");
        name.textContent = student.name;

        // Creamos un párrafo para las horas de estudio.
        const hours = document.createElement("p");
        hours.textContent = "Study hours: " + student.studyHours;

        // Creamos un párrafo inicialmente vacío donde
        // mostraremos la predicción cuando se solicite.
        const prediction = document.createElement("p");
        prediction.className = "prediction";

        // Le asignamos un identificador HTML único.
        //
        // Ejemplo: prediction-1 para el estudiante con ID 1.
        prediction.id = "prediction-" + student.id;

        // Creamos un contenedor para los botones.
        const actions = document.createElement("div");
        actions.className = "student-actions";

        // Generamos los botones asociados al estudiante.
        //
        // Cada botón recibe:
        // - Su texto.
        // - El identificador del estudiante.
        // - La función que ejecutará cuando se pulse.
        const editButton = createButton(
            "Edit",
            student.id,
            startEdit
        );

        const deleteButton = createButton(
            "Delete",
            student.id,
            deleteStudent
        );

        const predictionButton = createButton(
            "Predict grade",
            student.id,
            requestPrediction
        );

        // Introducimos los botones dentro de su contenedor.
        actions.appendChild(editButton);
        actions.appendChild(deleteButton);
        actions.appendChild(predictionButton);

        // Construimos la tarjeta del estudiante.
        card.appendChild(name);
        card.appendChild(hours);
        card.appendChild(prediction);
        card.appendChild(actions);

        // Añadimos la tarjeta a la lista visible del HTML.
        studentList.appendChild(card);
    }
}


// ============================================================
// 6. CREAR UN BOTÓN HTML
// ============================================================

/*
 * Función auxiliar que crea un botón.
 *
 * Parámetros:
 * label        -> texto del botón.
 * studentId    -> identificador del estudiante.
 * clickHandler -> función que se ejecutará al pulsarlo.
 */
function createButton(label, studentId, clickHandler) {

    // Creamos un elemento <button>.
    const button = document.createElement("button");

    // Evitamos que el botón envíe un formulario HTML.
    button.type = "button";

    // Establecemos el texto visible.
    button.textContent = label;

    // dataset permite almacenar información adicional
    // dentro de un elemento HTML mediante atributos data-*.
    //
    // Por ejemplo, para studentId = 3:
    // <button data-student-id="3">Edit</button>
    //
    // Posteriormente podremos recuperar este identificador
    // cuando el usuario pulse el botón.
    button.dataset.studentId = String(studentId);

    // Asociamos el evento click con la función recibida.
    //
    // Por ejemplo, startEdit, deleteStudent o requestPrediction.
    button.addEventListener("click", clickHandler);

    // Devolvemos el botón creado.
    return button;
}


// ============================================================
// 7. BUSCAR UN ESTUDIANTE EN EL ARRAY LOCAL
// ============================================================

/*
 * Busca un estudiante por su identificador dentro
 * del array currentStudents.
 *
 * IMPORTANTE:
 * Esta función NO consulta la API Java.
 * Busca únicamente en los datos que ya tenemos en el navegador.
 */
function findStudentById(studentId) {

    // Recorremos los estudiantes almacenados localmente.
    for (let index = 0; index < currentStudents.length; index++) {

        const student = currentStudents[index];

        // Convertimos el ID a texto para compararlo
        // con el identificador obtenido del botón HTML.
        if (String(student.id) === studentId) {

            // Devolvemos el estudiante encontrado.
            return student;
        }
    }

    // Si no encontramos ningún estudiante, devolvemos null.
    return null;
}


// ============================================================
// 8. EDITAR UN ESTUDIANTE
// ============================================================

/*
 * Prepara el formulario para modificar un estudiante.
 *
 * Esta función NO realiza todavía ninguna petición HTTP.
 *
 * Simplemente introduce los datos actuales del estudiante
 * en el formulario para que el usuario pueda modificarlos.
 */
function startEdit(event) {

    // Obtenemos el ID almacenado en el botón pulsado.
    //
    // currentTarget representa el elemento que tiene
    // asociado el evento, en este caso el botón.
    const studentId = event.currentTarget.dataset.studentId;

    // Buscamos el estudiante en el array local.
    const student = findStudentById(studentId);

    // Comprobamos si existe.
    if (student === null) {
        showMessage("Student not found.", true);
        return;
    }

    // Rellenamos el formulario con sus datos.
    studentIdInput.value = student.id;
    nameInput.value = student.name;
    studyHoursInput.value = student.studyHours;

    // Cambiamos el título para indicar que estamos editando.
    formTitle.textContent = "Edit student";

    // Mostramos el botón para cancelar la edición.
    cancelEditButton.classList.remove("hidden");

    // Situamos el cursor en el campo del nombre.
    nameInput.focus();

    // Cuando el usuario guarde el formulario,
    // saveStudent() detectará que existe un ID
    // y realizará una petición PUT.
}


// ============================================================
// 9. ELIMINAR UN ESTUDIANTE: DELETE /students/{id}
// ============================================================

/*
 * Solicita a la API Java que elimine un estudiante.
 *
 * Ejemplo:
 * DELETE http://localhost:8080/students/1
 */
async function deleteStudent(event) {

    // Recuperamos el ID del estudiante asociado al botón.
    const studentId = event.currentTarget.dataset.studentId;

    try {

        // Enviamos una petición HTTP DELETE a Java.
        const response = await fetch(
            "/students/" + studentId,
            {
                method: "DELETE"
            }
        );

        // Comprobamos si la operación tuvo éxito.
        if (!response.ok) {
            await showApiError(response);
            return;
        }

        // Informamos al usuario.
        showMessage("Student deleted.", false);

        // Actualizamos la lista de estudiantes.
        await loadStudents();

    } catch (error) {

        // Mostramos un error si falla la comunicación.
        showMessage("Could not delete the student.", true);
    }
}


// ============================================================
// 10. SOLICITAR UNA PREDICCIÓN
// ============================================================

/*
 * Solicita la predicción de nota para un estudiante.
 *
 * Petición del navegador:
 * GET /students/{id}/prediction
 *
 * FLUJO COMPLETO:
 *
 * 1. JavaScript solicita la predicción a la API Java.
 * 2. StudentController recibe la petición.
 * 3. StudentService busca los datos del estudiante.
 * 4. PredictionClient realiza una petición POST a Python.
 * 5. Python calcula la nota mediante la fórmula ficticia.
 * 6. Python devuelve el resultado a Java.
 * 7. Java devuelve una respuesta JSON al navegador.
 * 8. JavaScript muestra la nota en el HTML.
 *
 * IMPORTANTE:
 * El navegador no conoce ni necesita conocer
 * cómo está implementado el servicio Python.
 */
async function requestPrediction(event) {

    // Recuperamos el identificador del estudiante.
    const studentId = event.currentTarget.dataset.studentId;

    // Localizamos el elemento HTML donde mostraremos
    // el resultado de la predicción.
    const result = document.getElementById(
        "prediction-" + studentId
    );

    // Mostramos un mensaje temporal mientras se calcula.
    result.textContent = "Requesting prediction...";

    try {

        // Solicitamos la predicción al backend Java.
        //
        // Aunque Python calcule la nota, el navegador
        // únicamente se comunica con Java.
        const response = await fetch(
            "/students/" + studentId + "/prediction"
        );

        // Comprobamos si Java ha devuelto un error HTTP.
        //
        // Por ejemplo:
        // 404 -> estudiante inexistente.
        // 503 -> servicio Python no disponible.
        if (!response.ok) {

            // Interpretamos el JSON con el mensaje de error.
            const errorData = await response.json();

            // Mostramos el mensaje recibido.
            result.textContent = errorData.error;
            return;
        }

        // Convertimos el JSON de respuesta a un objeto JavaScript.
        //
        // Por ejemplo:
        // {"predictedGrade":7.5}
        const prediction = await response.json();

        // Mostramos la nota estimada en la página.
        result.textContent =
            "Predicted grade: " + prediction.predictedGrade;

    } catch (error) {

        // Error de comunicación o procesamiento de la respuesta.
        result.textContent = "Could not request a prediction.";
    }
}


// ============================================================
// 11. RESTAURAR EL FORMULARIO
// ============================================================

/*
 * Devuelve el formulario a su estado inicial,
 * preparado para registrar un nuevo estudiante.
 */
function resetForm() {

    // Restauramos los valores iniciales de los campos.
    form.reset();

    // Eliminamos el identificador del estudiante.
    //
    // Esto es importante porque saveStudent() utiliza
    // este valor para decidir entre POST y PUT.
    studentIdInput.value = "";

    // Restauramos el título del formulario.
    formTitle.textContent = "Add student";

    // Ocultamos el botón de cancelar edición.
    cancelEditButton.classList.add("hidden");
}


// ============================================================
// 12. MOSTRAR ERRORES DEVUELTOS POR LA API
// ============================================================

/*
 * Recibe una respuesta HTTP de error y muestra
 * el mensaje proporcionado por el backend Java.
 *
 * Ejemplo de respuesta:
 *
 * HTTP/1.1 400 Bad Request
 *
 * {
 *   "error": "Invalid study hours"
 * }
 */
async function showApiError(response) {

    // Extraemos el objeto JSON que contiene el error.
    const errorData = await response.json();

    // Mostramos el mensaje recibido del servidor.
    showMessage(errorData.error, true);
}


// ============================================================
// 13. MOSTRAR MENSAJES EN LA INTERFAZ
// ============================================================

/*
 * Muestra un mensaje informativo o de error.
 *
 * Parámetros:
 * text    -> contenido del mensaje.
 * isError -> true si es un error; false en caso contrario.
 */
function showMessage(text, isError) {

    // Modificamos el texto del elemento HTML correspondiente.
    message.textContent = text;

    if (isError) {

        // Añadimos la clase CSS "error".
        // Esta clase permite aplicar un estilo visual diferente.
        message.classList.add("error");

    } else {

        // Eliminamos la clase CSS de error.
        message.classList.remove("error");
    }
}
