/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.specs

import org.openqa.selenium.By
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.Preliminary.*
import uk.gov.hmrc.ui.pages.{AuthWizard, ReturnTaskList}
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import uk.gov.hmrc.ui.tags.*

class PreliminaryQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Preliminary Questions") {

    Scenario(
      "Complete the Preliminary Questions journey as a Company",
      PreliminaryJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Then("the PreliminaryBeforeYouStart page is shown")
      PreliminaryBeforeYouStart.verifyPageTitle(PreliminaryBeforeYouStart.pageTitle)

      When("the user starts the preliminary questions")
      PreliminaryBeforeYouStart.clickContinueButton()
      Then("the PreliminaryWhoIsMakingThePurchase page is shown")
      PreliminaryWhoIsMakingThePurchase.verifyPageTitle(PreliminaryWhoIsMakingThePurchase.pageTitle)

      When("the user selects Company as the type of purchaser")
      PreliminaryWhoIsMakingThePurchase.radioButton(PreliminaryWhoIsMakingThePurchase.company)
      PreliminaryWhoIsMakingThePurchase.saveAndContinue()
      Then("the PreliminaryPurchaserName page is shown")
      PreliminaryPurchaserName.verifyPageTitle(PreliminaryPurchaserName.pageTitleCompany)

      When("the user provides the company name")
      PreliminaryPurchaserName.input(
        By.id(PreliminaryPurchaserName.companyName),
        PreliminaryPurchaserName.companyNameInput
      )
      PreliminaryPurchaserName.saveAndContinue()
      Then("the PreliminaryPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.pageTitle)

      When("the user enters the property address manually")
      PreliminaryPropertyAddress.clickAddressManually()
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.editPageTitle)
      PreliminaryPropertyAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("the ConfirmPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.confirmPageTitle)

      When("the user confirms the property address")
      PreliminaryPropertyAddress.clickConfirmAddress()
      Then("the TransactionType page is shown")
      TransactionType.verifyPageTitle(TransactionType.pageTitle)

      When("the user selects Conveyance/transfer with lease involvement as the transaction type")
      TransactionType.radioButton(TransactionType.conveyance)
      TransactionType.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user updates the purchaser type to Individual")
      PreliminaryCheckYourAnswers.clickPurchaserTypeChange()
      PreliminaryWhoIsMakingThePurchase.verifyPageTitle(PreliminaryWhoIsMakingThePurchase.pageTitle)
      PreliminaryWhoIsMakingThePurchase.radioButton(PreliminaryWhoIsMakingThePurchase.individual)
      PreliminaryWhoIsMakingThePurchase.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user updates the transaction type to Grant of lease")
      PreliminaryCheckYourAnswers.clickTransactionTypeChange()
      TransactionType.verifyPageTitle(TransactionType.pageTitle)
      TransactionType.radioButton(TransactionType.grantOfLease)
      TransactionType.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user submits the preliminary questions")
      PreliminaryCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Preliminary Questions journey as an Individual",
      PreliminaryJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Then("the PreliminaryBeforeYouStart page is shown")
      PreliminaryBeforeYouStart.verifyPageTitle(PreliminaryBeforeYouStart.pageTitle)

      When("the user starts the preliminary questions")
      PreliminaryBeforeYouStart.clickContinueButton()
      Then("the PreliminaryWhoIsMakingThePurchase page is shown")
      PreliminaryWhoIsMakingThePurchase.verifyPageTitle(PreliminaryWhoIsMakingThePurchase.pageTitle)

      When("the user selects Individual as the type of purchaser")
      PreliminaryWhoIsMakingThePurchase.radioButton(PreliminaryWhoIsMakingThePurchase.individual)
      PreliminaryWhoIsMakingThePurchase.saveAndContinue()
      Then("the PreliminaryPurchaserName page is shown")
      PreliminaryPurchaserName.verifyPageTitle(PreliminaryPurchaserName.pageTitle)

      When("the user provides the purchaser's surname")
      PreliminaryPurchaserName.input(
        By.id(PreliminaryPurchaserName.purchasersSurname),
        PreliminaryPurchaserName.purchasersSurnameInput
      )
      PreliminaryPurchaserName.saveAndContinue()
      Then("the PreliminaryPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.pageTitle)

      When("the user enters the property address manually")
      PreliminaryPropertyAddress.clickAddressManually()
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.editPageTitle)
      PreliminaryPropertyAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("the ConfirmPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.confirmPageTitle)

      When("the user confirms the property address")
      PreliminaryPropertyAddress.clickConfirmAddress()
      Then("the TransactionType page is shown")
      TransactionType.verifyPageTitle(TransactionType.pageTitle)

      When("the user selects Conveyance/transfer with lease involvement as the transaction type")
      TransactionType.radioButton(TransactionType.conveyance)
      TransactionType.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user updates the purchaser name")
      PreliminaryCheckYourAnswers.clickPurchaserNameChange()
      PreliminaryPurchaserName.verifyPageTitle(PreliminaryPurchaserName.pageTitle)
      PreliminaryPurchaserName.input(
        By.id(PreliminaryPurchaserName.purchasersSurname),
        PreliminaryPurchaserName.purchasersSurnameCYAInput
      )
      PreliminaryPurchaserName.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user updates the property address")
      PreliminaryCheckYourAnswers.clickPropertyAddressChange()
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.pageTitle)
      PreliminaryPropertyAddress.clickAddressManually()
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.editPageTitle)
      PreliminaryPropertyAddress.enterAddressManually("523", "AGC", "TE11 1TS")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.confirmPageTitle)
      PreliminaryPropertyAddress.clickConfirmAddress()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)
    }
  }
}
