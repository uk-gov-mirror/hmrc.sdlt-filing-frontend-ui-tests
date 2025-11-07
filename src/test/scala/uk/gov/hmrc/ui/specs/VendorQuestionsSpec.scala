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
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.PrelimQuestions.{AboutTheTransactionPage, BeforeYouStartPage, CYAPage, IndividualOrCompanyPage, PropertyAddress, PurchasersNamePage}
import uk.gov.hmrc.ui.pages.Vendor.AboutTheVendorPage
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class VendorQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing frontend Vendor questions") {
    Scenario("Hit the TaskList for business vendor questions") {
      Given("I enter login using the Authority Wizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Given("Given the user is on Return Task list Page")
      ReturnTaskListPage.navigateToPage(
        "http://localhost:10910/stamp-duty-land-tax-filing/returnTaskList?returnId=123456"
      )
      And("User should click on vendor questions in return Task List page")
      AboutTheVendorPage.clickLinkById("task-list-link-vendor-questions")
      Then("User should be on About the vendor page")
      AboutTheVendorPage.verifyPageTitle(AboutTheVendorPage.pageTitle)
      Then("User should click on business radio button")
      AboutTheVendorPage.radioButton(AboutTheVendorPage.business)
      And("user clicks An Save and Continue Button")
      AboutTheVendorPage.saveAndContinue()
    }

    Scenario("Hit the TaskList for individual vendor questions") {
      Given("I enter login using the Authority Wizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Given("Given the user is on Return Task list Page")
      ReturnTaskListPage.navigateToPage(
        "http://localhost:10910/stamp-duty-land-tax-filing/returnTaskList?returnId=123456"
      )
      And("User should click on vendor questions in return Task List page")
      AboutTheVendorPage.clickLinkById("task-list-link-vendor-questions")
      Then("User should be on About the vendor page")
      AboutTheVendorPage.verifyPageTitle(AboutTheVendorPage.pageTitle)
      Then("User should click on individual radio button")
      AboutTheVendorPage.radioButton(AboutTheVendorPage.individual)
      And("user clicks An Save and Continue Button")
      AboutTheVendorPage.saveAndContinue()
    }
  }
}
