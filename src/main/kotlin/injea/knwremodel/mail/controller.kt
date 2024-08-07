package injea.knwremodel.mail

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/mail")
class MailController(private val mailS: MailService) {
    // 인증 번호 발송
    class sendDTO(var mail: String)
    @PostMapping("/send")
    fun send(@RequestBody dto: sendDTO): ResponseEntity<*> {
        mailS.sendMail(dto.mail)
        return ResponseEntity.ok("인증번호 발송. 1분 30초 안에 입력하시오.")
    }

    // 인증 번호 확인
    class confirmNumberDTO(var authNumber: String)
    @PostMapping("/confirmNumber")
    fun confirmNumber(@RequestBody dto: confirmNumberDTO): ResponseEntity<*> {
        mailS.confirmNumber(dto.authNumber)
        return ResponseEntity.ok("이메일 인증이 성공했습니다.")
    }
}