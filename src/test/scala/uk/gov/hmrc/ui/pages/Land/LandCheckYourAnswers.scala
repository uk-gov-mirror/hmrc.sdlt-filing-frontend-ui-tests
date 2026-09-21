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

package uk.gov.hmrc.ui.pages.Land

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.BasePage

object LandCheckYourAnswers extends BasePage {

  override def pageUrl: String = "about-the-land/check-answers"

  override def pageTitle: String = "Check your answers - About the land - Stamp Taxes Online - GOV.UK"

  val typeOfPropertyChange =
    "a[href='/stamp-duty-land-tax-filing/about-the-land/type-of-property/change']"

  val interestTransferredCreated =
    "a[href ='/stamp-duty-land-tax-filing/about-the-land/interest-transferred-or-created/change']"

  val landAddress =
    "a[href ='/stamp-duty-land-tax-filing/about-the-land/land-address-lookup?changeRoute=change']"

  val localAuthorityCode =
    "a[href ='/stamp-duty-land-tax-filing/about-the-land/local-authority-code/change']"

  val HM_LandRegistration =
    "a[href ='/stamp-duty-land-tax-filing/about-the-land/HM-land-registration/change']"

  val doYouHaveNLPG =
    "a[href='/stamp-duty-land-tax-filing/about-the-land/add-NLPG-UPRN/change']"

  val sendingPlanByPost =
    "a[href='/stamp-duty-land-tax-filing/about-the-land/sending-a-plan/change']"

  val mineralsOrMineralRights =
    "a[href='/stamp-duty-land-tax-filing/about-the-land/mineral-rights/change']"

  val titleNumber = "a[href='/stamp-duty-land-tax-filing/about-the-land/title-number/change']"

  val enterNLPGUPRN = "a[href='/stamp-duty-land-tax-filing/about-the-land/enter-NLPG-UPRN/change']"

  val agriculturalOrDevelopmentalLand =
    "a[href='/stamp-duty-land-tax-filing/about-the-land/agricultural-or-developmental-land/change']"

  val doYouKnowAreaOfLand = "a[href='/stamp-duty-land-tax-filing/about-the-land/add-area-of-land/change']"

  val areaOfLand = "a[href='/stamp-duty-land-tax-filing/about-the-land/unit-of-measurement/change']"

  def clickTypeOfPropertyChange(): Unit = click(By.cssSelector(typeOfPropertyChange))

  def clickInterestTransferredOrCreated(): Unit =
    click(By.cssSelector(interestTransferredCreated))

  def clickLandAddress(): Unit = click(By.cssSelector(landAddress))

  def clickLocalAuthorityCode(): Unit = click(By.cssSelector(localAuthorityCode))

  def clickHMLandRegistration(): Unit = click(By.cssSelector(HM_LandRegistration))

  def clickDoYouHaveNLPGUPRN(): Unit = click(By.cssSelector(doYouHaveNLPG))

  def clickSendingPlanByPost(): Unit = click(By.cssSelector(sendingPlanByPost))

  def clickMineralsOrMineralRights(): Unit = click(By.cssSelector(mineralsOrMineralRights))

  def clickTitleNumber(): Unit = click(By.cssSelector(titleNumber))

  def clickEnterNLPGUPRN(): Unit = click(By.cssSelector(enterNLPGUPRN))

  def clickAgriculturalOrDevelopmentalLand(): Unit =
    click(By.cssSelector(agriculturalOrDevelopmentalLand))

  def clickDoYouKnowAreaOfLand(): Unit = click(By.cssSelector(doYouKnowAreaOfLand))

  def clickAreaOfLand(): Unit = click(By.cssSelector(areaOfLand))

}
