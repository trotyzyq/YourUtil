package com.trotyzyq.common.util.rsa;

/**
 * Created by DOmmy on 2018/5/9.
 */
public class RsaSignException extends RuntimeException{

    public RsaSignException() {
    }

    public RsaSignException(String message) {
        super(message);
    }

    public RsaSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public RsaSignException(Throwable cause) {
        super(cause);
    }

}
