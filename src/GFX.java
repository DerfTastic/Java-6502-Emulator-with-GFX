import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class GFX extends JFrame implements ActionListener {
	GFXPanel p = new GFXPanel();
	Timer t;
	Timer cursorTimer;
	Font lcdFont;
	Scanner s;

	boolean graphicalCursorBlinkFlag = false;

	boolean debug = false;

	//Internal flags
	boolean increment = true;
	boolean cursor = false;
	int cursorX = 0;
	int cursorY = 0;
	boolean cursorBlink = false;
	boolean fourBitMode = false;

	char[] text = new char[0x50];

	public GFX() {
		this.setSize(680,450 + 37); // 37 is height of header I think
		this.setLocation(1100, 300);
		t = new Timer(100,this);
		t.start();
		cursorTimer = new Timer(500,this);
		cursorTimer.start();

		String s = "";
		
		for (int i = 0; i < 0x50; i++) {
			if (i<s.length()) {
				text[i] = s.charAt(i);
			} else {
				text[i] = ' ';
			}
		}
		
		this.setTitle("VGA signal from GFX card");
		this.setContentPane(p);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GFX lcd = new GFX();

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("[RS] [data]");
		
		while (true) {
			String input = scan.nextLine();
			
			boolean rs = input.charAt(0) == '1' ? true : false;
			byte data = Byte.parseByte(input.substring(2,10), 2);
			
			System.out.println(rs ? "Data" : "Instruction" + ": 0x" + ROMLoader.byteToHexString(data));

//			lcd.write(rs, data);
		}
	}
	
	//Read from LCD
	public byte read(boolean regSel) {
		return 0x0;
	}
	
	public class GFXPanel extends JPanel {
		public GFXPanel() {
			try {
				lcdFont = new Font("Courier New Bold", Font.PLAIN, 18);
			    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			    ge.registerFont(lcdFont);
			} catch (Exception e) {}
		}
		
		public void paintComponent(Graphics g) {
			g.setColor(Color.gray);
			g.fillRect(0, 0, p.getWidth(), p.getHeight());
//			g.setColor(new Color(128, 0, 0));
//			for (int i = 0; i<16; i++) {
//				for (int j = 0; j<2; j++) {
//					g.fillRect(12+33*i, 25+50*j, 30, 47);
//				}
//			}
			g.setColor(Color.black);
			g.fillRect(20, 20, 5*128, 5*64);

			g.setColor(Color.white);
			g.setFont(lcdFont);
			g.drawString("0", 20, 17);
			g.drawString("100", 520-30, 17);
			g.drawString("127", 660-30, 17);
			g.drawString("64", 0, 340);
			g.drawString("75", 0, 395);

			byte c;
			for (int y = 0; y < 64; y++) {
				for (int x = 0; x < 128; x++) {
					c = EaterEmulator.ram.array[0x2000 + y * 128 + x];
					g.setColor(new Color((c << 2) & 0b11000000, (c << 4) & 0b11000000, (c << 6) & 0b11000000));
					g.fillRect(x * 5 + 20, y * 5 + 20, 5, 5);
				}
			}

			g.setColor(Color.white);

			g.drawRect(Math.floorMod(cursorX, 128) * 5 + 20, (cursorX >= 128 ? 2*cursorY+1 : 2*cursorY) * 5 + 20, 5, 5);
			g.drawString("Last pixel write at: (" + String.valueOf(Math.floorMod(cursorX, 128)) + ", " +
					String.valueOf(cursorX >= 128 ? 2*cursorY+1 : 2*cursorY) + ")", 10, getHeight()-10);
			g.drawString("Pixel address: 0x" + String.format("%04X", (cursorY*256 + cursorX) + 0x2000), 400, getHeight()-10);
			g.drawRect(19, 19, 5*100, 5*75);

		}
	}

	/* Sets the cursor boolean to true and sets the cursor's coordinates based
	   on the parameters. */
	public void setCursor(int x, int y) {
		cursorX = x;
		cursorY = y;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(t)) {
			p.repaint();
		}
		if (arg0.getSource().equals(cursorTimer)) {
			if (cursor) {
				if (cursorBlink) {
					graphicalCursorBlinkFlag = !graphicalCursorBlinkFlag;
				} else {
					graphicalCursorBlinkFlag = cursor;
				}
			} else {
				graphicalCursorBlinkFlag = false;
			}
		}
	}
}
