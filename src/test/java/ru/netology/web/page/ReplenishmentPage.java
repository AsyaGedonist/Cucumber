package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {

    private SelenideElement inputAmount = $("[data-test-id=amount] input");
    private SelenideElement inputFrom = $("[data-test-id=from] input");
    private SelenideElement replenButton = $("[data-test-id=action-transfer]");
    public DashboardPage replenishment (int sum, DataHelper.CardInfo cardInfo){
        inputAmount.setValue(String.valueOf(sum));
        inputFrom.setValue(cardInfo.getNumber());
        replenButton.click();
        return new DashboardPage();
    }

}
