package com.a506.blockai.api.service;

import com.a506.blockai.api.dto.request.BiometricsCertificateRequest;
import com.a506.blockai.api.dto.request.FaceBiometricRequest;
import com.a506.blockai.api.dto.request.VoiceBiometricRequest;
import com.a506.blockai.api.dto.response.CertificationResponse;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final AiService aiService;
    private final EthereumService ethereumService;
    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final float faceSimilarity = 0.8f;
    private final float voiceSimilarity = 0.03f;


    public void certifyBiometrics(int userId, BiometricsCertificateRequest biometricsCertificateRequest) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        String didAddress = getDidAddress(userId);
        List<Type> ethereumCallResult = getBiometricsCertificateFromBlockchain(didAddress);
        String faceCertificateFromBlockchain = ethereumService.decode(String.valueOf(ethereumCallResult.get(0).getValue()));
        String voiceCertificateFromBlockchain = ethereumService.decode(String.valueOf(ethereumCallResult.get(1).getValue()));
        BigInteger bigIntegerExpiryTime = (BigInteger) ethereumCallResult.get(2).getValue();
        LocalDateTime expiryTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(bigIntegerExpiryTime.longValue()),
                TimeZone.getDefault().toZoneId());
        System.out.println(didAddress);

        // DID 만료 여부 확인
        if (expiryTime.isBefore(LocalDateTime.now())) {
            throw new DidExpiredException();
        }

        // 얼굴 유사도
        FaceBiometricRequest faceBiometricsRequest = new FaceBiometricRequest(biometricsCertificateRequest.getFace(), faceCertificateFromBlockchain);
        float faceScore = aiService.identifyFace(faceBiometricsRequest);
        System.out.println(faceScore);

        // 목소리 유사도
        VoiceBiometricRequest voiceBiometricsRequest = new VoiceBiometricRequest(biometricsCertificateRequest.getVoice(), voiceCertificateFromBlockchain);
        float voiceScore = aiService.identifyVoice(voiceBiometricsRequest);
        System.out.println(voiceScore);

        if (!isSamePerson(faceScore, voiceScore)) {
            throw new UnauthorizedAccessException();
        }
        saveCertificateHistory(user, biometricsCertificateRequest.getCertifiedBy());
    }

    private boolean isIssuedDid(DID did) {
        return did != null;
    }

    private boolean isSamePerson(float faceScore, float voiceScore) {
        return faceScore >= faceSimilarity && voiceScore >= voiceSimilarity;
    }

    public List<Type> getBiometricsCertificateFromBlockchain(String didAddress) throws IOException {
        List<TypeReference<?>> outputParameters = getOutputParameters();
        Function function = new Function("getDID", Arrays.asList(new Address(didAddress)), outputParameters);
        List<Type> ethereumCallResult = ethereumService.ethCall(function);
        return ethereumCallResult;
    }

    private List<TypeReference<?>> getOutputParameters() {
        List<TypeReference<?>> outputParameters = Arrays.asList(
                new TypeReference<Utf8String>() {
                },
                new TypeReference<Utf8String>() {
                },
                new TypeReference<Uint256>() {
                }
        );
        return outputParameters;
    }

    private void saveCertificateHistory(User user, String certifiedBy) {
        Certification certification = new Certification(certifiedBy);
        user.addCertification(certification);
        certificationRepository.save(certification);
    }

    private String getDidAddress(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        DID did = user.getDid();
        if (!isIssuedDid(did)) throw new DidNotYetIssuedException();
        return did.getDidAddress();
    }

    public List<CertificationResponse> searchAllCertification(int userId) {
        return userRepository.findById(userId).get()
                .getCertifications()
                .stream()
                .map(CertificationResponse::from)
                .collect(Collectors.toList());
    }

}
