package com.free.paris.beans.propertyeditors;

import com.free.paris.util.StringUtils;

import java.beans.PropertyEditorSupport;

public class CustomBooleanEditor extends PropertyEditorSupport {
    public static final String VALUE_TRUE = "true";
    public static final String VALUE_FALSE = "false";

    public static final String VALUE_ON = "on";
    public static final String VALUE_OFF = "off";

    public static final String VALUE_YES = "yes";
    public static final String VALUE_NO = "no";

    public static final String VALUE_1 = "1";
    public static final String VALUE_0 = "0";

    private final boolean allowEmpty;

    public CustomBooleanEditor(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String input = text == null ? null : text.trim();
        if (allowEmpty && !StringUtils.hasLength(input)) {
            setValue(null);
            return;
        }
        if (VALUE_TRUE.equalsIgnoreCase(input) || VALUE_ON.equalsIgnoreCase(input) ||
                VALUE_1.equalsIgnoreCase(input) || VALUE_YES.equalsIgnoreCase(input)) {
            setValue(Boolean.TRUE);
            return;
        }
        if (VALUE_FALSE.equalsIgnoreCase(input) || VALUE_OFF.equalsIgnoreCase(input) ||
                VALUE_0.equalsIgnoreCase(input) || VALUE_NO.equalsIgnoreCase(input)) {
            setValue(Boolean.FALSE);
            return;
        }
        throw new IllegalArgumentException("Invalid boolean value [" + text + "]");
    }


    @Override
    public String getAsText() {
        if (Boolean.TRUE.equals(getValue())) {
            return VALUE_TRUE;
        }
        if (Boolean.FALSE.equals(getValue())) {
            return VALUE_FALSE;
        }

        return "";
    }
}
