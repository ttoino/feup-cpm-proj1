package pt.up.fe.cpm.tiktek.backend

import kotlinx.datetime.toLocalDate

val emailRegex =
    Regex("^[a-zA-Z0-9.!#\$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$")

fun String.isEmailAddress(): Boolean = emailRegex.matches(this)

fun String.isPassword(): Boolean = this.length >= 8

fun String.isNif(): Boolean = this.length == 9 && this.all { it.isDigit() }

fun String.isDate(): Boolean =
    try {
        this.toLocalDate()
        true
    } catch (e: NumberFormatException) {
        false
    }

fun String.isCreditCardNumber(): Boolean = this.length == 16 && this.all { it.isDigit() }

val expirationDateRegex = Regex("^(0[1-9]|1[0-2])/(\\d{2})\$")

fun String.isExpirationDate(): Boolean = expirationDateRegex.matches(this)

fun String.isCvv(): Boolean = this.length == 3 && this.all { it.isDigit() }
