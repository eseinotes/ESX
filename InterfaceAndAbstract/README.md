# Interfaces y clases abstractas en Java

## Descripción del proyecto

Este proyecto es un ejemplo didáctico de **programación orientada a objetos (POO)** en Java. Su finalidad es mostrar cómo se utilizan las **interfaces**, las **clases abstractas**, la **herencia**, la **sobrescritura de métodos** y el **polimorfismo**.

Para facilitar su comprensión, el código se divide en dos ejemplos independientes:

1. Un sistema de pagos basado en una interfaz.
2. Una jerarquía de animales basada en una clase abstracta.

## Objetivos de aprendizaje

Al estudiar y ejecutar este proyecto, el alumnado podrá:

- Comprender qué es una interfaz y cómo se implementa.
- Comprender qué es una clase abstracta y cómo se hereda de ella.
- Diferenciar entre `implements` y `extends`.
- Reconocer el uso de métodos abstractos y métodos ya implementados.
- Aplicar la anotación `@Override` correctamente.
- Entender el polimorfismo mediante variables de un tipo general que contienen objetos de tipos más específicos.
- Identificar situaciones en las que conviene utilizar una interfaz o una clase abstracta.

## Tecnologías utilizadas

- Java 26, según la configuración del proyecto.
- Apache Maven.
- Aplicación de consola.

## Estructura del proyecto

```text
InterfaceAndAbstract/
├── pom.xml
└── src/main/java/com/mycompany/interfaceandabstract/
    ├── InterfaceAndAbstract.java
    ├── Payment.java
    ├── CreditCardPayment.java
    ├── PayPalPayment.java
    ├── Animal.java
    ├── Dog.java
    └── Cat.java
```

## Ejemplo 1: interfaces y medios de pago

### La interfaz `Payment`

`Payment` define un contrato común para cualquier forma de pago:

```java
public interface Payment {
    void pay(double amount);
}
```

La interfaz declara qué operación debe existir, pero no determina en este caso cómo se realiza. Toda clase que implemente `Payment` está obligada a proporcionar una implementación del método `pay`.

### Implementaciones de la interfaz

Las clases `CreditCardPayment` y `PayPalPayment` implementan el mismo contrato:

```java
public class CreditCardPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " euros con tarjeta.");
    }
}
```

```java
public class PayPalPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Pagando " + amount + " euros con PayPal.");
    }
}
```

Las dos clases ofrecen la operación `pay`, aunque cada una la resuelve de una forma diferente. Esto permite añadir en el futuro otros medios de pago, como una transferencia bancaria, sin modificar el contrato principal.

### Polimorfismo mediante una interfaz

En la clase principal se crean objetos concretos utilizando referencias del tipo `Payment`:

```java
Payment payment1 = new CreditCardPayment();
Payment payment2 = new PayPalPayment();

payment1.pay(100);
payment2.pay(50);
```

Aunque ambas variables tienen el mismo tipo declarado, Java ejecuta la implementación correspondiente al objeto real. A este comportamiento se le denomina **polimorfismo**.

## Ejemplo 2: clases abstractas y animales

### La clase abstracta `Animal`

`Animal` representa las características comunes de todos los animales del ejemplo:

```java
public abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " está comiendo.");
    }

    public abstract void makeSound();
}
```

Esta clase contiene:

- Un atributo compartido: `name`.
- Un constructor que inicializa dicho atributo.
- Un método concreto, `eat`, cuyo comportamiento se comparte entre las subclases.
- Un método abstracto, `makeSound`, que cada subclase debe implementar.

Una clase abstracta no puede instanciarse directamente. Por tanto, no sería válido escribir `new Animal(...)`.

### Las subclases `Dog` y `Cat`

`Dog` y `Cat` heredan de `Animal` mediante `extends`. Sus constructores llaman a `super(name)` para ejecutar el constructor de la clase padre y ambas clases sobrescriben `makeSound`:

```java
public class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " dice: Guau!");
    }
}
```

El gato utiliza la misma estructura, pero su implementación imprime `Miau!`.

### Polimorfismo mediante una clase abstracta

El método `main` también utiliza referencias de tipo `Animal`:

```java
Animal animal1 = new Dog("Toby");
Animal animal2 = new Cat("Michi");

animal1.makeSound();
animal2.makeSound();
```

Las variables se declaran como `Animal`, pero contienen un `Dog` y un `Cat`. Al llamar a `makeSound`, Java selecciona en tiempo de ejecución el método sobrescrito de cada objeto.

## Interfaz frente a clase abstracta

| Aspecto | Interfaz `Payment` | Clase abstracta `Animal` |
|---|---|---|
| Relación utilizada | `implements` | `extends` |
| Finalidad en el proyecto | Definir un contrato común | Definir una base común |
| Estado compartido | No se utiliza | Sí: el atributo `name` |
| Constructor | No | Sí |
| Comportamiento ya implementado | No en este ejemplo | Sí: el método `eat` |
| Comportamiento obligatorio | `pay` | `makeSound` |
| Herencia múltiple | Una clase puede implementar varias interfaces | Una clase solo puede extender una clase |

Como orientación general:

- Se utiliza una **interfaz** cuando diferentes clases deben cumplir el mismo contrato, aunque no pertenezcan necesariamente a la misma familia.
- Se utiliza una **clase abstracta** cuando varias clases relacionadas necesitan compartir estado, construcción o comportamiento común.

## Flujo de ejecución

La clase `InterfaceAndAbstract` contiene el método `main`. El programa realiza las siguientes acciones:

1. Crea un pago con tarjeta y otro con PayPal.
2. Ejecuta ambos pagos mediante el contrato `Payment`.
3. Crea un perro y un gato.
4. Ejecuta el comportamiento compartido `eat`.
5. Ejecuta el sonido específico de cada animal.
6. Repite la llamada a los sonidos utilizando referencias polimórficas de tipo `Animal`.

La salida aproximada es:

```text
Pagando 100.0 euros con tarjeta.
Pagando 50.0 euros con PayPal.
Toby está comiendo.
Toby dice: Guau!
Michi está comiendo.
Michi dice: Miau!
Toby dice: Guau!
Michi dice: Miau!
```

## Compilación y ejecución

Es necesario tener instalado un JDK compatible con la versión indicada en `pom.xml` y Apache Maven.

Para compilar el proyecto:

```bash
mvn compile
```

Para ejecutar la clase principal:

```bash
mvn exec:java
```

También puede importarse como proyecto Maven desde un IDE y ejecutar directamente el método `main` de `InterfaceAndAbstract.java`.

## Conceptos importantes que deben observarse

- `Payment` y `Animal` no se utilizan para crear directamente los objetos concretos del ejemplo.
- `CreditCardPayment` y `PayPalPayment` cumplen el mismo contrato.
- `Dog` y `Cat` reutilizan el atributo, el constructor y el método concreto de `Animal`.
- `@Override` indica que un método implementa o sobrescribe un método definido en un tipo superior.
- El tipo de la referencia determina qué operaciones se pueden invocar; el tipo real del objeto determina qué implementación se ejecuta.
- El polimorfismo reduce el acoplamiento y facilita ampliar un programa con nuevas implementaciones.

## Actividades propuestas

1. Crear una clase `BankTransferPayment` que implemente `Payment`.
2. Crear una clase `Cow` que herede de `Animal` e implemente su sonido.
3. Añadir un método concreto `sleep()` a `Animal` y utilizarlo con todos los animales.
4. Guardar varios objetos `Payment` en una colección y recorrerla para ejecutar distintos pagos.
5. Guardar varios animales en una colección de tipo `Animal` y llamar a `makeSound()` sin comprobar su clase concreta.
6. Investigar qué ocurriría si una clase no implementase el método abstracto heredado.
7. Debatir por qué `Payment` se ha modelado como interfaz y `Animal` como clase abstracta.

## Preguntas de autoevaluación

- ¿Se puede crear un objeto directamente a partir de `Animal`? ¿Por qué?
- ¿Qué obliga a hacer `implements Payment`?
- ¿Qué diferencia existe entre `eat()` y `makeSound()` dentro de `Animal`?
- ¿Por qué una variable `Animal` puede contener un objeto `Dog`?
- ¿Qué método se ejecuta al llamar a `animal1.makeSound()`?
- ¿Cuándo sería más apropiado utilizar una interfaz que una clase abstracta?

## Conclusión

Las interfaces y las clases abstractas permiten trabajar con abstracciones y definir estructuras extensibles. En este proyecto, `Payment` demuestra cómo varias clases pueden cumplir un mismo contrato, mientras que `Animal` muestra cómo varias subclases pueden compartir estado y comportamiento y, al mismo tiempo, proporcionar sus propias implementaciones. Ambos mecanismos hacen posible aplicar polimorfismo y diseñar código más flexible y mantenible.
