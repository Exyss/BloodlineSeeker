package app.frontend.mainwindow.listeners.imageholder;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import app.frontend.mainwindow.MainWindowManager;

/** 
 * This ImageHolderMouseWheelListener implements MouseWheelListener in order to manage mouse wheel rotation. When it occurs reduces or increments the zoom of the graph image.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class ImageHolderMouseWheelListener implements MouseWheelListener {
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent wheel) {
        MainWindowManager.getWindowImageController().zoom(-wheel.getWheelRotation());
    }
}