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

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

public class Main extends Application {

    private TemperatureUnitDAO unitDao = new TemperatureUnitDAO();
    private TempRecordDAO recordDao = new TempRecordDAO();
    private TemperatureConverter converter = new TemperatureConverter();

    private ObservableList<TempRecord> rows = FXCollections.observableArrayList();
    private Label status = new Label("");

    public static double parse(String text) {
        return Double.parseDouble(text.trim().replace(',', '.'));
    }

    public static String fmt(double number) {
        return String.format(Locale.of("fi", "FI"), "%.2f", number);
    }

    @Override
    public void start(Stage stage) throws Exception {
        DBConnection.init();
        List<TemperatureUnit> units = unitDao.findAll();

        TextField valueField = new TextField();
        TextField distanceField = new TextField();
        TextField timeField = new TextField();

        ComboBox<TemperatureUnit> fromBox = new ComboBox<>();
        fromBox.getItems().addAll(units);
        fromBox.getSelectionModel().select(0);

        ComboBox<TemperatureUnit> toBox = new ComboBox<>();
        toBox.getItems().addAll(units);
        toBox.getSelectionModel().select(1);

        Button saveButton = new Button("Save");
        Button deleteButton = new Button("Delete");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, new Label("Temperature"), valueField, fromBox, new Label("to"), toBox);
        form.addRow(1, new Label("Distance (km)"), distanceField);
        form.addRow(2, new Label("Time (h)"), timeField);
        form.addRow(3, saveButton, deleteButton);

        // table with the saved records
        TableView<TempRecord> table = new TableView<>(rows);

        TableColumn<TempRecord, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getId())));

        TableColumn<TempRecord, String> fromColumn = new TableColumn<>("From");
        fromColumn.setCellValueFactory(data -> new SimpleStringProperty(
                fmt(data.getValue().getInputValue()) + " " + data.getValue().getFromSymbol()));

        TableColumn<TempRecord, String> toColumn = new TableColumn<>("To");
        toColumn.setCellValueFactory(data -> new SimpleStringProperty(
                fmt(data.getValue().getResultValue()) + " " + data.getValue().getToSymbol()));

        TableColumn<TempRecord, String> distanceColumn = new TableColumn<>("Distance km");
        distanceColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getDistanceKm())));

        TableColumn<TempRecord, String> timeColumn = new TableColumn<>("Time h");
        timeColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getTimeHours())));

        TableColumn<TempRecord, String> speedColumn = new TableColumn<>("Speed km/h");
        speedColumn.setCellValueFactory(data -> new SimpleStringProperty(fmt(data.getValue().getSpeedKmh())));

        table.getColumns().add(idColumn);
        table.getColumns().add(fromColumn);
        table.getColumns().add(toColumn);
        table.getColumns().add(distanceColumn);
        table.getColumns().add(timeColumn);
        table.getColumns().add(speedColumn);

        saveButton.setOnAction(e -> {
            try {
                double value = parse(valueField.getText());
                double distance = parse(distanceField.getText());
                double time = parse(timeField.getText());
                TemperatureUnit from = fromBox.getValue();
                TemperatureUnit to = toBox.getValue();

                double result = converter.convert(value, from.getSymbol(), to.getSymbol());

                TempRecord record = new TempRecord(from.getId(), to.getId(), value, result, distance, time);
                recordDao.insert(record);
                refreshTable();

                status.setText("Saved: " + fmt(value) + from.getSymbol() + " = " + fmt(result) + to.getSymbol());
            } catch (NumberFormatException ex) {
                status.setText("Please type numbers in all the fields");
            } catch (Exception ex) {
                status.setText("Error: " + ex.getMessage());
            }
        });

        deleteButton.setOnAction(e -> {
            TempRecord selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                status.setText("Select a row first");
                return;
            }
            try {
                recordDao.delete(selected.getId());
                refreshTable();
                status.setText("Deleted record " + selected.getId());
            } catch (SQLException ex) {
                status.setText("Error: " + ex.getMessage());
            }
        });

        refreshTable();

        VBox root = new VBox(10, form, table, status);
        root.setPadding(new Insets(10));

        stage.setTitle("Temperature Converter");
        stage.setScene(new Scene(root, 640, 480));
        stage.show();
    }

    private void refreshTable() throws SQLException {
        rows.setAll(recordDao.findAll());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
