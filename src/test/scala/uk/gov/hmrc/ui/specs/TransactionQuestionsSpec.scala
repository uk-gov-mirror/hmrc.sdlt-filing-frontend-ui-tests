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
import uk.gov.hmrc.ui.pages.Transaction.*
import uk.gov.hmrc.ui.pages.Preliminary.TransactionType
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation

class TransactionQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend About The Transaction Questions") {

    Scenario(
      "Complete the Transactions Questions journey using transaction type grant of lease and relief reason charities",
      TransactionJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("prelim-only"))

      When("the user opens the transaction questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-transaction")
      Then("the TransactionBeforeYouStart page is shown")
      TransactionBeforeYouStart.verifyPageTitle(TransactionBeforeYouStart.pageTitle)

      When("the user starts the transaction questions")
      TransactionBeforeYouStart.clickContinueButton()
      Then("the ConfirmTypeOfTransaction page is shown")
      ConfirmTypeOfTransaction.verifyPageTitle(ConfirmTypeOfTransaction.pageTitle)

      When("the user confirms the transaction type is incorrect")
      ConfirmTypeOfTransaction.radioButton(ConfirmTypeOfTransaction.no)
      ConfirmTypeOfTransaction.saveAndContinue()
      Then("the TransactionType page is shown")
      TransactionType.verifyPageTitle(TransactionType.pageTitleTransaction)

      When("the user selects 'Grant of Lease' as the transaction type")
      TransactionType.radioButton(TransactionType.grantOfLease)
      TransactionType.saveAndContinue()
      Then("the ChangingTypeOfTransaction page is shown")
      ChangingTypeOfTransaction.verifyPageTitle(ChangingTypeOfTransaction.pageTitle)

      When("the user confirms to change the type of transaction")
      ChangingTypeOfTransaction.radioButton(ChangingTypeOfTransaction.yes)
      ChangingTypeOfTransaction.saveAndContinue()
      Then("the EffectiveDateOfTransaction page is shown")
      EffectiveDateOfTransaction.verifyPageTitle(EffectiveDateOfTransaction.pageTitle)

      When("the user provides the effective date of transaction")
      EffectiveDateOfTransaction.enterEffectiveDateOfTransaction()
      EffectiveDateOfTransaction.saveAndContinue()
      Then("the AddDateOfContract page is shown")
      AddDateOfContract.verifyPageTitle(
        AddDateOfContract.pageTitle
      )

      When("the user doesn't add the date of contact or conclusion of missives")
      AddDateOfContract.radioButton(AddDateOfContract.no)
      AddDateOfContract.saveAndContinue()
      Then("the LinkedTransactions page is shown")
      LinkedTransactions.verifyPageTitle(LinkedTransactions.pageTitle)

      When("the user confirms the transaction is not linked to another")
      LinkedTransactions.radioButton(LinkedTransactions.no)
      LinkedTransactions.saveAndContinue()
      Then("the ClaimingRelief page is shown")
      ClaimingRelief.verifyPageTitle(ClaimingRelief.pageTitle)

      When("the user confirms the purchaser is eligible to claim relief")
      ClaimingRelief.radioButton(ClaimingRelief.yes)
      ClaimingRelief.saveAndContinue()
      Then("the ReasonForRelief page is shown")
      ReasonForRelief.verifyPageTitle(ReasonForRelief.pageTitle)

      When("the user selects relocation of employment as the reason for claiming relief")
      ReasonForRelief.radioButton(ReasonForRelief.relocationOfEmployment)
      ReasonForRelief.saveAndContinue()
      Then("the PartialRelief page is shown")
      PartialRelief.verifyPageTitle(PartialRelief.pageTitle)

      When("the user confirms the purchaser is not claiming relief on part of the land")
      PartialRelief.radioButton(PartialRelief.no)
      PartialRelief.saveAndContinue()
      Then("the ConsiderationsAffectedByUncertainFutureEvents page is shown")
      ConsiderationsAffectedByUncertainFutureEvents.verifyPageTitle(
        ConsiderationsAffectedByUncertainFutureEvents.pageTitle
      )

      When("the user confirms a part of the consideration is contingent or dependent on uncertain future events")
      ConsiderationsAffectedByUncertainFutureEvents.radioButton(ConsiderationsAffectedByUncertainFutureEvents.yes)
      ConsiderationsAffectedByUncertainFutureEvents.saveAndContinue()
      Then("the DeferringPayment page is shown")
      DeferringPayment.verifyPageTitle(DeferringPayment.pageTitle)

      When("the user confirms the purchaser is applying for a deferment")
      DeferringPayment.radioButton(DeferringPayment.yes)
      DeferringPayment.saveAndContinue()
      Then("the SaleOfABusiness page is shown")
      SaleOfABusiness.verifyPageTitle(SaleOfABusiness.pageTitle)

      When("the user confirms the transaction is part of the sale of a business")
      SaleOfABusiness.radioButton(SaleOfABusiness.yes)
      SaleOfABusiness.saveAndContinue()
      Then("the AssetsIncludedInSaleOfTheBusiness page is shown")
      AssetsIncludedInSaleOfTheBusiness.verifyPageTitle(AssetsIncludedInSaleOfTheBusiness.pageTitle)

      When("the user selects assets that are included in this transaction")
      AssetsIncludedInSaleOfTheBusiness.checkbox(AssetsIncludedInSaleOfTheBusiness.stock, true)
      AssetsIncludedInSaleOfTheBusiness.checkbox(AssetsIncludedInSaleOfTheBusiness.chattelsAndMovables, true)
      AssetsIncludedInSaleOfTheBusiness.saveAndContinue()
      Then("the TotalConsiderationOfAllAssets page is shown")
      TotalConsiderationOfAllAssets.verifyPageTitle(TotalConsiderationOfAllAssets.pageTitle)

      When("the user provides the total amount of consideration for the sale of the business")
      TotalConsiderationOfAllAssets.input(
        By.id(TotalConsiderationOfAllAssets.totalConsiderationOfAllAssets),
        TotalConsiderationOfAllAssets.totalConsiderationOfAllAssetsInput
      )
      TotalConsiderationOfAllAssets.saveAndContinue()
      Then("the CAP1OrNSBC page is shown")
      CAP1OrNSBC.verifyPageTitle(CAP1OrNSBC.pageTitle)

      When("the user confirms they have applied for a CAP1 or NSBC for the transaction")
      CAP1OrNSBC.radioButton(CAP1OrNSBC.yes)
      CAP1OrNSBC.saveAndContinue()
      Then("the HaveYouFollowedTheRuling page is shown")
      HaveYouFollowedTheRuling.verifyPageTitle(
        HaveYouFollowedTheRuling.pageTitle
      )

      When("the user confirms they have followed the ruling under CAP1 or NSBC")
      HaveYouFollowedTheRuling.radioButton(HaveYouFollowedTheRuling.yes)
      HaveYouFollowedTheRuling.saveAndContinue()
      Then("the RestrictionsCovenantsAndConditions page is shown")
      RestrictionsCovenantsAndConditions.verifyPageTitle(RestrictionsCovenantsAndConditions.pageTitle)

      When(
        "the user confirms there are no restrictions, covenants or conditions affecting the value of the interest transferred"
      )
      RestrictionsCovenantsAndConditions.radioButton(RestrictionsCovenantsAndConditions.no)
      RestrictionsCovenantsAndConditions.saveAndContinue()
      Then("the ExchangeOrPartExchange page is shown")
      ExchangeOrPartExchange.verifyPageTitle(ExchangeOrPartExchange.pageTitle)

      When("the user confirms the land is not being exchanged or part exchanged")
      ExchangeOrPartExchange.radioButton(ExchangeOrPartExchange.no)
      ExchangeOrPartExchange.saveAndContinue()
      Then("the ExercisingAnOption page is shown")
      ExercisingAnOption.verifyPageTitle(ExercisingAnOption.pageTitle)

      When("the user confirms the transaction is pursuant to a previous option agreement")
      ExercisingAnOption.radioButton(ExercisingAnOption.yes)
      ExercisingAnOption.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates the type of transaction")
      TransactionCheckYourAnswers.clickTypeOfTransaction()
      TransactionType.verifyPageTitle(TransactionType.pageTitleTransaction)
      TransactionType.radioButton(TransactionType.conveyance)
      TransactionType.saveAndContinue()
      Then("the TotalConsiderationOfTransaction page is shown")
      TotalConsiderationOfTransaction.verifyPageTitle(TotalConsiderationOfTransaction.pageTitle)

      When("the user provides the total consideration")
      TotalConsiderationOfTransaction.input(
        By.id(TotalConsiderationOfTransaction.totalConsideration),
        TotalConsiderationOfTransaction.totalConsiderationInput
      )
      TotalConsiderationOfTransaction.saveAndContinue()
      Then("the IsVATIncluded page is shown")
      IsVatIncluded.verifyPageTitle(IsVatIncluded.pageTitle)

      When("the user confirms there is vat included in the total consideration")
      IsVatIncluded.radioButton(IsVatIncluded.yes)
      IsVatIncluded.saveAndContinue()
      Then("the VATAmount page is shown")
      VATAmount.verifyPageTitle(VATAmount.pageTitle)

      When("the user provides the VAT Amount")
      VATAmount.input(
        By.id(VATAmount.totalAmountOfVAT),
        VATAmount.totalAmountOfVATInput
      )
      VATAmount.saveAndContinue()
      Then("the FormsOfConsideration page is shown")
      FormsOfConsideration.verifyPageTitle(FormsOfConsideration.pageTitle)

      When("the user provides all forms the consideration takes")
      FormsOfConsideration.checkbox(FormsOfConsideration.cash, true)
      FormsOfConsideration.checkbox(FormsOfConsideration.building_works, true)
      FormsOfConsideration.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates the effective date of transaction")
      TransactionCheckYourAnswers.clickEffectiveDateOfTransaction()
      EffectiveDateOfTransaction.enterEffectiveDateOfTransactionCYA()
      EffectiveDateOfTransaction.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to provide a date of contract")
      TransactionCheckYourAnswers.clickAddDateOfContract()
      AddDateOfContract.radioButton(AddDateOfContract.yes)
      AddDateOfContract.saveAndContinue()
      EnterDateOfContract.verifyPageTitle(EnterDateOfContract.pageTitle)
      EnterDateOfContract.enterDateOfContract()
      EnterDateOfContract.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm the transaction is linked")
      TransactionCheckYourAnswers.clickLinkedTransactions()
      LinkedTransactions.verifyPageTitle(LinkedTransactions.pageTitle)
      LinkedTransactions.radioButton(LinkedTransactions.yes)
      LinkedTransactions.saveAndContinue()
      TotalConsiderationOfLinkedTransaction.verifyPageTitle(TotalConsiderationOfLinkedTransaction.pageTitle)
      TotalConsiderationOfLinkedTransaction.input(
        By.id(TotalConsiderationOfLinkedTransaction.totalConsiderationOfLT),
        TotalConsiderationOfLinkedTransaction.totalConsiderationOfLTInput
      )
      TotalConsiderationOfLinkedTransaction.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user confirms the purchaser is eligible to claim relief")
      TransactionCheckYourAnswers.clickClaimingRelief()
      ClaimingRelief.verifyPageTitle(ClaimingRelief.pageTitle)
      ClaimingRelief.radioButton(ClaimingRelief.yes)
      ClaimingRelief.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm charities as reason for claiming relief")
      TransactionCheckYourAnswers.clickReasonForRelief()
      ReasonForRelief.verifyPageTitle(ReasonForRelief.pageTitle)
      ReasonForRelief.radioButton(ReasonForRelief.charitiesRelief)
      ReasonForRelief.saveAndContinue()
      Then("the AddRegisteredCharityNumber page is shown")
      AddRegisteredCharityNumber.verifyPageTitle(AddRegisteredCharityNumber.pageTitle)

      When("the user confirms to know the charity's registered number")
      AddRegisteredCharityNumber.radioButton(AddRegisteredCharityNumber.yes)
      AddRegisteredCharityNumber.saveAndContinue()
      Then("the EnterRegisteredCharityNumber page is shown")
      EnterRegisteredCharityNumber.verifyPageTitle(EnterRegisteredCharityNumber.pageTitle)

      When("the user provides the charity's registration number")
      EnterRegisteredCharityNumber.input(
        By.id(EnterRegisteredCharityNumber.charityRegistrationNumber),
        EnterRegisteredCharityNumber.charityRegistrationNumberInput
      )
      EnterRegisteredCharityNumber.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to restrictions, covenants or conditions details")
      TransactionCheckYourAnswers.clickRestrictionsCovenantsAndConditions()
      RestrictionsCovenantsAndConditions.verifyPageTitle(RestrictionsCovenantsAndConditions.pageTitle)
      RestrictionsCovenantsAndConditions.radioButton(RestrictionsCovenantsAndConditions.yes)
      RestrictionsCovenantsAndConditions.saveAndContinue()
      DescriptionOfRestrictionsCovenantsAndConditions.verifyPageTitle(
        DescriptionOfRestrictionsCovenantsAndConditions.pageTitle
      )
      DescriptionOfRestrictionsCovenantsAndConditions.input(
        By.id(DescriptionOfRestrictionsCovenantsAndConditions.restrictionsCovenantsAndConditions),
        DescriptionOfRestrictionsCovenantsAndConditions.restrictionsCovenantsAndConditionsInput
      )
      DescriptionOfRestrictionsCovenantsAndConditions.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm the land is being exchanged or part exchanged")
      TransactionCheckYourAnswers.clickExchangeOrPartExchange()
      ExchangeOrPartExchange.verifyPageTitle(ExchangeOrPartExchange.pageTitle)
      ExchangeOrPartExchange.radioButton(ExchangeOrPartExchange.yes)
      ExchangeOrPartExchange.saveAndContinue()
      TransactionAddressLookup.clickAddressManually()
      TransactionAddressLookup.verifyPageTitle(TransactionAddressLookup.editPageTitle)
      TransactionAddressLookup.enterAddressManually("523", "AGC", "TE11 1TS")
      TransactionAddressLookup.verifyPageTitle(TransactionAddressLookup.confirmPageTitle)
      TransactionAddressLookup.clickConfirmAddress()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm the purchaser is claiming relief")
      TransactionCheckYourAnswers.clickPartialRelief()
      PartialRelief.verifyPageTitle(PartialRelief.pageTitle)
      PartialRelief.radioButton(PartialRelief.yes)
      PartialRelief.saveAndContinue()
      ClaimingPartialRelief.verifyPageTitle(ClaimingPartialRelief.pageTitle)
      ClaimingPartialRelief.input(
        By.id(ClaimingPartialRelief.totalPartialRelief),
        ClaimingPartialRelief.totalPartialReliefInput
      )
      ClaimingPartialRelief.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user submits the transaction questions")
      TransactionCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Transactions Questions journey using other transaction types and relief reason part exchange",
      TransactionJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("prelimTransactionL-property-type-mixed"))

      When("the user opens the transaction questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-transaction")
      Then("the TransactionBeforeYouStart page is shown")
      TransactionBeforeYouStart.verifyPageTitle(TransactionBeforeYouStart.pageTitle)

      When("the user starts the transaction questions")
      TransactionBeforeYouStart.clickContinueButton()
      Then("the ConfirmTypeOfTransaction page is shown")
      ConfirmTypeOfTransaction.verifyPageTitle(ConfirmTypeOfTransaction.pageTitle)

      When("the user confirms the transaction type is incorrect")
      ConfirmTypeOfTransaction.radioButton(ConfirmTypeOfTransaction.no)
      ConfirmTypeOfTransaction.saveAndContinue()
      Then("the TransactionType page is shown")
      TransactionType.verifyPageTitle(TransactionType.pageTitleTransaction)

      When("the user selects 'Conveyance' as the transaction type")
      TransactionType.radioButton(TransactionType.conveyance)
      TransactionType.saveAndContinue()
      Then("the ChangingTypeOfTransaction page is shown")
      ChangingTypeOfTransaction.verifyPageTitle(ChangingTypeOfTransaction.pageTitle)

      When("the user confirms to change the type of transaction")
      ChangingTypeOfTransaction.radioButton(ChangingTypeOfTransaction.yes)
      ChangingTypeOfTransaction.saveAndContinue()
      Then("the EffectiveDateOfTransaction page is shown")
      EffectiveDateOfTransaction.verifyPageTitle(EffectiveDateOfTransaction.pageTitle)

      When("the user provides the effective date of transaction")
      EffectiveDateOfTransaction.clickDropdownText()
      EffectiveDateOfTransaction.verifyPageText(EffectiveDateOfTransaction.dropdownText, 1)
      EffectiveDateOfTransaction.enterEffectiveDateOfTransaction()
      EffectiveDateOfTransaction.saveAndContinue()
      Then("the AddDateOfContract page is shown")
      AddDateOfContract.verifyPageTitle(
        AddDateOfContract.pageTitle
      )

      When("the user adds the date of contact or conclusion of missives")
      AddDateOfContract.radioButton(AddDateOfContract.yes)
      AddDateOfContract.saveAndContinue()
      Then("the EnterDateOfContract page is shown")
      EnterDateOfContract.verifyPageTitle(EnterDateOfContract.pageTitle)

      When("the user provides the date of contract or conclusion of missives")
      EnterDateOfContract.enterDateOfContract()
      EnterDateOfContract.saveAndContinue()
      Then("the TotalConsiderationOfTransaction page is shown")
      TotalConsiderationOfTransaction.verifyPageTitle(TotalConsiderationOfTransaction.pageTitle)

      When("the user provides the total consideration")
      TotalConsiderationOfTransaction.input(
        By.id(TotalConsiderationOfTransaction.totalConsideration),
        TotalConsiderationOfTransaction.totalConsiderationInput
      )
      TotalConsiderationOfTransaction.saveAndContinue()
      Then("the IsVATIncluded page is shown")
      IsVatIncluded.verifyPageTitle(IsVatIncluded.pageTitle)

      When("the user confirms there is vat included in the total consideration")
      IsVatIncluded.radioButton(IsVatIncluded.yes)
      IsVatIncluded.saveAndContinue()
      Then("the VATAmount page is shown")
      VATAmount.verifyPageTitle(VATAmount.pageTitle)

      When("the user provides the VAT Amount")
      VATAmount.input(
        By.id(VATAmount.totalAmountOfVAT),
        VATAmount.totalAmountOfVATInput
      )
      VATAmount.saveAndContinue()
      Then("the FormsOfConsideration page is shown")
      FormsOfConsideration.verifyPageTitle(FormsOfConsideration.pageTitle)

      When("the user provides all forms the consideration takes")
      FormsOfConsideration.checkbox(FormsOfConsideration.cash, true)
      FormsOfConsideration.checkbox(FormsOfConsideration.building_works, true)
      FormsOfConsideration.checkbox(FormsOfConsideration.shares_quoted_company, true)
      FormsOfConsideration.checkbox(FormsOfConsideration.contingent, true)
      FormsOfConsideration.saveAndContinue()
      Then("the LinkedTransactions page is shown")
      LinkedTransactions.verifyPageTitle(LinkedTransactions.pageTitle)

      When("the user confirms the transaction is linked to another")
      LinkedTransactions.radioButton(LinkedTransactions.yes)
      LinkedTransactions.saveAndContinue()
      Then("the TotalConsiderationOfLinkedTransaction page is shown")
      TotalConsiderationOfLinkedTransaction.verifyPageTitle(TotalConsiderationOfLinkedTransaction.pageTitle)

      When("the user provides the total consideration of linked transactions")
      TotalConsiderationOfLinkedTransaction.input(
        By.id(TotalConsiderationOfLinkedTransaction.totalConsiderationOfLT),
        TotalConsiderationOfLinkedTransaction.totalConsiderationOfLTInput
      )
      TotalConsiderationOfLinkedTransaction.saveAndContinue()
      Then("the ClaimingRelief page is shown")
      ClaimingRelief.verifyPageTitle(ClaimingRelief.pageTitle)

      When("the user confirms the purchaser is eligible to claim relief")
      ClaimingRelief.radioButton(ClaimingRelief.yes)
      ClaimingRelief.saveAndContinue()
      Then("the ReasonForRelief page is shown")
      ReasonForRelief.verifyPageTitle(ReasonForRelief.pageTitle)

      When("the user selects part exchange as the reason for claiming relief")
      ReasonForRelief.radioButton(ReasonForRelief.partExchange)
      ReasonForRelief.saveAndContinue()
      Then("the IsThePurchaserRegisteredWithCIS page is shown")
      IsThePurchaserRegisteredWithCIS.verifyPageTitle(IsThePurchaserRegisteredWithCIS.pageTitle)

      When("the user confirms the purchaser is registered with cis")
      IsThePurchaserRegisteredWithCIS.radioButton(IsThePurchaserRegisteredWithCIS.yes)
      IsThePurchaserRegisteredWithCIS.saveAndContinue()
      Then("the EnterCISRegistrationNumber page is shown")
      EnterCISRegistrationNumber.verifyPageTitle(EnterCISRegistrationNumber.pageTitle)

      When("the user provides the purchaser's cis registration number")
      EnterCISRegistrationNumber.input(
        By.id(EnterCISRegistrationNumber.CISRegistrationNumber),
        EnterCISRegistrationNumber.CISRegistrationNumberInput
      )
      EnterCISRegistrationNumber.saveAndContinue()
      Then("the PartialRelief page is shown")
      PartialRelief.verifyPageTitle(PartialRelief.pageTitle)

      When("the user confirms the purchaser is claiming relief on part of the land")
      PartialRelief.radioButton(PartialRelief.yes)
      PartialRelief.saveAndContinue()
      Then("the ClaimingPartialRelief page is shown")
      ClaimingPartialRelief.verifyPageTitle(ClaimingPartialRelief.pageTitle)

      When("the user provides the total consideration remains chargeable to sdlt")
      ClaimingPartialRelief.input(
        By.id(ClaimingPartialRelief.totalPartialRelief),
        ClaimingPartialRelief.totalPartialReliefInput
      )
      ClaimingPartialRelief.saveAndContinue()
      Then("the ConsiderationsAffectedByUncertainFutureEvents page is shown")
      ConsiderationsAffectedByUncertainFutureEvents.verifyPageTitle(
        ConsiderationsAffectedByUncertainFutureEvents.pageTitle
      )

      When("the user confirms a part of the consideration is not contingent or dependent on uncertain future events")
      ConsiderationsAffectedByUncertainFutureEvents.radioButton(ConsiderationsAffectedByUncertainFutureEvents.no)
      ConsiderationsAffectedByUncertainFutureEvents.saveAndContinue()
      Then("the UseOfLandOrProperty page is shown")
      UseOfLandOrProperty.verifyPageTitle(UseOfLandOrProperty.pageTitle)

      When("the user confirms what the land is being used for")
      UseOfLandOrProperty.checkbox(UseOfLandOrProperty.other, true)
      UseOfLandOrProperty.checkbox(UseOfLandOrProperty.otherIndustrialUnit, true)
      UseOfLandOrProperty.saveAndContinue()
      Then("the SaleOfABusiness page is shown")
      SaleOfABusiness.verifyPageTitle(SaleOfABusiness.pageTitle)

      When("the user confirms the transaction is not part of the sale of a business")
      SaleOfABusiness.radioButton(SaleOfABusiness.no)
      SaleOfABusiness.saveAndContinue()
      Then("the CAP1OrNSBC page is shown")
      CAP1OrNSBC.verifyPageTitle(CAP1OrNSBC.pageTitle)

      When("the user confirms they have not applied for a CAP1 or NSBC for the transaction")
      CAP1OrNSBC.radioButton(CAP1OrNSBC.no)
      CAP1OrNSBC.saveAndContinue()
      Then("the RestrictionsCovenantsAndConditions page is shown")
      RestrictionsCovenantsAndConditions.verifyPageTitle(RestrictionsCovenantsAndConditions.pageTitle)

      When(
        "the user confirms there are no restrictions, covenants or conditions affecting the value of the interest transferred"
      )
      RestrictionsCovenantsAndConditions.radioButton(RestrictionsCovenantsAndConditions.no)
      RestrictionsCovenantsAndConditions.saveAndContinue()
      Then("the ExchangeOrPartExchange page is shown")
      ExchangeOrPartExchange.verifyPageTitle(ExchangeOrPartExchange.pageTitle)

      When("the user confirms the land is being exchanged or part exchanged")
      ExchangeOrPartExchange.radioButton(ExchangeOrPartExchange.yes)
      ExchangeOrPartExchange.saveAndContinue()
      Then("the TransactionAddressLookup page is shown")
      TransactionAddressLookup.verifyPageTitle(TransactionAddressLookup.pageTitle)

      When("the user provides the transaction exchange address")
      TransactionAddressLookup.clickAddressManually()
      TransactionAddressLookup.verifyPageTitle(TransactionAddressLookup.editPageTitle)
      TransactionAddressLookup.enterAddressManually("523", "AGC", "TE11 1TS")
      TransactionAddressLookup.verifyPageTitle(TransactionAddressLookup.confirmPageTitle)
      TransactionAddressLookup.clickConfirmAddress()
      Then("the ExercisingAnOption page is shown")
      ExercisingAnOption.verifyPageTitle(ExercisingAnOption.pageTitle)

      When("the user confirms the transaction is pursuant to a previous option agreement")
      ExercisingAnOption.radioButton(ExercisingAnOption.yes)
      ExercisingAnOption.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates the total consideration")
      TransactionCheckYourAnswers.clickTotalConsiderationOfTransaction()
      TotalConsiderationOfTransaction.verifyPageTitle(TotalConsiderationOfTransaction.pageTitle)
      TotalConsiderationOfTransaction.input(
        By.id(TotalConsiderationOfTransaction.totalConsideration),
        TotalConsiderationOfTransaction.totalConsiderationInputCYA
      )
      TotalConsiderationOfTransaction.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user confirms there is no vat included in the total consideration")
      TransactionCheckYourAnswers.clickIsVATIncluded()
      IsVatIncluded.verifyPageTitle(IsVatIncluded.pageTitle)
      IsVatIncluded.radioButton(IsVatIncluded.no)
      IsVatIncluded.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their forms of consideration")
      TransactionCheckYourAnswers.clickFormsOfConsideration()
      FormsOfConsideration.verifyPageTitle(FormsOfConsideration.pageTitle)
      FormsOfConsideration.checkbox(FormsOfConsideration.cash, true)
      FormsOfConsideration.checkbox(FormsOfConsideration.building_works, true)
      FormsOfConsideration.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When(
        "the user updates their answer to confirm if any part of consideration is contingent on uncertain future events"
      )
      TransactionCheckYourAnswers.clickUncertainFutureEvent()
      ConsiderationsAffectedByUncertainFutureEvents.verifyPageTitle(
        ConsiderationsAffectedByUncertainFutureEvents.pageTitle
      )
      ConsiderationsAffectedByUncertainFutureEvents.radioButton(ConsiderationsAffectedByUncertainFutureEvents.yes)
      ConsiderationsAffectedByUncertainFutureEvents.saveAndContinue()
      DeferringPayment.verifyPageTitle(DeferringPayment.pageTitle)
      DeferringPayment.radioButton(DeferringPayment.yes)
      DeferringPayment.saveAndContinue()
      UseOfLandOrProperty.verifyPageTitle(UseOfLandOrProperty.pageTitle)
      UseOfLandOrProperty.checkbox(UseOfLandOrProperty.other, true)
      UseOfLandOrProperty.checkbox(UseOfLandOrProperty.otherIndustrialUnit, false)
      UseOfLandOrProperty.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm the transaction is part of the sale of a business")
      TransactionCheckYourAnswers.clickSaleOfBusiness()
      SaleOfABusiness.verifyPageTitle(SaleOfABusiness.pageTitle)
      SaleOfABusiness.radioButton(SaleOfABusiness.yes)
      SaleOfABusiness.saveAndContinue()
      AssetsIncludedInSaleOfTheBusiness.verifyPageTitle(AssetsIncludedInSaleOfTheBusiness.pageTitle)
      AssetsIncludedInSaleOfTheBusiness.checkbox(AssetsIncludedInSaleOfTheBusiness.others, true)
      AssetsIncludedInSaleOfTheBusiness.saveAndContinue()
      TotalConsiderationOfAllAssets.verifyPageTitle(TotalConsiderationOfAllAssets.pageTitle)
      TotalConsiderationOfAllAssets.input(
        By.id(TotalConsiderationOfAllAssets.totalConsiderationOfAllAssets),
        TotalConsiderationOfAllAssets.totalConsiderationOfAllAssetsInput
      )
      TotalConsiderationOfAllAssets.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm they have applied for CAP1 or NSBC")
      TransactionCheckYourAnswers.clickCAP1OrNSBC()
      CAP1OrNSBC.verifyPageTitle(CAP1OrNSBC.pageTitle)
      CAP1OrNSBC.radioButton(CAP1OrNSBC.yes)
      CAP1OrNSBC.saveAndContinue()
      HaveYouFollowedTheRuling.verifyPageTitle(
        HaveYouFollowedTheRuling.pageTitle
      )
      HaveYouFollowedTheRuling.radioButton(HaveYouFollowedTheRuling.yes)
      HaveYouFollowedTheRuling.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user updates their answer to not exercising an option")
      TransactionCheckYourAnswers.clickExercisingAnOption()
      ExercisingAnOption.verifyPageTitle(ExercisingAnOption.pageTitle)
      ExercisingAnOption.radioButton(ExercisingAnOption.no)
      ExercisingAnOption.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user submits the transaction questions")
      TransactionCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }
  }
}
