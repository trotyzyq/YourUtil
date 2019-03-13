package com.trotyzyq.common.config.config;

import com.google.common.base.MoreObjects;
import com.trotyzyq.common.util.MoneyUtil;
import org.apache.commons.lang.StringUtils;

public abstract class ValueParser {

    /**
     * Value的处理过程
     *
     * @param value
     * @return
     */
    public abstract Object parse(Object value);

    public static ValueParser YuanToFen = new ValueParser() {
        @Override
        public Object parse(Object value) {
            return MoneyUtil.convertYuanToFen(String.valueOf(value));
        }
    };


    public static ValueParser YuanToFenStr = new ValueParser() {
        @Override
        public Object parse(Object value) {
            return MoneyUtil.convertYuanToFenStr(String.valueOf(value));
        }
    };

    public static ValueParser YuanToHao = new ValueParser() {
        @Override
        public Object parse(Object value) {
            return MoneyUtil.convertYuanToHao(String.valueOf(value));
        }
    };

    public static ValueParser FenToYuan = new ValueParser() {
        @Override
        public Object parse(Object value) {
            return MoneyUtil.convertFenToYuan(String.valueOf(value));
        }
    };

    public static ValueParser FloatToInt = new ValueParser() {
        @Override
        public Object parse(Object value) {

            String retValue = (String) value;
            if(StringUtils.isBlank(retValue)){
                return 0;
            }
            return (int) Float.parseFloat(retValue);
        }
    };

    public static ValueParser ToInt = new ValueParser() {
        @Override
        public Object parse(Object value) {

            String retValue = (String) value;
            if(StringUtils.isBlank(retValue)){
                return 0;
            }
            return Integer.parseInt(retValue);
        }
    };

    public static ValueParser ToFloat = new ValueParser() {
        @Override
        public Object parse(Object value) {

            String retValue = (String) value;
            if(StringUtils.isBlank(retValue)){
                return 0;
            }
            return Float.parseFloat(retValue);
        }
    };

    public static ValueParser ToLong = new ValueParser() {
        @Override
        public Object parse(Object value) {
            String retValue = (String) value;
            if(StringUtils.isBlank(retValue)){
                return 0;
            }
            return Long.valueOf(retValue.trim());
        }
    };

    public static ValueParser ToSting = new ValueParser() {
        @Override
        public Object parse(Object value) {
            return String.valueOf(value);
        }
    };

    public static ValueParser defaultValue(final Object defaultValue) {
        return new ValueParser() {
            @Override
            public Object parse(Object value) {
                return MoreObjects.firstNonNull(value, defaultValue);
            }
        };
    }

    public static ValueParser defaultEmptyValue(final Object defaultValue) {
        return new ValueParser() {
            @Override
            public Object parse(Object value) {
                if(value==null||value.equals("")){
                    return defaultValue;
                }
                return value;
            }
        };
    }

    public static ValueParser fixedValue(final Object fixedValue) {
        return new ValueParser() {
            @Override
            public Object parse(Object value) {
                return fixedValue;
            }
        };
    }

    /**
     * 处理日期格式 YYYY-MM-DD
     *
     * @return
     */
    public static ValueParser parserDefaultDateFormat() {
        return new ValueParser() {
            @Override
            public Object parse(Object value) {
                if (value == null) {
                    return null;
                }
                return StringUtils.replace(value.toString(), "/", "-");
            }
        };
    }
}
