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
import uk.gov.hmrc.ui.pages.Purchaser.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import uk.gov.hmrc.ui.tags.*

class PurchaserQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Purchaser Questions") {

    Scenario(
      "Complete the Purchaser Questions journey changing from an individual to a company",
      PurchaserJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("no-purchaser"))

      When("the user opens the purchaser questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.clickContinueButton()
      Then("the PurchaserWhoIsMakingThePurchase page is shown")
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)

      When("the user chooses an individual as the purchaser")
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.individual)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      Then("the PurchaserName page is shown")
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)

      When("the user provides the purchaser first name, middle name, and surname")
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

      When("the user confirms to enter the purchaser address manually")
      PurchaserConfirmAddress.radioButton(PurchaserConfirmAddress.no)
      PurchaserConfirmAddress.saveAndContinue()
      Then("the PurchaserAddress page is shown")
      PurchaserAddress.verifyPageTitle(PurchaserAddress.pageTitle)

      When("the user enters the purchaser address manually")
      PurchaserAddress.clickAddressManually()
      PurchaserAddress.verifyPageTitle(PurchaserAddress.editPageTitle)
      PurchaserAddress.enterAddressManually("123", "ABC", "TE13 1ES")
      Then("the ConfirmPurchaserAddress page is shown")
      PurchaserAddress.verifyPageTitle(PurchaserAddress.confirmPageTitle)

      When("the user confirms the purchaser address")
      PurchaserAddress.clickConfirmAddress()
      Then("the DoesPurchaserHavePhoneNumber page is shown")
      DoesPurchaserHavePhoneNumber.verifyPageTitle(DoesPurchaserHavePhoneNumber.pageTitle)

      When("the user confirms to not add a phone number")
      DoesPurchaserHavePhoneNumber.radioButton(DoesPurchaserHavePhoneNumber.no)
      DoesPurchaserHavePhoneNumber.saveAndContinue()
      Then("the AddPurchaserNationalInsuranceNumber page is shown")
      AddPurchaserNationalInsuranceNumber.verifyPageTitle(AddPurchaserNationalInsuranceNumber.pageTitle)

      When("the user confirms to add a national insurance number")
      AddPurchaserNationalInsuranceNumber.radioButton(AddPurchaserNationalInsuranceNumber.yes)
      AddPurchaserNationalInsuranceNumber.saveAndContinue()
      Then("the EnterPurchaserNationalInsuranceNumber page is shown")
      EnterPurchaserNationalInsuranceNumber.verifyPageTitle(EnterPurchaserNationalInsuranceNumber.pageTitle)

      When("the user provides a national insurance number")
      EnterPurchaserNationalInsuranceNumber.input(
        By.id(EnterPurchaserNationalInsuranceNumber.Nino),
        EnterPurchaserNationalInsuranceNumber.NinoValue
      )
      EnterPurchaserNationalInsuranceNumber.saveAndContinue()
      Then("the DateOfBirth page is shown")
      DateOfBirth.verifyPageTitle(DateOfBirth.pageTitle)

      When("the user provides the purchaser date of birth")
      DateOfBirth.enterDateOfBirth()
      DateOfBirth.saveAndContinue()
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

      When("the user updates the purchaser name")
      PurchaserCheckYourAnswers.clickPurchaserNameChange()
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)
      PurchaserName.input(By.id(PurchaserName.surnameId), PurchaserName.surnameInput2)
      PurchaserName.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the national insurance number")
      PurchaserCheckYourAnswers.clickPurchaserNINumberChange()
      EnterPurchaserNationalInsuranceNumber.verifyPageTitle(EnterPurchaserNationalInsuranceNumber.pageTitle)
      EnterPurchaserNationalInsuranceNumber.input(
        By.id(EnterPurchaserNationalInsuranceNumber.Nino),
        EnterPurchaserNationalInsuranceNumber.NinoValue2
      )
      EnterPurchaserNationalInsuranceNumber.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the date of birth")
      PurchaserCheckYourAnswers.clickPurchaserDOBChange()
      DateOfBirth.verifyPageTitle(DateOfBirth.pageTitle)
      DateOfBirth.enterDateOfBirth()
      DateOfBirth.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser type to company")
      PurchaserCheckYourAnswers.clickPurchaserTypeChange()
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.company)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)
      PurchaserName.input(
        By.id(PurchaserName.companyId),
        PurchaserName.companyName2
      )
      PurchaserName.saveAndContinue()
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitle)
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.vatRegistrationNumber)
      ConfirmPurchaserIdentity.saveAndContinue()
      VATRegistrationNumber.verifyPageTitle(VATRegistrationNumber.pageTitle)
      VATRegistrationNumber.input(
        By.id(VATRegistrationNumber.vat),
        VATRegistrationNumber.VATNumber
      )
      VATRegistrationNumber.saveAndContinue()
      DoYouKnowTypeOfCompany.verifyPageTitle(DoYouKnowTypeOfCompany.pageTitle)
      DoYouKnowTypeOfCompany.radioButton(DoYouKnowTypeOfCompany.no)
      DoYouKnowTypeOfCompany.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)
    }

    Scenario(
      "Complete the Purchaser Questions journey as an Individual with no National Insurance Number",
      PurchaserJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("no-purchaser"))

      When("the user opens the purchaser questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.clickContinueButton()
      Then("the PurchaserWhoIsMakingThePurchase page is shown")
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)

      When("the user chooses an individual as the purchaser")
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.individual)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      Then("the PurchaserName page is shown")
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)

      When("the user provides the purchaser first name, middle name, and surname")
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

      When("the user confirms the purchaser address")
      PurchaserConfirmAddress.radioButton(PurchaserConfirmAddress.yes)
      PurchaserConfirmAddress.saveAndContinue()
      Then("the DoesPurchaserHavePhoneNumber page is shown")
      DoesPurchaserHavePhoneNumber.verifyPageTitle(DoesPurchaserHavePhoneNumber.pageTitle)

      When("the user confirms to add a phone number")
      DoesPurchaserHavePhoneNumber.radioButton(DoesPurchaserHavePhoneNumber.yes)
      DoesPurchaserHavePhoneNumber.saveAndContinue()
      Then("the EnterPurchaserPhoneNumber page is shown")
      EnterPurchaserPhoneNumber.verifyPageTitle(EnterPurchaserPhoneNumber.pageTitle)

      When("the user provides a phone number")
      EnterPurchaserPhoneNumber.input(
        By.id(EnterPurchaserPhoneNumber.phoneNumberInputField),
        EnterPurchaserPhoneNumber.phoneNumberValue
      )
      EnterPurchaserPhoneNumber.saveAndContinue()
      Then("the AddPurchaserNationalInsuranceNumber page is shown")
      AddPurchaserNationalInsuranceNumber.verifyPageTitle(AddPurchaserNationalInsuranceNumber.pageTitle)

      When("the user confirms to not add a national insurance number")
      AddPurchaserNationalInsuranceNumber.radioButton(AddPurchaserNationalInsuranceNumber.no)
      AddPurchaserNationalInsuranceNumber.saveAndContinue()
      Then("the FormOfIDIndividual page is shown")
      FormOfIDIndividual.verifyPageTitle(FormOfIDIndividual.pageTitle)

      When("the user provides id number or reference")
      FormOfIDIndividual.input(
        By.id(FormOfIDIndividual.purchaserIdNumberOrReference),
        FormOfIDIndividual.idNumberOrReferenceInput
      )
      And("country of issue")
      FormOfIDIndividual.input(
        By.id(FormOfIDIndividual.purchaserCountryIssued),
        FormOfIDIndividual.countryIssuedInput
      )
      FormOfIDIndividual.saveAndContinue()
      Then("the ActingAsATrustee page is shown")
      ActingAsATrustee.verifyPageTitle(ActingAsATrustee.pageTitle)

      When("the user confirms the purchaser is not acting as a trustee")
      ActingAsATrustee.radioButton(ActingAsATrustee.no)
      ActingAsATrustee.saveAndContinue()
      Then("the PurchaserAndVendorConnected page is shown")
      PurchaserAndVendorConnected.verifyPageTitle(PurchaserAndVendorConnected.pageTitle)

      When("the user confirms the purchaser and vendor are connected")
      PurchaserAndVendorConnected.radioButton(PurchaserAndVendorConnected.yes)
      PurchaserAndVendorConnected.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser address")
      PurchaserCheckYourAnswers.clickPurchaserAddressChange()
      PurchaserAddress.verifyPageTitle(PurchaserAddress.pageTitle)
      PurchaserAddress.clickAddressManually()
      PurchaserAddress.verifyPageTitle(PurchaserAddress.editPageTitle)
      PurchaserAddress.enterAddressManually("123", "TEST", "ZZ11 1ZZ")
      PurchaserAddress.verifyPageTitle(PurchaserAddress.confirmPageTitle)
      PurchaserAddress.clickConfirmAddress()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser phone number")
      PurchaserCheckYourAnswers.clickPurchaserPhoneNumberChange()
      EnterPurchaserPhoneNumber.verifyPageTitle(EnterPurchaserPhoneNumber.pageTitle)
      EnterPurchaserPhoneNumber.input(
        By.id(EnterPurchaserPhoneNumber.phoneNumberInputField),
        EnterPurchaserPhoneNumber.phoneNumberValue2
      )
      EnterPurchaserPhoneNumber.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the form of id details")
      PurchaserCheckYourAnswers.clickIndivFormOfIDChange()
      FormOfIDIndividual.verifyPageTitle(FormOfIDIndividual.pageTitle)
      FormOfIDIndividual.input(
        By.id(FormOfIDIndividual.purchaserIdNumberOrReference),
        FormOfIDIndividual.idNumberOrReferenceInput2
      )
      FormOfIDIndividual.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)
    }

    Scenario(
      "Complete the Full Purchaser Questions journey",
      PurchaserJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("2-purchasers-company"))

      When("the user opens the purchaser questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)

      When("the user removes an existing purchaser")
      PurchaserOverview.clickRemovePurchaser()
      Then("the PurchaserRemove page is shown")
      PurchaserRemove.verifyPageTitle(PurchaserRemove.pageTitle)

      When("the user confirms the purchaser removal")
      PurchaserRemove.radioButton(PurchaserRemove.yes)
      PurchaserRemove.clickContinueButton()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)

      When("the user changes the main purchaser")
      PurchaserOverview.clickMainPurchaserChange()
      Then("the ChangePurchaser page is shown")
      ChangePurchaser.verifyPageTitle(ChangePurchaser.pageTitle)

      When("the user selects the second purchaser as the main purchaser")
      ChangePurchaser.radioButton(ChangePurchaser.secondPurchaser)
      ChangePurchaser.saveAndContinue()
      Then("the ConfirmChangingPurchaser page is shown")
      ConfirmChangingPurchaser.verifyPageTitle(ConfirmChangingPurchaser.pageTitle)

      When("the user confirms the purchaser change")
      ConfirmChangingPurchaser.radioButton(ConfirmChangingPurchaser.yes)
      ConfirmChangingPurchaser.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)

      When("the user adds a new purchaser")
      PurchaserOverview.radioButton(PurchaserOverview.yes)
      PurchaserOverview.clickContinueButton()
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.clickContinueButton()
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

      When("the user edits the vendor agent details")
      PurchaserOverview.clickPurchaserChange()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)
    }

    Scenario(
      "Complete the Purchaser Questions journey as a Company and changing all forms of id",
      PurchaserJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("no-purchaser"))

      When("the user clicks on the 'Purchaser Questions' link")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.clickContinueButton()
      Then("the PurchaserWhoIsMakingThePurchase page is shown")
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)

      When("the user chooses a company as the purchaser")
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.company)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      Then("the PurchaserName page is shown")
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)

      When("the user provides the company name")
      PurchaserName.input(
        By.id(PurchaserName.companyId),
        PurchaserName.companyName2
      )
      PurchaserName.saveAndContinue()
      Then("the PurchaserConfirmAddress page is shown")
      PurchaserConfirmAddress.verifyPageTitle(PurchaserConfirmAddress.pageTitle)

      When("the user confirms the purchaser address")
      PurchaserConfirmAddress.radioButton(PurchaserConfirmAddress.yes)
      PurchaserConfirmAddress.saveAndContinue()
      Then("the DoesPurchaserHavePhoneNumber page is shown")
      DoesPurchaserHavePhoneNumber.verifyPageTitle(DoesPurchaserHavePhoneNumber.pageTitle)

      When("the user confirms to not add a phone number")
      DoesPurchaserHavePhoneNumber.radioButton(DoesPurchaserHavePhoneNumber.no)
      DoesPurchaserHavePhoneNumber.saveAndContinue()
      Then("the ConfirmPurchaserIdentity page is shown")
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitle)

      When("the user chooses vat registration number to confirm the purchaser identity")
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.vatRegistrationNumber)
      ConfirmPurchaserIdentity.saveAndContinue()
      Then("the VATRegistrationNumber page is shown")
      VATRegistrationNumber.verifyPageTitle(VATRegistrationNumber.pageTitle)

      When("the user provides the vat registration number")
      VATRegistrationNumber.input(
        By.id(VATRegistrationNumber.vat),
        VATRegistrationNumber.VATNumber
      )
      VATRegistrationNumber.saveAndContinue()
      Then("the DoYouKnowTypeOfCompany page is shown")
      DoYouKnowTypeOfCompany.verifyPageTitle(DoYouKnowTypeOfCompany.pageTitle)

      When("the user confirms they dont know what type of company the purchaser is")
      DoYouKnowTypeOfCompany.radioButton(DoYouKnowTypeOfCompany.no)
      DoYouKnowTypeOfCompany.saveAndContinue()
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

      When("the user updates the purchaser form of id to partnership unique taxpayer reference")
      PurchaserCheckYourAnswers.clickFormOfIDChange()
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitle)
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.partnershipUTR)
      ConfirmPurchaserIdentity.saveAndContinue()
      Then("the PartnershipUTR page is shown")
      PartnershipUTR.verifyPageTitle(PartnershipUTR.pageTitle)

      When("the user provides the partnership unique taxpayer reference")
      PartnershipUTR.input(
        By.id(PartnershipUTR.purchaserUTRReference),
        PartnershipUTR.purchaserUTRInput
      )
      PartnershipUTR.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser form of id to corporation tax unique taxpayer reference")
      PurchaserCheckYourAnswers.clickFormOfIDChange()
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitle)
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.corporationTaxUTR)
      ConfirmPurchaserIdentity.saveAndContinue()
      Then("the CorporationTaxUTR page is shown")
      CorporationTaxUTR.verifyPageTitle(CorporationTaxUTR.pageTitle)

      When("the user provides the corporation tax unique taxpayer reference")
      CorporationTaxUTR.input(
        By.id(CorporationTaxUTR.corporationTaxUTR),
        CorporationTaxUTR.corporationTaxUTRInput
      )
      CorporationTaxUTR.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser form of id to another form of id")
      PurchaserCheckYourAnswers.clickFormOfIDChange()
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitle)
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.anotherFormOfID)
      ConfirmPurchaserIdentity.saveAndContinue()
      FormOfIDCompany.verifyPageTitle(FormOfIDCompany.pageTitle)
      FormOfIDCompany.input(
        By.id(FormOfIDCompany.idNumberOrReference),
        FormOfIDCompany.idNumberOrReferenceInput
      )
      FormOfIDCompany.input(
        By.id(FormOfIDCompany.countryOfIssue),
        FormOfIDCompany.countryOfIssueInput
      )
      FormOfIDCompany.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates their answer to add a phone number")
      PurchaserCheckYourAnswers.clickAddPhoneNumberChange()
      Then("the DoesPurchaserHavePhoneNumber page is shown")
      DoesPurchaserHavePhoneNumber.verifyPageTitle(DoesPurchaserHavePhoneNumber.pageTitle)

      When("the user confirms to add a phone number")
      DoesPurchaserHavePhoneNumber.radioButton(DoesPurchaserHavePhoneNumber.yes)
      DoesPurchaserHavePhoneNumber.saveAndContinue()
      Then("the EnterPurchaserPhoneNumber page is shown")
      EnterPurchaserPhoneNumber.verifyPageTitle(EnterPurchaserPhoneNumber.pageTitle)

      When("the user provides a phone number")
      EnterPurchaserPhoneNumber.input(
        By.id(EnterPurchaserPhoneNumber.phoneNumberInputField),
        EnterPurchaserPhoneNumber.phoneNumberValue
      )
      EnterPurchaserPhoneNumber.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates their answer to do you know the type of company to yes")
      PurchaserCheckYourAnswers.clickDoYouKnowCompanyNameChange()
      Then("the DoYouKnowTypeOfCompany page is shown")
      DoYouKnowTypeOfCompany.verifyPageTitle(DoYouKnowTypeOfCompany.pageTitle)

      When("the user provides the type of company")
      DoYouKnowTypeOfCompany.radioButton(DoYouKnowTypeOfCompany.yes)
      DoYouKnowTypeOfCompany.saveAndContinue()
      Then("the TypeOfCompany page is shown")
      TypeOfCompany.verifyPageTitle(TypeOfCompany.pageTitle)

      When("the user provides the type of company details")
      TypeOfCompany.checkbox(
        TypeOfCompany.Unincorporated_sole_trader_other_than_builder,
        true
      )
      TypeOfCompany.checkbox(TypeOfCompany.Superannuation_or_pension_fund, true)
      TypeOfCompany.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser is not acting as a trustee")
      PurchaserCheckYourAnswers.clickIsCompanyActingAsTrusteeChange()
      ActingAsATrustee.verifyPageTitle(ActingAsATrustee.pageTitle)
      ActingAsATrustee.radioButton(ActingAsATrustee.no)
      ActingAsATrustee.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user updates the purchaser and vendor are not connected")
      PurchaserCheckYourAnswers.clickAreCompanyAndVendorConnectedChange()
      PurchaserAndVendorConnected.verifyPageTitle(PurchaserAndVendorConnected.pageTitle)
      PurchaserAndVendorConnected.radioButton(PurchaserAndVendorConnected.no)
      PurchaserAndVendorConnected.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the PurchaserOverview page is shown")
      PurchaserOverview.verifyPageTitle(PurchaserOverview.pageTitle)
      When("the user adds a new purchaser")
      PurchaserOverview.clickAddPurchaser()
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)
    }

    Scenario(
      "Complete the Purchaser Questions journey as a Company and changed to an Individual",
      PurchaserJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("incomplete-purchaser"))

      When("the user opens the purchaser questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-purchaser")
      Then("the PurchaserBeforeYouStart page is shown")
      PurchaserBeforeYouStart.verifyPageTitle(PurchaserBeforeYouStart.pageTitle)

      When("the user starts the purchaser questions")
      PurchaserBeforeYouStart.clickContinueButton()
      Then("the ConfirmPurchaserName page is shown")
      ConfirmPurchaserName.verifyPageTitle(ConfirmPurchaserName.pageTitle)

      When("the user confirms the purchaser name")
      ConfirmPurchaserName.radioButton(ConfirmPurchaserName.yes)
      ConfirmPurchaserName.clickContinueButton()
      Then("the ConfirmPurchaserAddress page is shown")
      PurchaserConfirmAddress.verifyPageTitle(PurchaserConfirmAddress.pageTitle)

      When("the user confirms the purchaser address")
      PurchaserConfirmAddress.radioButton(PurchaserConfirmAddress.yes)
      PurchaserConfirmAddress.saveAndContinue()
      Then("the DoesPurchaserHavePhoneNumber page is shown")
      DoesPurchaserHavePhoneNumber.verifyPageTitle(DoesPurchaserHavePhoneNumber.pageTitle)

      When("the user confirms to not add a phone number")
      DoesPurchaserHavePhoneNumber.radioButton(DoesPurchaserHavePhoneNumber.no)
      DoesPurchaserHavePhoneNumber.saveAndContinue()
      Then("the ConfirmPurchaserIdentity page is shown")
      ConfirmPurchaserIdentity.verifyPageTitle(ConfirmPurchaserIdentity.pageTitleStub)

      When("the user chooses vat registration number to confirm the purchaser identity")
      ConfirmPurchaserIdentity.radioButton(ConfirmPurchaserIdentity.vatRegistrationNumber)
      ConfirmPurchaserIdentity.saveAndContinue()
      Then("the VATRegistrationNumber page is shown")
      VATRegistrationNumber.verifyPageTitle(VATRegistrationNumber.pageTitle)

      When("the user provides the vat registration number")
      VATRegistrationNumber.input(
        By.id(VATRegistrationNumber.vat),
        VATRegistrationNumber.VATNumber
      )
      VATRegistrationNumber.saveAndContinue()
      Then("the DoYouKnowTypeOfCompany page is shown")
      DoYouKnowTypeOfCompany.verifyPageTitle(DoYouKnowTypeOfCompany.pageTitle)

      When("the user confirms they know what type of company the purchaser is")
      DoYouKnowTypeOfCompany.radioButton(DoYouKnowTypeOfCompany.yes)
      DoYouKnowTypeOfCompany.saveAndContinue()
      Then("the TypeOfCompany page is shown")
      TypeOfCompany.verifyPageTitle(TypeOfCompany.pageTitle)

      When("the user confirms the purchaser is two types of companies")
      TypeOfCompany.checkbox(TypeOfCompany.Bank, true)
      TypeOfCompany.checkbox(TypeOfCompany.Building_Society, true)
      TypeOfCompany.saveAndContinue()
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

      When("the user updates the purchaser type to individual")
      PurchaserCheckYourAnswers.clickPurchaserTypeChange()
      PurchaserWhoIsMakingThePurchase.verifyPageTitle(PurchaserWhoIsMakingThePurchase.pageTitle)
      PurchaserWhoIsMakingThePurchase.radioButton(PurchaserWhoIsMakingThePurchase.individual)
      PurchaserWhoIsMakingThePurchase.saveAndContinue()
      PurchaserName.verifyPageTitle(PurchaserName.pageTitle)
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
      AddPurchaserNationalInsuranceNumber.verifyPageTitle(AddPurchaserNationalInsuranceNumber.pageTitle)
      AddPurchaserNationalInsuranceNumber.radioButton(AddPurchaserNationalInsuranceNumber.yes)
      AddPurchaserNationalInsuranceNumber.saveAndContinue()
      EnterPurchaserNationalInsuranceNumber.verifyPageTitle(EnterPurchaserNationalInsuranceNumber.pageTitle)
      EnterPurchaserNationalInsuranceNumber.input(
        By.id(EnterPurchaserNationalInsuranceNumber.Nino),
        EnterPurchaserNationalInsuranceNumber.NinoValue2
      )
      EnterPurchaserNationalInsuranceNumber.saveAndContinue()
      DateOfBirth.verifyPageTitle(DateOfBirth.pageTitle)
      DateOfBirth.enterDateOfBirth()
      DateOfBirth.saveAndContinue()
      Then("the PurchaserCheckYourAnswers page is shown")
      PurchaserCheckYourAnswers.verifyPageTitle(PurchaserCheckYourAnswers.pageTitle)

      When("the user submits the purchaser questions")
      PurchaserCheckYourAnswers.saveAndContinue()
      Then("the Purchaser Incomplete page is shown")
      PurchaserIncomplete.verifyPageTitle(PurchaserIncomplete.pageTitle)
    }
  }
}
