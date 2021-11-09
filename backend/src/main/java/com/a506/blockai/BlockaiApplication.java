package com.a506.blockai;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
public class BlockaiApplication {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ExecutionException, InterruptedException {

		SpringApplication.run(BlockaiApplication.class, args);

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
//		inputParameters.add(new Address(address));
//
//		// 1. 호출하고자 하는 function 세팅[functionName, parameters]
//		Function function = new Function("addCertificate", inputParameters, Collections.emptyList());
//
//		// 2. sendTransaction
//		String txHash = contractService.ethSendTransaction(function);
//		System.out.println(txHash);


		// 3. getReceipt
//		TransactionReceipt receipt = contractService.getReceipt(txHash);
//		System.out.println("receipt = " + receipt);
	}

}
