package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

public class UpKeyAction extends AbstractAction{
    private static final long serialVersionUID = 28L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(0,-1);
    }
}