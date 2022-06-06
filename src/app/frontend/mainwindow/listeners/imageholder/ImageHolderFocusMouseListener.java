package app.frontend.mainwindow.listeners.imageholder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

public class ImageHolderFocusMouseListener extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent e) {
        BackendManager.printDebug("Image holder get focus");

        MainWindowManager.getWindowImageHolder().getLabel().requestFocus();        
    }
}