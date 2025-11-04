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

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.ui.pages.PropertyAddress.click

object CYAPage extends BasePage {

  override def pageUrl: String = "preliminary-questions/check-answers"

  override def pageTitle: String =
    "Check your answers – Preliminary-questions - Stamp Taxes Online - GOV.UK"

  val purchaserTypeChange =
    "a[href ='/stamp-duty-land-tax-filing/change/preliminary-questions/who-is-making-the-purchase']"

  val purchaserNameChange = "a[href ='/stamp-duty-land-tax-filing/preliminary-questions/purchaser-name/change']"

  val propertyAddressChange = "a[href ='/stamp-duty-land-tax-filing/preliminary-questions/address?changeRoute=change']"

  val transactionTypeChange = "a[href ='/stamp-duty-land-tax-filing/change/preliminary-questions/transaction-type']"

  def clickPurchaserNameChange(): Unit = click(By.cssSelector(purchaserNameChange))

  def clickPurchaserTypeChange(): Unit = click(By.cssSelector(purchaserTypeChange))

  def clickPropetyAddressChange(): Unit = click(By.cssSelector(propertyAddressChange))

  def clickTransactionTypeChange(): Unit = click(By.cssSelector(transactionTypeChange))
}
