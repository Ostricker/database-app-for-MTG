package controller;

import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.DataResult;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteJDBCDriverConnection {

  public static Connection connect() {
    Connection conn = null;
    try {
      String url = "jdbc:sqlite:src/main/java/database/mainDatabase.db";

      conn = DriverManager.getConnection(url);

      System.out.println("Connection to database succesfull");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  public DataResult getAllData(String tableName) throws SQLException {
    List<List<Object>> data = new ArrayList<>();
    List<String> columnNames = new ArrayList<>();

    Statement stmt = connect().createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);

    int columnCount = rs.getMetaData().getColumnCount();
    for (int i = 1; i <= columnCount; i++) {
      columnNames.add(rs.getMetaData().getColumnName(i));
    }

    while (rs.next()) {
      List<Object> row = new ArrayList<>();
      for (int i = 1; i <= columnCount; i++) {
        row.add(rs.getObject(i));
      }
      data.add(row);
    }

    return new DataResult(columnNames, data);
  }

  public void populateTableView(TableView<List<Object>> tableView, String tableName) {
    try {
      tableView.getColumns().clear();
      SQLiteJDBCDriverConnection importedCardsDriver = new SQLiteJDBCDriverConnection();
      DataResult dataIC = importedCardsDriver.getAllData(tableName);
      for (int i = 0; i < dataIC.getNumColumns(); i++) {
        TableColumn<List<Object>, Object> column = new TableColumn<>(dataIC.getColumnName(i));
        int columnIndex = i;
        column.setCellValueFactory(
            cellData -> new SimpleObjectProperty<>(cellData.getValue().get(columnIndex)));
        tableView.getColumns().add(column);
      }
      tableView.getItems().setAll(dataIC.getData());
      tableView.sort();
      System.out.println(tableView + " populated from " + tableName);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void insertIntoSavedCards(List<Object> list) {
    String sql =
        "INSERT INTO savedCards(ID,\"Card Name\",Sell,Buy,Have,Want) VALUES("
            + list.get(0)
            + ",\""
            + list.get(1).toString()
            + "\","
            + list.get(2)
            + ","
            + list.get(3)
            + ","
            + list.get(4)
            + ","
            + list.get(5)
            + ")";

    try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
      pstmt.executeUpdate();
      System.out.println("INSERT successful");
    } catch (SQLException e1) {
      System.out.println("INSERT STATEMENT FAILED");
    }
  }

  public void deleteFromTable(Integer id, String tableName, TableView<List<Object>> tableView) {
    tableView.getSelectionModel().clearSelection();

    String sql = "DELETE FROM " + tableName + " WHERE ID=" + id;

    try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
      pstmt.executeUpdate();
      System.out.println("DELETE successful");
    } catch (SQLException e) {
      System.out.println("DELETE STATEMENT FAILED");
    }
    populateTableView(tableView, tableName);
  }
}
