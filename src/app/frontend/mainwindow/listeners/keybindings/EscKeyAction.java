package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This EscKeyAction extends AbstractAction in order to manage the esc-button pressing. When it occurs the focus is removed from the searchbar.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class EscKeyAction extends AbstractAction{
    private static final long serialVersionUID = 25L;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowSearchBar().removeFocus();
    }
}