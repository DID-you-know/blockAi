package com.a506.blockai.api.service;

import com.a506.blockai.config.EthereumProperties;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class EthereumService {

    private final String from;
    private final String contract;
    private final String privateKey;
    private final Admin web3j;

    public EthereumService(EthereumProperties ethereumProperties) {
        this.from = ethereumProperties.getFrom();
        this.contract = ethereumProperties.getContract();
        this.privateKey = ethereumProperties.getPrivateKey();
        this.web3j = Admin.build(new HttpService(ethereumProperties.getNetworkUrl()));

    }

    public List<Type> ethCall(Function function) throws IOException {
        //1. transaction 제작
        Transaction transaction = Transaction.createEthCallTransaction(from, contract,
                FunctionEncoder.encode(function));

        //2. ethereum 호출후 결과 가져오기
        EthCall ethCall = web3j.ethCall(transaction, DefaultBlockParameterName.LATEST).send();

        //3. 결과값 decode
        List<Type> decode = FunctionReturnDecoder.decode(ethCall.getResult(),
                function.getOutputParameters());

//        System.out.println("ethCall.getResult() = " + ethCall.getResult());
//        System.out.println("getValue = " + decode.get(0).getValue());
//        System.out.println("getType = " + decode.get(0).getTypeAsString());

        return decode;
//        return decode.get(0).getValue();
    }

    public String test() throws IOException {
        Web3ClientVersion web3ClientVersion = null;
        web3ClientVersion = web3j.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println(clientVersion);
        return clientVersion;
    }

    public String ethSendRawTransaction(Function function) throws IOException, ExecutionException, InterruptedException {

        //nonce 조회
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                from, DefaultBlockParameterName.PENDING).send();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println(nonce);

        BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
        BigInteger GAS_LIMIT = BigInteger.valueOf(772197L);

        // 트랜잭션 생성
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE,
                GAS_LIMIT, contract, FunctionEncoder.encode(function));

        // 트랜잭션 서명
        Credentials credentials = Credentials.create(privateKey);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        // 트랜잭션 전송
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if(ethSendTransaction.hasError()) {
            System.out.println("Transcation error : " + ethSendTransaction.getError().getMessage());
        }
        String hash = ethSendTransaction.getTransactionHash();
        return hash;
    }

    public TransactionReceipt getReceipt(String transactionHash) throws IOException {

        EthGetTransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash).send();

        if (transactionReceipt.getTransactionReceipt().isPresent()) {
            System.out.println("transactionReceipt.getResult().getContractAddress() = " +
                    transactionReceipt.getResult());
        } else {
            System.out.println("transaction complete not yet");
        }
        return transactionReceipt.getResult();
    }



    public String sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        return bytesToHex(md.digest());
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        builder.append("0x");
        for (byte b: bytes) {
            builder.append(String.format("%02X", b));
        }
        return builder.toString();
    }

}