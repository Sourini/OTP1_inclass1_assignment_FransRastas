package org.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private final TemperatureConverter converter =
            new TemperatureConverter();

    private final TemperatureUnitDAO unitDAO =
            new TemperatureUnitDAO();

    private final TempRecordDAO recordDAO =
            new TempRecordDAO();

    private final ObservableList<TempRecord> records =
            FXCollections.observableArrayList();

    private ResourceBundle messages;
    private Locale locale;
    private boolean initializing;

    @FXML
    private ComboBox<String> languageBox;

    @FXML
    private TextField temperatureField;

    @FXML
    private TextField distanceField;

    @FXML
    private TextField timeField;

    @FXML
    private ComboBox<TemperatureUnit> fromBox;

    @FXML
    private ComboBox<TemperatureUnit> toBox;

    @FXML
    private TableView<TempRecord> recordTable;

    @FXML
    private TableColumn<TempRecord, String> fromColumn;

    @FXML
    private TableColumn<TempRecord, String> toColumn;

    @FXML
    private TableColumn<TempRecord, String> distanceColumn;

    @FXML
    private TableColumn<TempRecord, String> timeColumn;

    @FXML
    private TableColumn<TempRecord, String> speedColumn;

    @FXML
    private Label messageLabel;

    @Override
    public void initialize(
            URL location,
            ResourceBundle resources
    ) {
        initializing = true;
        messages = resources;
        locale = resources.getLocale();

        configureLanguageBox();
        configureTemperatureUnits();
        configureTable();
        loadRecords();

        initializing = false;
    }

    private void configureLanguageBox() {
        languageBox.setItems(
                FXCollections.observableArrayList(
                        "English",
                        "Suomi",
                        "Svenska",
                        "日本語",
                        "العربية"
                )
        );

        String language = locale.getLanguage();

        switch (language) {
            case "fi" -> languageBox.setValue("Suomi");
            case "sv" -> languageBox.setValue("Svenska");
            case "ja" -> languageBox.setValue("日本語");
            case "ar" -> languageBox.setValue("العربية");
            default -> languageBox.setValue("English");
        }
    }

    private void configureTemperatureUnits() {
        try {
            List<TemperatureUnit> units =
                    unitDAO.findAll();

            fromBox.setItems(
                    FXCollections.observableArrayList(units)
            );

            toBox.setItems(
                    FXCollections.observableArrayList(units)
            );

            fromBox.getSelectionModel().select(0);
            toBox.getSelectionModel().select(1);
        } catch (SQLException exception) {
            showError(exception);
        }
    }

    private void configureTable() {
        recordTable.setItems(records);

        fromColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        format(
                                data.getValue().inputValue()
                        )
                                + " "
                                + data.getValue().fromSymbol()
                )
        );

        toColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        format(
                                data.getValue().resultValue()
                        )
                                + " "
                                + data.getValue().toSymbol()
                )
        );

        distanceColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        format(
                                data.getValue().distanceKm()
                        )
                )
        );

        timeColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        format(
                                data.getValue().timeHours()
                        )
                )
        );

        speedColumn.setCellValueFactory(
                data -> new SimpleStringProperty(
                        format(
                                data.getValue().speedKmh()
                        )
                )
        );
    }

    @FXML
    private void convertAndSave() {
        try {
            TemperatureUnit from = fromBox.getValue();
            TemperatureUnit to = toBox.getValue();

            if (from == null || to == null) {
                throw new IllegalArgumentException(
                        messages.getString("selectUnits")
                );
            }

            double input = Main.parse(
                    temperatureField.getText()
            );

            double distance = Main.parse(
                    distanceField.getText()
            );

            double time = Main.parse(
                    timeField.getText()
            );

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

            messageLabel.setText(
                    String.format(
                            messages.getString("saved"),
                            format(result),
                            to.symbol()
                    )
            );
        } catch (Exception exception) {
            showError(exception);
        }
    }

    @FXML
    private void changeLanguage(
            ActionEvent event
    ) {
        if (initializing) {
            return;
        }

        Locale selectedLocale =
                switch (languageBox.getValue()) {
                    case "Suomi" ->
                            Locale.of("fi", "FI");

                    case "Svenska" ->
                            Locale.of("sv", "SE");

                    case "日本語" ->
                            Locale.of("ja", "JP");

                    case "العربية" ->
                            Locale.of("ar", "SA");

                    default ->
                            Locale.of("en", "US");
                };

        Main.showView(selectedLocale);
    }

    private void loadRecords() {
        try {
            records.setAll(recordDAO.findAll());
        } catch (SQLException exception) {
            showError(exception);
        }
    }

    private String format(double value) {
        NumberFormat formatter =
                NumberFormat.getNumberInstance(locale);

        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        return formatter.format(value);
    }

    private void showError(Exception exception) {
        messageLabel.setText(
                String.format(
                        messages.getString("error"),
                        exception.getMessage()
                )
        );
    }
}