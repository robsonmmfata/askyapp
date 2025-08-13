package br.com.smartpos.asky.data.model

data class QRCodeResponse(
    val code: String,
    val deviceId: String,
    val deviceName: String,
    val permissions: List<String>,
    val expiresAt: Long,
    val isOwnDevice: Boolean = true
)

data class QRCodeLoginRequest(
    val code: String,
    val deviceId: String,
    val deviceName: String
)

data class QRCodeLoginResponse(
    val token: String,
    val refreshToken: String,
    val user: UserInfo,
    val permissions: List<String>
)

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)
