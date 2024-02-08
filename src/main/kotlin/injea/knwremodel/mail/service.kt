package injea.knwremodel.mail

import injea.knwremodel.user.UserService
import jakarta.mail.internet.MimeMessage
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class MailService(
    private val userS: UserService,
    private val javaMailSender: JavaMailSender,
    private val httpSession: HttpSession
) {
    @Value("\${spring.mail.username}")
    private val senderEmail: String? = null

    fun CreateMail(mail: String, number: Int): MimeMessage {
        val message = javaMailSender.createMimeMessage()

        message.setFrom(senderEmail)
        message.setRecipients(MimeMessage.RecipientType.TO, mail)
        message.subject = "이메일 인증"
        var body = ""
        body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>"
        body += "<h1>$number</h1>"
        body += "<h3>" + "감사합니다." + "</h3>"
        message.setText(body, "UTF-8", "html")

        return message
    }

    fun sendMail(mail: String) {
        if (mail.endsWith("kangnam.ac.kr") == false){
            throw IllegalArgumentException("강남대학교 이메일로만 인증 가능합니다. 다시 시도해주세요.")
        }

        val number = (Math.random() * (90000)).toInt() + 100000 // (int) Math.random() * (최댓값-최소값+1) + 최소값

        val message = CreateMail(mail, number)

        httpSession.setAttribute("timer", System.currentTimeMillis() + 90 * 1000)
        httpSession.setAttribute("number", number)

        javaMailSender.send(message)
    }

    fun confirmNumber(enteredNumber: String) {
        val timer = httpSession.getAttribute("timer") as Long
        val number = httpSession.getAttribute("number") as Int

        if (System.currentTimeMillis() > timer) {
            httpSession.removeAttribute("timer")
            httpSession.removeAttribute("number")
            throw IllegalArgumentException("인증 번호가 만료되었습니다. 다시 시도해주세요")
        }
        else if(number != enteredNumber.toInt()) {
            throw IllegalArgumentException("인증 번호가 다릅니다. 다시 시도해주세요.")
        }
        userS.setCurrentUserRole(injea.knwremodel.entity.Role.USER)
        httpSession.removeAttribute("timer")
        httpSession.removeAttribute("number")
    }
}