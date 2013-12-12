/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package identer.listener;

import indenter.model.Indenter;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rachidcalazans
 */
public class BoardListener extends Thread implements ClipboardOwner {  
  Clipboard clipBoard = Toolkit.getDefaultToolkit().getSystemClipboard();  
  String options;
  String content;

  public BoardListener(String options) {
      this.options = options;
  }    
  
  @Override
  public void run() {  
    Transferable trans = clipBoard.getContents(this);  
    setContent(trans);  
    System.out.println("Listener iniciado");  
    while(true) {}  
  }  
   
  @Override
  public void lostOwnership(Clipboard c, Transferable t) {  
    System.out.println("lostOwnership");
    Transferable contents = clipBoard.getContents(this); //EXCEPTION  
    getContent(contents);  
    Indenter indenter = new Indenter(options);
    content = indenter.indent(content);    
    setContent(contents);  
  }  
   
  private void getContent(Transferable t) {  
      System.out.println("getContent");
      try {
          String data = (String) clipBoard.getData(DataFlavor.stringFlavor);  
          content = data;
      } catch (UnsupportedFlavorException ex) {
          Logger.getLogger(BoardListener.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(BoardListener.class.getName()).log(Level.SEVERE, null, ex);
      }
  }  
   
  private void setContent(Transferable t) {
    System.out.println("setContent"); 
    if (content == null) {                
        clipBoard.setContents(t, this);          
    } else {        
        StringSelection selection = new StringSelection(content);  
        clipBoard.setContents(selection, this);                  
    }
  }  
   
  public void setOptions(String options){
      this.options = options;
  }
  
//  public static void main(String[] args) {  
//    BoardListener b = new BoardListener("<(?::[\\w_]++|[\\w_]++:)( *)(.+)>");  
//    b.start();  
//  }  
} 