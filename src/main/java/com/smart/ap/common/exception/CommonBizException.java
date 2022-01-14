package com.smart.ap.common.exception;

import lombok.Getter;
import lombok.Setter;

public class CommonBizException extends RuntimeException {
    private static final long serialVersionUID = 264887350921870905L;

    @Getter @Setter
    private String resMsg; //응답메세지

    public CommonBizException() {
        super();
        this.resMsg = "서버에서 장애가 발생하였습니다.<br />잠시 후 다시 시도해주세요.";
    }

    public CommonBizException(Exception e) {
        super();
        this.resMsg = "서버에서 장애가 발생하였습니다.<br />잠시 후 다시 시도해주세요.";
    }

    public CommonBizException(String bizErrMsg) {
        super();
        this.resMsg = bizErrMsg;
    }

    public CommonBizException(Exception e, String bizErrMsg) {
        super(e);
        this.resMsg = bizErrMsg;
    }

}
