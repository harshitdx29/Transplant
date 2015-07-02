
/*Computer Graphics Project
 * 
 * Created by:
 * Akhil Tripathi  2011C6PS739P
 * Harshit Gupta   2011C6PS837P
 * Snehil Shwetabh 2011C6PS502P
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


 class Home extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private BufferedImage convetIcon(Icon icon)
	{
		BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TRANSLUCENT);
            Graphics g = bi.createGraphics();
            // paint the Icon to the BufferedImage.
            icon.paintIcon(null, g, 0,0);
            
           
            g.dispose();
            
		return bi;
		
	}
	private  BufferedImage Rotate(BufferedImage originalImage,Double angle) {
		BufferedImage newImage = null;
		AffineTransform tx = new AffineTransform();

		// last, width = height and height = width :)
		tx.translate(originalImage.getHeight()/2 ,originalImage.getWidth()/2);

		tx.rotate(angle);
		
		// first - center image at the origin so rotate works OK
		tx.translate(-originalImage.getWidth()/2 ,-originalImage.getHeight() /2);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// new destination image where height = width and width = height.
		newImage =new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), originalImage.getType());
		op.filter(originalImage, newImage);
		return newImage;
		}

	private  BufferedImage Reflect(BufferedImage originalImage) {
		BufferedImage newImage = null;
		
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-originalImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		// new destination image where height = width and width = height.
		newImage =new BufferedImage(originalImage.getHeight(), originalImage.getWidth(), originalImage.getType());
		op.filter(originalImage, newImage);
		return newImage;
		}
	
	private  BufferedImage Scale(BufferedImage originalImage,Double amount) {
		BufferedImage newImage = null;
		
	    if(originalImage != null)
	    {
	    	if(amount>1 && amount*originalImage.getWidth()<500 && amount*originalImage.getWidth()!=0) 
	        {
	    		newImage = new BufferedImage(amount.intValue()*originalImage.getWidth(), amount.intValue()*originalImage.getHeight(), originalImage.getType());
	    		}
	    	else
	    	{
	    		newImage = new BufferedImage(originalImage.getWidth(),originalImage.getHeight(), originalImage.getType());
	    	}
	        Graphics2D g = newImage.createGraphics();
	        AffineTransform at =AffineTransform.getScaleInstance(amount,amount);
			
	        g.drawRenderedImage(originalImage, at);
	    }
	    return newImage;
		}
	/**
	 * Create the frame.
	 */
	int click=0,x,y;
	private JTextField ra;
	private JTextField sf;
	public Home() {
		setTitle("Transplant Graphics");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		//setResizable(false);
		
		JLayeredPane layeredPane = new JLayeredPane();
		contentPane.add(layeredPane, BorderLayout.CENTER);
		
		final JLabel label = new JLabel("");
		label.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
		});
		layeredPane.setLayer(label, 2);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				click=e.getButton();
				x=e.getX();
				y=e.getY();
				
			}
				
		});
		
		
		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(click==MouseEvent.BUTTON1){
					Point curr=MouseInfo.getPointerInfo().getLocation();
					if((curr.x>getX()) && (curr.x<(getX()+getWidth())) && (curr.y>getY()) && (curr.y<(getX()+getHeight())) )
						label.setLocation(curr.x-getX()-90,curr.y-getY()-90);
					
					validate();
					}
				
				
			}
		});
		label.setIcon(new ImageIcon("Hip.png"));
		label.setBounds(10, 11, 150, 150);
		layeredPane.add(label);
		
		final JLabel bckg = new JLabel("");
		bckg.setIcon(new ImageIcon("back.jpg"));
		bckg.setBounds(0, 0, 500, 500);
		layeredPane.add(bckg);
		
		JLabel label_2 = new JLabel("Rotate By:");
		layeredPane.setLayer(label_2, 1);
		label_2.setForeground(new Color(204, 255, 102));
		label_2.setBounds(10, 421, 66, 20);
		layeredPane.add(label_2);
		
		ra = new JTextField();
		ra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					Double angle=0.0;
					try{
						angle=Math.toRadians(Double.parseDouble(ra.getText()));
					}
					catch(Exception se){
						JOptionPane.showMessageDialog(null, "Invalid degrees!");
					}
					ImageIcon rot=new ImageIcon(Rotate(convetIcon(label.getIcon()),angle));
					label.setSize(rot.getIconWidth(),rot.getIconHeight());
					label.setIcon(rot);
					validate();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					bckg.requestFocusInWindow();
				}
			}
		});
		
		layeredPane.setLayer(ra, 1);
		ra.setBounds(10, 440, 86, 20);
		layeredPane.add(ra);
		ra.setColumns(10);
		
		JLabel lblScaleFactor = new JLabel("Scale Factor:");
		layeredPane.setLayer(lblScaleFactor, 1);
		lblScaleFactor.setForeground(new Color(204, 255, 102));
		lblScaleFactor.setBounds(10, 381, 86, 20);
		layeredPane.add(lblScaleFactor);
		
		sf = new JTextField();
		layeredPane.setLayer(sf, 1);
		sf.setColumns(10);
		sf.setBounds(10, 400, 86, 20);
		layeredPane.add(sf);
		
		JButton btnMirror = new JButton("Mirror");
		btnMirror.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon scal=new ImageIcon(Reflect(convetIcon(label.getIcon())));
				label.setSize(scal.getIconWidth(),scal.getIconHeight());
				label.setIcon(scal);
				validate();
			}
		});
		layeredPane.setLayer(btnMirror, 1);
		btnMirror.setBounds(399, 11, 89, 23);
		layeredPane.add(btnMirror);
		
		sf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					Double amount=0.0;
					try{
						amount=Double.parseDouble(sf.getText());
					}
					catch(Exception se){
						JOptionPane.showMessageDialog(null, "Invalid Factor!");
					}
					ImageIcon scal=new ImageIcon(Scale(convetIcon(label.getIcon()),amount));
					label.setSize(scal.getIconWidth(),scal.getIconHeight());
					label.setIcon(scal);
					validate();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					bckg.requestFocusInWindow();
				}
			}
		});
	}
}
