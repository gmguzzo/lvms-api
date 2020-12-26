package br.com.louvemos.api.exception;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.springframework.http.HttpStatus;
import br.com.louvemos.api.base.BaseDTO;

/**
 *
 * @author heits
 */
public class LvmsException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -187500547480316378L;

    private LvmsCodesEnum codeEnum;

    private String message;

    public LvmsException(LvmsCodesEnum errorCode, Object... vars) {
        this.codeEnum = errorCode;
        this.message = errorCode.getReadableText(vars);
    }

    public LvmsCodesEnum getCodeEnum() {
        return codeEnum;
    }

    public Long getCode() {
        return this.codeEnum.getCode();
    }

    public HttpStatus getHttpStatus() {
        return this.codeEnum.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Object getResponseBody() {
        BaseDTO bd = new BaseDTO();
        bd.setCode(this.codeEnum.getCode());
        bd.setHttpStatus(this.codeEnum.getHttpStatus().value());
        bd.setMessage(this.message);
        return bd;
    }

}
