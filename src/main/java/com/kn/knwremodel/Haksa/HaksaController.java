package com.kn.knwremodel.Haksa;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/Haksa")
@RequiredArgsConstructor
public class HaksaController {
    private final HaksaService haksaS;

    @PostMapping("/request")
    public ResponseEntity request(){
        return ResponseEntity.ok(haksaS.findAll());
    }
}