package ru.netology.web.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private SelenideElement inputAmount = $("[data-test-id=amount] input");
  private SelenideElement inputFrom = $("[data-test-id=from] input");
  private SelenideElement replenButton = $("[data-test-id=action-transfer]");
  private ElementsCollection cards = $$(".list__item div");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";
  private final String cardNumberStart = "**** **** **** ";
  private final String cardNumberFinish = ", баланс";

  public DashboardPage() {
    heading.shouldBe(visible);
  }

//  public int getCardBalanceV1(String id) {
//    val text = cards.findBy(attribute("data-test-id", id)).text();
//    return extractBalance(text);
//  }

    public int getCardBalanceV2(DataHelper.CardInfo cardInfo) {
    val text = cards.findBy(Condition.text(cardInfo.getMaskNumber())).text();
    return extractBalance(text);
  }


  private int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }

//  public DashboardPage replenishmentOpenV1 (DataHelper.CardInfo cardInfo) {
//    cards.findBy(attribute("data-test-id", cardInfo.getDataTestId())).find(".button").click();
//    return new DashboardPage();
//  }

    public DashboardPage replenishmentOpenV2 (DataHelper.CardInfo cardInfo) {
    cards.findBy(Condition.text(cardInfo.getMaskNumber())).find(".button").click();
    return new DashboardPage();
  }


  public DashboardPage replenishment (int sum, DataHelper.CardInfo cardInfo){
    inputAmount.setValue(String.valueOf(sum));
    inputFrom.setValue(cardInfo.getNumber());
    replenButton.click();
    return new DashboardPage();
  }

}
