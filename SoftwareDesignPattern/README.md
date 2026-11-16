# Patrones de diseño aplicados a una biblioteca

Proyecto didáctico en Java que compara una solución **sin patrón** con otra
**aplicando un patrón de diseño**. Todos los ejemplos usan situaciones de una
biblioteca: préstamos, búsquedas, notificaciones, impresión de carnés y
persistencia de libros.

El objetivo no es memorizar clases, sino reconocer un problema de diseño y
entender qué responsabilidades separa cada patrón.

## Organización del código

```text
src/main/java/
├── com/mycompany/softwaredesignpattern/  # Menú y punto de entrada
├── model/                                # Modelos compartidos
├── services/                             # Servicios usados por Facade
├── withoutpattern/                       # Soluciones acopladas o rígidas
└── withpattern/                          # Soluciones que aplican patrones
```

Cada subcarpeta de `withpattern` y `withoutpattern` corresponde a un patrón.
La clase `SoftwareDesignPattern` contiene el menú principal y llama al método
`run()` del ejemplo elegido.

## 1. Singleton

**Intención:** garantizar que exista una única instancia de una clase y ofrecer
un punto global de acceso a ella.

En el ejemplo, `LibraryConfig` contiene el máximo de libros que puede tomar un
estudiante.

- Sin Singleton, el administrador y el sistema de préstamos crean objetos
  distintos. Cambiar uno no cambia el otro y aparecen configuraciones
  incoherentes.
- Con Singleton, el constructor es privado, la instancia se guarda en un campo
  `static` y `getInstance()` devuelve siempre el mismo objeto.

Clases clave: `withpattern.singleton.LibraryConfig` y `WithSingleton`.

Debe usarse con prudencia: introduce estado global, dificulta ciertos tests y
no sustituye automáticamente a una buena inyección de dependencias.

## 2. Factory (fábrica)

**Intención:** concentrar la creación de objetos para que el cliente no dependa
directamente de sus clases concretas.

El sistema puede enviar una notificación por correo electrónico o SMS. Sin el
patrón, el cliente decide con un `if` qué clase instanciar. Con la fábrica,
`NotificationFactory.createNotification(type)` encapsula esa decisión y
devuelve la interfaz `Notification`.

Clases clave: `Notification`, `EmailNotification`, `SMSNotification` y
`NotificationFactory`, dentro de `withpattern.factory`.

## 3. Adapter

**Intención:** permitir que colaboren clases con interfaces incompatibles.

La impresora moderna recibe un código como `String`, mientras que la impresora
antigua necesita un `int`. Sin Adapter, el cliente repite la conversión y la
selección de impresora. `PrinterAdapter` implementa la interfaz común
`CardPrinter`, realiza la adaptación y oculta ambas impresoras al cliente.

Clases clave: `CardPrinter`, `LegacyPrinter`, `ModernPrinter` y
`PrinterAdapter`, dentro de `withpattern.adapter`.

La idea esencial es que el cliente solo conoce la interfaz esperada; el
adaptador traduce la llamada al componente existente.

## 4. Facade

**Intención:** ofrecer una interfaz sencilla para un subsistema formado por
varios servicios.

Realizar un préstamo requiere validar al usuario, comprobar el libro, registrar
el préstamo, actualizar su disponibilidad y enviar una confirmación. Sin
Facade, el cliente conoce y coordina todos esos pasos. `LibraryFacade` los
encapsula detrás de una sola operación: `borrowBook(studentId, bookId)`.

Clases clave: `withpattern.facade.LibraryFacade` y los servicios de `services`.

La fachada simplifica el uso del subsistema; no impide utilizar directamente
sus servicios cuando una operación especializada lo necesita.

## 5. Strategy

**Intención:** encapsular algoritmos intercambiables detrás de una interfaz
común.

La biblioteca permite buscar por título, autor o año. Sin Strategy, un método
grande contiene una cadena de condiciones y debe modificarse al añadir una
búsqueda. Con Strategy, `SearchStrategy` define el contrato y cada algoritmo se
implementa en una clase independiente.

Clases clave: `SearchStrategy`, `SearchByTitle`, `SearchByAuthor`,
`SearchByYear` y `withpattern.strategy.Library`.

`Library.setStrategy(...)` permite cambiar el algoritmo durante la ejecución.
Así se favorece el principio abierto/cerrado: se añaden estrategias sin cambiar
las ya existentes.

## 6. Observer

**Intención:** establecer una relación uno-a-muchos para notificar cambios sin
acoplar el emisor a receptores concretos.

Cuando se devuelve un libro, varios estudiantes quieren saber que está
disponible. Sin Observer, `Library` guarda estudiantes concretos y los notifica
uno por uno. Con el patrón, mantiene una colección de objetos `Observer` y
ofrece `subscribe()` y `unsubscribe()`.

Clases clave: `Observer`, `Student` y `withpattern.observer.Library`.

Al devolver un libro, `Library` recorre las suscripciones y llama a `update()`.
Pueden añadirse otros observadores sin modificar la biblioteca.

## 7. Repository

**Intención:** separar la lógica de dominio de los detalles de almacenamiento.

Sin Repository, la biblioteca gestiona directamente un `HashMap`. Con el
patrón, depende de la interfaz `BookRepository`, que define `save()`,
`findByIsbn()` y `findAll()`.

Clases clave: `BookRepository`, `InMemoryBookRepository`,
`MySQLBookRepository` y `withpattern.repository.Library`.

El ejemplo ejecutable usa el repositorio en memoria. La implementación MySQL es
solo un esqueleto didáctico: muestra que se puede cambiar la persistencia sin
modificar la lógica de `Library`, pero no abre una conexión real.

## 8. Decorator

**Intención:** añadir responsabilidades a un objeto dinámicamente, sin crear
una jerarquía de subclases para cada combinación posible.

El préstamo básico puede complementarse con una notificación y un registro de
log. Sin Decorator, un único método recibe banderas booleanas y acumula todas
las responsabilidades. Con el patrón, `BasicLoanService` proporciona la
operación base y cada decorador envuelve otro `LoanService`.

Clases clave: `LoanService`, `BasicLoanService`, `LoanDecorator`,
`NotificationDecorator` y `LoggingDecorator`.

Los decoradores se pueden componer:

```java
LoanService service = new LoggingDecorator(
    new NotificationDecorator(
        new BasicLoanService()
    )
);
```

El orden de envoltura importa, porque determina el orden en que se ejecutan las
responsabilidades añadidas.

## Guía para estudiar los ejemplos

Para cada patrón:

1. Ejecuta primero la opción “Sin”.
2. Localiza qué clase conoce demasiados detalles o contiene demasiadas
   decisiones.
3. Ejecuta la opción “Con”.
4. Identifica la interfaz, las implementaciones y el cliente.
5. Imagina un nuevo requisito y comprueba qué clases habría que modificar en
   ambas versiones.

Preguntas útiles: ¿qué cambia?, ¿qué permanece estable?, ¿qué dependencia se ha
invertido?, ¿se ha reducido el acoplamiento? y ¿qué coste de complejidad añade
el patrón?


