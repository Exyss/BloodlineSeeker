package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This DownKeyAction extends AbstractAction in order to manage the down-button pressing. When it occurs the image shifts down.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class DownKeyAction extends AbstractAction {
    private static final long serialVersionUID = 24L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(0, +1);                    
    }
}