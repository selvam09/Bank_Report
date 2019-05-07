/**
 * Project Name: rabo_bank_inventory
 * Package Name: com.rabobank.util
 * Class Name: StringUtil.java
 * Description:
 *
 *
 * Created Date:May 6, 2019
 * Modified Date:May 6, 2019
 *
 * Copyright to Selvam
 */
package com.rabobank.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

    /**
     * LOGGER
     */
    private static Logger lOGGER = LoggerFactory.getLogger(StringUtil.class);

    /**
     * This method is used to validate the string.Check the string isempty and not null then returns boolean value
     *
     * @param inputStr
     * @return boolean
     *         May 6, 2019
     */
    public static boolean isValidString(String inputStr) {
        boolean isValid = false;
        try {
            if ((inputStr != null) && !inputStr.isEmpty() && !"null".equalsIgnoreCase(inputStr) && !inputStr.equalsIgnoreCase("undefined")) {
                isValid = true;
            }
        } catch (Exception e) {
            StringUtil.lOGGER.error("Exception occured at isValidString {}", e.getMessage());
        }
        return isValid;
    }
}
