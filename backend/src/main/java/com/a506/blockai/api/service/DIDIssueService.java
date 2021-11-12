package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.DIDIssueRequest;
import com.a506.blockai.db.entity.DID;
import com.a506.blockai.db.entity.User;
import com.a506.blockai.db.repository.UserRepository;
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
        // 랜덤 DID address 발급
        String address = ethereumService.sha256(LocalDateTime.now().toString() + userId).substring(0,42);
        System.out.println("new DID address : " + address);

        // DID 발급
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Utf8String(didIssueRequest.getFacePath()));
        inputParameters.add(new Utf8String(didIssueRequest.getVoiceId()));
        inputParameters.add(new Address(address));

        // 1. 호출하고자 하는 function 세팅[functionName, parameters]
        Function function = new Function("addDID", inputParameters, Collections.emptyList());

        // 2. 트랜잭션 전송
        String txHash = ethereumService.ethSendRawTransaction(function);
        System.out.println("txhash : " + txHash);

        // DB에 업데이트
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        DID did = user.getDid();
        if (isIssuedDid(did)) {
            did.updateDid(address);
        } else {
            did = new DID(address);
        }
        return address;
    }

    private boolean isIssuedDid(DID did) {
        return did != null;
    }


}
