package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.DIDIssueRequest;
import com.a506.blockai.db.entity.DID;
import com.a506.blockai.db.entity.User;
import com.a506.blockai.db.repository.UserRepository;
import com.a506.blockai.exception.BadRequestException;
import com.a506.blockai.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class DIDIssueService {

    private final EthereumService ethereumService;
    private final UserRepository userRepository;

    public String issueDID(int userId, DIDIssueRequest didIssueRequest) throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {
        // 존재하는 유저인지 확인
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        System.out.println("face: " + didIssueRequest.getFacePath());
        System.out.println("voice: " + didIssueRequest.getVoiceId());
        // 인증 데이터가 존재하는지 확인
        if (isBadBiometricsRequest(didIssueRequest)) throw new BadRequestException();

        // 랜덤 DID address 발급
        String address = ethereumService.sha256(LocalDateTime.now().toString() + userId).substring(0,42);

        // DID 발급
        List<Type> inputParameters = new ArrayList<>();
        String encryptedFacePath =  ethereumService.encode(didIssueRequest.getFacePath());
        String encryptedVoiceId = ethereumService.encode(didIssueRequest.getVoiceId());
        inputParameters.add(new Utf8String(encryptedFacePath));
        inputParameters.add(new Utf8String(encryptedVoiceId));
        inputParameters.add(new Address(address));

        // 1. 호출하고자 하는 function 세팅[functionName, parameters]
        Function function = new Function("addDID", inputParameters, Collections.emptyList());

        // 2. 트랜잭션 전송
        String txHash = ethereumService.ethSendRawTransaction(function);

        // DB에 업데이트
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (isIssuedDid(user)) {
            DID did = user.getDid();
            did.updateDid(address);
        } else {
            user.createDid(address);
        }
        userRepository.save(user);
        return address;
    }

    private boolean isBadBiometricsRequest(DIDIssueRequest didIssueRequest) {
        return didIssueRequest.getFacePath() == null || didIssueRequest.getVoiceId() == null;
    }

    private boolean isIssuedDid(User user) {
        return user.getDid() != null;
    }

}
