/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import topicmanager.TopicManager;
import topicmanager.TopicManagerImpl;

/**
 *
 * @author juanluis
 */
public class The_system {
    
    public static int numberOfClients = 2;
    
    public static void main(String[] args){
        final TopicManager topicManager = new TopicManagerImpl();
        
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        SwingClient client = new SwingClient(topicManager);
                        client.createAndShowGUI();
                }
        });
        javax.swing.SwingUtilities.invokeLater( new Runnable() {
                @Override
                public void run() {
                        SwingClient client = new SwingClient(topicManager);
                        client.createAndShowGUI();
                }
        });
    }
}
