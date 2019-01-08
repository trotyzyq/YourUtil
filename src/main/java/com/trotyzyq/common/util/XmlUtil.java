package com.trotyzyq.common.util;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

/**
 * Created by trotyzyq on 2018/8/9.
 */

public class XmlUtil {

    public String xmlToJson(String xml){
        JSONObject object = null;
        try {
            object = XML.toJSONObject(xml);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  object.toString();
    }
}
