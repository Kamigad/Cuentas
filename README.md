# 🏦 Sistema de Cuentas Bancarias

Aplicación web multicapa para la gestión de cuentas bancarias, desarrollada con **Spring Boot**, **JSF (Jakarta Faces)**, **PrimeFaces** y **JoinFaces**. Permite listar, agregar, editar y eliminar cuentas bancarias sin recargar la página, usando componentes AJAX nativos de PrimeFaces.

---

## 🚀 Funcionalidades

- **Listar** todas las cuentas bancarias registradas
- **Agregar** una nueva cuenta mediante un formulario modal (`p:dialog`)
- **Editar** una cuenta existente reutilizando el mismo modal
- **Eliminar** una cuenta con ventana de confirmación (`p:confirmDialog`)
- Mensajes de retroalimentación (`p:growl`) para cada operación
- Actualización parcial de la tabla vía AJAX sin recargar la página

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Rol |
|---|---|---|
| Java | 21 (LTS) | Lenguaje base |
| Spring Boot | 4.1.0 | Framework backend |
| Spring Data JPA | 4.1.0 | Capa de acceso a datos |
| JoinFaces | 6.1.0 | Puente entre Spring Boot y JSF |
| Jakarta Faces (JSF) | — | Framework de vistas |
| PrimeFaces | — | Librería de componentes UI |
| PrimeFlex | 3.3.1 | Utilidades CSS (layout) |
| MySQL | 8.x | Base de datos |
| Lombok | — | Reducción de código boilerplate |
| Maven | 3.x | Gestión de dependencias |

---

## 🏗️ Arquitectura multicapas

El proyecto sigue una **arquitectura N-capas**, separando responsabilidades:

```
┌───────────────────────────────┐
│  Capa de Presentación         │  PrimeFaces + JSF (.xhtml)
├───────────────────────────────┤
│  Capa de Servicio             │  Spring (lógica de negocio)
├───────────────────────────────┤
│  Capa de Datos                │  Spring Data JPA
├───────────────────────────────┤
│  Base de Datos                │  MySQL
└───────────────────────────────┘
```

JoinFaces actúa como puente: permite que los Managed Beans de JSF (`@Named`, `@ViewScoped`) sean gestionados e inyectados por el contenedor de Spring (`@Autowired` funciona dentro de ellos).

---

## 📦 Modelo de datos

Una **Cuenta** se compone de:

| Campo | Tipo | Descripción |
|---|---|---|
| `idCuenta` | Integer | Identificador único (autogenerado) |
| `nombre` | String | Nombre del titular de la cuenta |
| `tipoCuenta` | TipoCuenta (enum) | AHORRO, INVERSION o CREDITO |
| `saldo` | Double | Saldo actual de la cuenta |

El campo `tipoCuenta` usa `@Enumerated(EnumType.STRING)`, por lo que MySQL lo almacena como un tipo `ENUM('AHORRO','CREDITO','INVERSION')` nativo, validando los valores tanto en Java como en la base de datos.

---

## 📁 Estructura del proyecto

```
src/main/
├── java/gm/cuentas/
│   ├── controlador/
│   │   └── IndexControlador.java
│   ├── modelo/
│   │   ├── Cuenta.java
│   │   └── TipoCuenta.java
│   ├── repositorio/
│   │   └── CuentaRepositorio.java
│   └── servicio/
│       ├── ICuentaBancariaServicio.java
│       └── CuentaBancariaServicio.java
└── resources/
    ├── META-INF/
    │   └── resources/
    │       └── index.xhtml
    └── application.properties
```

---

## ⚙️ Requisitos previos

- [Java JDK 21+](https://www.oracle.com/java/technologies/downloads/) (LTS)
- [Maven 3+](https://maven.apache.org/download.cgi)
- [MySQL 8+](https://dev.mysql.com/downloads/)
- Un IDE como [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## 🔧 Configuración y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tu-usuario/cuentas.git
cd cuentas
```

### 2. Configurar las variables de entorno

| Variable | Descripción | Ejemplo |
|---|---|---|
| `URL` | URL de conexión a MySQL | `jdbc:mysql://localhost:3306/cuentas_db?createDatabaseIfNotExist=true` |
| `USER_NAME` | Usuario de MySQL | `root` |
| `PASSWORD_DB` | Contraseña de MySQL | `tu_password` |

**En IntelliJ:** `Run > Edit Configurations > Environment Variables`

### 3. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

O directamente desde IntelliJ ejecutando la clase principal.

### 4. Acceder a la aplicación

```
http://localhost:8080/index.xhtml
```

---

## 📌 Acciones disponibles (Managed Bean)

A diferencia de una app Spring MVC tradicional, JSF no expone rutas HTTP por acción; todas las operaciones pasan por un único punto de entrada (`/index.xhtml`) y se resuelven mediante Expression Language (`#{}`) contra los métodos del bean `indexControlador`.

| Método del Bean | Disparado por | Descripción |
|---|---|---|
| `cargarDatos()` | Menú "Inicio" | Recarga la lista de cuentas desde la BD |
| `agregarCuenta()` | `@PostConstruct` / interno | Inicializa `cuentaAgregada` como objeto vacío |
| `guardarCuenta()` | Botón "Guardar" del modal | Inserta o actualiza según si `idCuenta` es `null` |
| `eliminarCuenta()` | Botón "Sí" del `confirmDialog` | Elimina la cuenta de BD y de la lista en memoria |

---

## 🎨 Nota sobre el tema visual

El tema de PrimeFaces se configura en `application.properties`:

```properties
joinfaces.primefaces.theme=vela
```

Y PrimeFlex se referencia fijando una versión específica (`3.3.1`), ya que versiones más recientes (`@latest`) dependen de variables CSS (`--p-yellow-500`, etc.) que no están definidas fuera de PrimeNG/PrimeReact.
