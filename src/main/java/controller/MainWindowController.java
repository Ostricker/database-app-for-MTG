package controller;

import java.net.URL;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.DataResult;

import javax.xml.crypto.Data;

public class MainWindowController {

    public TableView<List<Object>> importedCardsTableView;
    public TableView<List<Object>> savedCardsTableView;

    public JFXButton moveic;
    public JFXButton deletesc;
    public JFXButton higlightic;
    public JFXButton deleteic;
    public JFXButton exportic;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
        SQLiteJDBCDriverConnection sqlConnection = new SQLiteJDBCDriverConnection();
        sqlConnection.populateTableView(importedCardsTableView, "importedCards");
        sqlConnection.populateTableView(savedCardsTableView, "savedCards");
    }

        public void onMoveImportedCard(ActionEvent actionEvent) {
    }

    public void onDeleteImportedCard(ActionEvent actionEvent) {
    }

    public void onHighlightImportedCard(ActionEvent actionEvent) {
    }

    public void onExportImportedCard(ActionEvent actionEvent) {
    }

    public void onDeleteSavedCard(ActionEvent actionEvent) {
    }
}
