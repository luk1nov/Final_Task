package by.lukyanov.final_task.util;

import by.lukyanov.final_task.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger();
    private static PasswordEncoder instance;

    private PasswordEncoder() {
    }

    public static PasswordEncoder getInstance(){
        if (instance == null){
            instance = new PasswordEncoder();
        }
        return instance;
    }

    public String encode(String decodedPassword) throws ServiceException {
        byte[] encodedBytes;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(decodedPassword.getBytes(StandardCharsets.UTF_8));
            encodedBytes = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
        BigInteger bigInteger = new BigInteger(1, encodedBytes);
        String encodedPassword = bigInteger.toString(16);
        return encodedPassword;
    }
}