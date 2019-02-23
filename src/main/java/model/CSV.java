package model;

import com.opencsv.CSVReader;
import controller.SQLiteJDBCDriverConnection;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSV {

  public void importCSV(File selectedFile) throws IOException {
    CSVReader reader = new CSVReader(new FileReader(selectedFile.getAbsoluteFile()), '\t');
    String[] nextLine;
    SQLiteJDBCDriverConnection sql = new SQLiteJDBCDriverConnection();
    int lineNumber = 0;
    while ((nextLine = reader.readNext()) != null) {
      lineNumber++;

      sql.insertIntoImportedCards(
          nextLine[0], nextLine[1], nextLine[3], nextLine[4], nextLine[5], nextLine[7]);
    }
  }
}
