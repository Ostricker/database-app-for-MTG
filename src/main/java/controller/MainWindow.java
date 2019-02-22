package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainWindow {

    public TableView importedCardsTableView;
    public TableColumn wantic;
    public TableColumn IDic;
    public TableColumn cardNameic;
    public TableColumn sellic;
    public TableColumn buyic;
    public TableColumn haveic;
    public TableView savedCardsTableView;
    public TableColumn IDsc;
    public TableColumn cardNamesc;
    public TableColumn sellsc;
    public TableColumn buysc;
    public TableColumn havesc;
    public TableColumn wantsc;
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
