package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.validateWith
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.model.UserWithPassword
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.birthdate
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.cvvCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.email
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.expirationDateCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.name
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.nameCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.nif
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.numberCc
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.password

val userValidator =
    Validator<User> {
        name.validateWith(nameValidator)
        nif.validateWith(nifValidator)
        birthdate.validateWith(birthdateValidator)
        email.validateWith(emailValidator)
        nameCc.validateWith(nameValidator)
        numberCc.validateWith(numberCcValidator)
        expirationDateCc.validateWith(expirationDateCcValidator)
        cvvCc.validateWith(cvcCcValidator)
    }

val userWithPasswordValidator =
    Validator<UserWithPassword> {
        name.validateWith(nameValidator)
        nif.validateWith(nifValidator)
        birthdate.validateWith(birthdateValidator)
        email.validateWith(emailValidator)
        nameCc.validateWith(nameValidator)
        numberCc.validateWith(numberCcValidator)
        expirationDateCc.validateWith(expirationDateCcValidator)
        cvvCc.validateWith(cvcCcValidator)
        password.validateWith(passwordValidator)
    }
