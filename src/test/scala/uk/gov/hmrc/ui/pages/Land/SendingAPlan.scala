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

import uk.gov.hmrc.ui.pages.BasePage
import org.openqa.selenium.By

object SendingAPlan extends BasePage {

  override def pageUrl: String = "about-the-land/sending-a-plan"

  override def pageTitle: String =
    "Sending a plan - About the land - Stamp Taxes Online - GOV.UK"

  val yes: String = "#value"

  val no: String = "#value-2"

  def clickDropdownText(): Unit = driver.findElement(By.cssSelector("summary.govuk-details__summary")).click()

  def dropdownText: String =
    "The plan should indicate the scale used or be endorsed as ‘Not to Scale’. It must also show the return’s reference number, a description of the land and the local authority code."
}
