package com.smart.ap.common.constraint;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.smart.ap.common.util.ResponseUtil;

public class ResEntity <T extends Map<String, Object>> extends ResponseEntity<T> {

    private T resMap;

    public ResEntity(T body, ResEnum wEnum, String resultMsg, boolean isBaseMessage) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.responseInfo(resMap, wEnum, resultMsg, isBaseMessage);
    }

    public ResEntity(T body, ResEnum wEnum, String resultMsg) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.responseInfo(resMap, wEnum, resultMsg, false);
    }

    public ResEntity(T body, ResEnum wEnum) {
        super(body, wEnum.getHttpStatus());
        resMap = body;
        ResponseUtil.responseInfo(resMap, wEnum, "", false);
    }
}
