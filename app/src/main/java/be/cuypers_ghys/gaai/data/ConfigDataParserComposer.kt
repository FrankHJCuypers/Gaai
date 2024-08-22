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

import be.cuypers_ghys.gaai.util.MODBUS
import be.cuypers_ghys.gaai.util.fromInt16LE
import be.cuypers_ghys.gaai.util.fromUint16LE
import be.cuypers_ghys.gaai.util.toUint16LE
import com.google.iot.cbor.CborInteger
import com.google.iot.cbor.CborMap
import com.google.iot.cbor.CborObject
import no.nordicsemi.android.kotlin.ble.profile.common.CRC16

/** Enumerates all CONFIG_CBOR keys. s*/
@Suppress("SpellCheckingInspection")
enum class CborKey(val keyNum: Int) {
    ChargeMode(1),
    ModbusSlaveAddress(2),
    CycleRate(3),
    IMax(4),
    IEvseMax(5),
    IEvseMin(6),
    ILevel1(7),
    SolarMode(8),
    PhaseSeq(9),
    ChargingPhases(10),
    BlePin(11),
    TouWeekStart(12),
    TouWeekStop(13),
    TouWeekendStart(14),
    TouWeekendStop(15),
    Timezone(16),
    RelayOffPeriod(17),
    ExternalRegulation(18),
    ICapacity(19)
}
/**
 * Parses and composes Config data.
 *
 * @author Frank HJ Cuypers
 */
object ConfigDataParserComposer {
    /**
     * Parses a byte array with the contents of the Config Get operation in Config_1_0 format into an
     * [ConfigData].
     * @param configGetData Byte array with the value read from the Config Get operation.
     * @return A [ConfigData] holding the parsed result.
     *      Null if *configGetData* is not 13 long or the CRC16 is not correct.
     */
    @Suppress("FunctionName")
    fun parseConfig_1_0(configGetData: ByteArray): ConfigData? {
        if (configGetData.size !=  13)
        {
            return null
        }

        return parse(configGetData, ConfigVersion.CONFIG_1_0)
    }

    /**
     * Parses a byte array with the contents of the Config Get operation in Config_1_1 format into an
     * [ConfigData].
     * @param configGetData Byte array with the value read from the Config Get operation.
     * @return A [ConfigData] holding the parsed result.
     *      Null if *configGetData* is not 15 long or the CRC16 is not correct.
     */
    @Suppress("FunctionName")
    fun parseConfig_1_1(configGetData: ByteArray): ConfigData? {
        if (configGetData.size !=  15)
        {
            return null
        }

        return parse(configGetData, ConfigVersion.CONFIG_1_1)
    }

    /**
     * Parses a byte array with the contents of the Config Get operation into an
     * [ConfigData].
     * @param configGetData Byte array with the value read from the Config Get operation.
     * @param configVersion The configuration version.
     * @return A [ConfigData] holding the parsed result.
     *      Null if *configGetData* is not 13 or 15 bytes long or the CRC16 is not correct.
     */
    fun parse(configGetData: ByteArray, configVersion : ConfigVersion): ConfigData? {
        if ((configGetData.size !=  13) && (configGetData.size !=  15))
        {
            return null
        }

        val crc =  configGetData.fromUint16LE(configGetData.size-2)
        val computedCrc = CRC16.MODBUS(configGetData,0, configGetData.size-2).toUShort()
        if ( computedCrc != crc )
        {
            return null
        }

        var offset = 0
        val maxGrid = configGetData[offset++].toUByte()

        var maxDevice = 0.toUByte()
        if ( configVersion == ConfigVersion.CONFIG_1_1 )
        {
            maxDevice = configGetData[offset++].toUByte()
        }

        val rawMode = configGetData[offset++]
        val mode = getMode(rawMode.toInt())
        val safe=  configGetData[offset++].toUByte()

        var networkType = NetWorkType.UNKNOWN
        if ( configVersion == ConfigVersion.CONFIG_1_1 )
        {
            val rawNetworkType = configGetData[offset++]
            networkType = getNetWorkType(rawNetworkType.toInt())
        }
        val touWeekStart = configGetData.fromInt16LE(offset)
        offset += 2
        val touWeekEnd = configGetData.fromInt16LE(offset)
        offset += 2
        val touWeekendStart = configGetData.fromInt16LE(offset)
        offset += 2
        val touWeekendEnd = configGetData.fromInt16LE(offset)
        return  ConfigData(
            maxGrid,
            maxDevice,
            mode,
            safe,
            networkType ,
            touWeekStart,
            touWeekEnd,
            touWeekendStart,
            touWeekendEnd,
            0u,
            0u,
            configVersion
        )
    }

    private fun getNetWorkType(rawNetworkType: Int) = when (rawNetworkType) {
        0 -> NetWorkType.MONO_TRIN
        2 -> NetWorkType.TRI
        else -> NetWorkType.UNKNOWN
    }

    private fun getRawNetWorkType(networkType: NetWorkType) = when (networkType) {
        NetWorkType.MONO_TRIN -> 0
        NetWorkType.TRI -> 2
        NetWorkType.UNKNOWN -> 0x69
    }

    private fun getMode(rawMode: Int) = when (rawMode) {
        0 -> Mode.ECO_PRIVATE
        1 -> Mode.MAX_PRIVATE
        4 -> Mode.ECO_OPEN
        5 -> Mode.MAX_OPEN
        else -> Mode.UNKNOWN
    }

    private fun getRawMode(mode: Mode) = when (mode) {
        Mode.ECO_PRIVATE -> 0
        Mode.MAX_PRIVATE -> 1
        Mode.ECO_OPEN -> 4
        Mode.MAX_OPEN -> 5
        else -> 0x69
    }

    /**
     * Parses a byte array with the contents of the Config Get operation in Config_CBOR format into an
     * [ConfigData].
     * @param configGetData Byte array with the value read from the Config Get operation.
     * @return A [ConfigData] holding the parsed result.
     *      Null if the CRC16 is not correct, configData is not correctly CBOR coded.
     */
    @Suppress("FunctionName")
    fun parseConfig_CBOR(configGetData: ByteArray): ConfigData? {
        val crc =  configGetData.fromUint16LE(configGetData.size-2)
        val computedCrc = CRC16.MODBUS(configGetData,0, configGetData.size-2).toUShort()
        if ( computedCrc != crc ){
            return null
        }

        val cborMap = CborMap.createFromCborByteArray(configGetData, 0, configGetData.size-2 ) ?: return null
        val subMap0 = cborMap.get(CborInteger.create(0)) ?: return null
        val subMap1 = cborMap.get(CborInteger.create(1)) ?: return null
        if ( subMap1 !is CborMap) return null

        var maxGrid: UByte = 0u
        var maxDevice: UByte = 0u
        var mode: Mode = Mode.UNKNOWN
        var safe: UByte = 0u
        var networkType: NetWorkType = NetWorkType.UNKNOWN
        var touWeekStart: Short = 0
        var touWeekEnd: Short = 0
        var touWeekendStart: Short = 0
        var touWeekendEnd: Short = 0
        var minDevice: UByte = 0u
        var iCapacity: UByte = 0u
        val configVersion = ConfigVersion.CONFIG_CBOR

        subMap1.mapValue().forEach { entry ->
            val intValue = entry.value as? CborInteger
            val intKey = entry.key as? CborInteger
            if ( (intValue!= null ) && (intKey != null)) {
                val rawInt = intValue.intValueExact()
                val rawKey = intKey.intValueExact()
                when( rawKey ){
                    CborKey.ChargeMode.keyNum -> mode = getMode(rawInt)
                    CborKey.IMax.keyNum -> maxGrid = rawInt.toUByte()
                    CborKey.IEvseMax.keyNum -> maxDevice = rawInt.toUByte()
                    CborKey.IEvseMin.keyNum -> minDevice = rawInt.toUByte()
                    CborKey.ILevel1.keyNum -> safe = rawInt.toUByte()
                    CborKey.PhaseSeq.keyNum -> networkType = getNetWorkType(rawInt )
                    CborKey.TouWeekStart.keyNum -> touWeekStart = rawInt.toShort()
                    CborKey.TouWeekStop.keyNum -> touWeekEnd = rawInt.toShort()
                    CborKey.TouWeekendStart.keyNum -> touWeekendStart = rawInt.toShort()
                    CborKey.TouWeekendStop.keyNum -> touWeekendEnd = rawInt.toShort()
                    CborKey.ICapacity.keyNum -> iCapacity = rawInt.toUByte()
                }
            }
        }
        return  ConfigData(
            maxGrid,
            maxDevice,
            mode,
            safe,
            networkType ,
            touWeekStart,
            touWeekEnd,
            touWeekendStart,
            touWeekendEnd,
            minDevice,
            iCapacity,
            configVersion
        )
    }

    /**
     * Composes a byte array with the contents of the Config Set operation from an
     * [ConfigData].
     * @param configGetData The data to compose.
     * @return Byte array with the compose configuration.
     */
    fun compose(configGetData: ConfigData): ByteArray {
        if ( configGetData.configVersion == ConfigVersion.CONFIG_CBOR ) {
            return composeConfig_CBOR(configGetData)
        }

        val dataLength = if (configGetData.configVersion == ConfigVersion.CONFIG_1_1) 15 else 13
        val data = ByteArray(dataLength)
        var offset = 0

        data[offset++]= configGetData.maxGrid.toByte()

        var maxDevice = 0.toUByte()
        if ( configGetData.configVersion == ConfigVersion.CONFIG_1_1 )
        {
            data[offset++]= configGetData.maxDevice.toByte()
        }

        data[offset++]= getRawMode(configGetData.mode).toByte()
        data[offset++]= configGetData.safe.toByte()

        if ( configGetData.configVersion == ConfigVersion.CONFIG_1_1 )
        {
            data[offset++] = getRawNetWorkType(configGetData.networkType).toByte()
        }
        data.toUint16LE(offset,configGetData.touWeekStart.toUInt())
        offset += 2
        data.toUint16LE(offset,configGetData.touWeekEnd.toUInt())
        offset += 2
        data.toUint16LE(offset,configGetData.touWeekendStart.toUInt())
        offset += 2
        data.toUint16LE(offset,configGetData.touWeekendEnd.toUInt())
        offset += 2

        val computedCrc = CRC16.MODBUS(data,0, dataLength-2).toUInt()
        data.toUint16LE(offset,computedCrc)
        return data
    }

    /**
     * Composes a byte array with the contents of the Config Set operation from an
     * [ConfigData] in the []ConfigVersion.CONFIG_CBOR] configuration.
     * @param configGetData The data to compose.
     * @return Byte array with the compose configuration.
     */
    @Suppress("FunctionName")
    fun composeConfig_CBOR(configGetData: ConfigData): ByteArray {
        assert(configGetData.configVersion == ConfigVersion.CONFIG_CBOR)

        val dataMap = HashMap<CborObject,CborObject>()
        dataMap[CborInteger.create(CborKey.ChargeMode.keyNum.toLong())] = CborInteger.create(getRawMode(configGetData.mode).toLong())
        dataMap[CborInteger.create(CborKey.IMax.keyNum.toLong())] = CborInteger.create((configGetData.maxGrid.toLong()))
        dataMap[CborInteger.create(CborKey.IEvseMax.keyNum.toLong())] = CborInteger.create((configGetData.maxDevice.toLong()))
        dataMap[CborInteger.create(CborKey.IEvseMin.keyNum.toLong())] = CborInteger.create((configGetData.minDevice.toLong()))
        dataMap[CborInteger.create(CborKey.ILevel1.keyNum.toLong())] = CborInteger.create((configGetData.safe.toLong()))
        dataMap[CborInteger.create(CborKey.PhaseSeq.keyNum.toLong())] = CborInteger.create(getRawNetWorkType(configGetData.networkType).toLong())
        dataMap[CborInteger.create(CborKey.TouWeekStart.keyNum.toLong())] = CborInteger.create((configGetData.touWeekStart.toLong()))
        dataMap[CborInteger.create(CborKey.TouWeekStop.keyNum.toLong())] = CborInteger.create((configGetData.touWeekEnd.toLong()))
        dataMap[CborInteger.create(CborKey.TouWeekendStart.keyNum.toLong())] = CborInteger.create((configGetData.touWeekendStart.toLong()))
        dataMap[CborInteger.create(CborKey.TouWeekendStop.keyNum.toLong())] = CborInteger.create((configGetData.touWeekendEnd.toLong()))
        dataMap[CborInteger.create(CborKey.ICapacity.keyNum.toLong())] = CborInteger.create((configGetData.iCapacity.toLong()))

        val subMap0 = HashMap<CborObject,CborObject>()
        subMap0[CborInteger.create(1L)] = CborInteger.create(1L)
        subMap0[CborInteger.create(2L)] = CborInteger.create(1L)

        val rootMap = HashMap<CborObject,CborObject>()
        rootMap[CborInteger.create(0L)] = CborMap.create(subMap0)
        rootMap[CborInteger.create(1L)] = CborMap.create(dataMap)

        // Note: toCborByteArray() does not output map pairs sorted by integer key value!
        val cborData = CborMap.create(rootMap).toCborByteArray()

        val computedCrc = CRC16.MODBUS(cborData, 0, cborData.size).toUInt()
        val computedCrcArray = ByteArray(2)
        computedCrcArray.toUint16LE(0,computedCrc)
        val cborDatWithCrc = cborData + computedCrcArray

        return cborDatWithCrc
    }

}