/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.customcomponents.customNotifications;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import raven.shadow.ShadowRenderer;

public class NotificationPanel extends javax.swing.JComponent {

    private JDialog dialog;
    private JPanel showPanel;
    private Animator animator;
    private Frame frame;
    private boolean showing;
    private Thread thread;
    private int animate = 10;
    
    private BufferedImage imageShadow;
    private int shadowSize = 6;
    private Type type;
    private Location location;
   

    public NotificationPanel(Frame frame, Type type, Location location, String message) {
        this.frame = frame;
        this.type = type;
        this.location = location;
        initComponents();
        init(message);
        initAnimator();
    }
    
    private void init(String message)
    {
        setBackground(Color.WHITE);
        dialog = new JDialog(frame);
        dialog.setUndecorated(true);
        dialog.setFocusableWindowState(false);
        dialog.setBackground(new Color(0,0,0,0));
        dialog.add(this);
        
        dialog.setSize(getPreferredSize());
        if(type == Type.SUCCESS)
        {
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Notifications/sucess.png")));
            messageLabel.setText("Success");
            
        } else if(type == Type.INFO){
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Notifications/info.png")));
            messageLabel.setText("Info");
            
        } else if(type == Type.WARNING){
            
            iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Notifications/warning.png")));
            messageLabel.setText("Warning");
        }
        
        messageTextLabel.setText(message);
        dialog.setVisible(true);
    }

    private void initAnimator()
    {
        TimingTarget target = new TimingTargetAdapter()
        {
            private int x;
            private int top;
            private boolean topToBot;
            
            
            @Override
            public void timingEvent(float fraction) 
            {
                if(showing)
                {
                    float alpha = 1f - fraction;
                    int y = (int)((1f - fraction) * animate);
                    if(topToBot)
                    {
                        dialog.setLocation(x, top + y);
                    }
                    else
                    {
                        dialog.setLocation(x, top - y);
                    }
                    dialog.setOpacity(alpha);
                }
                else
                {
                    float alpha = fraction;
                    int y = (int)(fraction * animate);
                    if(topToBot)
                    {
                        dialog.setLocation(x, top + y);
                    }
                    else
                    {
                        dialog.setLocation(x, top - y);
                    }
                    dialog.setOpacity(alpha);
                }
                repaint();
            }
            
            @Override
            public void end() 
            {
                showing = !showing;
                if(showing)
                {
                    thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            sleep();
                            closeNotification();
                        }
                        
                    });
                    thread.start();
                }
                else
                {
                    dialog.dispose();
                }
            }

            @Override
            public void begin()
            {
                System.out.println("its " + showing);
                if(!showing)
                {
                    dialog.setOpacity(0f);
                    int margin = 10;
                    int y = 0;
                    if(location == Location.TOP_CENTER)
                    {
                        x = 800;
                        y = frame.getY();
                        topToBot = true;
                    }
                    
                    top = y;
                    //dialog.setLocation(x, y);
                    dialog.setBounds(0, 0, 400, 70);
                    dialog.setVisible(true);
                }
                
                
            }

            
            
        };
        animator = new Animator(500, target);
        animator.setResolution(5);
                
    }
    
    public void showNotification()
    {
        animator.start();
    }
    
    private void closeNotification()
    {
        if(thread != null && thread.isAlive())
        {
            thread.interrupt();
        }
        if(animator.isRunning())
        {
            if(!showing)
            {
                animator.stop();
                showing = true;
                animator.start();
            }
            
        }
        else
        {
            showing = true;
            animator.start();
        }
    }
    
    private void sleep()
    {
        try {
            Thread.sleep(3000);
            System.out.println("done sleepins");
        } catch (Exception e) {
        }
    }
    
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.drawImage(imageShadow, 0, 0, null);
        int x = shadowSize;
        int y = shadowSize;
        int width = getWidth() - shadowSize * 2;
        int height = getHeight() - shadowSize * 2;
        g2.fillRect(x, y, width, height);
        
       
        switch(type)
        {
            
            case SUCCESS:
                g2.setColor(new Color(18,163,24));
                break;
                
            case INFO:
                g2.setColor(new Color(28,139,206));
                break;
                
            case WARNING:
                g2.setColor(new Color(241,195,15));
                break;
                
            default:
                g2.setColor(new Color(0,0,0));
                break;        
        }
        
        
        g2.fillRect(5, 6, 5, getHeight() - shadowSize * 2);
        g2.dispose();
        super.paint(g); 
    }
    
    

    @Override
    public void setBounds(int i1, int i2, int i3, int i4)
    {
        super.setBounds(i1, i2, i3 ,i4); 
        //createImageShadow();
    }

    /*private void createImageShadow()
    {
        System.out.println("Width " + this.getWidth() + " height " + this.getHeight());
        imageShadow = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(createShadow(), 0, 0, null);
        g2.dispose();
        
    }
    

    private BufferedImage createShadow()
    {
        BufferedImage image = new BufferedImage(getWidth() - shadowSize * 2, getHeight() - shadowSize * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.fillRect(0, 0, image.getWidth(), image.getHeight());
        g2.dispose();
        
        return new ShadowRenderer(shadowSize, 0.3f, new Color(100, 100, 100)).createShadow(image);
    }*/
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        iconLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        messageTextLabel = new javax.swing.JLabel();
        messageLabel = new javax.swing.JLabel();
        closeNotificationbtn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(null);

        iconLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Notifications/sucess.png"))); // NOI18N
        add(iconLabel);
        iconLabel.setBounds(20, 20, 30, 30);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(null);

        messageTextLabel.setForeground(new java.awt.Color(127, 127, 127));
        messageTextLabel.setText("Message Text");
        jPanel1.add(messageTextLabel);
        messageTextLabel.setBounds(0, 25, 110, 20);

        messageLabel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        messageLabel.setForeground(new java.awt.Color(38, 38, 38));
        messageLabel.setText("Message");
        jPanel1.add(messageLabel);
        messageLabel.setBounds(0, 0, 110, 30);

        add(jPanel1);
        jPanel1.setBounds(65, 10, 270, 50);

        closeNotificationbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Data/Images/Notifications/close.png"))); // NOI18N
        closeNotificationbtn.setBorder(null);
        closeNotificationbtn.setBorderPainted(false);
        closeNotificationbtn.setContentAreaFilled(false);
        closeNotificationbtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeNotificationbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeNotificationbtnActionPerformed(evt);
            }
        });
        add(closeNotificationbtn);
        closeNotificationbtn.setBounds(340, 15, 50, 40);
    }// </editor-fold>//GEN-END:initComponents

    private void closeNotificationbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeNotificationbtnActionPerformed
        closeNotification();
    }//GEN-LAST:event_closeNotificationbtnActionPerformed

    public static enum Type
    {
        SUCCESS,
        INFO,
        WARNING
    }
    
    public static enum Location
    {
        TOP_CENTER,
        TOP_RIGHT,
        TOP_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT,
        BOTTOM_LEFT,
        CENTER
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeNotificationbtn;
    private javax.swing.JLabel iconLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel messageLabel;
    private javax.swing.JLabel messageTextLabel;
    // End of variables declaration//GEN-END:variables
}
