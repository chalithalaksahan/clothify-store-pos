package util;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.util.function.Function;

public class ComboBoxSearchUtil {
    public static <T> void makeSearchable(ComboBox<T> comboBox, ObservableList<T> items, Function<T, String> stringMapper) {

        // 1. Wrap the list
        FilteredList<T> filteredList = new FilteredList<>(items, p -> true);
        comboBox.setItems(filteredList);
        comboBox.setEditable(true);

        // 2. Set the String Converter dynamically using your Function
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                // Extracts the string using the getter you pass in
                return object == null ? "" : stringMapper.apply(object);
            }

            @Override
            public T fromString(String string) {
                if (string == null || string.isEmpty()) return null;
                return items.stream()
                        .filter(item -> {
                            String itemString = stringMapper.apply(item);
                            return itemString != null && itemString.equalsIgnoreCase(string);
                        })
                        .findFirst()
                        .orElse(null);
            }
        });

        // 3. Add the Universal Search Listener
        comboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (comboBox.getSelectionModel().getSelectedItem() != null) {
                return; // Prevent text deletion upon selection
            }

            Platform.runLater(() -> {
                filteredList.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    // Search dynamically using the getter
                    String itemString = stringMapper.apply(item);
                    return itemString != null && itemString.toLowerCase().contains(newValue.toLowerCase());
                });

                if (!filteredList.isEmpty() && !comboBox.isShowing()) {
                    comboBox.show();
                }
            });
        });
    }
}
