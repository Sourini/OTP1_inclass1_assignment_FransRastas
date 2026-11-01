package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        DBConnection.init();
        showView(Locale.of("en", "US"));
    }

    public static void showView(Locale locale) {
        try {
            ResourceBundle messages = ResourceBundle.getBundle(
                    "MessagesBundle",
                    locale
            );

            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(
                            "/org/example/main-view.fxml"
                    ),
                    messages
            );

            Parent root = loader.load();

            if ("ar".equals(locale.getLanguage())) {
                root.setNodeOrientation(
                        NodeOrientation.RIGHT_TO_LEFT
                );
            } else {
                root.setNodeOrientation(
                        NodeOrientation.LEFT_TO_RIGHT
                );
            }

            Scene scene = primaryStage.getScene();

            if (scene == null) {
                scene = new Scene(root, 760, 500);
                primaryStage.setScene(scene);
            } else {
                scene.setRoot(root);
            }

            primaryStage.setTitle(
                    messages.getString("title")
            );

            primaryStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    static double parse(String text) {
        return Double.parseDouble(
                text.trim().replace(',', '.')
        );
    }

    static String format(double value) {
        return String.format(Locale.US, "%.2f", value);
    }

    public static void main(String[] args) {
        launch(args);
    }
}