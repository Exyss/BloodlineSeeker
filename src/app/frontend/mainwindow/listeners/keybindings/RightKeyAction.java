package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This RightKeyAction extends AbstractAction in order to manage the right-button pressing. When it occurs the image shifts to right.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class RightKeyAction extends AbstractAction{
    private static final long serialVersionUID = 27L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(+1,0);
    }
}