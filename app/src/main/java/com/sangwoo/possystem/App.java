package com.sangwoo.possystem;

import com.sangwoo.possystem.ui.db_login.DBLoginView;

import javax.swing.*;

public class App {
    private static AppComponent appComponent;

    public static void main(String... args) {
        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule())
                .build();

        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            new DBLoginView();
        });
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
