package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

public class EscKeyAction extends AbstractAction{
    private static final long serialVersionUID = 25L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowSearchBar().removeFocus();
    }
}