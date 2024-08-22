/*
 * Project Gaai: one app to control the Nexxtender Home charger.
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

import android.content.Context
import be.cuypers_ghys.gaai.ble.DummyGaaiBleService
import be.cuypers_ghys.gaai.ble.GaaiBleService


/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val devicesRepository: DevicesRepository
}

/**
 *  [AppContainer] implementation that provides instance of [OfflineDevicesRepository]
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer(context : Context): AppContainer {
    private val gaaiBleService : GaaiBleService = DummyGaaiBleService()

    /**
     * Implementation for [DevicesRepository]
     */
    override val devicesRepository : DevicesRepository by lazy {
        OfflineDevicesRepository(GaaiDatabase.getDatabase(context).deviceDao())
    }
}