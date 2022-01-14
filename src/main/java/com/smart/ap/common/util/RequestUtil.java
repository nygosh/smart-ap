package com.smart.ap.common.util;


import java.util.HashMap;
import java.util.Map;

import com.smart.ap.common.exception.CommonBizException;

public class RequestUtil {

    public static void checkParam(Map<?, ?> prmMap, String[] checkList) {
        for (String selecteStr : checkList) {
            if (!prmMap.containsKey(selecteStr)) {
                System.out.println("containsKey 입력항목이 누락되었습니다[" + selecteStr + "]");
              throw new CommonBizException("입력항목이 누락되었습니다[" + selecteStr + "]");
            }

            if (StringUtil.isEmptyObj(prmMap.get(selecteStr))) {
                System.out.println("isEmptyObj 입력항목이 입력되지 않았습니다.[" + selecteStr + "]");
              throw new CommonBizException("입력항목이 입력되지 않았습니다.[" + selecteStr + "]");
            }
        }
    }

    public static Map<String, Object> getCheckParam(Map<?, ?> prmMap, String[] checkList) {
        System.out.println("checkParam : prmMap[" + prmMap + "]");

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resCode", "0000");
        resultMap.put("resMsg", "");

        for (String selecteStr : checkList) {
            if (!prmMap.containsKey(selecteStr)) {
                resultMap.put("resCode", "9999");
                resultMap.put("resMsg", StringUtil.nvl(resultMap.get("resMsg")) + "/입력항목 누락[" + selecteStr + "]");
            }

            if (StringUtil.isEmptyObj(prmMap.get(selecteStr))) {
                resultMap.put("resCode", "9999");
                resultMap.put("resMsg", StringUtil.nvl(resultMap.get("resMsg")) + "/입력항목 미입력 [" + selecteStr + "]");
            }
        }

        return resultMap;
    }

}
