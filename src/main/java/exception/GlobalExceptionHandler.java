package exception;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        // 1. Print the error to your console/logs so you can fix it later
        System.err.println("CRITICAL ERROR on thread " + thread.getName());
        throwable.printStackTrace();

        // 2. Show a friendly error dialog to the user safely on the UI thread
        Platform.runLater(() -> showErrorDialog(throwable));
    }

    private void showErrorDialog(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("System Error");
        alert.setHeaderText("An unexpected error occurred!");

        // Use a generic message for the user, or the exception's actual message
        alert.setContentText(throwable.getMessage() != null ?
                throwable.getMessage() : "Please contact the system administrator.");

        // --- Pro-Tip: Add an expandable section for the actual Stack Trace ---
        // This is incredibly helpful so the shop owner can screenshot the exact error for you
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Technical details:");
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox expContent = new VBox();
        expContent.getChildren().addAll(label, textArea);

        // Add the expandable technical details to the alert
        alert.getDialogPane().setExpandableContent(expContent);

        // Show the alert and wait for the user to click OK
        alert.showAndWait();
    }
}