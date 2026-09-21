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
import uk.gov.hmrc.ui.pages.PurchaserAgent.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import uk.gov.hmrc.ui.tags.*

class PurchaserAgentQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Purchaser Agent Questions") {

    Scenario(
      "Complete the Purchaser Agent questions by adding a new agent",
      PurchaserAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("purchaser-no-agents"))

      When("the user opens the purchaser agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser’s-agent")
      Then("the PurchaserAgentBeforeYouStart page is shown")
      PurchaserAgentBeforeYouStart.verifyPageTitle(PurchaserAgentBeforeYouStart.pageTitle)

      When("the user starts the purchaser agent questions")
      PurchaserAgentBeforeYouStart.radioButton(PurchaserAgentBeforeYouStart.yes)
      PurchaserAgentBeforeYouStart.clickContinueButton()
      Then("the SelectPurchaserAgent page is shown")
      SelectPurchaserAgent.verifyPageTitle(SelectPurchaserAgent.pageTitle)

      When("the user adds a new purchaser agent")
      SelectPurchaserAgent.radioButton(SelectPurchaserAgent.addNewAgent)
      SelectPurchaserAgent.saveAndContinue()
      Then("the PurchaserAgentName page is shown")
      PurchaserAgentName.verifyPageTitle(PurchaserAgentName.pageTitle)

      When("the user provides the purchaser agent name")
      PurchaserAgentName.input(By.id(PurchaserAgentName.agentName), PurchaserAgentName.agentNameInput)
      PurchaserAgentName.saveAndContinue()
      Then("the PurchaserAgentAddress page is shown")
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.pageTitle)

      When("the user enters the purchaser agent address manually")
      PurchaserAgentAddress.clickAddressManually()
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.editPageTitleAgent)
      PurchaserAgentAddress.enterAddressManually("523", "AGC", "TE11 1TS")
      Then("the ConfirmPurchaserAgentAddress page is shown")
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.confirmPageTitleAgent)

      When("the user confirms the purchaser agent address")
      PurchaserAgentAddress.clickConfirmAddress()
      Then("the AddPurchaserAgentContactDetails page is shown")
      AddPurchaserAgentContactDetails.verifyPageTitle(AddPurchaserAgentContactDetails.pageTitle)

      When("the user confirms to add the purchaser agent contact details")
      AddPurchaserAgentContactDetails.radioButton(AddPurchaserAgentContactDetails.yes)
      AddPurchaserAgentContactDetails.saveAndContinue()
      Then("the PurchaserAgentEnterAgentContactDetails page is shown")
      PurchaserAgentEnterContactDetails.verifyPageTitle(PurchaserAgentEnterContactDetails.pageTitle)

      When("the user enters the purchaser agent contact details")
      PurchaserAgentEnterContactDetails.input(
        By.id(PurchaserAgentEnterContactDetails.emailAddress),
        PurchaserAgentEnterContactDetails.emailAddressInput
      )
      PurchaserAgentEnterContactDetails.input(
        By.id(PurchaserAgentEnterContactDetails.phoneNumber),
        PurchaserAgentEnterContactDetails.phoneNumberInput
      )
      PurchaserAgentEnterContactDetails.saveAndContinue()
      Then("the AddPurchaserAgentReferenceNumber page is shown")
      AddPurchaserAgentReferenceNumber.verifyPageTitle(AddPurchaserAgentReferenceNumber.pageTitle)

      When("the user confirms to not add the purchaser agent reference")
      AddPurchaserAgentReferenceNumber.radioButton(AddPurchaserAgentReferenceNumber.no)
      AddPurchaserAgentReferenceNumber.saveAndContinue()
      Then("the PurchaserAgentAuthorisedForCorrespondence page is shown")
      PurchaserAgentAuthorisedForCorrespondence.verifyPageTitle(
        PurchaserAgentAuthorisedForCorrespondence.pageTitle
      )

      When("the user confirms the purchaser agent is authorised for correspondence")
      PurchaserAgentAuthorisedForCorrespondence.radioButton(PurchaserAgentAuthorisedForCorrespondence.yes)
      PurchaserAgentAuthorisedForCorrespondence.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user updates the purchaser agent name")
      PurchaserAgentCheckYourAnswers.clickPurchaserAgentNameChange()
      PurchaserAgentName.verifyPageTitle(PurchaserAgentName.pageTitle)
      PurchaserAgentName.input(By.id(PurchaserAgentName.agentName), PurchaserAgentName.agentNameInput2)
      PurchaserAgentName.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user updates the purchaser agent address")
      PurchaserAgentCheckYourAnswers.clickPurchaserAgentAddressChange()
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.pageTitle2)
      PurchaserAgentAddress.clickAddressManually()
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.editPageTitleAgent2)
      PurchaserAgentAddress.enterAddressManually("123", "TEST", "ZZ11 1ZZ")
      PurchaserAgentAddress.verifyPageTitle(PurchaserAgentAddress.confirmPageTitleAgent2)
      PurchaserAgentAddress.clickConfirmAddress()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user updates their answer to not add purchaser agent contact details")
      PurchaserAgentCheckYourAnswers.clickAddAgentContactDetailsChange()
      AddPurchaserAgentContactDetails.verifyPageTitle(AddPurchaserAgentContactDetails.pageTitle)
      AddPurchaserAgentContactDetails.radioButton(AddPurchaserAgentContactDetails.no)
      AddPurchaserAgentContactDetails.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user updates their answer to add a purchaser agent reference number")
      PurchaserAgentCheckYourAnswers.clickDoYouWantToAddReferenceChange()
      AddPurchaserAgentReferenceNumber.verifyPageTitle(AddPurchaserAgentReferenceNumber.pageTitle)
      AddPurchaserAgentReferenceNumber.radioButton(AddPurchaserAgentReferenceNumber.yes)
      AddPurchaserAgentReferenceNumber.saveAndContinue()

      When("the user updates the purchaser agent reference number details")
      PurchaserAgentEnterReferenceNumber.verifyPageTitle(PurchaserAgentEnterReferenceNumber.pageTitle)
      PurchaserAgentEnterReferenceNumber.input(
        By.id(PurchaserAgentEnterReferenceNumber.purchaserReference),
        PurchaserAgentEnterReferenceNumber.purchaserReferenceInput
      )
      PurchaserAgentEnterReferenceNumber.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user updates the purchaser agent authorised for correspondence details")
      PurchaserAgentCheckYourAnswers.clickIsAgentAuthorizedForCorrespondenceChange()
      PurchaserAgentAuthorisedForCorrespondence.verifyPageTitle(
        PurchaserAgentAuthorisedForCorrespondence.pageTitle
      )
      PurchaserAgentAuthorisedForCorrespondence.radioButton(PurchaserAgentAuthorisedForCorrespondence.no)
      PurchaserAgentAuthorisedForCorrespondence.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user submits the purchaser agent questions")
      PurchaserAgentCheckYourAnswers.saveAndContinue()
      Then("the PurchaserAgentOverview page is shown")
      PurchaserAgentOverview.verifyPageTitle(PurchaserAgentOverview.pageTitle)

      When("the user starts another purchaser agent journey")
      PurchaserAgentOverview.radioButton(PurchaserAgentOverview.yes)
      PurchaserAgentOverview.saveAndContinue()
    }

    Scenario(
      "Complete the Purchaser Agent Journey for an existing agent",
      PurchaserAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("purchaser-no-agents"))

      When("the user opens the purchaser agent questions")
      PurchaserAgentBeforeYouStart.clickLinkById("task-list-link-about-the-purchaser’s-agent")
      Then("the PurchaserAgentBeforeYouStart page is shown")
      PurchaserAgentBeforeYouStart.verifyPageTitle(PurchaserAgentBeforeYouStart.pageTitle)

      When("the user starts the purchaser agent questions")
      PurchaserAgentBeforeYouStart.radioButton(PurchaserAgentBeforeYouStart.yes)
      PurchaserAgentBeforeYouStart.clickContinueButton()
      Then("the SelectPurchaserAgent page is shown")
      SelectPurchaserAgent.verifyPageTitle(SelectPurchaserAgent.pageTitle)

      When("the user adds an existing agent")
      SelectPurchaserAgent.radioButton(SelectPurchaserAgent.selectAgent)
      SelectPurchaserAgent.saveAndContinue()
      Then("the AddPurchaserAgentReferenceNumber page is shown")
      AddPurchaserAgentReferenceNumber.verifyPageTitle(AddPurchaserAgentReferenceNumber.pageTitle)

      When("the user confirms to add the purchaser agent reference")
      AddPurchaserAgentReferenceNumber.radioButton(AddPurchaserAgentReferenceNumber.yes)
      AddPurchaserAgentReferenceNumber.saveAndContinue()
      Then("the PurchaserAgentEnterReferenceNumber page is shown")
      PurchaserAgentEnterReferenceNumber.verifyPageTitle(PurchaserAgentEnterReferenceNumber.pageTitle)

      When("the user enters the purchaser agent reference number")
      PurchaserAgentEnterReferenceNumber.input(
        By.id(PurchaserAgentEnterReferenceNumber.purchaserReference),
        PurchaserAgentEnterReferenceNumber.purchaserReferenceInput
      )
      PurchaserAgentEnterReferenceNumber.saveAndContinue()
      Then("the PurchaserAgentAuthorisedForCorrespondence page is shown")
      PurchaserAgentAuthorisedForCorrespondence.verifyPageTitle(
        PurchaserAgentAuthorisedForCorrespondence.pageTitle
      )

      When("the user confirms the purchaser agent is authorised for correspondence")
      PurchaserAgentAuthorisedForCorrespondence.radioButton(PurchaserAgentAuthorisedForCorrespondence.yes)
      PurchaserAgentAuthorisedForCorrespondence.saveAndContinue()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)

      When("the user submits the purchaser agent questions")
      PurchaserAgentCheckYourAnswers.saveAndContinue()
      Then("the PurchaserAgentOverview page is shown")
      PurchaserAgentOverview.verifyPageTitle(PurchaserAgentOverview.pageTitle)
    }

    Scenario(
      "Complete the Purchaser Agent Journey with a completed purchaser agent",
      PurchaserAgentJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("purchaser-agent"))

      When("the user opens the purchaser agent questions")
      PurchaserAgentBeforeYouStart.clickLinkById("task-list-link-about-the-purchaser’s-agent")
      Then("the PurchaserAgentOverview page is shown")
      PurchaserAgentOverview.verifyPageTitle(PurchaserAgentOverview.pageTitle)

      When("the user removes an existing purchaser agent")
      PurchaserAgentOverview.clickPurchaserAgentRemove()
      Then("the RemovePurchaserAgent page is shown")
      RemovePurchaserAgent.verifyPageTitle(RemovePurchaserAgent.pageTitle)

      When("the user confirms the purchaser agent removal")
      RemovePurchaserAgent.radioButton(RemovePurchaserAgent.yes)
      RemovePurchaserAgent.saveAndContinue()
      Then("the PurchaserAgentOverview page is shown")
      PurchaserAgentOverview.verifyPageTitle(PurchaserAgentOverview.pageTitle)

      When("the user edits the purchaser agent details")
      PurchaserAgentOverview.clickPurchaserAgentChange()
      Then("the PurchaserAgentCheckYourAnswers page is shown")
      PurchaserAgentCheckYourAnswers.verifyPageTitle(PurchaserAgentCheckYourAnswers.pageTitle)
    }
  }
}
