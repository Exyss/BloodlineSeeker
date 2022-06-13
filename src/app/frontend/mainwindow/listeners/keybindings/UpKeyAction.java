package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This UpKeyAction extends AbstractAction in order to manage the up-button pressing. When it occurs the image shifts up.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class UpKeyAction extends AbstractAction{
    private static final long serialVersionUID = 28L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(0,-1);
    }
}