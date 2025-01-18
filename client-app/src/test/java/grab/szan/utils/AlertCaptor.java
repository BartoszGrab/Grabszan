package grab.szan.utils;

/**
 * Helper class for capturing alert messages in tests.
 */
public class AlertCaptor {
    private static String lastAlertTitle;
    private static String lastAlertContent;

    public static void captureAlert(String title, String content) {
        lastAlertTitle = title;
        lastAlertContent = content;
    }

    public static String getLastAlertTitle() {
        return lastAlertTitle;
    }

    public static String getLastAlertContent() {
        return lastAlertContent;
    }

    public static void reset() {
        lastAlertTitle = null;
        lastAlertContent = null;
    }
}
