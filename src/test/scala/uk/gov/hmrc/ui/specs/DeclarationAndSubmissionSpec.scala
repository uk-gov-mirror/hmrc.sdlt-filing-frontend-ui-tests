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

import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.DeclarationAndSubmission.*
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import org.openqa.selenium.By
import uk.gov.hmrc.selenium.webdriver.Driver.instance

class DeclarationAndSubmissionSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Declaration and Submission Questions") {

    Scenario(
      "Complete the Declaration and Submission questions for a complete submission",
      DeclarationAndSubmissionJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("submission-complete-multiples")
      )

      Then("the DeclarationAndSubmissionBeforeYouStart page is shown")
      DeclarationAndSubmissionBeforeYouStart.verifyPageTitle(DeclarationAndSubmissionBeforeYouStart.pageTitle)

      When("the user views their completed sdlt return")
      DeclarationAndSubmissionBeforeYouStart.click(DeclarationAndSubmissionBeforeYouStart.viewAndPrintThisReturnLink)
      Then("the YourCompletedSDLTReturn page is shown")
      YourCompletedSDLTReturn.switchToNewTabAndValidateTitle(YourCompletedSDLTReturn.pageTitle)

      When("the user starts the submit your return questions")
      DeclarationAndSubmissionBeforeYouStart.clickContinueButton()
      Then("the AddEmailConfirmation page is shown")
      AddEmailConfirmation.verifyPageTitle(AddEmailConfirmation.pageTitle)

      When("the user chooses to receive an email confirmation when the return is submitted")
      AddEmailConfirmation.radioButton(AddEmailConfirmation.yes)
      AddEmailConfirmation.saveAndContinue()
      Then("the EnterEmailAddress page is shown")
      EnterEmailAddress.verifyPageTitle(EnterEmailAddress.pageTitle)

      When("the user provides their email address")
      EnterEmailAddress.input(By.id(EnterEmailAddress.emailAddress), EnterEmailAddress.emailAddressInput)
      EnterEmailAddress.saveAndContinue()
      Then("the SDLT5CertificateForEachLandOrProperty page is shown")
      SDLT5CertificateForEachLandOrProperty.verifyPageTitle(SDLT5CertificateForEachLandOrProperty.pageTitle)

      When("the user confirms to receive an sdlt5 certificate for each area of land")
      SDLT5CertificateForEachLandOrProperty.radioButton(SDLT5CertificateForEachLandOrProperty.yes)
      SDLT5CertificateForEachLandOrProperty.saveAndContinue()
      Then("the WhoAreYouSubmittingThisReturnFor page is shown")
      WhoAreYouSubmittingThisReturnFor.verifyPageTitle(WhoAreYouSubmittingThisReturnFor.pageTitle)

      When("the user confirms to submit the return for authorised purchasers")
      WhoAreYouSubmittingThisReturnFor.radioButton(WhoAreYouSubmittingThisReturnFor.purchaserAuthorised)
      WhoAreYouSubmittingThisReturnFor.saveAndContinue()
      Then("the DeclarationConfirmation page is shown")
      DeclarationConfirmation.verifyPageTitle(DeclarationConfirmation.pageTitle)

      When("the user has read the declaration and submits their return")
      DeclarationConfirmation.confirmAndSubmit()
    }

    Scenario(
      "Submission completed and the user can view their submitted return and SDLT5 certificate",
      DeclarationAndSubmissionJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("submission-complete")
      )
      Then("the SubmissionComplete page is shown")
      SubmissionComplete.waitForPage()
      SubmissionComplete.verifyPageTitle(SubmissionComplete.pageTitle)

      When("the user views their submitted sdlt return")
      SubmissionComplete.click(SubmissionComplete.submittedReturnLink)
      Then("the YourCompletedSDLTReturn page is shown")
      YourCompletedSDLTReturn.verifyPageTitle(YourCompletedSDLTReturn.submittedReturnPageTitle)

      When("the user views their sdlt5 certificate")
      SubmissionComplete.navigateBackToPage()
      SubmissionComplete.verifyPageTitle(SubmissionComplete.pageTitle)
      SubmissionComplete.click(SubmissionComplete.sdlt5certificateLink)
      Then("the SubmissionReceiptAndSDLT5 page is shown")
      SubmissionReceiptAndSDLT5.switchToNewTabAndValidateTitle(SubmissionReceiptAndSDLT5.pageTitle)
    }
  }
}
