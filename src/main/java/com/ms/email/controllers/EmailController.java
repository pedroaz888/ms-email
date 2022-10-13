package com.ms.email.controllers;


import com.ms.email.dtos.EmailDto;
import com.ms.email.models.EmailModel;
import com.ms.email.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {

        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailService.sendEmail(emailModel);

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);

    }


    @GetMapping("/listar")
    public ResponseEntity<Page<EmailModel>> getAllEmails(Principal principal) {

        PageRequest paginacao = PageRequest.of(0, 7);
        return ResponseEntity.status(HttpStatus.OK).body(emailService.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getId(@PathVariable(value = "id") UUID id) {

        Optional<EmailModel> emailModelOptional = emailService.getId(id);

        if (!emailModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(emailModelOptional.get());


    }

}