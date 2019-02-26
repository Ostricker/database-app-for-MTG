package model;

public class CSVCardBean {

  public String ID, CardName, Sell, Buy, Have, Want;

  public String getID() {
    return ID;
  }

  public String getCardName() {
    return CardName;
  }

  public String getSell() {
    return Sell;
  }

  public String getBuy() {
    return Buy;
  }

  public String getHave() {
    return Have;
  }

  public String getWant() {
    return Want;
  }

  public void setID(String ID) {
    this.ID = ID;
  }

  public void setCardName(String cardName) {
    CardName = cardName;
  }

  public void setSell(String sell) {
    Sell = sell;
  }

  public void setBuy(String buy) {
    Buy = buy;
  }

  public void setHave(String have) {
    Have = have;
  }

  public void setWant(String want) {
    Want = want;
  }

  @Override
  public String toString() {
    return "Karta [ID = "
        + ID
        + ", Card Name = "
        + CardName
        + ", Sell = "
        + Sell
        + ", Buy = "
        + Buy
        + ", Have= "
        + Have
        + ", Want = "
        + Want
        + "]";
  }
}
