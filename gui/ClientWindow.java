package gui;

import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 *
 * @author H2O
 */
public class ClientWindow extends javax.swing.JFrame {

    /** Creates new form ClientWindow */
    public ClientWindow(Thread ClientT) {
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
        UserLisT = new javax.swing.JList();
        SendButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        DisplayText = new javax.swing.JTextPane();

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
        UserLisT.setListData(users);
        UserLisT.setModel(new javax.swing.AbstractListModel() {
            public int getSize() { return users.size(); }
            public Object getElementAt(int i) { return users.get(i); }
        });
        jScrollPane3.setViewportView(UserLisT);

        SendButton.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        SendButton.setText("Send");
        SendButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                SendButtonMouseReleased(evt);
            }
        });

        DisplayText.setEditable(false);
        jScrollPane1.setViewportView(DisplayText);

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

        //Display the msg from input panel to output panel
        DisplayText.setText(DisplayText.getText() + clientName + " says: \n" + TextInputPanel.getText() + "\n");
        TextInputPanel.setText("");
    }                                        

    private void TextInputPanelKeyPressed(java.awt.event.KeyEvent evt) {                                          
         // check for hotkey ALT + S
        if(evt.isAltDown() && (evt.getKeyCode() == KeyEvent.VK_S)){
        // Display msg from input panel to output panel
        DisplayText.setText(DisplayText.getText() + clientName + " says: \n" + TextInputPanel.getText() + "\n");
        TextInputPanel.setText("");
        }
    }                                         
    
    
    public void updateUserList(Vector<String> users){
    	this.users = users;
    	UserLisT.setListData(users);
    }

    /**
    * @param args the command line arguments
    */
  

    // Variables declaration - do not modify                    
    private Thread client;
    private String clientName;
    private javax.swing.JTextPane DisplayText;
    private javax.swing.JButton SendButton;
    private javax.swing.JTextPane TextInputPanel;
    private javax.swing.JList UserLisT;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private Vector<String> users = new Vector<String>(1);
    private Window a = new Window(this);
    // End of variables declaration                   


}
