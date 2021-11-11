package com.a506.blockai;

import com.a506.blockai.api.service.EthereumService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class BlockaiApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {

		 SpringApplication.run(BlockaiApplication.class, args);

		// DID 발급 테스트
//		ContractService contractService = new ContractService();
//
//		String id = "dsdsaa3223";
//		String address = contractService.sha256(LocalDateTime.now().toString() + id).substring(0,42);
//		System.out.println(address);
//
//		System.out.println(contractService.test());
//
//		List<Type> inputParameters = new ArrayList<>();
//		inputParameters.add(new Utf8String("faceData"));
//		inputParameters.add(new Utf8String("voiceData"));
//		inputParameters.add(new Address("0x3861ce91ED1b8dE311F025923F711EA07a8501c1"));
//
//		// 1. 호출하고자 하는 function 세팅[functionName, parameters]
//		Function function = new Function("addDID", inputParameters, Collections.emptyList());
//
//		// 2. sendTransaction
//		String txHash = contractService.ethSendRawTransaction(function);
//		System.out.println(txHash);
//
//
//		TransactionReceipt receipt = contractService.getReceipt(txHash);
//		System.out.println("receipt = " + receipt);

		// RSA키 생성 test
//		RSAService rsaService = new RSAService();
//
//		HashMap<String, String> rsaKeyPair = rsaService.createKeypairAsString();
//		String publicKey = rsaKeyPair.get("publicKey");
//		String privateKey = rsaKeyPair.get("privateKey");
//
//		System.out.println("만들어진 공개키:" + publicKey);
//		System.out.println("만들어진 개인키:" + privateKey);
//
//		String plainText = "플레인 텍스트";
//		System.out.println("평문: " + plainText);
//
//		String encryptedText = rsaService.encode(plainText, publicKey);
//		System.out.println("암호화: " + encryptedText);
//
//		String decryptedText = rsaService.decode(encryptedText, privateKey);
//		System.out.println("복호화: " + decryptedText);


	}

}
