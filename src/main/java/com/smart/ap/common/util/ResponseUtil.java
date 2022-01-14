package com.smart.ap.common.util;

import java.util.HashMap;
import java.util.Map;

import com.smart.ap.common.constraint.ResEnum;

public class ResponseUtil {

    /**
     * 공통 응답정보를 추가한
     * @param prmMap
     * @param wEnum
     * @return
     */
    public static Map<String, Object>  responseInfo(Map<String, Object> prmMap, ResEnum wEnum, String resultMsg, boolean isBaseMessage) {
        Map<String, Object> resultMap = (prmMap == null) ? new HashMap<String,Object>() : prmMap;
        //String requiredFileds[] = {"resTime","resCode","resMsg"};
        if (!resultMap.containsKey("resTime")){
            resultMap.put("resTime", DateUtil.getSimpleTime());
        }
        if (!resultMap.containsKey("resCode")){
            resultMap.put("resCode", wEnum.getCode());
        }
        if (!resultMap.containsKey("resMsg")){
            resultMap.put("resMsg", wEnum.getMessage(resultMsg, isBaseMessage));
        }
        return resultMap;
    }

    /**
     * 공통 응답
     *
     * @param param : data
     * @param resCode
     * @param resMsg
     * @return
     */
    public static Map<String, Object> addRsltInfo(Object param, String resCode, String resMsg) {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("resCode", resCode);
    	map.put("resMsg", resMsg);
    	map.put("data", param);
        return map;
    }

    /**
     * 공통 응답 - 유효성
     *
     * @param param : data
     * @param chkResult : 유효성
     * @return
     */
    public static Map<String, Object> addRsltInfo(Object param, Map<String, Object> chkResult) {
    	if(chkResult == null) {
    		chkResult = new HashMap<String, Object>();
    		chkResult.put("resCode", "0000");
    		chkResult.put("resMsg", "정상");
    	}
        return addRsltInfo(param, chkResult.get("resCode").toString(), chkResult.get("resMsg").toString());
    }
}
