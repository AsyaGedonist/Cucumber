package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPageV1;
import ru.netology.page.ReplenishmentPage;
import ru.netology.page.VerificationPage;

import static org.junit.Assert.assertEquals;


public class TemplateSteps {
    private static LoginPageV1 loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;

    private static ReplenishmentPage replenishmentPage;

    @Пусть("открыта страница с формой авторизации {string}")
    public void openAuthPage(String url) {
        loginPage = Selenide.open(url, LoginPageV1.class);
    }

    @Когда("пользователь пытается авторизоваться с именем {string} и паролем {string}")
    public void loginWithNameAndPassword(String login, String password) {
        verificationPage = loginPage.validLogin(login, password);
    }

    @И("пользователь вводит проверочный код 'из смс' {string}")
    public void setValidCode(String verificationCode) {
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @И("пользователь вызывает кнопку Пополнения карты с маскированным номером {string}")
    public void increaseFirstCard(String maskCard) {
        replenishmentPage = dashboardPage.replenishmentOpenV2(maskCard);
    }

    @И("пользователь вводит сумму {int} для пополнения с карты {string}")
    public void replenishmentFiveThousandsToFirstCard(int sum, String cardNumber) {
        dashboardPage = replenishmentPage.replenishment(sum, cardNumber);
    }


    @Тогда("баланс карты с маскированным номером {string} == {int}")
    public void balanceFirstCard(String maskNumber, int balance) {
        int getBalance = dashboardPage.getCardBalanceV2(maskNumber);
        assertEquals(getBalance, balance);
    }
}