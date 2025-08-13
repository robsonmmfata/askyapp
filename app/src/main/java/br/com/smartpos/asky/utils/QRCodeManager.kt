package br.com.smartpos.asky.utils

import android.content.Context
import android.provider.Settings
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.journeyapps.barcodescanner.BarcodeEncoder

object QRCodeManager {
    
    fun generateDeviceId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }
    
    fun generateUniqueCode(): String {
        return (100000..999999).random().toString()
    }
    
    fun createQRCodeBitmap(content: String, width: Int = 512, height: Int = 512): android.graphics.Bitmap {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height)
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.createBitmap(bitMatrix)
    }
    
    fun isValidQRCode(content: String): Boolean {
        return content.matches(Regex("^[A-Z0-9]{6,8}$"))
    }
}
