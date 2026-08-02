package cat.informaticassa.icfact.ui.main.pagines.client.table;

import cat.informaticassa.icfact.client.model.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public final class ClientColumns {
    private ClientColumns() {
    }

    public static TableColumn<Client, String> nom() {
        TableColumn<Client, String> columna = new TableColumn<>("Nom");
        columna.setCellValueFactory(new PropertyValueFactory<>("nom"));
        return columna;
    }

    public static TableColumn<Client, String> nif() {
        TableColumn<Client, String> columna = new TableColumn<>("NIF");
        columna.setCellValueFactory(new PropertyValueFactory<>("nif"));
        return columna;
    }

    public static TableColumn<Client, String> telefon() {
        TableColumn<Client, String> columna = new TableColumn<>("Telèfon");
        columna.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        return columna;
    }

    public static TableColumn<Client, String> poblacio() {
        TableColumn<Client, String> columna = new TableColumn<>("Població");
        columna.setCellValueFactory(cell -> {
            Client client = cell.getValue();
            if (client.getAdreca() == null ||
                    client.getAdreca().getPoblacio() == null) {
                return new SimpleStringProperty("");
            }
            return new SimpleStringProperty(
                    client.getAdreca().getPoblacio().getNom()
            );
        });
        return columna;
    }

    public static TableColumn<Client, String> email() {
        TableColumn<Client, String> columna = new TableColumn<>("Email");
        columna.setCellValueFactory(new PropertyValueFactory<>("email"));
        return columna;
    }
}
