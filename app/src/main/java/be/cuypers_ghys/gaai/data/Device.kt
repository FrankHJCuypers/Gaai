/*
 * Project Gaai: one app to control the Nexxtender chargers.
 * Copyright © 2024, Frank HJ Cuypers
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package be.cuypers_ghys.gaai.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Possible charger type values.
 *
 * @author Frank HJ Cuypers
 */
enum class ChargerType {
  HOME, MOBILE, UNKNOWN
}

/**
 * Entity data class represents a single row in the [GaaiDatabase] with Nexxtender charger devices.
 *
 * @author Frank HJ Cuypers
 */
@Entity(
  tableName = "devices",
  indices = [Index(value = ["mac"], unique = true), Index(value = ["pn", "sn"], unique = true)]
)
data class Device(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,

  /**
   * The Product Number (PN) as printed on the bottom of the device.
   * pn+[sn]  must be unique.
   */
  val pn: String = "",

  /**
   * The Serial Number (SN) as printed on the bottom of the device.
   * [pn]+sn  must be unique.
   */
  val sn: String = "",

  /**
   * The device's MAC as found after scanning for the [serviceDataValue].
   * The MAC must be unique.
   */
  val mac: String = "",

  /**
   * The value that this device advertises in the
   * [be.cuypers_ghys.gaai.viewmodel.NexxtenderHomeSpecification.UUID_NEXXTENDER_CHARGER_SERVICE_DATA_SERVICE].
   * Supposedly also unique.
   */
  val serviceDataValue: Int = 0,

  /**
   * The charger type, based on the value that this device advertises in the
   * [be.cuypers_ghys.gaai.viewmodel.NexxtenderHomeSpecification.UUID_BLE_DEVICE_NAME_CHARACTERISTIC].
   */
  @ColumnInfo(name = "chargerType", defaultValue = "HOME")
  val type: ChargerType = ChargerType.HOME
)
