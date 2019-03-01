/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.calendar;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.sql.Time;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Show;


class Calendar extends JPanel {
    
    private final int START;
    private final List<Show> shows;
    private final Event event;
    private final double eventStart;
    
    private final int H_SPACING = 30;
    
    public Calendar(Event e) {
        shows = DBConnector.getInstance().getShowsAtEvent(e);
        event = e;
        eventStart = timeToDouble(e.getId().getTime());        
        START = (int)(long)eventStart;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Consolas", Font.PLAIN, 20));
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        for(int i = 1; i < 25; i++) {
            g2d.setColor(Color.GRAY);
            String h = String.valueOf((START+i-1)%24);
            if(h.length()==1) h=" "+h;
            g2d.drawString(h, 20, (i*H_SPACING)+5);
            
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawLine(50, i*H_SPACING, 500, i*H_SPACING);
        }
        
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Calendar.class.getResourceAsStream("/files/AvenirNextLTPro-MediumCn.ttf"));
            font = font.deriveFont(20F);
            g2d.setFont(font);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Calendar.class.getName()).log(Level.SEVERE, null, ex);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
        }

        int oldBottomCorner = 0;
        int oldRightCorner = 0;
        for(Show s : shows) {
            double showtime = timeToDouble(s.getShowtime());
            if (showtime<eventStart) showtime+=24;
            double diff = showtime - eventStart + 1;
            double l = diff*H_SPACING;

            g2d.setColor(Color.RED);
//            g2d.drawLine(50, (int)l, 500, (int)l);
            
            String bandName = s.getId().getBand();
            int strWidth = g2d.getFontMetrics().stringWidth(bandName);
            int strHeight = g2d.getFontMetrics().getHeight();
            
            int newTopCorner = (int)l-strHeight+10;
            int newLeftCorner = 55;
            
            if(newTopCorner<oldBottomCorner) newLeftCorner = oldRightCorner+10;
            
            g2d.fillRect(newLeftCorner, newTopCorner, strWidth+10, strHeight);
            
            oldBottomCorner = newTopCorner + strHeight;
            oldRightCorner = newLeftCorner + strWidth+10;
            
            g2d.setColor(Color.BLACK);
            g2d.drawString(bandName, newLeftCorner+5, (int)l+5);
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
    
    private double timeToDouble(Time time) {
        String[] splittedTime = time.toString().split(":");
        return Double.valueOf(splittedTime[0])+Double.valueOf(splittedTime[1])/60;
    }
}

public class CalendarView extends JFrame {

    public CalendarView(Event e) {
        initUI(e);
    }

    private void initUI(Event e) {

        add(new Calendar(e));

        setTitle(e.getName()+ " line-up");
        try {
            setIconImage(ImageIO.read(CalendarView.class.getResourceAsStream("/files/icon.png")));
        } catch (IOException ex) {
            Logger.getLogger(CalendarView.class.getName()).log(Level.SEVERE, null, ex);
        }
        setSize(600, 800);
        setLocationRelativeTo(null);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
