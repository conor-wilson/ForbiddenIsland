package uix;

//import java.awt.BorderLayout;
//import java.awt.Color;
//
//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;

//public class ForbiddenFrame extends JFrame {
//
//	private String frameTitle = "Forbidden Island";
//	private Color oceanColor = new Color(29,162,216);
//	private Color logoPanelColor = new Color(6,66,115);
//	private ForbiddenImageIcon icon = new ForbiddenImageIcon("src\\uix\\island.png");
//	private ForbiddenImageIcon logo = new ForbiddenImageIcon ("src\\uix\\logo.png");
//	private int frameWidth = 700;
//	private int frameHeight = 700;
//
//	/*
//	 * Singleton Instance
//	 */
//	private static ForbiddenFrame frameView;
//	public static ForbiddenFrame getInstance() {
//		if (frameView == null) {
//			frameView = new ForbiddenFrame();
//		}
//		return frameView;
//	}
//
//	private void setupLogoPanel() {
//
//		// Shrink the logo to the appropriate size
//		ForbiddenImageIcon newLogo = logo.shrink(6);
//
//		// Create logo label
//		ForbiddenLabel label = new ForbiddenLabel();
//		label.setIcon(newLogo);
//		label.setVerticalAlignment(JLabel.CENTER);
//		label.setHorizontalAlignment(JLabel.CENTER);
//
//		// Create the Panel
//		ForbiddenPanel myPanel = new ForbiddenPanel();
//		myPanel.setBackground(logoPanelColor);
//		myPanel.setBounds(0,0,frameWidth, frameHeight/5);
//		myPanel.setLayout(new BorderLayout());
//		myPanel.add(label);
//		this.add(myPanel);
//	}
//
//	private void setupGameBoardPanel() {
//		ForbiddenPanel gameBoardPanel = new ForbiddenPanel();
//		gameBoardPanel.setBackground(oceanColor);
//		gameBoardPanel.setBounds(0,frameHeight/5,frameWidth,frameHeight*4/5);
//		this.add(gameBoardPanel);
//	}
//
//	/*
//	 * Force singleton instances to exist
//	 * Setup all views
//	 */
//	public void setup() {
//
//		// Setup frame behavior
////		this.setLayout(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setResizable(false);
//
//		// Setup frame appearance
//		this.setTitle(frameTitle);
//		this.setSize(frameWidth,frameHeight);
//		this.setIconImage(icon.getImage());
//		this.getContentPane().setBackground(oceanColor);
//
//		// Add the Panels
//		this.setupLogoPanel();
//		this.setupGameBoardPanel();
////		this.addLogoLabel();
//
//		// Show frame to the world
//		this.setVisible(true);
//	}
//
//	/*
//	 * Constructor
//	 */
//	ForbiddenFrame() {}
//
//}
