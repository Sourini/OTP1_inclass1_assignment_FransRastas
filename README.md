# Temperature Converter

JavaFX temperature converter created for OTP1 In-class Assignment 6. The application converts temperatures, calculates speed from distance and time, and saves the results in an embedded H2 database.

## Assignment requirements

The application includes:

- A JavaFX graphical interface.
- Temperature conversion between Celsius, Fahrenheit, and Kelvin.
- Speed calculation using distance and time.
- An H2 database with two related tables.
- Database interaction through the JavaFX interface.
- JUnit tests and a JaCoCo coverage report.
- A Jenkins pipeline.
- A Docker image that can display the GUI through XMing.

## Technologies

- Java 21
- JavaFX 21
- Maven
- H2 Database
- JUnit 5
- JaCoCo
- Jenkins
- Docker and Docker Hub
- XMing or another X server

## Design and implementation

`TemperatureConverter` contains the temperature conversion and speed calculation methods.

`Main` creates the JavaFX interface. The interface accepts:

- A temperature.
- The original temperature unit.
- The target temperature unit.
- Distance in kilometres.
- Time in hours.

Pressing **Convert and save** converts the temperature, calculates the speed, saves the values to the database, and refreshes the table.

`DBConnection` creates the database schema. `TemperatureUnitDAO` reads the available units, while `TempRecordDAO` saves and reads conversion records.

The database contains two related tables:

### `temperature_unit`

Stores Celsius, Fahrenheit, and Kelvin.

### `temp_record`

Stores:

- Original and converted temperatures.
- Original and target units.
- Distance.
- Time.
- Calculated speed.

The `from_unit_id` and `to_unit_id` columns are foreign keys that reference the `temperature_unit` table.

## Testing and quality assurance

Run the tests with:

```bash
mvn clean verify
```

The tests verify:

- Fahrenheit, Celsius, and Kelvin conversions.
- Conversions using the generic `convert` method.
- Extreme-temperature boundaries.
- Speed calculation.
- Invalid input handling.
- Creation of the related database tables.
- Loading temperature units.
- Saving and reading database records.
- GUI number parsing and formatting.

The JaCoCo HTML report is generated at:

```text
target/site/jacoco/index.html
```

## Run locally

### Prerequisites

- JDK 21
- Apache Maven

Clone the repository:

```bash
git clone https://github.com/Sourini/OTP1_inclass1_assignment_FransRastas.git
cd OTP1_inclass1_assignment_FransRastas
```

Run the JavaFX application:

```bash
mvn clean javafx:run
```

The embedded H2 database is created automatically inside the `data` directory.

## Run with Docker and XMing

### 1. Start XMing

Start XMing before running the container.

When using XLaunch, select:

1. **Multiple windows**
2. **Start no client**
3. **Disable access control**

### 2. Build the Docker image

```bash
docker build -t sourini/temperature-converter .
```

### 3. Run the Docker image

```bash
docker run --rm -e DISPLAY=host.docker.internal:0.0 sourini/temperature-converter
```

The JavaFX window should appear through XMing.

Docker Hub image:

<https://hub.docker.com/r/sourini/temperature-converter>

## Jenkins pipeline

The Jenkins pipeline:

1. Runs the Maven tests and build.
2. Publishes the JUnit results.
3. Publishes the JaCoCo coverage report.
4. Builds the Docker image.
5. Runs the JavaFX application through XMing.
6. Pushes the build-number and `latest` tags to Docker Hub.

Jenkins requires:

- A Maven installation named `maven3`.
- Docker available to the Jenkins service.
- A Docker Hub username/password credential with the ID `dockerhub`.
- XMing running before the **Run Docker Image** stage.

## Project structure

```text
.
├── Dockerfile
├── Jenkinsfile
├── pom.xml
└── src
    ├── main/java/org/example
    │   ├── Main.java
    │   ├── Launcher.java
    │   ├── TemperatureConverter.java
    │   ├── DBConnection.java
    │   ├── TemperatureUnit.java
    │   ├── TemperatureUnitDAO.java
    │   ├── TempRecord.java
    │   └── TempRecordDAO.java
    └── test/java/org/example
        ├── TemperatureConverterTest.java
        ├── DatabaseTest.java
        └── MainTest.java
```

## Author

Frans Rastas