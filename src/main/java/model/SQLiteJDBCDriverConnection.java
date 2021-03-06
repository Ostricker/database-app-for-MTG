package model;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteJDBCDriverConnection {

  private static Connection connect() {
    String testDatabase = "src/main/java/database/mainDatabase.db";
    String liveDatabase = "C:/Users/Public/SQLite/mainDatabase.db";

    Connection conn = null;
    try {
      String url = "jdbc:sqlite:" + testDatabase;

      conn = DriverManager.getConnection(url);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  private DataResult getAllData(String tableName) throws SQLException {
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
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Boolean insertIntoSavedCards(List<Object> list) {
    String sql =
        "INSERT INTO savedCards(ID,\"Card Name\",Sell,Buy,Have,Want) VALUES("
            + list.get(0)
            + ",\""
            + list.get(1)
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
      return true;
    } catch (SQLException e1) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Chyba");
      alert.setContentText(e1.toString());
      alert.showAndWait();
      System.out.println(e1);
      return false;
    }
  }

  public void insertIntoImportedCards(
      String id, String cardName, String sell, String buy, String have, String want) {
    String sql =
        "INSERT INTO importedCards(ID,\"Card Name\",Sell,Buy,Have,Want) VALUES("
            + id
            + ",\""
            + cardName
            + "\","
            + sell
            + ","
            + buy
            + ","
            + have
            + ","
            + want
            + ")";

    try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
      pstmt.executeUpdate();
      System.out.println("INSERT successful");
    } catch (SQLException e1) {
      System.out.println(e1);
    }
  }

  public void deleteFromTable(String tableName, Integer id) {
    String sql = "DELETE FROM " + tableName + " WHERE ID=" + id;

    try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
      pstmt.executeUpdate();
      System.out.println("DELETE successful");
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void deleteFromTable(String tableName) {
    String sql = "DELETE FROM " + tableName;

    try (PreparedStatement pstmt = connect().prepareStatement(sql)) {
      pstmt.executeUpdate();
      System.out.println("DELETE successful");
    } catch (SQLException e) {
      System.out.println(e);
    }
  }
}
