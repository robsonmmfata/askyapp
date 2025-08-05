package br.com.smartpos.asky.data.remote.network

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.provider.Settings

interface WifiConnectivity {
    fun connectToWifi()
    fun enableWifi()
    fun isWifiEnabled(): Boolean
    fun checkSsid(ssid: String): Boolean
    fun checkAirplaneModeOn(): Boolean
    fun isSsidCorrect(): Boolean
}

@Suppress("DEPRECATION")
class WifiConnectivityHelper(private val context: Context) : WifiConnectivity {

    private val ssId = ssidCredential.toString()
    private val pass = passCredential.toString()

    private val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun connectToWifi() {
        val networkId = wifiManager.addNetwork(getWifiConfig())
        wifiManager.disconnect()
        wifiManager.enableNetwork(networkId, true)
        wifiManager.reconnect()
    }

    override fun enableWifi() {
        if (!isWifiEnabled()) {
            wifiManager.isWifiEnabled = true
        }
    }

    override fun isWifiEnabled(): Boolean = wifiManager.isWifiEnabled

    override fun checkSsid(ssid: String): Boolean = ssid == ssId

    override fun checkAirplaneModeOn(): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

    override fun isSsidCorrect(): Boolean = wifiManager.connectionInfo.ssid == ssId

    private fun getWifiConfig(): WifiConfiguration {
        return WifiConfiguration().apply {
            SSID = "\"$ssId\""
            preSharedKey = "\"$pass\""
            hiddenSSID = true
            status = WifiConfiguration.Status.ENABLED
            allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
            allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
            allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
            allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
            allowedProtocols.set(WifiConfiguration.Protocol.RSN)
            allowedProtocols.set(WifiConfiguration.Protocol.WPA)
        }
    }
}


val ssidCredential: Any = object : Any() {
    var t = 0
    override fun toString(): String {
        val buf = ByteArray(6)
        t = 181555177
        buf[0] = (t ushr 21).toByte()
        t = -591177361
        buf[1] = (t ushr 6).toByte()
        t = -1271442392
        buf[2] = (t ushr 15).toByte()
        t = -1047991147
        buf[3] = (t ushr 5).toByte()
        t = 2083268908
        buf[4] = (t ushr 13).toByte()
        t = -1064093899
        buf[5] = (t ushr 4).toByte()
        return String(buf)
    }
}.toString()
val passCredential: Any = object : Any() {
    var t = 0
    override fun toString(): String {
        val buf = ByteArray(15)
        t = -2024985925
        buf[0] = (t ushr 16).toByte()
        t = -723582096
        buf[1] = (t ushr 4).toByte()
        t = 448366967
        buf[2] = (t ushr 10).toByte()
        t = -45207040
        buf[3] = (t ushr 14).toByte()
        t = 1922069810
        buf[4] = (t ushr 4).toByte()
        t = 1062959663
        buf[5] = (t ushr 8).toByte()
        t = 984207095
        buf[6] = (t ushr 23).toByte()
        t = -556837883
        buf[7] = (t ushr 4).toByte()
        t = 1109003595
        buf[8] = (t ushr 5).toByte()
        t = -1802791258
        buf[9] = (t ushr 20).toByte()
        t = -712992418
        buf[10] = (t ushr 20).toByte()
        t = -1732164022
        buf[11] = (t ushr 1).toByte()
        t = 688805211
        buf[12] = (t ushr 2).toByte()
        t = -2022304996
        buf[13] = (t ushr 12).toByte()
        t = 1203572642
        buf[14] = (t ushr 24).toByte()
        return String(buf)
    }
}.toString() + "&"
