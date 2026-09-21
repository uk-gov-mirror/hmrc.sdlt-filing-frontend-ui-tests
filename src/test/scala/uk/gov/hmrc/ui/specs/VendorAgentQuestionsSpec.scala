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
import uk.gov.hmrc.ui.pages.Vendor.*
import uk.gov.hmrc.ui.pages.VendorAgent.*
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class VendorAgentQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Vendor Questions") {

    Scenario(
      "Complete the Vendor Agent journey with contact details and reference information",
      VendorAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("no-return-agent-and-main-vendor-not-represented-by-agent")
      )

      When("the user opens the vendor agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-vendor’s-agent")
      Then("the VendorAgentBeforeYouStart page is shown")
      VendorAgentBeforeYouStart.verifyPageTitle(VendorAgentBeforeYouStart.pageTitle)

      When("the user starts the vendor agent questions")
      VendorAgentBeforeYouStart.radioButton(VendorAgentBeforeYouStart.yes)
      VendorAgentBeforeYouStart.clickContinueButton()
      Then("the VendorAgentName page is shown")
      VendorAgentName.verifyPageTitle(VendorAgentName.pageTitle)

      When("the user provides the vendor agent name")
      VendorAgentName.input(
        By.id(VendorAgentName.agentName),
        VendorAgentName.agentNameInput
      )
      VendorAgentName.saveAndContinue()
      Then("the VendorAgentAddress page is shown")
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.pageTitle)

      When("the user enters the vendor agent address manually")
      VendorAgentAddress.clickAddressManually()
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.editPageTitleAgent)
      VendorAgentAddress.enterAddressManually("523", "AGC", "TE12 1TS")
      Then("the Confirm Vendor Agent Property Address page is shown")
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.confirmPageTitleAgent)

      When("the user confirms the vendor agent address")
      VendorPropertyAddress.clickConfirmAddress()
      Then("the AddVendorAgentContactDetails page is shown")
      AddVendorAgentContactDetails.verifyPageTitle(AddVendorAgentContactDetails.pageTitle)

      When("the user confirms to add the vendor agent contact details")
      AddVendorAgentContactDetails.radioButton(AddVendorAgentContactDetails.yes)
      AddVendorAgentContactDetails.saveAndContinue()
      Then("the VendorAgentEnterContactDetails page is shown")
      VendorAgentEnterContactDetails.verifyPageTitle(VendorAgentEnterContactDetails.pageTitle)

      When("the user enters the vendor agent contact details")
      VendorAgentEnterContactDetails.input(
        By.id(VendorAgentEnterContactDetails.phoneNumber),
        VendorAgentEnterContactDetails.phoneNumberInput
      )
      VendorAgentEnterContactDetails.input(
        By.id(VendorAgentEnterContactDetails.emailAddress),
        VendorAgentEnterContactDetails.emailAddressInput
      )
      VendorAgentEnterContactDetails.saveAndContinue()
      Then("the AddVendorAgentReferenceNumber page is shown")
      AddVendorAgentReferenceNumber.verifyPageTitle(AddVendorAgentReferenceNumber.pageTitle)

      When("the user confirms to add the vendor agent reference")
      AddVendorAgentReferenceNumber.radioButton(AddVendorAgentReferenceNumber.yes)
      AddVendorAgentReferenceNumber.saveAndContinue()
      Then("the VendorAgentEnterReferenceNumber page is shown")
      VendorAgentEnterReferenceNumber.verifyPageTitle(VendorAgentEnterReferenceNumber.pageTitle)

      When("the user enters the vendor agent reference number")
      VendorAgentEnterReferenceNumber.input(
        By.id(VendorAgentEnterReferenceNumber.agentReference),
        VendorAgentEnterReferenceNumber.agentReferenceNumber
      )
      VendorAgentEnterReferenceNumber.saveAndContinue()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user submits the vendor agent questions")
      VendorAgentCheckYourAnswers.saveAndContinue()
      Then("the VendorAgentOverview page is shown")
      VendorAgentOverview.verifyPageTitle(VendorAgentOverview.pageTitle)

      When("the user adds a vendor agent")
      VendorAgentOverview.radioButton(VendorAgentOverview.yes)
      VendorAgentOverview.saveAndContinue()
      Then("the VendorAgentName page is shown")
      VendorAgentName.verifyPageTitle(VendorAgentName.pageTitle)
    }

    Scenario(
      "Complete the Vendor Agent journey without contact details and reference information",
      VendorAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("no-return-agent-and-main-vendor-not-represented-by-agent")
      )

      When("the user opens the vendor agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-vendor’s-agent")
      Then("the VendorAgentBeforeYouStart page is shown")
      VendorAgentBeforeYouStart.verifyPageTitle(VendorAgentBeforeYouStart.pageTitle)

      When("the user starts the vendor agent questions")
      VendorAgentBeforeYouStart.radioButton(VendorAgentBeforeYouStart.yes)
      VendorAgentBeforeYouStart.clickContinueButton()
      Then("the VendorAgentName page is shown")
      VendorAgentName.verifyPageTitle(VendorAgentName.pageTitle)

      When("the user provides the vendor agent name")
      VendorAgentName.input(
        By.id(VendorAgentName.agentName),
        VendorAgentName.agentNameInput
      )
      VendorAgentName.saveAndContinue()
      Then("the VendorAgentAddress page is shown")
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.pageTitle)

      When("the user enters the vendor agent address manually")
      VendorAgentAddress.clickAddressManually()
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.editPageTitleAgent)
      VendorAgentAddress.enterAddressManually("523", "AGC", "TE12 1TS")
      Then("the Confirm Vendor Property Address page is shown")
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.confirmPageTitleAgent)

      When("the user confirms the vendor agent address")
      VendorPropertyAddress.clickConfirmAddress()
      Then("the AddVendorAgentContactDetails page is shown")
      AddVendorAgentContactDetails.verifyPageTitle(AddVendorAgentContactDetails.pageTitle)

      When("the user confirms to not add vendor agent contact details")
      AddVendorAgentContactDetails.radioButton(AddVendorAgentContactDetails.no)
      AddVendorAgentContactDetails.saveAndContinue()
      Then("the AddVendorAgentReferenceNumber page is shown")
      AddVendorAgentReferenceNumber.verifyPageTitle(AddVendorAgentReferenceNumber.pageTitle)

      When("the user confirms to not add a vendor agent reference number")
      AddVendorAgentReferenceNumber.radioButton(AddVendorAgentReferenceNumber.no)
      AddVendorAgentReferenceNumber.saveAndContinue()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user updates the vendor agent name")
      VendorAgentCheckYourAnswers.clickVendorAgentNameChange()
      VendorAgentName.verifyPageTitle(VendorAgentName.pageTitle)
      VendorAgentName.input(By.id(VendorAgentName.agentName), VendorAgentName.agentNameInput2)
      VendorAgentName.saveAndContinue()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user updates the vendor agent address")
      VendorAgentCheckYourAnswers.clickVendorAgentAddressChange()
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.pageTitle2)
      VendorAgentAddress.clickAddressManually()
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.editPageTitleAgent2)
      VendorAgentAddress.enterAddressManually("123", "TEST", "ZZ11 1ZZ")
      VendorAgentAddress.verifyPageTitle(VendorAgentAddress.confirmPageTitleAgent2)
      VendorAgentAddress.clickConfirmAddress()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user updates their answer to add vendor agent contact details")
      VendorAgentCheckYourAnswers.clickAddAgentContactDetailsChange()
      AddVendorAgentContactDetails.verifyPageTitle(AddVendorAgentContactDetails.pageTitle)
      AddVendorAgentContactDetails.radioButton(AddVendorAgentContactDetails.yes)
      AddVendorAgentContactDetails.saveAndContinue()
      VendorAgentEnterContactDetails.verifyPageTitle(VendorAgentEnterContactDetails.pageTitle)
      VendorAgentEnterContactDetails.input(
        By.id(VendorAgentEnterContactDetails.phoneNumber),
        VendorAgentEnterContactDetails.phoneNumberInput
      )
      VendorAgentEnterContactDetails.saveAndContinue()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user updates their answer to add a reference number")
      VendorAgentCheckYourAnswers.clickDoYouWantToAddReferenceChange()
      AddVendorAgentReferenceNumber.verifyPageTitle(AddVendorAgentReferenceNumber.pageTitle)
      AddVendorAgentReferenceNumber.radioButton(AddVendorAgentReferenceNumber.yes)
      AddVendorAgentReferenceNumber.saveAndContinue()
      VendorAgentEnterReferenceNumber.verifyPageTitle(VendorAgentEnterReferenceNumber.pageTitle)
      VendorAgentEnterReferenceNumber.input(
        By.id(VendorAgentEnterReferenceNumber.agentReference),
        VendorAgentEnterReferenceNumber.agentReferenceNumber2
      )
      VendorAgentEnterReferenceNumber.saveAndContinue()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)

      When("the user submits the vendor agent questions")
      VendorAgentCheckYourAnswers.saveAndContinue()
      Then("the VendorAgentOverview page is shown")
      VendorAgentOverview.verifyPageTitle(VendorAgentOverview.pageTitle)
    }

    Scenario(
      "Remove and edit an existing vendor agent",
      VendorAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("5-vendors"))

      When("the user opens the vendor agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-vendor’s-agent")
      Then("the VendorAgentOverview page is shown")
      VendorAgentOverview.verifyPageTitle(VendorAgentOverview.pageTitle)

      When("the user removes an existing vendor agent")
      VendorAgentOverview.clickVendorAgentRemove()
      Then("the RemoveVendorAgent page is shown")
      RemoveVendorAgent.verifyPageTitle(RemoveVendorAgent.pageTitle)

      When("the user confirms the vendor agent removal")
      RemoveVendorAgent.radioButton(RemoveVendorAgent.yes)
      RemoveVendorAgent.saveAndContinue()
      Then("the VendorAgentOverview page is shown")
      VendorAgentOverview.verifyPageTitle(VendorAgentOverview.pageTitle)

      When("the user edits the vendor agent details")
      VendorAgentOverview.clickVendorAgentChange()
      Then("the VendorAgentCheckYourAnswers page is shown")
      VendorAgentCheckYourAnswers.verifyPageTitle(VendorAgentCheckYourAnswers.pageTitle)
    }
  }
}
