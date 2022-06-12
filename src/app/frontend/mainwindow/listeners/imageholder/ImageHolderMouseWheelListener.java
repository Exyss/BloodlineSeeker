package app.frontend.mainwindow.listeners.imageholder;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import app.frontend.mainwindow.MainWindowManager;

public class ImageHolderMouseWheelListener implements MouseWheelListener {
    
    /** 
     * @param wheel
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent wheel) {
        MainWindowManager.getWindowImageController().zoom(-wheel.getWheelRotation());
    }
}