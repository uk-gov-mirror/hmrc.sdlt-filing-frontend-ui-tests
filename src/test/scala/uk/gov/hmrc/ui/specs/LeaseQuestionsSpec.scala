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
import uk.gov.hmrc.ui.pages.Lease.*
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class LeaseQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Lease Questions") {

    Scenario(
      "Complete the Lease Questions journey using the Grant of lease transaction type",
      LeaseJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("prelimTransactionL-property-type-residential"))

      When("the user opens the lease questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-lease")
      Then("the LeaseBeforeYouStart page is shown")
      LeaseBeforeYouStart.verifyPageTitle(LeaseBeforeYouStart.pageTitle)

      When("the user starts the lease questions")
      LeaseBeforeYouStart.clickContinueButton()
      Then("the TypeOfLease page is shown")
      TypeOfLease.verifyPageTitle(TypeOfLease.pageTitle)

      When("the user selects Residential as the type of lease")
      TypeOfLease.radioButton(TypeOfLease.residential)
      TypeOfLease.saveAndContinue()
      Then("the LeaseStartDate page is shown")
      LeaseStartDate.verifyPageTitle(LeaseStartDate.pageTitle)

      When("the user provides the lease start date")
      LeaseStartDate.enterLeaseStartDate()
      LeaseStartDate.saveAndContinue()
      Then("the LeaseEndDate page is shown")
      LeaseEndDate.verifyPageTitle(LeaseEndDate.pageTitle)

      When("the user reads the lease end date details")
      LeaseEndDate.clickDropdownText()
      LeaseEndDate.verifyPageText(LeaseEndDate.dropdownText, 1)
      And("provides the lease end date")
      LeaseEndDate.enterLeaseEndDate()
      LeaseEndDate.saveAndContinue()
      Then("the AddRentFreePeriod page is shown")
      AddRentFreePeriod.verifyPageTitle(AddRentFreePeriod.pageTitle)

      When("the user confirms the lease does not include a rent free period")
      AddRentFreePeriod.radioButton(AddRentFreePeriod.no)
      AddRentFreePeriod.saveAndContinue()
      Then("the AnnualStartingRent page is shown")
      AnnualStartingRent.verifyPageTitle(AnnualStartingRent.pageTitle)

      When("the user validates the drop down text for about variable or uncertain rent")
      AnnualStartingRent.clickDropdownText()
      AnnualStartingRent.verifyPageText(AnnualStartingRent.dropdownText, 2)
      And("the user provides the annual starting rent")
      AnnualStartingRent.input(By.id(AnnualStartingRent.annualStartingRent), AnnualStartingRent.annualStartingRentInput)
      AnnualStartingRent.saveAndContinue()
      Then("the EndOfAnnualStartingRent page is shown")
      EndOfAnnualStartingRent.verifyPageTitle(EndOfAnnualStartingRent.pageTitle)

      When("the user provides the end date for starting rent")
      EndOfAnnualStartingRent.enterEndOfAnnualStartingRent()
      EndOfAnnualStartingRent.saveAndContinue()
      Then("the LaterRent page is shown")
      LaterRent.verifyPageTitle(LaterRent.pageTitle)

      When("the user confirms they know the later rent")
      LaterRent.radioButton(LaterRent.yes)
      LaterRent.saveAndContinue()
      Then("the ThousandPoundThreshold page is shown")
      OneThousandPoundThreshold.verifyPageTitle(OneThousandPoundThreshold.pageTitle)

      When("the user confirms the annual rent is £1000 or more")
      OneThousandPoundThreshold.radioButton(OneThousandPoundThreshold.yes)
      OneThousandPoundThreshold.saveAndContinue()
      Then("the AddAnnualRentVAT page is shown")
      AddAnnualRentVAT.verifyPageTitle(AddAnnualRentVAT.pageTitle)

      When("the user confirms vat is payable on the annual rent")
      AddAnnualRentVAT.radioButton(AddAnnualRentVAT.yes)
      AddAnnualRentVAT.saveAndContinue()
      Then("the EnterAnnualRentVAT page is shown")
      EnterAnnualRentVAT.verifyPageTitle(EnterAnnualRentVAT.pageTitle)

      When("the user provides the total amount of vat payable on the annual rent")
      EnterAnnualRentVAT.input(
        By.id(EnterAnnualRentVAT.annualRentVATAmount),
        EnterAnnualRentVAT.annualRentVATAmountInput
      )
      EnterAnnualRentVAT.saveAndContinue()
      Then("the EnterTotalPremiumPayable page is shown")
      EnterTotalPremiumPayable.verifyPageTitle(EnterTotalPremiumPayable.pageTitle)

      When("the user provides the total premium payable including vat")
      EnterTotalPremiumPayable.input(
        By.id(EnterTotalPremiumPayable.TotalPremiumPayable),
        EnterTotalPremiumPayable.TotalPremiumPayableInput
      )
      EnterTotalPremiumPayable.saveAndContinue()
      Then("the NetPresentValue page is shown")
      NetPresentValue.verifyPageTitle(NetPresentValue.pageTitle)

      When("the user provides the net present value")
      NetPresentValue.input(
        By.id(NetPresentValue.NetPresentValue),
        NetPresentValue.NetPresentValueInput
      )
      NetPresentValue.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer for the lease start date")
      LeaseCheckYourAnswers.clickLeaseStartDate()
      LeaseStartDate.verifyPageTitle(LeaseStartDate.pageTitle)
      LeaseStartDate.enterLeaseStartDateCYA()
      LeaseStartDate.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer for the lease end date")
      LeaseCheckYourAnswers.clickLeaseEndDate()
      LeaseEndDate.verifyPageTitle(LeaseEndDate.pageTitle)
      LeaseEndDate.enterLeaseEndDateCYA()
      LeaseEndDate.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm the lease includes a rent-free period")
      LeaseCheckYourAnswers.clickDoesLeaseIncludeFreePeriod()
      AddRentFreePeriod.verifyPageTitle(AddRentFreePeriod.pageTitle)
      AddRentFreePeriod.radioButton(AddRentFreePeriod.yes)
      AddRentFreePeriod.saveAndContinue()
      Then("the EnterRentFreePeriod page is shown")
      EnterRentFreePeriod.verifyPageTitle(EnterRentFreePeriod.pageTitle)

      When("the user provides the rent-free periods months")
      EnterRentFreePeriod.input(By.id(EnterRentFreePeriod.rentFreePeriod), EnterRentFreePeriod.inputRentFreePeriod)
      EnterRentFreePeriod.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer to the annual starting rent")
      LeaseCheckYourAnswers.clickAnnualStartingRent()
      AnnualStartingRent.verifyPageTitle(AnnualStartingRent.pageTitle)
      AnnualStartingRent.input(
        By.id(AnnualStartingRent.annualStartingRent),
        AnnualStartingRent.annualStartingRentInputCYA
      )
      AnnualStartingRent.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer to the end of annual starting rent")
      LeaseCheckYourAnswers.clickEndOfAnnualStartingRent()
      EndOfAnnualStartingRent.verifyPageTitle(EndOfAnnualStartingRent.pageTitle)
      EndOfAnnualStartingRent.enterEndOfAnnualStartingRentCYA()
      EndOfAnnualStartingRent.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer to the net present value")
      LeaseCheckYourAnswers.clickNetPresentValue()
      NetPresentValue.verifyPageTitle(NetPresentValue.pageTitle)
      NetPresentValue.input(
        By.id(NetPresentValue.NetPresentValue),
        NetPresentValue.NetPresentValueInputCYA
      )
      NetPresentValue.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)
    }

    Scenario(
      "Complete the Lease Questions journey using non Grant of lease transaction types",
      LeaseJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("prelimTransactionA"))

      When("the user opens the lease questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-lease")
      Then("the LeaseBeforeYouStart page is shown")
      LeaseBeforeYouStart.verifyPageTitle(LeaseBeforeYouStart.pageTitle)

      When("the user starts the lease questions")
      LeaseBeforeYouStart.clickContinueButton()
      Then("the TypeOfLease page is shown")
      TypeOfLease.verifyPageTitle(TypeOfLease.pageTitle)

      When("the user selects Mixed Used as the type of lease")
      TypeOfLease.radioButton(TypeOfLease.mixedUse)
      TypeOfLease.saveAndContinue()
      Then("the LeaseStartDate page is shown")
      LeaseStartDate.verifyPageTitle(LeaseStartDate.pageTitle)

      When("the user provides the lease start date")
      LeaseStartDate.enterLeaseStartDate()
      LeaseStartDate.saveAndContinue()
      Then("the LeaseEndDate page is shown")
      LeaseEndDate.verifyPageTitle(LeaseEndDate.pageTitle)

      When("the user provides the lease end date")
      LeaseEndDate.enterLeaseEndDate()
      LeaseEndDate.saveAndContinue()
      Then("the AddRentFreePeriod page is shown")
      AddRentFreePeriod.verifyPageTitle(AddRentFreePeriod.pageTitle)

      When("the user confirms the lease does not include a rent free period")
      AddRentFreePeriod.radioButton(AddRentFreePeriod.no)
      AddRentFreePeriod.saveAndContinue()
      Then("the AnnualStartingRent page is shown")
      AnnualStartingRent.verifyPageTitle(AnnualStartingRent.pageTitle)

      When("the user provides the annual starting rent")
      AnnualStartingRent.input(By.id(AnnualStartingRent.annualStartingRent), AnnualStartingRent.annualStartingRentInput)
      AnnualStartingRent.saveAndContinue()
      Then("the EndOfAnnualStartingRent page is shown")
      EndOfAnnualStartingRent.verifyPageTitle(EndOfAnnualStartingRent.pageTitle)

      When("the user provides the end date for starting rent")
      EndOfAnnualStartingRent.enterEndOfAnnualStartingRent()
      EndOfAnnualStartingRent.saveAndContinue()
      Then("the LaterRent page is shown")
      LaterRent.verifyPageTitle(LaterRent.pageTitle)

      When("the user confirms they know the later rent")
      LaterRent.radioButton(LaterRent.no)
      LaterRent.saveAndContinue()
      Then("the AddAnnualRentVAT page is shown")
      AddAnnualRentVAT.verifyPageTitle(AddAnnualRentVAT.pageTitle)

      When("the user confirms vat is not payable on the annual rent")
      AddAnnualRentVAT.radioButton(AddAnnualRentVAT.no)
      AddAnnualRentVAT.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates the type of lease to Residential")
      LeaseCheckYourAnswers.clickTypeOfLease()
      TypeOfLease.verifyPageTitle(TypeOfLease.pageTitle)
      TypeOfLease.radioButton(TypeOfLease.residential)
      TypeOfLease.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer for the later rent being known")
      LeaseCheckYourAnswers.clickLaterRentKnown()
      LaterRent.verifyPageTitle(LaterRent.pageTitle)
      LaterRent.radioButton(LaterRent.yes)
      LaterRent.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)

      When("the user updates their answer to provide the total amount of vat payable")
      LeaseCheckYourAnswers.clickIsVatPayableOnRent()
      AddAnnualRentVAT.verifyPageTitle(AddAnnualRentVAT.pageTitle)
      AddAnnualRentVAT.radioButton(AddAnnualRentVAT.yes)
      AddAnnualRentVAT.saveAndContinue()
      EnterAnnualRentVAT.verifyPageTitle(EnterAnnualRentVAT.pageTitle)
      EnterAnnualRentVAT.input(
        By.id(EnterAnnualRentVAT.annualRentVATAmount),
        EnterAnnualRentVAT.annualRentVATAmountInput
      )
      EnterAnnualRentVAT.saveAndContinue()
      Then("the LeaseCheckYourAnswers page is shown")
      LeaseCheckYourAnswers.verifyPageTitle(LeaseCheckYourAnswers.pageTitle)
    }
  }
}
