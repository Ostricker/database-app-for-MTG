package controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

public class MainWindowController {

  public TableView<List<Object>> importedCardsTableView;
  public TableView<List<Object>> savedCardsTableView;

  public List<Object> selectedImportedCard;
  public List<Object> selectedSavedCard;

  public JFXButton moveic;
  public JFXButton deletesc;
  public JFXButton higlightic;
  public JFXButton deleteic;
  public JFXButton exportic;
  @FXML private ResourceBundle resources;

  @FXML private URL location;

  @FXML
  void initialize() {
    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.populateTableView(importedCardsTableView, "importedCards");
    conn.populateTableView(savedCardsTableView, "savedCards");
    importedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    savedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    importedCardsTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                selectedImportedCard = importedCardsTableView.getSelectionModel().getSelectedItem();
                System.out.println(selectedImportedCard);
              }
            });

    savedCardsTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                selectedSavedCard = savedCardsTableView.getSelectionModel().getSelectedItem();
                System.out.println(selectedSavedCard);
              }
            });
  }

  public void onMoveImportedCard(ActionEvent actionEvent) {
    List<Object> selectedItem = importedCardsTableView.getSelectionModel().getSelectedItem();
    importedCardsTableView.getItems().remove(selectedItem);
    importedCardsTableView.getSelectionModel().clearSelection();

    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.insertIntoSavedCards(selectedImportedCard);
    savedCardsTableView.getColumns().clear();
    conn.populateTableView(savedCardsTableView, "savedCards");
  }

  public void onDeleteImportedCard(ActionEvent actionEvent) {
    List<Object> selectedItem = importedCardsTableView.getSelectionModel().getSelectedItem();
    importedCardsTableView.getItems().remove(selectedItem);
    importedCardsTableView.getSelectionModel().clearSelection();
  }

  public void onHighlightImportedCard(ActionEvent actionEvent) {}

  public void onExportImportedCard(ActionEvent actionEvent) {}

  public void onDeleteSavedCard(ActionEvent actionEvent) {
    List<Object> selectedItem = savedCardsTableView.getSelectionModel().getSelectedItem();
    savedCardsTableView.getItems().remove(selectedItem);
    savedCardsTableView.getSelectionModel().clearSelection();
  }

  public void onImportCVS(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {

      System.out.println("File selected: " + selectedFile.getName());
    } else {
      System.out.println("File selection cancelled.");
    }
  }

  public void onAbout(ActionEvent actionEvent) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("O programu");
    alert.setHeaderText("Program pro Argam Apy Alaverdian, aby se mu dobře pracovalo.");
    alert.setContentText("Vytvořeno 2019 by Jan Olšanský");

    alert.showAndWait();
  }
}
