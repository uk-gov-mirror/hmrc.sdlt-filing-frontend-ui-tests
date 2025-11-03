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
import uk.gov.hmrc.ui.pages._
import uk.gov.hmrc.ui.pages.{AboutTheTransactionPage, AuthWizard, BeforeYouStartPage}
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class PrelimQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing frontend Task List Homepage") {
    Scenario("Hit the TaskList with no return id and is a business") {

      Given("I enter login using the Authority Wizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      When("User should be on the Before You Start page")
      BeforeYouStartPage.verifyPageTitle(BeforeYouStartPage.pageTitle)
      And("User clicks on the continue button")
      BeforeYouStartPage.saveAndContinue()
      Then("User should be on Is the User and Individual Page")
      IndividualOrCompanyPage.verifyPageTitle(IndividualOrCompanyPage.pageTitle)
      When("user clicks A Business Radio Button as a business")
      IndividualOrCompanyPage.radioButton(IndividualOrCompanyPage.business)
      And("user clicks An Save and Continue Button")
      IndividualOrCompanyPage.saveAndContinue()
      Then("User is directed to the Input your name page")
      PurchasersNamePage.verifyPageTitle(PurchasersNamePage.pageTitle)
      Then("User input their name or company name and submits")
      PurchasersNamePage.input(By.id("purchaserSurnameOrCompanyName"), "Test Name")
      PurchasersNamePage.clickSubmitButton()
      Given("User is on the Address Look-up screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.pageTitle)
      When("User clicks on the link")
      PropertyAddress.clickAddressManually()
      And("User enters the address manually")
      PropertyAddress.verifyPageTitle(PropertyAddress.editPageTitle)
      PropertyAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("User is on the Review screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.confirmPageTitle)
      And("User clicks continue")
      PropertyAddress.clickContinueButton()
      Then("User should be navigated to the About the Transaction Page")
      AboutTheTransactionPage.verifyPageTitle(AboutTheTransactionPage.pageTitle)
      And("User check the first radio button")
      AboutTheTransactionPage.radioButton("#value_0")
      And("User clicks continue")
      AboutTheTransactionPage.saveAndContinue()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
      Then("User clicks on link to change Purchaser's type")
      CYAPage.clickPurchaserTypeChange()
      Then("User should be on Is the User and Individual Page")
      IndividualOrCompanyPage.verifyPageTitle(IndividualOrCompanyPage.pageTitle)
      When("user clicks An Individual Radio Button")
      IndividualOrCompanyPage.radioButton(IndividualOrCompanyPage.business)
      And("user clicks An Save and Continue Button")
      IndividualOrCompanyPage.saveAndContinue()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
      Then("User clicks on link to change transaction type")
      CYAPage.clickTransactionTypeChange()
      Then("User should be navigated to the About the Transaction Page")
      AboutTheTransactionPage.verifyPageTitle(AboutTheTransactionPage.pageTitle)
      And("User check the first radio button")
      AboutTheTransactionPage.radioButton("#value_0")
      And("User clicks continue")
      AboutTheTransactionPage.saveAndContinue()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
    }

    Scenario("Hit the TaskList with no return id and is a individual") {
      Given("I enter login using the Authority Wizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Then("User should be on the Before You Start page")
      BeforeYouStartPage.verifyPageTitle(BeforeYouStartPage.pageTitle)
      Then("User clicks on the continue button")
      BeforeYouStartPage.saveAndContinue()
      Then("User should be on Is the User and Individual Page")
      IndividualOrCompanyPage.verifyPageTitle(IndividualOrCompanyPage.pageTitle)
      When("user clicks A Business Radio Button")
      IndividualOrCompanyPage.radioButton(IndividualOrCompanyPage.individual)
      And("user clicks An Save and Continue Button")
      IndividualOrCompanyPage.saveAndContinue()
      Then("User is directed to the Input your name page")
      PurchasersNamePage.verifyPageTitle(PurchasersNamePage.pageTitle)
      Then("User input their name or company name and submits")
      PurchasersNamePage.input(By.id("purchaserSurnameOrCompanyName"), "Test Name")
      PurchasersNamePage.clickSubmitButton()
      Given("User is on the Address Look-up screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.pageTitle)
      When("User clicks on the link")
      PropertyAddress.clickAddressManually()
      And("User enters the address manually")
      PropertyAddress.verifyPageTitle(PropertyAddress.editPageTitle)
      PropertyAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("User is on the Review screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.confirmPageTitle)
      And("User clicks continue")
      PropertyAddress.clickContinueButton()
      Then("User should be navigated to the About the Transaction Page")
      AboutTheTransactionPage.verifyPageTitle(AboutTheTransactionPage.pageTitle)
      And("User check the first radio button")
      AboutTheTransactionPage.radioButton("#value_0")
      And("User clicks continue")
      AboutTheTransactionPage.saveAndContinue()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
      Then("User clicks on link to change Purchaser's name")
      CYAPage.clickPurchaserNameChange()
      Then("User is directed to the Input your name page")
      PurchasersNamePage.verifyPageTitle(PurchasersNamePage.pageTitle)
      Then("User input their name or company name and submits")
      PurchasersNamePage.input(By.id("purchaserSurnameOrCompanyName"), "Test check your answers")
      PurchasersNamePage.clickSubmitButton()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
      Then("User clicks on link to change Property Address")
      CYAPage.clickPropetyAddressChange()
      Given("User is on the Address Look-up screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.pageTitle)
      When("User clicks on the link")
      PropertyAddress.clickAddressManually()
      And("User enters the address manually")
      PropertyAddress.verifyPageTitle(PropertyAddress.editPageTitle)
      PropertyAddress.enterAddressManually("523", "AGC", "TE11 1TS")
      Then("User is on the Review screen")
      PropertyAddress.verifyPageTitle(PropertyAddress.confirmPageTitle)
      And("User clicks continue")
      PropertyAddress.clickContinueButton()
      And("User should be navigated to Check your answers page")
      CYAPage.verifyPageTitle(CYAPage.pageTitle)
    }
  }
}
