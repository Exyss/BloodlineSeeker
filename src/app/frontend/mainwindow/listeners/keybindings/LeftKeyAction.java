package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This LeftKeyAction extends AbstractAction in order to manage the left-button pressing. When it occurs the image shifts to left.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class LeftKeyAction extends AbstractAction{
    private static final long serialVersionUID = 26L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(-1,0);
    }
}