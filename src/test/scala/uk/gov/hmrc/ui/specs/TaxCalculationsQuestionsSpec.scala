/*
 * Copyright 2026 HM Revenue & Customs
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

import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, GivenWhenThen}
import org.scalatest.featurespec.AnyFeatureSpec
import org.scalatest.verbs.ShouldVerb
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.selenium.webdriver.{Browser, ScreenshotOnFailure}
import uk.gov.hmrc.ui.pages.*
import uk.gov.hmrc.ui.pages.TaxCalculations.*
import uk.gov.hmrc.ui.pages.Transaction.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import org.openqa.selenium.By

class TaxCalculationsQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Tax Calculations") {

    Scenario(
      "Complete the Freehold calculated Tax Calculation Journey",
      TaxCalculationJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("freehold-tax-calculated")
      )

      When("the user navigated to tax calculation")
      ReturnTaskList.clickLinkById("task-list-link-tax-calculation")
      Then("the Is this effective date of transaction page is displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)

      When("the user confirms the effective date of the transaction and continues")
      ConfirmEffectiveDateOfTransaction.clickContinueButton()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects effective date before oct 2024 as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.yes)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Freehold calculated Before you start page is displayed")
      TaxCalculationBeforeYouStart.verifyPageTitle(TaxCalculationBeforeYouStart.pageTitle)

      When("the user is navigated to sdlt due page")
      TaxCalculationBeforeYouStart.clickContinueButton()
      Then("the user is navigated to the calculate SDLT due page")
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.pageTitle)

      When("the user want the breakdown page journey")
      CalculatedSDLTDue.clickSDLTBreakDownLink()
      Then("the user is navigated to the SDLT breakdown page")
      SDLTBreakdown.verifyPageTitle(SDLTBreakdown.pageTitle)

      When("the user want to go return to the tax calculation page")
      SDLTBreakdown.clickReturnTaxPage()
      Then("the user is navigated to the calculate SDLT due page")
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.pageTitle)

      When("the user want to go return to the tax calculation page")
      CalculatedSDLTDue.clickContinueButton()
      Then("user is navigated to what is the SDLT self-assessment page")
      SDLTSelfAssessment.verifyPageTitle(
        SDLTSelfAssessment.pageTitleFreeholdTax
      )

      When("user enter the self assessed amount value and click save and continue button")
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("user is navigated to what is the total amount due page")
      TotalAmountDue.verifyPageTitle(
        TotalAmountDue.pageTitleFreehold
      )

      When("user enter the total amount value and click save and continue button")
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the pay penalties page")
      ArePenaltiesAndInterestIncluded.verifyPageTitle(ArePenaltiesAndInterestIncluded.pageTitle)

      When("user selects no penalties and interest value and continues")
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.no)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter self assessed sdlt amount")
      TaxCalculationCheckYourAnswers.clickselfAssessedSDLTAmountChange()
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter amount to be paid")
      TaxCalculationCheckYourAnswers.clickamountTobePaidChange()
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and change yes to pay penalties page")
      TaxCalculationCheckYourAnswers.clickpenaltiesChange()
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.yes)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks save and continue button")
      TaxCalculationCheckYourAnswers.confirmAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Freehold not calculated Tax Calculation Journey",
      TaxCalculationJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("freehold-self-assessed")
      )

      When("the user navigated to tax calculation")
      ReturnTaskList.clickLinkById("task-list-link-tax-calculation")
      Then("the Is this effective date of transaction page is displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)

      When("the confirm effective date of the transaction page displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)
      ConfirmEffectiveDateOfTransaction.clickContinueButton()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects 1st oct 2024 effective date as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.yes)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Freehold not calculated Before you start page is displayed")
      TaxCalculationBeforeYouStart.verifyPageTitle(TaxCalculationBeforeYouStart.pageTitleFreeholdNotCalculated)

      When("the user click save and continues")
      TaxCalculationBeforeYouStart.clickContinueButton()
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.freeholdSelfAssesedSDLTDuepageTitle)
      CalculatedSDLTDue.clickContinueButton()
      Then("user is navigated to what is the SDLT self-assessment page")
      SDLTSelfAssessment.verifyPageTitle(
        SDLTSelfAssessment.pageTitleFreeholdSelfAssesed
      )

      When("user enter the amount value and click save and continue button")
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("user is navigated to what is the total amount due page")
      TotalAmountDue.verifyPageTitle(
        TotalAmountDue.pageTitleFreeholdSelfAssesedTAD
      )

      When("user enter the amount value and click save and continue button")
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("user is navigated to penalties page")
      ArePenaltiesAndInterestIncluded.verifyPageTitle(ArePenaltiesAndInterestIncluded.pageTitleFreeholdSelfAssesed)

      When("user selects penalties and interest value and continues")
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.yes)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter self assessed sdlt amount")
      TaxCalculationCheckYourAnswers.clickselfAssessedSDLTAmountChange()
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter amount to be paid")
      TaxCalculationCheckYourAnswers.clickamountTobePaidChange()
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and select no penalties and interest value on penalties page")
      TaxCalculationCheckYourAnswers.clickpenaltiesChange()
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.no)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on save and continue button on check your answers page")
      TaxCalculationCheckYourAnswers.confirmAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Leasehold calculated Tax Calculation Journey",
      TaxCalculationJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("leasehold-tax-calculated")
      )

      When("the user navigated to tax calculation")
      ReturnTaskList.clickLinkById("task-list-link-tax-calculation")
      Then("the Is this effective date of transaction page is displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)

      Then("the confirm effective date of the transaction page displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)
      ConfirmEffectiveDateOfTransaction.clickContinueButton()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects effective date before oct 2024 as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.yes)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Freehold calculated Before you start page is displayed")
      TaxCalculationBeforeYouStart.verifyPageTitle(TaxCalculationBeforeYouStart.pageTitleLeaseholdCalculated)

      When("the user start sdlt due page")
      TaxCalculationBeforeYouStart.clickContinueButton()
      Then("the user is navigated to the calculate SDLT due page")
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.leaseholdSDLTDuepageTitle)

      When("the user start sdlt breakdown page")
      CalculatedSDLTDue.clickSDLTBreakDownLink()
      Then("the user is navigated to the SDLT breakdown page")
      SDLTBreakdown.verifyPageTitle(SDLTBreakdown.leaseholdSDLTBreakdownpageTitle)

      When("the user click return to tax calculation hyperlink")
      SDLTBreakdown.clickReturnTaxPage()
      Then("the user is navigated to the calculate SDLT due page")
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.leaseholdSDLTDuepageTitle)

      When("the user start sdlt self assessment page")
      CalculatedSDLTDue.clickContinueButton()
      Then("user is navigated to what is the SDLT self-assessment page")
      SDLTSelfAssessment.verifyPageTitle(
        SDLTSelfAssessment.pageTitleLeaseholdTax
      )

      When("the user start total amount page")
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("user is navigated to what is the total amount due page")
      TotalAmountDue.verifyPageTitle(
        TotalAmountDue.pageTitleLeasehold
      )

      When("the user start pay penalties page ")
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the pay penalties page")
      ArePenaltiesAndInterestIncluded.verifyPageTitle(
        ArePenaltiesAndInterestIncluded.pageTitleLeaseholdCalculated
      )

      When("user selects penalties and interest value as yes and continues")
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.yes)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter self assessed sdlt amount")
      TaxCalculationCheckYourAnswers.clickselfAssessedSDLTAmountChange()
      SDLTSelfAssessment.input(
        By.id(SDLTSelfAssessment.saaValue),
        SDLTSelfAssessment.saaInput
      )
      SDLTSelfAssessment.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter amount to be paid")
      TaxCalculationCheckYourAnswers.clickamountTobePaidChange()
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and change yes to pay penalties page")
      TaxCalculationCheckYourAnswers.clickpenaltiesChange()
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.yes)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user start return task list page")
      TaxCalculationCheckYourAnswers.confirmAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Leasehold not calculated Tax Calculation Journey",
      TaxCalculationJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("leasehold-self-assessed")
      )
      When("the user navigated to tax calculation")
      ReturnTaskList.clickLinkById("task-list-link-tax-calculation")
      Then("the Is this effective date of transaction page is displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)

      Then("the confirm effective date of the transaction page displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)
      ConfirmEffectiveDateOfTransaction.clickContinueButton()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects effective date before oct 2024 as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.yes)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Freehold calculated Before you start page is displayed")
      TaxCalculationBeforeYouStart.verifyPageTitle(TaxCalculationBeforeYouStart.pageTitleLeaseholdNotCalculated)

      When("the user start sdlt due page")
      TaxCalculationBeforeYouStart.clickContinueButton()
      Then("the user is navigated to the calculate SDLT due page")
      CalculatedSDLTDue.verifyPageTitle(CalculatedSDLTDue.leaseholdSelfAssesedSDLTDuepageTitle)

      When("the user click save and continue button")
      CalculatedSDLTDue.clickContinueButton()
      Then("the user is navigated to the total premium value page")
      TaxDueOnTotalPremiumPayable.verifyPageTitle(
        TaxDueOnTotalPremiumPayable.pageTitle
      )

      When("user enter amount in the box and click continues")
      TaxDueOnTotalPremiumPayable.input(
        By.id(TaxDueOnTotalPremiumPayable.tppTax),
        TaxDueOnTotalPremiumPayable.tppTaxInput
      )
      TaxDueOnTotalPremiumPayable.saveAndContinue()
      Then("user is navigated to what is the tax due on the NPV page")
      TaxDueOnNPV.verifyPageTitle(TaxDueOnNPV.pageTitle)

      When("user enter the NPV value and click save and continue button")
      TaxDueOnNPV.input(
        By.id(TaxDueOnNPV.taxDueOnNPVAmountInput),
        TaxDueOnNPV.taxDueOnNPVAmount
      )
      TaxDueOnNPV.saveAndContinue()
      Then("user is navigated to what is the total amount due page")
      TotalAmountDue.verifyPageTitle(
        TotalAmountDue.pageTitle
      )

      When("user enter the amount value and click save and continue button")
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the pay penalties page")
      ArePenaltiesAndInterestIncluded.verifyPageTitle(
        ArePenaltiesAndInterestIncluded.pageTitleLeaseholdNotCalculated
      )

      When("user selects no penalties and interest value and continues")
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.no)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and enter amount to be paid")
      TaxCalculationCheckYourAnswers.clickamountTobePaidChange()
      TotalAmountDue.input(
        By.id(TotalAmountDue.tppTax),
        TotalAmountDue.tppTaxInput
      )
      TotalAmountDue.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user clicks on change link and change yes to pay penalties page")
      TaxCalculationCheckYourAnswers.clickpenaltiesChange()
      ArePenaltiesAndInterestIncluded.radioButton(ArePenaltiesAndInterestIncluded.yes)
      ArePenaltiesAndInterestIncluded.saveAndContinue()
      Then("the user is navigated to the check your answers page")
      TaxCalculationCheckYourAnswers.verifyPageTitle(TaxCalculationCheckYourAnswers.pageTitle)

      When("the user start return task list page")
      TaxCalculationCheckYourAnswers.confirmAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete effective date Journey",
      TaxCalculationJourney
    ) {

      Given("the user logs in through the Authority Wizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("leasehold-self-assessed")
      )
      When("the user navigated to tax calculation")
      ReturnTaskList.clickLinkById("task-list-link-tax-calculation")
      Then("the Is this effective date of transaction page is displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)

      Then("the confirm effective date of the transaction page displayed")
      ConfirmEffectiveDateOfTransaction.verifyPageTitle(ConfirmEffectiveDateOfTransaction.pageTitle)
      ConfirmEffectiveDateOfTransaction.clickContinueButton()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects effective date before oct 2024 as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.no)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Effective Date of Transaction page is displayed")
      EffectiveDateOfTransaction.verifyPageTitle(EffectiveDateOfTransaction.pageTitle)
    }
  }
}
