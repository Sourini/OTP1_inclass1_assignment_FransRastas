package org.example;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Main extends Application {

    private final TemperatureConverter converter =
            new TemperatureConverter();

    private final TemperatureUnitDAO unitDAO =
            new TemperatureUnitDAO();

    private final TempRecordDAO recordDAO =
            new TempRecordDAO();

    private final ObservableList<TempRecord> records =
            FXCollections.observableArrayList();

    static double parse(String text) {
        return Double.parseDouble(
                text.trim().replace(',', '.')
        );
    }

    static String format(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DBConnection.init();

        List<TemperatureUnit> units = unitDAO.findAll();

        TextField temperatureField = new TextField();
        TextField distanceField = new TextField();
        TextField timeField = new TextField();

        ComboBox<TemperatureUnit> fromBox =
                new ComboBox<>(
                        FXCollections.observableArrayList(units)
                );

        ComboBox<TemperatureUnit> toBox =
                new ComboBox<>(
                        FXCollections.observableArrayList(units)
                );

        fromBox.getSelectionModel().select(0);
        toBox.getSelectionModel().select(1);

        Button saveButton =
                new Button("Convert and save");

        Label message = new Label();

        GridPane form = new GridPane();
        form.setHgap(8);
        form.setVgap(8);

        form.addRow(
                0,
                new Label("Temperature"),
                temperatureField,
                fromBox,
                new Label("to"),
                toBox
        );

        form.addRow(
                1,
                new Label("Distance (km)"),
                distanceField
        );

        form.addRow(
                2,
                new Label("Time (hours)"),
                timeField
        );

        form.add(saveButton, 1, 3);

        TableView<TempRecord> table = createTable();
        table.setItems(records);

        saveButton.setOnAction(event -> {
            try {
                TemperatureUnit from = fromBox.getValue();
                TemperatureUnit to = toBox.getValue();

                double input =
                        parse(temperatureField.getText());

                double distance =
                        parse(distanceField.getText());

                double time =
                        parse(timeField.getText());

                double result = converter.convert(
                        input,
                        from.symbol(),
                        to.symbol()
                );

                TempRecord record = new TempRecord(
                        from.id(),
                        to.id(),
                        from.symbol(),
                        to.symbol(),
                        input,
                        result,
                        distance,
                        time
                );

                recordDAO.insert(record);
                loadRecords();

                message.setText(
                        "Saved: "
                                + format(result)
                                + " "
                                + to.symbol()
                );
            } catch (Exception exception) {
                message.setText(
                        "Error: " + exception.getMessage()
                );
            }
        });

        loadRecords();

        VBox root = new VBox(
                10,
                form,
                table,
                message
        );

        root.setPadding(new Insets(12));

        stage.setTitle("Temperature Converter");
        stage.setScene(new Scene(root, 720, 450));
        stage.show();
    }

    private TableView<TempRecord> createTable() {
        TableView<TempRecord> table =
                new TableView<>();

        table.getColumns().add(
                column(
                        "From",
                        record ->
                                format(record.inputValue())
                                        + " "
                                        + record.fromSymbol()
                )
        );

        table.getColumns().add(
                column(
                        "To",
                        record ->
                                format(record.resultValue())
                                        + " "
                                        + record.toSymbol()
                )
        );

        table.getColumns().add(
                column(
                        "Distance km",
                        record -> format(record.distanceKm())
                )
        );

        table.getColumns().add(
                column(
                        "Time h",
                        record -> format(record.timeHours())
                )
        );

        table.getColumns().add(
                column(
                        "Speed km/h",
                        record -> format(record.speedKmh())
                )
        );

        return table;
    }

    private TableColumn<TempRecord, String> column(
            String title,
            Function<TempRecord, String> value
    ) {
        TableColumn<TempRecord, String> column =
                new TableColumn<>(title);

        column.setCellValueFactory(
                data -> new SimpleStringProperty(
                        value.apply(data.getValue())
                )
        );

        return column;
    }

    private void loadRecords() throws Exception {
        records.setAll(recordDAO.findAll());
    }

    public static void main(String[] args) {
        launch(args);
    }
}