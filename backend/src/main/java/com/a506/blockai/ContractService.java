package com.a506.blockai;

import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ContractService {

    private String from = "0x3861ce91ED1b8dE311F025923F711EA07a8501c1";

    private String contract = "0x94aA46Fc81D4f59f01048319417d074803f067d4";

    // hardcording because of testing
    private String pwd = "0xaca10c42b1fe8262a88f696e5c389a895f1091e8bec36a3f41a8fbd47d3f1f1b";

    private Web3j web3j = null;

    public ContractService() {
        web3j = Web3j.build(new HttpService("https://ropsten.infura.io/v3/9aa3d95b3bc440fa88ea12eaa4456161"));
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
                from, DefaultBlockParameterName.LATEST).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println(nonce);
//        Transaction transaction = Transaction.createFunctionCallTransaction(from, nonce, Transaction.DEFAULT_GAS,
//                BigInteger.valueOf(1000000L), contract, FunctionEncoder.encode(function));

        BigInteger GAS_PRICE = BigInteger.valueOf(20000000L);

        BigInteger GAS_LIMIT = BigInteger.valueOf(772197L);

        // 트랜잭션 생성
        RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, GAS_PRICE,
                GAS_LIMIT, contract, FunctionEncoder.encode(function));

        // 트랜잭션 서명
        Credentials credentials = Credentials.create(pwd);
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        // 트랜잭션 전송
        EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

        if(ethSendTransaction.hasError()) {
            System.out.println(ethSendTransaction.getError().getMessage());
        }
        String hash = ethSendTransaction.getTransactionHash();
        return hash;
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

    public String ethSendTransaction(Function function)
            throws IOException, InterruptedException, ExecutionException {

        // 1. account에 대한 nonce값 가져오기.
        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                from, DefaultBlockParameterName.LATEST).sendAsync().get();

        // 계정 이더 잔액 조회
//        EthGetBalance ethGetBalance = web3j.ethGetBalance(from, DefaultBlockParameterName.LATEST)
//                .sendAsync()
//                .get();
//        BigInteger wei = ethGetBalance.getBalance();
//        System.out.println(Convert.fromWei(wei.toString() , Convert.Unit.ETHER).toString());

        // 2. Account Lock 해제
      //  PersonalUnlockAccount personalUnlockAccount = web3j.personalUnlockAccount(from, pwd).send();

      //  if (personalUnlockAccount.accountUnlocked()) { // unlock 일때

            BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        System.out.println(nonce);
            // 3. Transaction 값 제작
            Transaction transaction = Transaction.createFunctionCallTransaction(from, nonce, Transaction.DEFAULT_GAS,
                    BigInteger.valueOf(1000000L), contract, FunctionEncoder.encode(function));

            // 4. ethereum Call &
//            EthSendTransaction ethSendTransactionResponse = web3j.ethSendTransaction(transaction).send();

            // 5. transaction에 대한 transaction Hash값 얻기.
//            String transactionHash = ethSendTransactionResponse.getTransactionHash();

            // ledger에 쓰여지기 까지 기다리기.
//            Thread.sleep(5000);

//            return transactionHash;


            EthSendTransaction transactionResponse = web3j.ethSendTransaction(transaction).send();
            if (transactionResponse.hasError()) {
                String message = transactionResponse.getError().getMessage();
                return message;
            }else{
                String hash = transactionResponse.getTransactionHash();
                return hash;
            }
//        } else {
//            throw new PersonalLockException("check ethereum personal Lock");
//        }
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

    private class PersonalLockException extends RuntimeException {
        public PersonalLockException(String msg) {
            super(msg);
        }
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