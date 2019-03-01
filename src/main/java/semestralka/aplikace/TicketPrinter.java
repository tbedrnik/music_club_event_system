/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka.aplikace;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import net.glxn.qrgen.javase.QRCode;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import semestralka.dbs.DBConnector;
import semestralka.dbs.entities.Club;
import semestralka.dbs.entities.Event;
import semestralka.dbs.entities.Show;
import semestralka.dbs.entities.Ticket;

/**
 *
 * @author tomas
 */
public class TicketPrinter {
    public static void print(Ticket t) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TicketPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        Integer opt = jfc.showSaveDialog(new JFrame());
        if(opt == JFileChooser.APPROVE_OPTION) {
            File requestedFolder = jfc.getSelectedFile();
            jfc.setVisible(false);
            
            try (PDDocument doc = new PDDocument()) {
                
                PDPage page = new PDPage();
                
                doc.addPage(page);
                
                PDFont font = PDType0Font.load(doc, TicketPrinter.class.getResourceAsStream("/files/AvenirNextLTPro-DemiCn.ttf"));
                
                try (PDPageContentStream cnt = new PDPageContentStream(doc, page)) {
                    File qrcode = QRCode.from(t.getId().toString()).withSize(150,150).file();
                    
//                    PDImageXObject pdImage = LosslessFactory.createFromImage(doc, ImageIO.read(TicketPrinter.class.getResourceAsStream("/files/icon.png")));
                    PDImageXObject pdImage = PDImageXObject.createFromFileByContent(qrcode, doc);

                    cnt.drawImage(pdImage, 80, 600);
                    
                    cnt.beginText();
                    
                    cnt.newLineAtOffset(230, 700);
                    cnt.setFont(font, 24);
                    cnt.showText("Ticket "+t.getId().toString());
                    
                    cnt.newLineAtOffset(0, -40);
                    cnt.setFont(font, 36);
                    
                    Event e = DBConnector.getInstance().getEventByKey(t.getEventKey());
                    
                    cnt.showText(e.getName());
                    
                    cnt.newLineAtOffset(0, -28);
                    cnt.setFont(font, 24);
                    cnt.showText(t.getEventKey().getPrintableDateTime());
                    
                    Club c = DBConnector.getInstance().getClubByName(t.getEventKey().getClub());
                    
                    cnt.newLineAtOffset(-130, -60);
                    cnt.showText(c.getName());
                    
                    cnt.newLineAtOffset(0, -20);
                    cnt.setFont(font, 16);
                    cnt.showText(c.getStreet() + ", " + c.getCity() + " " + c.getPostalcode().toString());
                    
                    cnt.newLineAtOffset(0, -40);
                    cnt.setFont(font, 28);
                    cnt.showText(t.getPrice().toString()+"CZK");
                    
                    List<Show> shows = DBConnector.getInstance().getShowsAtEvent(e);
                    
                    if(shows.size()>0) {
                        cnt.newLineAtOffset(0, -40);
                        cnt.setFont(font, 28);
                        cnt.setNonStrokingColor(127, 127, 127);
                        cnt.showText("Line up:");

                        cnt.setFont(font, 20);

                        for(Show s : DBConnector.getInstance().getShowsAtEvent(e)) {
                            cnt.newLineAtOffset(0, -24);
                            cnt.showText(s.getPrintableShowtime()+" "+s.getId().getBand());
        }
    }
                    

                    cnt.endText();
                    
}
                
                doc.save(new File(requestedFolder, "vstupenka_"+t.getId().toString()+".pdf"));
                
            } catch (IOException ex) {
                Logger.getLogger(TicketPrinter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private TicketPrinter() {
    }
}
