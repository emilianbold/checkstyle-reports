/** This file is part of Checkstyle Reports.

    Checkstyle Reports is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License.

    Checkstyle Reports is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * Main.java
 *
 * Created on October 9, 2007, 12:44 AM
 *
 */

package ro.emilianbold.reports.checkstyle;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Launcher.
 * @author Emilian Bold
 */
public final class Main {
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f=new JFrame("Checkstyle charts");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.getContentPane().add(new General());
                f.pack();
                f.setVisible(true);         
            }
        });
    }
    
}
