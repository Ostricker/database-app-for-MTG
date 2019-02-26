package model;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSV {

  public void importCSV(File selectedFile) throws IOException {
    Map<String, String> mapping = new HashMap<String, String>();
    mapping.put("ID", "ID");
    mapping.put("Card Name", "CardName");
    mapping.put("Sell", "Sell");
    mapping.put("Buy", "Buy");
    mapping.put("Have", "Have");
    mapping.put("Want", "Want");

    HeaderColumnNameTranslateMappingStrategy<CSVCardBean> strategy =
        new HeaderColumnNameTranslateMappingStrategy<CSVCardBean>();
    strategy.setType(CSVCardBean.class);
    strategy.setColumnMapping(mapping);

    CSVReader csvReader = null;
    csvReader = new CSVReader(new FileReader(selectedFile.getAbsoluteFile()), '\t');
    CsvToBean csvToBean = new CsvToBean();

    List<CSVCardBean> list = csvToBean.parse(strategy, csvReader);
    SQLiteJDBCDriverConnection sql = new SQLiteJDBCDriverConnection();

    for (CSVCardBean card : list) {

      sql.insertIntoImportedCards(
          card.getID(),
          card.getCardName(),
          card.getSell(),
          card.getBuy(),
          card.getHave(),
          card.getWant());
    }
  }
}
