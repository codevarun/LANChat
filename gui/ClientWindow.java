package gui;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.JOptionPane;

import peer.Client;

/**
 *
 * @author H2O
 */
public class ClientWindow extends javax.swing.JFrame {

    /** Creates new form ClientWindow */
    public ClientWindow(Client ClientT) {
    	this.client = ClientT;
        initComponents();
      
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        TextInputPanel = new javax.swing.JTextPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        userList = new javax.swing.JList();
        SendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayText = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Client Channel");
        setLocationByPlatform(true);
        a.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// kill the client thread 
				client.interrupt();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}});
        TextInputPanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TextInputPanelKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(TextInputPanel);
        userList.setListData(users);
        userList.setModel(new javax.swing.AbstractListModel() {
            public int getSize() { return users.size(); }
            public Object getElementAt(int i) { return users.get(i); }
        });
        jScrollPane3.setViewportView(userList);

        SendButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        SendButton.setText("Send");
        SendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SendButtonMouseReleased(evt);
            }
        });

        displayText.setEditable(false);
        jScrollPane1.setViewportView(displayText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(SendButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>                        

    private void SendButtonMouseReleased(java.awt.event.MouseEvent evt) {                                         

    	/*
        //Display the msg from input panel to output panel
        DisplayText.setText(DisplayText.getText() + clientName + " says: \n" + TextInputPanel.getText() + "\n");
        TextInputPanel.setText("");
    	*/
    	String text = TextInputPanel.getText();
    	TextInputPanel.setText("");
		client.send(text);
    }                                        

    private void TextInputPanelKeyPressed(java.awt.event.KeyEvent evt) {                                          
         // check for hotkey ALT + S
        if(evt.isControlDown() && (evt.getKeyCode() == KeyEvent.VK_ENTER)){
            TextInputPanel.setText(TextInputPanel.getText()+ "\n");
        }
        else if(evt.getKeyCode() == KeyEvent.VK_ENTER){
        	// Display msg from input panel to output panel
        	evt.consume();
        	String text = TextInputPanel.getText();
        	TextInputPanel.setText("");
    		client.send(text);
        }
        else{}
    }                                         
    
    public void Dialog(String msg){
    	JOptionPane.showMessageDialog(null, msg);
    }
    
    public void addText(String text) {
    	displayText.setText(displayText.getText() + text + "\n");
    }
    
    public void updateUserList(Vector<String> users){
    	this.users = users;
    	userList.setListData(users);
    }

    /**
    * @param args the command line arguments
    */
  

    // Variables declaration - do not modify                    
    private Client client;
    private javax.swing.JTextPane displayText;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextPane TextInputPanel;
    private javax.swing.JList userList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private Vector<String> users = new Vector<String>(1);
    private Window a = new Window(this);
    // End of variables declaration                   


}
