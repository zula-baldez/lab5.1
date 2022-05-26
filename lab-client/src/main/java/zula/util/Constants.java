package zula.util;

import zula.common.data.DragonType;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public final class Constants {
    public static final Color MAIN_COLOR = new Color(0x412C84);
    public static final Color SUB_COLOR = new Color(0xFFFFFF);
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final Font MAIN_FONT = new Font("Tahoma", Font.PLAIN, SCREEN_HEIGHT / 27); //при 1080 будет 40 шрифт
    public static final Font SUB_FONT = new Font("Tahoma", Font.PLAIN, SCREEN_HEIGHT / 54); //в 2 раза поменьше
    public static final String[] LANGUAGES = {"Русский", "Македонски", "Latviski", "letón"};
    public static final ResourceBundle RU_BUNDLE = ResourceBundle.getBundle("zula.resources.Resource", new Locale("ru", "RU"));
    public static final ResourceBundle LVA_BUNDLE = ResourceBundle.getBundle("zula.resources.Resource", new Locale("lva", "LVA"));
    public static final ResourceBundle MKD_BUNDLE = ResourceBundle.getBundle("zula.resources.Resource", new Locale("mkd", "MKD"));
    public static final ResourceBundle ESP_BUNDLE = ResourceBundle.getBundle("zula.resources.Resource", new Locale("esp", "ESP"));
    public static final HashMap<String, ResourceBundle> LANGUAGES_AND_BUNDLES = new HashMap<>();
    public static final String[] TYPES = Arrays.stream(DragonType.values()).map(Enum::toString).toArray(String[]::new);
    public static final String[] COLORS = {"BLACK", "BLUE", "BROWN", "WHITE", "YELLOW", "NULL"};

    static {
        LANGUAGES_AND_BUNDLES.put("Русский", RU_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("Latviski", LVA_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("Македонски", MKD_BUNDLE);
        LANGUAGES_AND_BUNDLES.put("letón", ESP_BUNDLE);
    }

    private Constants() {
        throw new Error();
    }


    public static ResourceBundle getBundleFromLanguageName(String s) {
        return LANGUAGES_AND_BUNDLES.get(s);
    }

    public static String getNameByBundle(ResourceBundle resourceBundle) {
        for (String key : LANGUAGES_AND_BUNDLES.keySet()) {
            if (LANGUAGES_AND_BUNDLES.get(key) == resourceBundle) {
                return key;
            }
        }
        return null;
    }


}
