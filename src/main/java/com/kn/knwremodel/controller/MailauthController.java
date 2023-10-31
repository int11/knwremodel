package com.kn.knwremodel.controller;


import com.kn.knwremodel.service.MailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MailauthController {

    @Autowired
    MailService ms;

    /* main */
    @RequestMapping( {"/main.do", "/"} )
    public ModelAndView main() {
        System.out.println( "main() 호출" );

        ModelAndView modelAndView = new ModelAndView( "index" );
        return modelAndView;
    }
    /* mail_ok */
    @RequestMapping( "/mail_ok.do" )
    public ModelAndView mail_ok(HttpServletRequest req) {
        System.out.println( "mail_ok() 호출" );
        ms.sendMail( req.getParameter( "email" ) );

        ModelAndView modelAndView = new ModelAndView( "mail_ok" );
        return modelAndView;
    }
}
