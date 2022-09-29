package ru.netology.web.test;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @AfterEach
    void startBalance () {
        open("http://localhost:9999");

        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalance = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalanceV2(secondCardInfo);
        var differencesAmount = firstCardBalance - secondCardBalance;
                
        if (differencesAmount < 0) {
                dashboardPage.replenishmentOpenV2(firstCardInfo)
                        .replenishment(differencesAmount/2, secondCardInfo);
        } else if (differencesAmount > 0) {
                dashboardPage.replenishmentOpenV2(secondCardInfo)
                        .replenishment(differencesAmount/2, firstCardInfo);
        }
    }
    
    @Test
    @DisplayName("Should view right start balance")
    void shouldViewStartBalance() {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalance = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalanceV2(secondCardInfo);

        assertEquals(firstCardInfo.getStartBalance(), firstCardBalance);
        assertEquals(firstCardInfo.getStartBalance(), secondCardBalance);
    }

    @ParameterizedTest
    @DisplayName("Should transfer to first card from second")
    @CsvSource({"9999", "10000"})
    void shouldTransferMoneyBetweenOwnCardsV1(int amount) {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalanceBefore = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalanceBefore = dashboardPage.getCardBalanceV2(secondCardInfo);

        dashboardPage.replenishmentOpenV2(firstCardInfo)
                .replenishment(amount, secondCardInfo);

        var firstCardBalanceAfter = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalanceAfter = dashboardPage.getCardBalanceV2(secondCardInfo);

        assertEquals((firstCardBalanceBefore + amount), firstCardBalanceAfter);
        assertEquals((secondCardBalanceBefore - amount), secondCardBalanceAfter);
    }

    @ParameterizedTest
    @DisplayName("Should transfer to second card from first")
    @CsvSource({"9999", "10000"})
    void shouldTransferMoneyBetweenOwnCardsV2(int amount) {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalanceBefore = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalanceBefore = dashboardPage.getCardBalanceV2(secondCardInfo);

        dashboardPage.replenishmentOpenV2(secondCardInfo)
                .replenishment(amount, firstCardInfo);

        var firstCardBalanceAfter = dashboardPage.getCardBalanceV2(firstCardInfo);
        var secondCardBalanceAfter = dashboardPage.getCardBalanceV2(secondCardInfo);

        assertEquals((firstCardBalanceBefore - amount), firstCardBalanceAfter);
        assertEquals((secondCardBalanceBefore + amount), secondCardBalanceAfter);
    }

//    @Test
//    @DisplayName("Should not transfer to first card from second - not enough money")
//    void shouldNotTransferMoneyBetweenOwnCardsV1() {
//        var loginPage = new LoginPageV1();
//        var authInfo = DataHelper.getAuthInfo();
//        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
//        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);
//
//        var verificationPage = loginPage.validLogin(authInfo);
//        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
//        var dashboardPage = verificationPage.validVerify(verificationCode);
//
//        var firstCardBalanceBefore = dashboardPage.getCardBalanceV2(firstCardInfo);
//        var secondCardBalanceBefore = dashboardPage.getCardBalanceV2(secondCardInfo);
//
//        dashboardPage.replenishmentOpenV2(firstCardInfo)
//                .replenishment(10_001, secondCardInfo);
//
//        var firstCardBalanceAfter = dashboardPage.getCardBalanceV2(firstCardInfo);
//        var secondCardBalanceAfter = dashboardPage.getCardBalanceV2(secondCardInfo);
//
//        assertEquals((firstCardBalanceBefore + 100_000), firstCardBalanceAfter);
//        assertEquals((secondCardBalanceBefore - 100_000), secondCardBalanceAfter);
//    }



}

