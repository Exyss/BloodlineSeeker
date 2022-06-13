package app.frontend.mainwindow.listeners.imageholder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import app.backend.BackendManager;
import app.frontend.mainwindow.MainWindowManager;

/** 
 * This ImageHolderFocusMouseListener extends MouseAdapter in order to manage mouse clicking on the ImageHolder, when it occurs, the image holder gets the focus.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class ImageHolderFocusMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        BackendManager.printDebug("Image holder get focus");

        MainWindowManager.getWindowImageHolder().getLabel().requestFocus();        
    }
}