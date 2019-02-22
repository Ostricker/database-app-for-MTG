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
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

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
    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.populateTableView(importedCardsTableView, importedCardsTable);
    conn.populateTableView(savedCardsTableView, savedCardsTable);
    importedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    savedCardsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    importedCardsTableView
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (obs, oldSelection, newSelection) -> {
              if (newSelection != null) {
                selectedImportedCard = importedCardsTableView.getSelectionModel().getSelectedItem();
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

  public void onMoveImportedCard(ActionEvent actionEvent) {
    deleteCall(importedCardsTableView, importedCardsTable);

    SQLiteJDBCDriverConnection conn = new SQLiteJDBCDriverConnection();
    conn.insertIntoSavedCards(selectedImportedCard);
    conn.populateTableView(savedCardsTableView, savedCardsTable);
  }

  public void onDeleteImportedCard(ActionEvent actionEvent) {
    deleteCall(importedCardsTableView, importedCardsTable);
  }

  public void onHighlightImportedCard(ActionEvent actionEvent) {}

  public void onExportImportedCard(ActionEvent actionEvent) {}

  public void onDeleteSavedCard(ActionEvent actionEvent) {
    deleteCall(savedCardsTableView, savedCardsTable);
  }

  public void onImportCVS(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    File selectedFile = fileChooser.showOpenDialog(null);

    ICsvListReader listReader = null;
    if (selectedFile != null) {
      System.out.println("File selected: " + selectedFile.getName());
      try {
        listReader =
            new CsvListReader(
                new FileReader(selectedFile.getAbsoluteFile()), CsvPreference.STANDARD_PREFERENCE);

        listReader.getHeader(true);

        while (listReader.read() != null) System.out.println(listReader.read());

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        if (listReader != null) {
          try {
            listReader.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }

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
    conn.deleteFromTable((Integer) selectedItem.get(0), table, tableView);
  }

  public CellProcessor[] getProcessors() {

    final CellProcessor[] processors =
        new CellProcessor[] {
          new UniqueHashCode(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull(),
          new NotNull()
        };

    return processors;
  }
}
