package com.a506.blockai.api.controller;

import com.a506.blockai.api.dto.request.DIDIssueRequest;
import com.a506.blockai.api.service.DIDIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class IssueController {

    private final DIDIssueService didIssueService;

    @PostMapping("/{userId}/did/issue")
    public Object certifyBiometrics(@PathVariable int userId, @RequestBody DIDIssueRequest didIssueRequest) throws IOException {
        String address = "";

        try {
          address = didIssueService.issueDID(userId, didIssueRequest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}