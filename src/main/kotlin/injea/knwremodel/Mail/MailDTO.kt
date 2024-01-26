package injea.knwremodel.Mail

class MailDTO {
    class send {
        var mail: String? = null
            private set

        constructor(mail: String?) {
            this.mail = mail
        }

        constructor()
    }

    class confirmNumber {
        var enteredNumber: String? = null
            private set

        constructor(enteredNumber: String?) {
            this.enteredNumber = enteredNumber
        }

        constructor()
    }
}

