package com.sangwoo.possystem.ui.main.menu;

import com.sangwoo.possystem.App;
import com.sangwoo.possystem.common.widgets.Toast;
import com.sangwoo.possystem.domain.model.*;
import com.sangwoo.possystem.domain.model.Menu;
import com.sangwoo.possystem.ui.BasePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MenuView extends BasePanel implements MenuViewContract.View {

    private static final Logger logger = LogManager.getLogger();

    private JLabel titleLabel;
    private JPanel contentPanel;
    private List<JButton> menuButtons;

    @Inject
    MenuViewContract.Presenter presenter;

    @Override
    public void initView() {
        presenter.bindView(this);

        setLayout(new BorderLayout());

        titleLabel = new JLabel("메뉴");
        contentPanel = new JPanel();

        Border padding = BorderFactory.createEmptyBorder(1, 1, 1, 1);
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(lineBorder, padding));
        contentPanel.setLayout(new GridBagLayout());

        initContent();

        add(titleLabel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        presenter.loadMenu();
    }

    @Override
    public void inject() {
        DaggerMenuViewComponent.builder()
                .appComponent(App.getAppComponent())
                .menuViewPresenterModule(new MenuViewPresenterModule())
                .build()
                .inject(this);
    }

    @Override
    public void setTitle(String title) { /* Do nothing */ }

    @Override
    public void succeedLoad(List<Menu> menus) {
        final AtomicInteger i = new AtomicInteger(0);

        menus.forEach(menu -> {
            if (i.get() < menuButtons.size())
                menuButtons.get(i.getAndIncrement()).setText(menu.getName());
        });

        initButtonListener(menus);
    }

    @Override
    public void failedLoad() {
        Toast.makeToast(getParentJFrame(), "  메뉴 로딩 실패  ");
    }

    private void initContent() {
        menuButtons = new ArrayList<>(20);
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.NORTH;
        IntStream.range(0, 20).forEach(i -> {
            if (i == 10) {
                c.gridx++;
                c.gridy = 0;
            }

            JButton button = new JButton();
            button.setPreferredSize(new Dimension(50, 30));
            menuButtons.add(button);
            contentPanel.add(button, c);
            c.gridy++;
        });
    }

    private void initButtonListener(List<Menu> menuList) {
        int min = Math.max(menuList.size(), menuButtons.size());

//        IntStream.range(0, min).forEach(i ->
//                menuButtons.get(i).addActionListener(e -> presenter.clickMenu(i)));
    }
}
