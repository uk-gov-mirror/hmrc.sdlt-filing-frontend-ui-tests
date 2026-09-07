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
import uk.gov.hmrc.ui.pages.Preliminary.*
import uk.gov.hmrc.ui.pages.Vendor.*
import uk.gov.hmrc.ui.pages.VendorAgent.*
import uk.gov.hmrc.ui.pages.Purchaser.*
import uk.gov.hmrc.ui.pages.PurchaserAgent.*
import uk.gov.hmrc.ui.pages.Land.*
import uk.gov.hmrc.ui.pages.UKResidency.*
import uk.gov.hmrc.ui.pages.Transaction.*
import uk.gov.hmrc.ui.pages.Lease.*
import uk.gov.hmrc.ui.pages.TaxCalculations.*
import uk.gov.hmrc.ui.pages.DeclarationAndSubmission.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import uk.gov.hmrc.ui.tags.*

class e2eSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend End to End Journeys") {

    Scenario(
      "Complete the Filing Journey from Preliminary to Transaction Questions",
      e2eJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation)
      Then("the PreliminaryBeforeYouStart page is shown")
      PreliminaryBeforeYouStart.verifyPageTitle(PreliminaryBeforeYouStart.pageTitle)

      When("the user starts the preliminary questions")
      PreliminaryBeforeYouStart.saveAndContinue()
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
      PreliminaryPurchaserName.clickSubmitButton()
      Then("the PreliminaryPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.pageTitle)

      When("the user enters the property address manually")
      PreliminaryPropertyAddress.clickAddressManually()
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.editPageTitle)
      PreliminaryPropertyAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("the ConfirmPropertyAddress page is shown")
      PreliminaryPropertyAddress.verifyPageTitle(PreliminaryPropertyAddress.confirmPageTitle)

      When("the user confirms the property address")
      PreliminaryPropertyAddress.clickContinueButton()
      Then("the TransactionType page is shown")
      TransactionType.verifyPageTitle(TransactionType.pageTitle)

      When("the user selects Conveyance/transfer with lease involvement as the transaction type")
      TransactionType.radioButton(TransactionType.conveyance)
      TransactionType.saveAndContinue()
      Then("the PreliminaryCheckYourAnswers page is shown")
      PreliminaryCheckYourAnswers.verifyPageTitle(PreliminaryCheckYourAnswers.pageTitle)

      When("the user submits the preliminary questions")
      PreliminaryCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the vendor questions")
      WhoIsTheVendor.clickLinkById("task-list-link-about-the-vendor")
      Then("the VendorOverview page is shown")
      VendorOverview.verifyPageTitle(VendorOverview.pageTitle)

      When("the user adds a new vendor")
      VendorOverview.radioButton(VendorOverview.yes)
      VendorOverview.saveAndContinue()
      Then("the VendorBeforeYouStart page is shown")
      VendorBeforeYouStart.verifyPageTitle(VendorBeforeYouStart.pageTitle)

      When("the user starts the vendor questions")
      VendorBeforeYouStart.saveAndContinue()
      Then("the WhoIsTheVendor page is shown")
      WhoIsTheVendor.verifyPageTitle(WhoIsTheVendor.pageTitle)

      When("the user selects Company as the vendor type")
      WhoIsTheVendor.radioButton(WhoIsTheVendor.company)
      WhoIsTheVendor.saveAndContinue()
      Then("the VendorName page is shown")
      WhoIsTheVendor.verifyPageTitle(VendorName.pageTitleCompany)

      When("the user provides the company name")
      VendorName.input(
        By.id(VendorName.companyName),
        VendorName.companyNameInput
      )
      VendorName.saveAndContinue()
      Then("the ConfirmVendorsAddress page is shown")
      ConfirmVendorsAddress.verifyPageTitle(ConfirmVendorsAddress.pageTitle)

      When("the user confirms to enter the vendor address manually")
      ConfirmVendorsAddress.radioButton(ConfirmVendorsAddress.no)
      ConfirmVendorsAddress.saveAndContinue()
      Then("the VendorPropertyAddress page is shown")
      VendorPropertyAddress.verifyPageTitle(VendorPropertyAddress.pageTitle)

      When("the user enters the vendor address manually")
      VendorPropertyAddress.clickAddressManually()
      VendorPropertyAddress.verifyPageTitle(VendorPropertyAddress.editPageTitleBusiness)
      VendorPropertyAddress.enterAddressManually("523", "AGC", "TE11 1TS")
      Then("the ConfirmVendorPropertyAddress page is shown")
      VendorPropertyAddress.verifyPageTitle(VendorPropertyAddress.confirmPageTitleBusiness)

      When("the user confirms the vendor address")
      VendorPropertyAddress.clickContinueButton()
      Then("the VendorCheckYourAnswers page is shown")
      VendorCheckYourAnswers.verifyPageTitle(VendorCheckYourAnswers.pageTitle)

      When("the user submits the vendor questions")
      VendorCheckYourAnswers.saveAndContinue()
      Then("the VendorOverview page is shown")
      VendorOverview.verifyPageTitle(VendorOverview.pageTitle)

      When("the user does not add another vendor")
      VendorOverview.radioButton(VendorOverview.no)
      VendorOverview.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the vendor agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-vendor’s-agent")
      Then("the VendorAgentBeforeYouStart page is shown")
      VendorAgentBeforeYouStart.verifyPageTitle(VendorAgentBeforeYouStart.pageTitle)

      When("the user starts the vendor agent questions")
      VendorAgentBeforeYouStart.radioButton(VendorAgentBeforeYouStart.yes)
      VendorAgentBeforeYouStart.saveAndContinue()
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
      VendorPropertyAddress.clickContinueButton()
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

      When("the user submits the vendor agent questions")
      VendorAgentCheckYourAnswers.saveAndContinue()
      Then("the VendorAgentOverview page is shown")
      VendorAgentOverview.verifyPageTitle(VendorAgentOverview.pageTitle)

      When("the user does not add another vendor agent")
      VendorAgentOverview.radioButton(VendorAgentOverview.no)
      VendorAgentOverview.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the purchaser questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the Purchaser Overview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)

      When("the user adds a new purchaser")
      PurchaserOverview.radioButton(PurchaserOverview.yes)
      PurchaserOverview.saveAndContinue()
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.saveAndContinue()
      Then("the PurchaserWhoIsMakingThePurchase page is shown")
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)

      When("the user chooses an individual as the purchaser")
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.individual)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      Then("the PurchaserName page is shown")
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)

      When("the user inputs the purchaser first name, middle name, and surname")
      PurchaserName.input(
        By.id(PurchaserName.forenameId),
        PurchaserName.forenameInput
      )
      PurchaserName.input(
        By.id(PurchaserName.middlenameId),
        PurchaserName.middlenameInput
      )
      PurchaserName.input(
        By.id(PurchaserName.surnameId),
        PurchaserName.surnameInput
      )
      PurchaserName.saveAndContinue()
      Then("the PurchaserConfirmAddress page is shown")
      PurchaserConfirmAddress.verifyPageTitle(PurchaserConfirmAddress.pageTitle)

      When("the user confirms the purchasers address")
      PurchaserConfirmAddress.radioButton(PurchaserConfirmAddress.yes)
      PurchaserConfirmAddress.saveAndContinue()
      Then("the ActingAsATrustee page is shown")
      ActingAsATrustee.verifyPageTitle(ActingAsATrustee.pageTitle)

      When("the user confirms the purchaser is acting as a trustee")
      ActingAsATrustee.radioButton(ActingAsATrustee.yes)
      ActingAsATrustee.saveAndContinue()
      Then("the PurchaserAndVendorConnected page is shown")
      PurchaserAndVendorConnected.verifyPageTitle(PurchaserAndVendorConnected.pageTitle)

      When("the user confirms the purchaser and vendor are connected")
      PurchaserAndVendorConnected.radioButton(PurchaserAndVendorConnected.yes)
      PurchaserAndVendorConnected.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)

      When("the user does not add another purchaser")
      PurchaserOverview.radioButton(PurchaserOverview.no)
      PurchaserOverview.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the purchaser agent questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser’s-agent")
      Then("the PurchaserAgentBeforeYouStart page is shown")
      PurchaserAgentBeforeYouStart.verifyPageTitle(PurchaserAgentBeforeYouStart.pageTitle)

      When("the user starts the purchaser agent questions")
      PurchaserAgentBeforeYouStart.radioButton(PurchaserAgentBeforeYouStart.yes)
      PurchaserAgentBeforeYouStart.saveAndContinue()
      Then("the SelectPurchaserAgent page is shown")
      SelectPurchaserAgent.verifyPageTitle(SelectPurchaserAgent.pageTitle)

      When("the user adds an existing agent")
      SelectPurchaserAgent.radioButton(SelectPurchaserAgent.selectAgent)
      SelectPurchaserAgent.saveAndContinue()
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

      When("the user submits the purchaser agent questions")
      PurchaserAgentCheckYourAnswers.saveAndContinue()
      Then("the PurchaserAgentOverview page is shown")
      PurchaserAgentOverview.verifyPageTitle(PurchaserAgentOverview.pageTitle)

      When("the user does not add another purchaser agent")
      PurchaserAgentOverview.radioButton(PurchaserAgentOverview.no)
      PurchaserAgentOverview.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the land questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-land")
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)

      When("the user adds a new area of land")
      LandOverview.radioButton(LandOverview.yes)
      LandOverview.saveAndContinue()
      Then("the LandBeforeYouStart page is shown")
      LandBeforeYouStart.verifyPageTitle(LandBeforeYouStart.pageTitle)

      When("the user starts the land questions")
      LandBeforeYouStart.saveAndContinue()
      Then("the TypeOfProperty page is shown")
      TypeOfProperty.verifyPageTitle(TypeOfProperty.pageTitle)

      When("the user selects residential as the property type")
      TypeOfProperty.radioButton(TypeOfProperty.residential)
      TypeOfProperty.saveAndContinue()
      Then("the InterestTransferredCreated page is shown")
      InterestTransferredOrCreated.verifyPageTitle(InterestTransferredOrCreated.pageTitle)

      When("the user selects long leasehold to describe the transaction")
      InterestTransferredOrCreated.radioButton(InterestTransferredOrCreated.LG)
      InterestTransferredOrCreated.saveAndContinue()
      Then("the LandAddress page is shown")
      LandAddress.verifyPageTitle(LandAddress.pageTitle)

      When("the user enters the land address manually")
      LandAddress.clickAddressManually()
      LandAddress.verifyPageTitle(LandAddress.editPageTitle)
      LandAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("the ConfirmLandAddress page is shown")
      LandAddress.verifyPageTitle(LandAddress.confirmPageTitle)

      When("the user confirms the land address")
      LandAddress.clickContinueButton()
      Then("the LocalAuthorityCode page is shown")
      LocalAuthorityCode.verifyPageTitle(LocalAuthorityCode.pageTitle)

      When("the user provides a local authority code")
      LocalAuthorityCode.input(
        By.id(LocalAuthorityCode.localAuthCode),
        LocalAuthorityCode.localAuthCodeInput
      )
      LocalAuthorityCode.saveAndContinue()
      Then("the HMLandRegistration page is shown")
      HMLandRegistration.verifyPageTitle(HMLandRegistration.pageTitle)

      When("the user confirms the land is not registered with hm land registry")
      HMLandRegistration.radioButton(HMLandRegistration.no)
      HMLandRegistration.saveAndContinue()
      Then("the AddNLPGUPRN page is shown")
      AddNLPGUPRN.verifyPageTitle(AddNLPGUPRN.pageTitle)

      When("the user confirms they do not have a NLPG UPRN")
      AddNLPGUPRN.radioButton(AddNLPGUPRN.no)
      AddNLPGUPRN.saveAndContinue()
      Then("the SendingPlanByPost page is shown")
      SendingAPlan.verifyPageTitle(SendingAPlan.pageTitle)

      When("the user confirms they will send a plan by post")
      SendingAPlan.radioButton(SendingAPlan.yes)
      SendingAPlan.saveAndContinue()
      Then("the MineralsOrMineralRights page is shown")
      MineralRights.verifyPageTitle(MineralRights.pageTitle)

      When("the user confirms there are mineral or mineral rights reserved")
      MineralRights.radioButton(MineralRights.yes)
      MineralRights.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user submits the land questions")
      LandCheckYourAnswers.saveAndContinue()
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)

      When("the user does not add another land")
      LandOverview.radioButton(LandOverview.no)
      LandOverview.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the transaction questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-transaction")
      Then("the TransactionBeforeYouStart page is shown")
      TransactionBeforeYouStart.verifyPageTitle(TransactionBeforeYouStart.pageTitle)

      When("the user starts the transaction questions")
      TransactionBeforeYouStart.saveAndContinue()
      Then("the ConfirmTypeOfTransaction page is shown")
      ConfirmTypeOfTransaction.verifyPageTitle(ConfirmTypeOfTransaction.pageTitle)

      When("the user confirms the transaction type is correct")
      ConfirmTypeOfTransaction.radioButton(ConfirmTypeOfTransaction.yes)
      ConfirmTypeOfTransaction.saveAndContinue()
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

      When("the user confirms the purchaser is not applying for a deferment")
      DeferringPayment.radioButton(DeferringPayment.no)
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
      TransactionAddressLookup.clickSubmitButton()
      Then("the ExercisingAnOption page is shown")
      ExercisingAnOption.verifyPageTitle(ExercisingAnOption.pageTitle)

      When("the user confirms the transaction is pursuant to a previous option agreement")
      ExercisingAnOption.radioButton(ExercisingAnOption.yes)
      ExercisingAnOption.saveAndContinue()
      Then("the TransactionCheckYourAnswers page is shown")
      TransactionCheckYourAnswers.verifyPageTitle(TransactionCheckYourAnswers.pageTitle)

      When("the user submits the transaction questions")
      TransactionCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Filing Journey from UK Residency, Lease Journey, Save and Delete returnid",
      e2eJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("e2e-from-uk-residency-to-tax-calculation"))
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the uk residency questions")
      ReturnTaskList.clickLinkById("task-list-link-about-uk-residency")
      Then("the UKResidencyBeforeYouStart page is shown")
      UKResidencyBeforeYouStart.verifyPageTitle(UKResidencyBeforeYouStart.pageTitle)

      When("the user starts the uk residency questions")
      UKResidencyBeforeYouStart.saveAndContinue()
      Then("the ResidencyStatus page is shown")
      ResidencyStatus.verifyPageTitle(ResidencyStatus.pageTitle)

      When("the user confirms there are non-UK resident purchasers")
      ResidencyStatus.radioButton(ResidencyStatus.yes)
      ResidencyStatus.saveAndContinue()
      Then("the CloseCompanies page is shown")
      CloseCompanies.verifyPageTitle(CloseCompanies.pageTitle)

      When("the user confirms that the purchaser is a UK close company controlled by non-UK residents")
      CloseCompanies.radioButton(CloseCompanies.yes)
      CloseCompanies.saveAndContinue()
      Then("the CrownEmploymentRelief page is shown")
      CrownEmploymentRelief.verifyPageTitle(CrownEmploymentRelief.pageTitle)

      When("the user confirms there are purchasers claiming Crown Employment relief")
      CrownEmploymentRelief.radioButton(CrownEmploymentRelief.yes)
      CrownEmploymentRelief.saveAndContinue()
      Then("the UKResidencyCheckYourAnswers page is shown")
      UKResidencyCheckYourAnswers.verifyPageTitle(UKResidencyCheckYourAnswers.pageTitle)

      When("the user submits the uk residency questions")
      UKResidencyCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user opens the lease questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-lease")
      Then("the LeaseBeforeYouStart page is shown")
      LeaseBeforeYouStart.verifyPageTitle(LeaseBeforeYouStart.pageTitle)

      When("the user starts the lease questions")
      LeaseBeforeYouStart.saveAndContinue()
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

      When("the user provides the lease end date")
      LeaseEndDate.enterLeaseEndDate()
      LeaseEndDate.saveAndContinue()
      Then("the AddRentFreePeriod page is shown")
      AddRentFreePeriod.verifyPageTitle(AddRentFreePeriod.pageTitle)

      When("the user confirms the lease includes a rent free period")
      AddRentFreePeriod.radioButton(AddRentFreePeriod.yes)
      AddRentFreePeriod.saveAndContinue()
      Then("the EnterRentFreePeriod page is shown")
      EnterRentFreePeriod.verifyPageTitle(EnterRentFreePeriod.pageTitle)

      When("the user provides the rent free period in months")
      EnterRentFreePeriod.input(By.id(EnterRentFreePeriod.rentFreePeriod), EnterRentFreePeriod.inputRentFreePeriod)
      EnterRentFreePeriod.saveAndContinue()
      Then("the AnnualStartingRent page is shown")
      AnnualStartingRent.verifyPageTitle(AnnualStartingRent.pageTitle)

      When("the user provides the annual starting rent")
      AnnualStartingRent.clickDropdownText()
      AnnualStartingRent.verifyPageText(AnnualStartingRent.dropdownText, 2)
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

      When("the user submits the lease questions")
      LeaseCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user attempts to save and exit the application")
      ReturnTaskList.clickSaveAndExit()
      Then("the SaveAndExit page is shown")
      SaveAndExit.verifyPageTitle(SaveAndExit.pageTitle)

      When("the user chooses not to save and exit the application")
      SaveAndExit.radioButton(SaveAndExit.no)
      SaveAndExit.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)

      When("the user attempts to delete the application")
      ReturnTaskList.clickDeleteReturn()
      Then("the delete return page is shown")
      DeleteReturn.verifyPageTitle(DeleteReturn.pageTitle)

      When("the user chooses not to save and exit the application")
      DeleteReturn.radioButton(DeleteReturn.no)
      DeleteReturn.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Freehold calculated Tax Calculation Journey",
      e2eJourney
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
      ConfirmEffectiveDateOfTransaction.saveAndContinue()
      Then("the user is navigated to Is this effective date of transaction page")
      IsThisTheEffectiveDateOfTransaction.verifyPageTitle(IsThisTheEffectiveDateOfTransaction.pageTitle)

      When("the user selects effective date before oct 2024 as yes and continues")
      IsThisTheEffectiveDateOfTransaction.radioButton(IsThisTheEffectiveDateOfTransaction.yes)
      IsThisTheEffectiveDateOfTransaction.saveAndContinue()
      Then("the Freehold calculated Before you start page is displayed")
      TaxCalculationBeforeYouStart.verifyPageTitle(TaxCalculationBeforeYouStart.pageTitle)

      When("the user is navigated to sdlt due page")
      TaxCalculationBeforeYouStart.saveAndContinue()
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
      CalculatedSDLTDue.saveAndContinue()
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
      TaxCalculationCheckYourAnswers.saveAndContinue()
      Then("the ReturnTaskList page is shown")
      ReturnTaskList.verifyPageTitle(ReturnTaskList.pageTitle)
    }

    Scenario(
      "Complete the Declaration and Submission questions for a complete submission",
      e2eJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(
        HASDIRECT,
        Organisation,
        returnId = Some("submission-complete-multiples")
      )

      Then("the DeclarationAndSubmissionBeforeYouStart page is shown")
      DeclarationAndSubmissionBeforeYouStart.verifyPageTitle(DeclarationAndSubmissionBeforeYouStart.pageTitle)

      When("the user starts the submit your return questions")
      DeclarationAndSubmissionBeforeYouStart.saveAndContinue()
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
      DeclarationConfirmation.saveAndContinue()
//      Then("the SubmissionComplete page is shown")
//      SubmissionComplete.waitForPage()
//      SubmissionComplete.verifyPageTitle(SubmissionComplete.pageTitle)
//
//      When("the user views their submitted sdlt return")
//      SubmissionComplete.click(SubmissionComplete.submittedReturnLink)
//      Then("the YourCompletedSDLTReturn page is shown")
//      YourCompletedSDLTReturn.verifyPageTitle(YourCompletedSDLTReturn.submittedReturnPageTitle)
//
//      When("the user views their sdlt5 certificate")
//      SubmissionComplete.navigateBackToPage()
//      SubmissionComplete.verifyPageTitle(SubmissionComplete.pageTitle)
//      SubmissionComplete.click(SubmissionComplete.sdlt5certificateLink)
//      Then("the SubmissionReceiptAndSDLT5 page is shown")
//      SubmissionReceiptAndSDLT5.switchToNewTabAndValidateTitle(SubmissionReceiptAndSDLT5.pageTitle)
    }
  }
}
