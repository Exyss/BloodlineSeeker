package app.frontend.mainwindow.listeners.resultcard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import app.backend.BackendManager;
import app.backend.scraper.results.dynasty.Dynasty;
import app.backend.scraper.results.member.Member;
import app.frontend.mainwindow.MainWindowManager;

public class GenerateGraphActionListener implements ActionListener {
    private Dynasty dynasty;
    private Member member;
    
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