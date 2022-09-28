package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import ru.netology.web.page.LoginPageV2;
import ru.netology.web.page.LoginPageV3;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void setup() {
      Configuration.holdBrowserOpen = true;
      open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
      var loginPage = new LoginPageV1();
      var authInfo = DataHelper.getAuthInfo();
      var firstCardInfo = DataHelper.getFirstCardInfo(authInfo);
      var secondCardInfo = DataHelper.getSecondCardInfo(authInfo);

      var verificationPage = loginPage.validLogin(authInfo);
      var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
      var dashboardPage = verificationPage.validVerify(verificationCode);

      var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo.getDataTestId());
      var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo.getDataTestId());

      assertEquals(firstCardBalance, 10000);
      assertEquals(secondCardBalance, 10000);
    }
}

