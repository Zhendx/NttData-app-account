package com.NttDataChallenge.account.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AppException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private ErrorApp error;

    public AppException(ErrorApp error){
        super();
        this.error =  error;
    }
}
