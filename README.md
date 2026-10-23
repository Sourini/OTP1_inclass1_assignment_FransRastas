# Temperature Converter

A small Java application created for an OTP1 in-class assignment. The project includes basic temperature-conversion logic, automated unit testing, code-coverage reporting and Docker packaging.

## 1. Assignment Description

The assignment was to implement and verify a simple temperature converter using Java and Maven.

The application provides the following functionality:

- Convert Fahrenheit to Celsius.
- Convert Celsius to Fahrenheit.
- Convert Kelvin to Celsius.
- Determine if the temperature is extreme.

The main deliverables included are:

- The `TemperatureConverter` Java class.
- Automated JUnit tests.
- JaCoCo HTML code-coverage report.
- Maven build configuration.
- Dockerfile for packaging and running the application.
- Jenkins pipeline for automated testing and building the Docker image.

## 2. Technologies and Tools Used

- **Java 21** — application implementation.
- **Apache Maven** — dependency management, compilation, testing, and packaging.
- **JUnit 5.10.2** — automated unit testing.
- **Maven Surefire Plugin 3.5.5** — execution of JUnit tests.
- **JaCoCo 0.8.11** — test-coverage measurement and HTML report generation.
- **Jenkins** — automated CI pipeline.
- **Docker** — container image creation and application execution.
- **Docker Hub** — storage of published container images.
- **Git and GitHub** — version control and source-code hosting.
- **IntelliJ IDEA** — development environment used for the project.

## 3. Design Approach and Implementation Method

The program uses a single class, `TemperatureConverter`, containing simple methods:

```java
fahrenheitToCelsius(double fahrenheit)
celsiusToFahrenheit(double celsius)
kelvinToCelsius(double kelvin)
isExtremeTemperature(double celsius)
```

The `main` method creates a converter instance and prints example Fahrenheit, Celsius, and Kelvin conversions.

The project's layout:

```text
src/
├── main/java/org/example/TemperatureConverter.java
└── test/java/org/example/TemperatureConverterTest.java
```

The Jenkins pipeline automates the following stages:

1. Build the Maven project.
2. Run the automated tests.
3. Generate and publish the test results.
6. Build and run a Docker image.

## 4. Testing and Quality Assurance Steps

### Automated test cases and results

The JUnit test class contains four test methods covering the application's core behavior.

| Test scenario | Inputs | Expected outcome |
|---|---|---|
| Fahrenheit to Celsius | `32`, `212`, `-40` °F | `0`, `100`, `-40` °C |
| Celsius to Fahrenheit | `0`, `100`, `-40` °C | `32`, `212`, `-40` °F |
| Extreme-temperature checks | Values below `-40`, above `50`, inside the range, and at both boundaries | Only values below `-40` or above `50` return `true` |
| Kelvin to Celsius | `0`, `100`, `273.15` K | `-273.15`, `-173.15`, `0` °C |

All assertions in the current test suite pass when the project is tested with Maven. Decimal results use a tolerance of `0.1`.

Run the tests with:

```bash
mvn clean test
```

After the tests finish, open the generated JaCoCo report at:

```text
target/site/jacoco/index.html
```

### Manual verification

## 5. How to Run

### Prerequisites

- JDK 21
- Apache Maven 3.x
- Git
- Docker Desktop

### Clone the repository

```bash
git clone https://github.com/Sourini/OTP1_inclass1_assignment_FransRastas.git
cd OTP1_inclass1_assignment_FransRastas
```

### Run the tests and generate coverage

```bash
mvn clean test
```

### Build and run the JAR

```bash
mvn clean package
java -jar target/otp_jutska.jar
```

Expected output:

```text
32°F = 0.0°C
100°C = 212.0°F
0 K = -273.15°C
```

### Build and run with Docker

```bash
docker build -t temperature-converter .
docker run --rm temperature-converter
```

## Repository Structure

```text
.
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── src
    ├── main/java/org/example/TemperatureConverter.java
    └── test/java/org/example/TemperatureConverterTest.java
```

## Author

Frans Rastas
