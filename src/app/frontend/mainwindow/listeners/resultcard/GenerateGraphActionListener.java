package app.frontend.mainwindow.listeners.resultcard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.mainwindow.MainWindowManager;
/** 
 * This GenerateGraphActionListener implements ActionListener in order to manage the updating graph image.
 * @author Alessio Bandiera
 * @author Andrea Ladogana
 * @author Matteo Benvenuti
 * @author Simone Bianco
 * @version 1.0
 */
public class GenerateGraphActionListener implements ActionListener {

    /**
     * A Dynasty object.
     */
    private Dynasty dynasty;

    /**
     * A Member object.
     */
    private Member member;
    
    /**
     *Initialized the dynasty and the member using the parameters.
     *@param dynasty the new value of the dynasty object.
     *@param member the new value of the member object.
     */
    public GenerateGraphActionListener(Dynasty dynasty, Member member) {
        this.dynasty = dynasty;
        this.member = member;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BackendManager.printDebug("Generate graph button pressed");

        String newTitle = MainWindowManager.buildNewTitle(this.member, this.dynasty);

        MainWindowManager.updateWindowTitle(newTitle);
        MainWindowManager.changeGraph(dynasty, member);
    }
}