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
import uk.gov.hmrc.ui.pages.UKResidency.*
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class UKResidencyQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend About The UK Residency Questions") {

    Scenario(
      "Complete the UK Residency Questions journey for individual non‑UK resident purchasers",
      UKResidencyJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("individual-purchaser-with-agents-full-land-residential-no-residency")
      )

      When("the user opens the uk residency questions")
      ReturnTaskList.clickLinkById("task-list-link-about-uk-residency")
      Then("the UKResidencyBeforeYouStart page is shown")
      UKResidencyBeforeYouStart.verifyPageTitle(UKResidencyBeforeYouStart.pageTitle)

      When("the user starts the uk residency questions journey")
      UKResidencyBeforeYouStart.clickContinueButton()
      Then("the ResidencyStatus page is shown")
      ResidencyStatus.verifyPageTitle(ResidencyStatus.pageTitle)
      ResidencyStatus.clickResidencyStatusLink()

      When("the user confirms there are non-UK resident purchasers")
      ResidencyStatus.radioButton(ResidencyStatus.yes)
      ResidencyStatus.saveAndContinue()
      Then("the CrownEmploymentRelief page is shown")
      CrownEmploymentRelief.verifyPageTitle(CrownEmploymentRelief.pageTitle)

      When("the user confirms there are purchasers claiming crown employment relief")
      CrownEmploymentRelief.radioButton(CrownEmploymentRelief.yes)
      CrownEmploymentRelief.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm there are no purchaser claiming crown employment relief")
      UKResidencyCheckYourAnswers.clickCrownEmploymentReliefChange()
      CrownEmploymentRelief.radioButton(CrownEmploymentRelief.no)
      CrownEmploymentRelief.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm there are no non-UK resident purchasers")
      UKResidencyCheckYourAnswers.clickResidencyStatusChange()
      ResidencyStatus.radioButton(ResidencyStatus.no)
      ResidencyStatus.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user submits the uk residency questions")
      UKResidencyCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the UK Residency Questions journey for a purchaser company with non‑UK residents and non‑UK close companies",
      UKResidencyJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("company-purchaser-with-agents-full-land-residential-no-residency")
      )

      When("the user starts the uk residency questions journey")
      ReturnTaskList.clickLinkById("task-list-link-about-uk-residency")
      Then("the UKResidencyBeforeYouStart page is shown")
      UKResidencyBeforeYouStart.verifyPageTitle(UKResidencyBeforeYouStart.pageTitle)

      When("the user starts the uk residency questions journey")
      UKResidencyBeforeYouStart.clickContinueButton()
      Then("the ResidencyStatus page is shown")
      ResidencyStatus.verifyPageTitle(ResidencyStatus.pageTitle)

      When("the user confirms there are non-UK resident purchasers")
      ResidencyStatus.radioButton(ResidencyStatus.yes)
      ResidencyStatus.saveAndContinue()
      Then("the CCloseCompaniesPage page is shown")
      CloseCompanies.verifyPageTitle(CloseCompanies.pageTitle)

      When("the user confirms that the purchaser is a UK close company controlled by non-UK residents")
      CloseCompanies.radioButton(CloseCompanies.yes)
      CloseCompanies.saveAndContinue()
      Then("the CrownEmploymentRelief page is shown")
      CrownEmploymentRelief.verifyPageTitle(CrownEmploymentRelief.pageTitle)

      When("the user confirms there are no purchasers claiming Crown Employment relief")
      CrownEmploymentRelief.radioButton(CrownEmploymentRelief.no)
      CrownEmploymentRelief.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user confirms there are purchasers claiming crown employment relief")
      UKResidencyCheckYourAnswers.clickCrownEmploymentReliefChange()
      CrownEmploymentRelief.radioButton(CrownEmploymentRelief.yes)
      CrownEmploymentRelief.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm there are no non-UK resident purchasers")
      UKResidencyCheckYourAnswers.clickResidencyStatusChange()
      ResidencyStatus.radioButton(ResidencyStatus.no)
      ResidencyStatus.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user confirms that the purchaser is not a UK close company controlled by non-UK residents")
      UKResidencyCheckYourAnswers.clickCloseCompaniesChange()
      CloseCompanies.radioButton(CloseCompanies.no)
      CloseCompanies.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)
    }
  }
}
