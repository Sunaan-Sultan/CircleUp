package com.project.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.project.models.security.TokenType
import java.util.Date
import java.time.Instant
import java.time.temporal.ChronoUnit

object TokenUtil {
    // should be loaded securely in BuildConfig or secrets manager
    private const val ACCESS_TOKEN_SECRET = "your_access_secret_jkdfhasjkdfhkjsafkldsjhjf"
    private const val REFRESH_TOKEN_SECRET = "your_refresh_secret_klsdfhfkjdjsfhjdksfhkjds"
    private const val OTP_TOKEN_SECRET = "your_otp_secret_askldjjkalsdaklsdlaksdklasdasd"


    private const val ACCESS_TOKEN_EXPIRES_IN_MINUTES = 5L
    private const val REFRESH_TOKEN_EXPIRES_IN_MINUTES = 30L * 24 * 60  // 30 days
    private const val OTP_TOKEN_EXPIRES_IN_MINUTES = 5L

    /**
     * Creates a JWT token with the specified payload and key type.
     *
     * @param payload A map containing the payload data.
     * @param keyType The token type: ACCESS, REFRESH or OTP.
     * @return The signed JWT token.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun createToken(
        payload: Map<String, String>,
        keyType: TokenType
    ): String {
        val (secretKey, expiresIn) = when (keyType) {
            TokenType.ACCESS -> ACCESS_TOKEN_SECRET to ACCESS_TOKEN_EXPIRES_IN_MINUTES
            TokenType.REFRESH -> REFRESH_TOKEN_SECRET to REFRESH_TOKEN_EXPIRES_IN_MINUTES
            TokenType.OTP -> OTP_TOKEN_SECRET to OTP_TOKEN_EXPIRES_IN_MINUTES
        }

        val algorithm = Algorithm.HMAC256(secretKey)
        // Calculate expiration date based on the current time and expiration interval (in minutes)
        val expiryDate = Date.from(Instant.now().plus(expiresIn, ChronoUnit.MINUTES))

        val jwtBuilder = JWT.create()
            .withExpiresAt(expiryDate)

        payload.forEach { (key, value) ->
            jwtBuilder.withClaim(key, value)
        }

        return jwtBuilder.sign(algorithm)
    }
}
