package injea.knwremodel.Mail

import injea.knwremodel.Mail.MailDTO.confirmNumber
import injea.knwremodel.Mail.MailDTO.send
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(private val mailS: MailService) {
    // 인증 번호 발송
    @PostMapping("/send")
    @Synchronized
    fun MailSend(@RequestBody dto: send): ResponseEntity<*> {
        try {
            mailS.sendMail(dto.mail)
            return ResponseEntity.ok("인증번호 발송. 1분 30초 안에 입력하시오.")
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }

    // 인증 번호 확인
    @PostMapping("/confirmNumber")
    @Synchronized
    fun ConfirmNumber(@RequestBody dto: confirmNumber): ResponseEntity<*> {
        try {
            mailS.confirmNumber(dto.enteredNumber)
            return ResponseEntity.ok("이메일 인증이 성공했습니다.")
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(e.message)
        }
    }
}