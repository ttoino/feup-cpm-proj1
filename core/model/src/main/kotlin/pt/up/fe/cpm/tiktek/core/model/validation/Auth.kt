package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.isTrue
import dev.nesk.akkurate.validateWith
import pt.up.fe.cpm.tiktek.core.model.LoginRequest
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.birthdate
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.cvvCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.email
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.expirationDateCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.name
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.nameCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.nif
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.numberCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.password

val termsAcceptedValidator =
    Validator<Boolean> {
        isTrue()
    }

val loginRequestValidator =
    Validator<LoginRequest> {
        email.validateWith(emailValidator)
        password.validateWith(passwordValidator)
    }

val registerRequestValidator =
    Validator<RegisterRequest> {
        name.validateWith(nameValidator)
        nif.validateWith(nifValidator)
        birthdate.validateWith(birthdateValidator)
        email.validateWith(emailValidator)
        password.validateWith(passwordValidator)
        nameCc.validateWith(nameValidator)
        numberCc.validateWith(numberCcValidator)
        expirationDateCc.validateWith(expirationDateCcValidator)
        cvvCc.validateWith(cvcCcValidator)
    }
