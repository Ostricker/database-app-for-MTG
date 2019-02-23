package controller;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import model.CSV;

public class MainWindowController {

  private String savedCardsTable = "savedCards";
  private String importedCardsTable = "importedCards";

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
    refreshTables();
    // importedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    // savedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    initializeListeners();
  }

  public void onMoveImportedCard(ActionEvent actionEvent) {
    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.insertIntoSavedCards(selectedImportedCard);

    deleteCall(importedCardsTableView, importedCardsTable);
    refreshTables();
  }

  public void onDeleteImportedCard(ActionEvent actionEvent) {
    deleteCall(importedCardsTableView, importedCardsTable);
  }

  public void onHighlightImportedCard(ActionEvent actionEvent) {}

  public void onExportImportedCard(ActionEvent actionEvent) {}

  public void onDeleteSavedCard(ActionEvent actionEvent) {
    deleteCall(savedCardsTableView, savedCardsTable);
  }

  public void onImportCVS(ActionEvent actionEvent) throws IOException {
    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(null);

    if (selectedFile != null) {
      System.out.println("File selected: " + selectedFile.getName());
      CSV csv = new CSV();
      csv.importCSV(selectedFile);
      refreshTables();

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

  public void deleteCall(TableView<List<Object>> tableView, String table) {
    List<Object> selectedItem = tableView.getSelectionModel().getSelectedItem();
    tableView.getItems().remove(selectedItem);

    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.deleteFromTable(table, (Integer) selectedItem.get(0));

    refreshTables();
  }

  public void refreshTables() {
    importedCardsTableView.getSelectionModel().clearSelection();
    savedCardsTableView.getSelectionModel().clearSelection();

    SQLiteJDBCDriverConnection sql = new SQLiteJDBCDriverConnection();
    sql.populateTableView(importedCardsTableView, importedCardsTable);
    sql.populateTableView(savedCardsTableView, savedCardsTable);
  }

  public void initializeListeners() {
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
              }
            });
  }

  public void onDeleteImportedTable(ActionEvent actionEvent) {
    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.deleteFromTable(importedCardsTable);

    refreshTables();
  }
}
