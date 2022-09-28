package ru.netology.web.test;

import com.codeborne.selenide.Configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @Test
    @DisplayName("Should view right start balance")
    void shouldEqualStartBalance() {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo.getDataTestId());
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo.getDataTestId());

        assertEquals(firstCardBalance, firstCardInfo.getStartBalance());
        assertEquals(secondCardBalance, secondCardInfo.getStartBalance());
    }

    @ParameterizedTest
    @DisplayName("Should transfer borderline to first card from second")
    @CsvSource(
            {"1000"}
    )
    void shouldTransferMoneyBetweenOwnCardsV1(double amount) {
        var loginPage = new LoginPageV1();
        var authInfo = DataHelper.getAuthInfo();
        var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
        var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        var dashboardPage = verificationPage.validVerify(verificationCode);

        var firstCardBalanceBefore = dashboardPage.getCardBalance(firstCardInfo.getDataTestId());
        var secondCardBalanceBefore = dashboardPage.getCardBalance(secondCardInfo.getDataTestId());

        dashboardPage.replenishmentOpen(firstCardInfo)
                .replenishment(amount, secondCardInfo);

        var firstCardBalanceAfter = dashboardPage.getCardBalance(firstCardInfo.getDataTestId());
        var secondCardBalanceAfter = dashboardPage.getCardBalance(secondCardInfo.getDataTestId());

        assertEquals((firstCardBalanceBefore + amount), firstCardBalanceAfter);
        assertEquals((secondCardBalanceBefore - amount), secondCardBalanceAfter);
    }
}

