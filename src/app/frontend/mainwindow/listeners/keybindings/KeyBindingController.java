package app.frontend.mainwindow.listeners.keybindings;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import app.frontend.mainwindow.MainWindowManager;

public class KeyBindingController {
    public void addKeyBindingController() {
        JComponent component = MainWindowManager.getWindowContentPane();
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        Action upAction = new UpKeyAction();
        Action downAction = new DownKeyAction();
        Action leftAction = new LeftKeyAction();
        Action rightAction = new RightKeyAction();
        Action escAction = new EscKeyAction();
        
        inputMap.put(KeyStroke.getKeyStroke("W"), "up");
        actionMap.put("up", upAction);
        
        inputMap.put(KeyStroke.getKeyStroke("S"), "down");
        actionMap.put("down", downAction);
        
        inputMap.put(KeyStroke.getKeyStroke("A"), "left");
        actionMap.put("left", leftAction);
        
        inputMap.put(KeyStroke.getKeyStroke("D"), "right");
        actionMap.put("right", rightAction);

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "esc");
        actionMap.put("esc", escAction);
    }
}