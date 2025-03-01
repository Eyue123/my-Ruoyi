package com.ruoyi.justForTest;
import org.apache.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Sysconfig {


    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(Sysconfig.class);

    private static final String BUNDLE_NAME = "sysconfig"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private Sysconfig() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return "0";
        }
    }
    public static void main(String[] av){
// logger.debug(getString("the_max_campaign"));
    }
}
