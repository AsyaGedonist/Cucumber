package ru.netology.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");
  private ElementsCollection cards = $$(".list__item div");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";

  public void verifyIsDashboardPage(){
    heading.shouldBe(visible);
  }

    public ReplenishmentPage replenishmentOpenV2 (String maskCard) {
    cards.findBy(Condition.text(maskCard)).find(".button").click();
    return new ReplenishmentPage();
  }

  public int getCardBalanceV2(String maskCard) {
    val text = cards.findBy(Condition.text(maskCard)).text();
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }

}
