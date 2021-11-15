package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.BiometricsCertificateRequest;
import com.a506.blockai.api.dto.request.FaceBiometricsRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricsRequest;
import com.a506.blockai.db.entity.Certification;
import com.a506.blockai.db.entity.DID;
import com.a506.blockai.db.entity.User;
import com.a506.blockai.db.repository.CertificationRepository;
import com.a506.blockai.db.repository.UserRepository;
import com.a506.blockai.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final AiServiceImpl aiService;
    private final EthereumService ethereumService;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final float similarity = 0.5f;

    public void certifyBiometrics(int userId, BiometricsCertificateRequest biometricsCertificateRequest) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        DID did = user.getDid();

        // DID 발급 여부 확인
        if (!isIssuedDid(did)) {
            throw new DidNotYetIssuedException();
        }

        String didAddress = did.getDidAddress();
        List<Type> ethereumCallResult = getBiometricsCertificateFromBlockchain(didAddress);
        String faceCertificateFromBlockchain = String.valueOf(ethereumCallResult.get(0).getValue());
        String voiceCertificateFromBlockchain = String.valueOf(ethereumCallResult.get(1).getValue());
        BigInteger bigIntegerExpiryTime = (BigInteger) ethereumCallResult.get(2).getValue();
        LocalDateTime expiryTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(bigIntegerExpiryTime.longValue()),
                TimeZone.getDefault().toZoneId());

        // DID 만료 여부 확인
        if (expiryTime.isBefore(LocalDateTime.now())) {
            throw new DidExpiredException();
        }

        // 얼굴 유사도
        FaceBiometricsRequest faceBiometricsRequest = new FaceBiometricsRequest(biometricsCertificateRequest.getFace(), faceCertificateFromBlockchain);
        float faceScore = aiService.identifyFace(faceBiometricsRequest);

        // 목소리 유사도
        VoiceBiometricsRequest voiceBiometricsRequest = new VoiceBiometricsRequest(biometricsCertificateRequest.getVoice(), voiceCertificateFromBlockchain);
        float voiceScore = aiService.identifyVoice(voiceBiometricsRequest);

        if (!isSamePerson(faceScore, voiceScore)) {
            throw new UnauthorizedAccessException();
        }
        saveCertificateHistory(user, biometricsCertificateRequest.getCertifiedBy());
    }

    private boolean isIssuedDid(DID did) {
        return did != null;
    }

    private boolean isSamePerson(float faceScore, float voiceScore) {
        return faceScore >= similarity && voiceScore >= similarity;
    }

    private List<Type> getBiometricsCertificateFromBlockchain(String didAddress) throws IOException {
        List<TypeReference<?>> outputParameters = Arrays.asList(
                new TypeReference<Utf8String>() {
                },
                new TypeReference<Utf8String>() {
                },
                new TypeReference<Uint256>() {
                }
        );
        Function function = new Function("getDID", Arrays.asList(new Address(didAddress)), outputParameters);
        List<Type> ethereumCallResult = ethereumService.ethCall(function);
        return ethereumCallResult;
    }

    private void saveCertificateHistory(User user, String certifiedBy) {
        Certification certification = new Certification(certifiedBy);
        user.addCertification(certification);
        certificationRepository.save(certification);
    }

}
