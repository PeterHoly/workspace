package desktopApp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Transform;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import com.example.bclib.Car;
import com.example.bclib.MapObject;

public class Render {

	private com.example.bclib.Display myDisplay;

	private String[] imagesCarCrash = { "explosion1", "explosion2",
			"explosion3", "explosion4", "explosion5", "explosion6",
			"explosion7" };
	private String[] imagesCarNitro = { "redfire1", "redfire2", "redfire3",
			"redfire4", "redfire5", "redfire6", "redfire7", "redfire8",
			"redfire9", "redfire10", "redfire11", "redfire12", "redfire13",
			"redfire14", "redfire15", "redfire16", "redfire17", "redfire18",
			"redfire19", "redfire20", "redfire21", "redfire22", "redfire23",
			"redfire24" };
	private String HP[] = { "HP 0", "HP 1", "HP 2", "HP 3", "HP 4", "HP 5",
			"HP 6", "HP 7", "HP 8", "HP 9", "HP 10" };
	private String win[] = { "WINNER", "GAME OVER" };

	private int numberImgCrash;
	private Image mapImg = null;
	private Image nitroBombPressedImg = null;
	private Image nitroBombImg = null;
	private Font myfont = null;
	private Color p1 = null;

	private Color colorBlack2;
	private Color colorSilver;

	private Boolean backBool = false;

	public Render(com.example.bclib.Display d) {
		myDisplay = d;
	}

	public void draw(MapObject mo, PaintEvent e, Shell s, int crashCar,
			String mapName) {

		if (mo instanceof Car) {
			synchronized (mo) {
				synchronized (myDisplay) {

					if (((Car) mo).getHp() <= 0) {

						numberImgCrash = ((Car) mo).getCrashImg(crashCar);

						if (numberImgCrash != -1) {

							Image myImage = new Image(e.display,
									Render.class.getResourceAsStream("/images/"
											+ imagesCarCrash[numberImgCrash]
											+ ".png"));
							e.gc.drawImage(
									myImage,
									0,
									0,
									myImage.getBounds().width,
									myImage.getBounds().height,
									(int) (mo.getRight() - mo.getWidth() / 2 - myImage
											.getBounds().width / 2),
									(int) (myDisplay.conversionY(mo.getBottom()
											+ mo.getHeight() / 2
											+ myImage.getBounds().height / 2)),
									myImage.getBounds().width, myImage
											.getBounds().height);
						}
					} else {

						if (((Car) mo).getnitroActived()) {
							Random rand = new Random();
							int n = rand.nextInt(23);
							Image myImage = new Image(e.display,
									Render.class.getResourceAsStream("/images/"
											+ imagesCarNitro[n] + ".png"));

							Transform t = new Transform(e.display);
							t.translate((float) mo.getX(),
									(float) myDisplay.conversionY(mo.getY()));
							t.rotate((float) -Math.toDegrees(mo.getAngle()));
							t.translate(-(float) mo.getX(),
									-(float) myDisplay.conversionY(mo.getY()));

							e.gc.setTransform(t);

							e.gc.drawImage(
									myImage,
									0,
									0,
									myImage.getBounds().width,
									myImage.getBounds().height,
									(int) mo.getLeft()
											- myImage.getBounds().width,
									(int) Math.round(myDisplay.conversionY(mo
											.getTop() - mo.getHeight() / 2)
											- (myImage.getBounds().height / 2)),
									myImage.getBounds().width,
									myImage.getBounds().height);

							e.gc.setTransform(null);
							t.dispose();
						}

						Image carImg = new Image(e.display,
								Render.class.getResourceAsStream("/images/"
										+ ((Car) mo).getImgCode() + ".png"));

						Transform t = new Transform(e.display);
						t.translate((float) mo.getX(),
								(float) myDisplay.conversionY(mo.getY()));
						t.rotate((float) -Math.toDegrees(mo.getAngle()));
						t.translate(-(float) mo.getX(),
								-(float) myDisplay.conversionY(mo.getY()));

						e.gc.setTransform(t);
						e.gc.drawImage(
								carImg,
								0,
								0,
								carImg.getBounds().width,
								carImg.getBounds().height,
								(int) Math.round(mo.getX() - mo.getWidth() / 2),
								(int) Math.round(myDisplay.conversionY(mo
										.getY() + mo.getHeight() / 2)),
								(int) Math.round(mo.getWidth()),
								(int) Math.round(mo.getHeight()));
						e.gc.setTransform(null);
						t.dispose();
					}
				}
			}
		} else {
			if (this.mapImg == null) {
				mapImg = new Image(e.display, mapName + ".png");
			}

			int srcY = (int) ((myDisplay.getY() + myDisplay.getHeight()) * (mapImg
					.getBounds().width / myDisplay.getWidth()));
			srcY = mapImg.getBounds().height - srcY;

			if (srcY > 0) {

				int srcX = 0;

				int srcWidth = (int) mapImg.getBounds().width;
				int srcHeight = (int) (myDisplay.getHeight() * (mapImg
						.getBounds().width / myDisplay.getWidth()));

				int destX = 0;
				int destY = 0;

				int destWidth = (int) myDisplay.getWidth();
				int destHeight = (int) myDisplay.getHeight();

				e.gc.drawImage(mapImg, srcX, srcY, srcWidth, srcHeight, destX,
						destY, destWidth, destHeight);
			}
		}
	}

	public boolean drawMenuButton(PaintEvent e, Shell s) {

		Font backFont = new Font(e.display, new FontData("Capture it", 12,
				SWT.NORMAL));

		Button backToMenu = new Button(s, SWT.PUSH);
		backToMenu.setSize(128, 30);
		backToMenu.setText("Back to menu");
		backToMenu.setLocation(10, 10);
		backToMenu.setFont(backFont);

		backToMenu.addPaintListener(backgroundButtonPaintL);
		backToMenu.addListener(SWT.Selection, backToMenuL);

		return backBool;
	}

	private Listener backToMenuL = new Listener() {
		public void handleEvent(Event event) {
			backBool = true;
		}
	};

	private PaintListener backgroundButtonPaintL = new PaintListener() {
		@Override
		public void paintControl(PaintEvent e) {
			if (e.widget instanceof Button) {
				Button button = (Button) e.widget;

				colorBlack2 = new org.eclipse.swt.graphics.Color(e.display, 40,
						45, 45);
				colorSilver = new org.eclipse.swt.graphics.Color(e.display,
						178, 184, 182);

				e.gc.setBackground(colorSilver);
				e.gc.fillRoundRectangle(0, 0, button.getBounds().width,
						button.getBounds().height, 0, 0);

				e.gc.setBackground(colorBlack2);
				e.gc.fillRoundRectangle(3, 3, button.getBounds().width - 6,
						button.getBounds().height - 6, 5, 5);

				e.gc.setForeground(colorSilver);

				Point textSize = e.gc.stringExtent(button.getText());
				int x = (button.getBounds().width - textSize.x) / 2;
				int y = (button.getBounds().height - textSize.y) / 2;

				e.gc.drawText(button.getText(), x, y);
			}
		}
	};

	public void drawWin(PaintEvent e, Shell s, int w) {
		Font winFont = new Font(e.display, new FontData("Capture it", 20,
				SWT.NORMAL));

		if (p1 == null) {
			p1 = new Color(e.display, 128, 128, 255);
		}
		e.gc.setFont(winFont);
		e.gc.setForeground(p1);
		if (w == 0) {
			e.gc.drawText(win[w], (int) myDisplay.getWidth() / 3 + 5,
					(int) myDisplay.getHeight() / 3, true);
		} else if (w == 1) {
			e.gc.drawText(win[w], (int) myDisplay.getWidth() / 4 + 5,
					(int) myDisplay.getHeight() / 3, true);
		}
	}

	public void drawImg(PaintEvent e, Shell s, boolean nitroPressed, int hp) {
		if (nitroPressed) {
			if (nitroBombPressedImg == null) {
				nitroBombPressedImg = new Image(
						e.display,
						Render.class
								.getResourceAsStream("/images/nitroPressed.png"));
			}
			e.gc.drawImage(nitroBombPressedImg, 0, 0,
					nitroBombPressedImg.getBounds().width,
					nitroBombPressedImg.getBounds().height,
					(int) myDisplay.getWidth() - 60,
					(int) myDisplay.getHeight() - 110,
					nitroBombPressedImg.getBounds().width / 3,
					nitroBombPressedImg.getBounds().height / 3);
		} else {
			if (nitroBombImg == null) {
				nitroBombImg = new Image(e.display,
						Render.class.getResourceAsStream("/images/nitro.png"));
			}
			e.gc.drawImage(nitroBombImg, 0, 0, nitroBombImg.getBounds().width,
					nitroBombImg.getBounds().height,
					(int) myDisplay.getWidth() - 60,
					(int) myDisplay.getHeight() - 110,
					nitroBombImg.getBounds().width / 3,
					nitroBombImg.getBounds().height / 3);
		}

		if (myfont == null) {
			myfont = new Font(e.display, new FontData("Capture it", 15,
					SWT.NORMAL));
		}

		if (p1 == null) {
			p1 = new Color(e.display, 128, 128, 255);
		}

		e.gc.setFont(myfont);
		e.gc.setForeground(p1);
		e.gc.drawText(HP[hp], (int) myDisplay.getWidth() - 80,
				(int) myDisplay.getHeight() - 400, true);
	}

	public static void createImg(byte[] map, String name) {
		try {
			InputStream in = new ByteArrayInputStream(map);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "png", new File(name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
