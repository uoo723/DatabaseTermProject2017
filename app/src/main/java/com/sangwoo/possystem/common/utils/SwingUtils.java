package com.sangwoo.possystem.common.utils;

import javax.swing.*;
import java.awt.*;

public final class SwingUtils {
    public static void ceneterWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
