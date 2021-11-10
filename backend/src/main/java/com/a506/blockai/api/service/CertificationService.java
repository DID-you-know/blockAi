package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.BiometricsCertificateRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import com.a506.blockai.db.entity.Certification;
import com.a506.blockai.db.entity.DID;
import com.a506.blockai.db.repository.CertificationRepository;
import com.a506.blockai.db.repository.DIDRepository;
import com.a506.blockai.exception.DidExpiredException;
import com.a506.blockai.exception.DidNotFoundException;
import com.a506.blockai.exception.DidNotYetIssuedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final AiServiceImpl aiService;
    private final EthereumService ethereumService;
    private final DIDRepository didRepository;
    private final CertificationRepository certificationRepository;
    private final float similarity = 0.5f;

    public void certifyBiometrics(int userId, BiometricsCertificateRequest biometricsCertificateRequest) throws IOException {
        DID did = didRepository.findById(userId)
                .orElseThrow(DidNotFoundException::new);

        // DID 발급 여부 확인
        if (!did.isDidFlag()) {
            throw new DidNotYetIssuedException();
        }

        String didAddress = did.getDidAddress();
        List<Type> ethereumCallResult = getBiometricsCertificateFromBlockchain(didAddress);
        String faceCertificateFromBlockchain = String.valueOf(ethereumCallResult.get(0).getValue());
        String voiceCertificateFromBlockchain = String.valueOf(ethereumCallResult.get(1).getValue());
        LocalDateTime expiryTime = (LocalDateTime) ethereumCallResult.get(0).getValue();

        // DID 만료 여부 확인
        if (expiryTime.isBefore(LocalDateTime.now())) {
            throw new DidExpiredException();
        }

        // 얼굴 유사도
        float faceScore = 0;

        // 목소리 유사도
        VoiceBiometricsRequest voiceBiometricsRequest = new VoiceBiometricsRequest(biometricsCertificateRequest.getVoice());
        float voiceScore = aiService.identify(voiceCertificateFromBlockchain, voiceBiometricsRequest);

        if (isSamePerson(faceScore, voiceScore)) {
            Certification certification = null;
            certificationRepository.save(certification);
        }
    }

    private boolean isSamePerson(float faceScore, float voiceScore) {
        return faceScore >= similarity && voiceScore >= similarity;
    }

    private List<Type> getBiometricsCertificateFromBlockchain(String didAddress) throws IOException {
        Function function = new Function("getDID", Arrays.asList(new Address(didAddress)), Collections.emptyList());
        List<Type> ethereumCallResult = ethereumService.ethCall(function);
        return ethereumCallResult;
    }
}
