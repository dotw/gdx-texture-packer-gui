package com.crashinvaders.texturepackergui;

import com.crashinvaders.common.Version;

import java.util.Locale;

public class AppConstants {
    public static final Version version = new Version("4.6.0");
    public static final String EXTERNAL_DIR = ".gdxtexturepackergui";
    public static final String MODULES_DIR = EXTERNAL_DIR + "/modules";
    public static final String PROJECT_FILE_EXT = "tpproj";
    public static final String[] IMAGE_FILE_EXT = new String[]{"png", "jpg", "jpeg"};

    public static final String PREF_NAME_COMMON = "common.xml";
    public static final String PREF_NAME_INSTALLED_MODULES = "installed_modules.xml";

    public static final Locale LOCALE_EN = Locale.ENGLISH;
    public static final Locale LOCALE_DE = Locale.GERMAN;
    public static final Locale LOCALE_RU = new Locale("ru", "");
    public static final Locale LOCALE_ZH_TW = new Locale("zh", "tw");
    public static final Locale LOCALE_DEFAULT = LOCALE_EN;
}