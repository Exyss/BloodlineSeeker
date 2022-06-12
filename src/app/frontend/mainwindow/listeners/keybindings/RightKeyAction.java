package app.frontend.mainwindow.listeners.keybindings;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import app.frontend.mainwindow.MainWindowManager;

public class RightKeyAction extends AbstractAction{
    private static final long serialVersionUID = 27L;

    
    /** 
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        MainWindowManager.getWindowImageController().move(+1,0);
    }
}