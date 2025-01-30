package ru.antoncharov.billing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.antoncharov.billing.dto.EmailRequest;
import ru.antoncharov.billing.dto.PhoneRequest;
import ru.antoncharov.billing.dto.SearchUserRequest;
import ru.antoncharov.billing.dto.UserDto;
import ru.antoncharov.billing.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/addPhone")
    public ResponseEntity<?> addPhoneToUser(@RequestBody PhoneRequest phoneRequest) {
        userService.addPhone(phoneRequest.getNewNumber());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/addEmail")
    public ResponseEntity<?> addEmailToUser(@RequestBody EmailRequest emailRequest) {
        userService.addEmail(emailRequest.getNewEmail());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/changePhone")
    public ResponseEntity<?> changePhoneToUser(@RequestBody PhoneRequest phoneRequest) {
        userService.changePhone(phoneRequest.getOldNumber(), phoneRequest.getNewNumber());

        return ResponseEntity.ok().build();
    }

    @PutMapping("/changeEmail")
    public ResponseEntity<?> changeEmailToUser(@RequestBody EmailRequest emailRequest) {
        userService.changeEmail(emailRequest.getOldEmail(), emailRequest.getNewEmail());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deletePhone")
    public ResponseEntity<?> deletePhoneToUser(@RequestBody PhoneRequest phoneRequest) {
        userService.deletePhone(phoneRequest.getOldNumber());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteEmail")
    public ResponseEntity<?> deleteEmailToUser(@RequestBody EmailRequest emailRequest) {
        userService.deleteEmail(emailRequest.getOldEmail());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<UserDto>> search(@RequestBody SearchUserRequest searchUserRequest) {
        return ResponseEntity.ok(userService.search(searchUserRequest));
    }
}
