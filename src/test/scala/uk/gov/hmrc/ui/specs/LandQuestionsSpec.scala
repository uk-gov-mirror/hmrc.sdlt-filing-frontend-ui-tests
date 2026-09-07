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
import uk.gov.hmrc.ui.pages.Land.*
import uk.gov.hmrc.ui.tags.*
import uk.gov.hmrc.ui.util.Users.LoginTypes.HASDIRECT
import uk.gov.hmrc.ui.util.Users.UserTypes.Organisation
import uk.gov.hmrc.selenium.webdriver.Driver.instance

class LandQuestionsSpec
    extends AnyFeatureSpec
    with BaseSpec
    with GivenWhenThen
    with ShouldVerb
    with BeforeAndAfterAll
    with BeforeAndAfterEach
    with Browser
    with ScreenshotOnFailure {

  Feature("SDLT Filing Frontend Land Questions") {

    Scenario(
      "Complete the Land Questions with a residential property",
      LandJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("no-land"))

      When("the user opens the land questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-land")
      Then("the LandAgentBeforeYouStart page is shown")
      LandBeforeYouStart.verifyPageTitle(LandBeforeYouStart.pageTitle)

      When("the user starts the land questions")
      LandBeforeYouStart.saveAndContinue()
      Then("the TypeOfProperty page is shown")
      TypeOfProperty.verifyPageTitle(TypeOfProperty.pageTitle)

      When("the user selects residental as the property type")
      TypeOfProperty.radioButton(TypeOfProperty.residential)
      TypeOfProperty.saveAndContinue()
      Then("the InterestTransferredOrCreated page is shown")
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

      When("the user validates the link containing the list of local auth codes")
      LocalAuthorityCode.validateListOfLocalAuthCodeLink()
      LocalAuthorityCode.clickDropdownText()
      LocalAuthorityCode.validateLandAndBuildingTransactionLink()
      LocalAuthorityCode.validateOneOfSpecialCasesLink()
      LocalAuthorityCode.validateLandTransactionTaxLink()
      LocalAuthorityCode.validateOneOfSpecialCasesWalesLink()
      And("provides a local authority code")
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

      When("the user reads the sending plan by post details")
      SendingAPlan.clickDropdownText()
      SendingAPlan.verifyPageText(SendingAPlan.dropdownText, 1)
      And("confirms they will send a plan by post")
      SendingAPlan.radioButton(SendingAPlan.yes)
      SendingAPlan.saveAndContinue()
      Then("the MineralsOrMineralRights page is shown")
      MineralRights.verifyPageTitle(MineralRights.pageTitle)

      When("the user confirms there are no mineral or mineral rights reserved")
      MineralRights.radioButton(MineralRights.no)
      MineralRights.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates the interest transferred or created to other")
      LandCheckYourAnswers.clickInterestTransferredOrCreated()
      InterestTransferredOrCreated.radioButton(InterestTransferredOrCreated.OT)
      InterestTransferredOrCreated.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates the land address")
      LandCheckYourAnswers.clickLandAddress()
      LandAddress.clickAddressManually()
      LandAddress.verifyPageTitle(LandAddress.editPageTitle)
      LandAddress.enterAddressManually("1 Silver Lane", "Test Town", "ZZ11 1ZZ")
      LandAddress.verifyPageTitle(LandAddress.confirmPageTitle)
      LandAddress.clickContinueButton()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates the local authority code")
      LandCheckYourAnswers.clickLocalAuthorityCode()
      LocalAuthorityCode.input(
        By.id(LocalAuthorityCode.localAuthCode),
        LocalAuthorityCode.welshLocalAuthCodeInput
      )
      LocalAuthorityCode.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer that the land is registered with hm land registry")
      LandCheckYourAnswers.clickHMLandRegistration()
      HMLandRegistration.radioButton(HMLandRegistration.yes)
      HMLandRegistration.saveAndContinue()
      TitleNumber.input(By.id(TitleNumber.landTitleNumber), TitleNumber.landTitleNumberInput)
      TitleNumber.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer to add a nlpg uprn")
      LandCheckYourAnswers.clickDoYouHaveNLPGUPRN()
      AddNLPGUPRN.radioButton(AddNLPGUPRN.yes)
      AddNLPGUPRN.saveAndContinue()
      EnterNLPGUPRN.input(By.id(EnterNLPGUPRN.nlpg_uprn), EnterNLPGUPRN.nlpg_uprnInput)
      EnterNLPGUPRN.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user submits the land questions")
      LandCheckYourAnswers.saveAndContinue()
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)

    }

    Scenario(
      "Complete the Land Questions with a mixed property",
      LandJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("no-land"))

      When("the user opens the land questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-land")
      Then("the LandAgentBeforeYouStart page is shown")
      LandBeforeYouStart.verifyPageTitle(LandBeforeYouStart.pageTitle)

      When("the user starts the land questions")
      LandBeforeYouStart.saveAndContinue()
      Then("the TypeOfProperty page is shown")
      TypeOfProperty.verifyPageTitle(TypeOfProperty.pageTitle)

      When("the user selects mixed as the property type")
      TypeOfProperty.radioButton(TypeOfProperty.mixed)
      TypeOfProperty.saveAndContinue()
      Then("the InterestTransferredCreated page is shown")
      InterestTransferredOrCreated.verifyPageTitle(InterestTransferredOrCreated.pageTitle)

      When("the user selects leasehold subject to describe the transaction")
      InterestTransferredOrCreated.radioButton(InterestTransferredOrCreated.LT)
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

      When("the user confirms the land is registered with hm land registry")
      HMLandRegistration.radioButton(HMLandRegistration.yes)
      HMLandRegistration.saveAndContinue()
      Then("the TitleNumber page is shown")
      TitleNumber.verifyPageTitle(TitleNumber.pageTitle)

      When("the user provides the title number")
      TitleNumber.input(By.id(TitleNumber.landTitleNumber), TitleNumber.landTitleNumberInput)
      TitleNumber.saveAndContinue()
      Then("the AddNLPGUPRN page is shown")
      AddNLPGUPRN.verifyPageTitle(AddNLPGUPRN.pageTitle)

      When("the user confirms to add an NLPG UPRN")
      AddNLPGUPRN.radioButton(AddNLPGUPRN.yes)
      AddNLPGUPRN.saveAndContinue()
      Then("the EnterNLPGUPRN page is shown")
      EnterNLPGUPRN.verifyPageTitle(EnterNLPGUPRN.pageTitle)

      When("the user provides an NLPG UPRN")
      EnterNLPGUPRN.input(By.id(EnterNLPGUPRN.nlpg_uprn), EnterNLPGUPRN.nlpg_uprnInput)
      EnterNLPGUPRN.saveAndContinue()
      Then("the SendingPlanByPost page is shown")
      SendingAPlan.verifyPageTitle(SendingAPlan.pageTitle)

      When("the user confirms they will not send a plan by post")
      SendingAPlan.radioButton(SendingAPlan.no)
      SendingAPlan.saveAndContinue()
      Then("the MineralsOrMineralRights page is shown")
      MineralRights.verifyPageTitle(MineralRights.pageTitle)

      When("the user confirms there are mineral or mineral rights reserved")
      MineralRights.radioButton(MineralRights.yes)
      MineralRights.saveAndContinue()
      Then("the AgriculturalOrDevelopmentalLand page is shown")
      AgriculturalOrDevelopmentalLand.verifyPageTitle(AgriculturalOrDevelopmentalLand.pageTitle)

      When("the user confirms the transaction involves agricultural or development land")
      AgriculturalOrDevelopmentalLand.radioButton(AgriculturalOrDevelopmentalLand.yes)
      AgriculturalOrDevelopmentalLand.saveAndContinue()
      Then("the AddAreaOfTheLand page is shown")
      AddAreaOfTheLand.verifyPageTitle(AddAreaOfTheLand.pageTitle)

      When("the user confirms to know the area of the land")
      AddAreaOfTheLand.radioButton(AddAreaOfTheLand.yes)
      AddAreaOfTheLand.saveAndContinue()
      Then("the UnitOfMeasurement page is shown")
      UnitOfMeasurement.verifyPageTitle(UnitOfMeasurement.pageTitle)

      When("the user selects square metres as the unit of measurement")
      UnitOfMeasurement.radioButton(UnitOfMeasurement.squareMetres)
      UnitOfMeasurement.saveAndContinue()
      Then("the EnterAreaOfTheLand page is shown")
      EnterAreaOfTheLand.verifyPageTitle(EnterAreaOfTheLand.pageTitle)
      EnterAreaOfTheLand.verifyElementIsDisplayed(EnterAreaOfTheLand.headerSquareMetre)

      When("the user returns to the previous page and updates the unit of measurement to hectares")
      EnterAreaOfTheLand.clickBackLink()
      UnitOfMeasurement.radioButton(UnitOfMeasurement.hectares)
      UnitOfMeasurement.saveAndContinue()
      Then("the EnterAreaOfTheLand page is shown")
      EnterAreaOfTheLand.verifyPageTitle(EnterAreaOfTheLand.pageTitle)
      EnterAreaOfTheLand.verifyElementIsDisplayed(EnterAreaOfTheLand.headerHectares)

      When("the user enters the area of the land in hectares")
      EnterAreaOfTheLand.input(By.id(EnterAreaOfTheLand.area), EnterAreaOfTheLand.areaHectares)
      EnterAreaOfTheLand.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates the type of property to non-residential")
      LandCheckYourAnswers.clickTypeOfPropertyChange()
      TypeOfProperty.radioButton(TypeOfProperty.non_residential)
      TypeOfProperty.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer to not provide a nlpg uprn")
      LandCheckYourAnswers.clickDoYouHaveNLPGUPRN()
      AddNLPGUPRN.radioButton(AddNLPGUPRN.no)
      AddNLPGUPRN.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer to send a plan by post")
      LandCheckYourAnswers.clickSendingPlanByPost()
      SendingAPlan.radioButton(SendingAPlan.yes)
      SendingAPlan.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer to confirm there are no mineral or mineral rights reserved")
      LandCheckYourAnswers.clickMineralsOrMineralRights()
      MineralRights.radioButton(MineralRights.no)
      MineralRights.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user updates their answer that the transaction does not involve agricultural or development land")
      LandCheckYourAnswers.clickAgriculturalOrDevelopmentalLand()
      AgriculturalOrDevelopmentalLand.radioButton(AgriculturalOrDevelopmentalLand.no)
      AgriculturalOrDevelopmentalLand.saveAndContinue()
      Then("the LandCheckYourAnswers page is shown")
      LandCheckYourAnswers.verifyPageTitle(LandCheckYourAnswers.pageTitle)

      When("the user submits the land questions")
      LandCheckYourAnswers.saveAndContinue()
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)
    }

    Scenario(
      "Validate remove land functionality in Land Journey",
      LandJourney
    ) {

      Given("the user is logged in through the AuthWizard page")
      AuthWizard.login(HASDIRECT, Organisation, returnId = Some("all-sections-complete-company"))

      When("the user opens the land questions")
      ReturnTaskList.clickLinkById("task-list-link-about-the-land")
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)

      When("the user removes an existing land")
      LandOverview.clickLandRemove()
      Then("the LandRemove page is shown")
      LandRemove.verifyPageTitle(LandRemove.pageTitle)

      When("the user confirms the land removal")
      LandRemove.radioButton(LandRemove.yes)
      LandRemove.saveAndContinue()
      Then("the LandOverview page is shown")
      LandOverview.verifyPageTitle(LandOverview.pageTitle)
      LandOverview.verifyElementIsDisplayed(LandOverview.removeBanner)
    }
  }
}
