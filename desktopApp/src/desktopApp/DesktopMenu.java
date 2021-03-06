package desktopApp;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

import com.example.bclib.Client;
import com.example.bclib.Game;
import com.example.bclib.components.Absorbers;
import com.example.bclib.components.Bodywork;
import com.example.bclib.components.Engine;
import com.example.bclib.components.Exhaust;
import com.example.bclib.components.Filter;
import com.example.bclib.components.Glass;
import com.example.bclib.components.Nitro;
import com.example.bclib.components.Wheel;

public class DesktopMenu {

	private String[] textKomponents = { "increase the speed of the car",
			"increase control car", "increase acceleration car",
			"instant acceleration car \n \t  - push space" };

	private boolean saveAllCarComponents = false;

	private int engineComponent;
	private int exhaustComponent;
	private int wheelComponent;
	private int absorbersComponent;
	private int filterComponent;
	private int nitroComponent;
	private int bodyworkComponent;
	private int glassComponent;

	private Button newGame;
	private Button joinGame;
	private Button buildCar;
	private Button play;
	private Button backNewGame;
	private Button backJoinGame;
	private Button backBuildCar;
	private Button connect;
	private Button save;
	private Button performance;
	private Button appearance;

	private List createdGames;
	private List createdMaps;

	private Spinner countPlayers;
	private Label writeCountPlayers;
	private Label chooseOneMap;
	private Label chooseGame;
	private Label textKomp;

	private Label engine;
	private Label exhaust;
	private Label filter;
	private Label absorber;
	private Label wheel;
	private Label nitro;
	private Label loadingText;
	private Label gameName;

	private Combo engineC;
	private Combo exhaustC;
	private Combo filterC;
	private Combo absorberC;
	private Combo wheelC;
	private Combo nitroC;

	private Label bodywork;
	private Label glass;

	private Combo bodyworkC;
	private Combo glassC;

	private ProgressBar loading;

	private Composite mainMenuComposite;
	private Composite newGameComposite;
	private Composite joinGameComposite;
	private Composite buildCarComposite;
	private Composite performanceComposite;
	private Composite appearanceComposite;
	private Composite loadingComposite;

	private Label carLabel;

	public DesktopMenu(final Display display, Shell shell, final Client client,
			final com.example.bclib.Display myDisplay, final Game game,
			final SurfacePanel sp) {

		mainMenuComposite = new Composite(shell, SWT.NULL);
		mainMenuComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/menuWallpaper.png")));
		mainMenuComposite.setSize(320, 410);
		mainMenuComposite.setBackgroundMode(SWT.INHERIT_FORCE);

		newGameComposite = new Composite(shell, SWT.NULL);
		newGameComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/menuWallpaper.png")));
		newGameComposite.setSize(320, 410);
		newGameComposite.setBackgroundMode(SWT.INHERIT_FORCE);

		joinGameComposite = new Composite(shell, SWT.NULL);
		joinGameComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/menuWallpaper.png")));
		joinGameComposite.setSize(320, 410);
		joinGameComposite.setBackgroundMode(SWT.INHERIT_FORCE);

		buildCarComposite = new Composite(shell, SWT.NULL);
		buildCarComposite.setSize(320, 410);
		buildCarComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/buildWallpaper.png")));
		buildCarComposite.setBackgroundMode(SWT.INHERIT_FORCE);

		performanceComposite = new Composite(buildCarComposite, SWT.NULL);
		performanceComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/buildWallpaper.png")));
		performanceComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		performanceComposite.setSize(320, 410);

		appearanceComposite = new Composite(buildCarComposite, SWT.NULL);
		appearanceComposite
				.setBackgroundImage(new Image(
						display,
						Render.class
								.getResourceAsStream("/images/buildWallpaperWithCarPlace.png")));
		appearanceComposite.setBackgroundMode(SWT.INHERIT_FORCE);
		appearanceComposite.setSize(320, 410);

		loadingComposite = new Composite(shell, SWT.NULL);
		loadingComposite.setBackgroundImage(new Image(display, Render.class
				.getResourceAsStream("/images/menuWallpaper.png")));
		loadingComposite.setSize(320, 410);
		loadingComposite.setBackgroundMode(SWT.INHERIT_FORCE);

		final org.eclipse.swt.graphics.Color colorBlack = display
				.getSystemColor(SWT.COLOR_BLACK);
		final org.eclipse.swt.graphics.Color colorGray = new org.eclipse.swt.graphics.Color(
				display, 132, 144, 142);
		final org.eclipse.swt.graphics.Color colorSilver = new org.eclipse.swt.graphics.Color(
				display, 178, 184, 182);
		final org.eclipse.swt.graphics.Color colorSilver2 = new org.eclipse.swt.graphics.Color(
				display, 196, 197, 201);
		final org.eclipse.swt.graphics.Color colorBlack2 = new org.eclipse.swt.graphics.Color(
				display, 40, 45, 45);

		// Menu---------
		Font myfont = new Font(display, new FontData("Capture it", 13,
				SWT.NORMAL));

		buildCar = new Button(mainMenuComposite, SWT.PUSH);
		buildCar.setSize(200, 40);
		buildCar.setText("Build Car");
		buildCar.setLocation(60, 334);
		buildCar.setFont(myfont);

		joinGame = new Button(mainMenuComposite, SWT.PUSH);
		joinGame.setSize(200, 40);
		joinGame.setText("Join Game");
		joinGame.setLocation(60, 292);
		joinGame.setFont(myfont);

		newGame = new Button(mainMenuComposite, SWT.PUSH);
		newGame.setSize(200, 40);
		newGame.setText("New Game");
		newGame.setLocation(60, 250);
		newGame.setFont(myfont);

		gameName = new Label(mainMenuComposite, SWT.NONE);
		gameName.setSize(200, 105);
		gameName.setLocation(60, 30);

		// New Game---------

		countPlayers = new Spinner(newGameComposite, SWT.TRANSPARENT);
		countPlayers.setMinimum(1);
		countPlayers.setMaximum(10);
		countPlayers.setSize(50, 40);
		countPlayers.setLocation(210, 230);
		countPlayers.setBackground(colorGray);
		countPlayers.setForeground(colorBlack);

		play = new Button(newGameComposite, SWT.PUSH);
		play.setSize(200, 40);
		play.setText("Play");
		play.setLocation(60, 300);
		play.setFont(myfont);

		writeCountPlayers = new Label(newGameComposite, SWT.NONE);
		writeCountPlayers.setSize(220, 20);
		writeCountPlayers.setText("Count players :");
		writeCountPlayers.setLocation(68, 240);
		writeCountPlayers.setForeground(colorSilver2);
		writeCountPlayers.setFont(myfont);

		chooseOneMap = new Label(newGameComposite, SWT.NONE);
		chooseOneMap.setSize(220, 20);
		chooseOneMap.setText("Choose one map :");
		chooseOneMap.setLocation(82, 85);
		chooseOneMap.setForeground(colorSilver2);
		chooseOneMap.setFont(myfont);

		createdMaps = new List(newGameComposite, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.TRANSPARENT);
		createdMaps.setBackground(colorGray);
		createdMaps.setForeground(colorBlack);
		createdMaps.setSize(200, 100);
		createdMaps.setLocation(62, 110);

		backNewGame = new Button(newGameComposite, SWT.PUSH);
		backNewGame.setSize(48, 48);
		backNewGame.setLocation(4, 358);

		// Join Game---------

		chooseGame = new Label(joinGameComposite, SWT.NULL);
		chooseGame.setSize(220, 20);
		chooseGame.setText("Choose one game :");
		chooseGame.setLocation(77, 100);
		chooseGame.setForeground(colorSilver2);
		chooseGame.setFont(myfont);

		createdGames = new List(joinGameComposite, SWT.BORDER | SWT.MULTI
				| SWT.V_SCROLL | SWT.TRANSPARENT);
		createdGames.setBackground(colorGray);
		createdGames.setForeground(colorBlack);
		createdGames.setSize(200, 150);
		createdGames.setLocation(62, 190);

		backJoinGame = new Button(joinGameComposite, SWT.PUSH);
		backJoinGame.setSize(48, 48);
		backJoinGame.setLocation(4, 358);

		connect = new Button(joinGameComposite, SWT.PUSH);
		connect.setSize(75, 40);
		connect.setText("Connect");
		connect.setLocation(241, 366);
		Font italicFont = new Font(display, new FontData("Arial", 11,
				SWT.ITALIC));
		connect.setFont(italicFont);

		// Build car---------

		backBuildCar = new Button(buildCarComposite, SWT.PUSH);
		backBuildCar.setSize(48, 48);
		backBuildCar.setLocation(4, 358);

		save = new Button(buildCarComposite, SWT.PUSH);
		save.setSize(65, 40);
		save.setText("Save");
		save.setLocation(251, 366);
		save.setFont(italicFont);

		// Vykon auta------

		Font perfAndAppeFont = new Font(display, new FontData("Capture it", 11,
				SWT.NORMAL));
		Font textComponents = new Font(display, new FontData("Capture it", 11,
				SWT.ITALIC));

		textKomp = new Label(performanceComposite, SWT.NULL);
		textKomp.setSize(250, 40);
		textKomp.setLocation(50, 60);
		textKomp.setFont(textComponents);
		textKomp.setForeground(colorSilver2);

		performance = new Button(buildCarComposite, SWT.NULL);
		performance.setSize(125, 40);
		performance.setText("Performance");
		performance.setLocation(30, 310);
		performance.setFont(perfAndAppeFont);

		engine = new Label(performanceComposite, SWT.NULL);
		engine.setSize(75, 20);
		engine.setText("Engine");
		engine.setLocation(35, 110);
		engine.setFont(perfAndAppeFont);
		engine.setForeground(colorSilver2);

		engineC = new Combo(performanceComposite, SWT.NULL);
		engineC.setSize(170, 20);
		engineC.setLocation(116, 105);

		exhaust = new Label(performanceComposite, SWT.NULL);
		exhaust.setSize(75, 20);
		exhaust.setText("Exhaust");
		exhaust.setLocation(35, 140);
		exhaust.setFont(perfAndAppeFont);
		exhaust.setForeground(colorSilver2);

		exhaustC = new Combo(performanceComposite, SWT.NULL);
		exhaustC.setSize(170, 20);
		exhaustC.setLocation(116, 135);

		filter = new Label(performanceComposite, SWT.NULL);
		filter.setSize(75, 20);
		filter.setText("Filter");
		filter.setLocation(35, 170);
		filter.setFont(perfAndAppeFont);
		filter.setForeground(colorSilver2);

		filterC = new Combo(performanceComposite, SWT.NULL);
		filterC.setSize(170, 20);
		filterC.setLocation(116, 165);

		absorber = new Label(performanceComposite, SWT.NULL);
		absorber.setSize(75, 20);
		absorber.setText("Absorber");
		absorber.setLocation(35, 200);
		absorber.setFont(perfAndAppeFont);
		absorber.setForeground(colorSilver2);

		absorberC = new Combo(performanceComposite, SWT.NULL);
		absorberC.setSize(170, 20);
		absorberC.setLocation(116, 195);

		wheel = new Label(performanceComposite, SWT.NULL);
		wheel.setSize(75, 20);
		wheel.setText("Wheel");
		wheel.setLocation(35, 230);
		wheel.setFont(perfAndAppeFont);
		wheel.setForeground(colorSilver2);

		wheelC = new Combo(performanceComposite, SWT.NULL);
		wheelC.setSize(170, 20);
		wheelC.setLocation(116, 225);

		nitro = new Label(performanceComposite, SWT.NULL);
		nitro.setSize(75, 20);
		nitro.setText("Nitro");
		nitro.setLocation(35, 260);
		nitro.setFont(perfAndAppeFont);
		nitro.setForeground(colorSilver2);

		nitroC = new Combo(performanceComposite, SWT.NULL);
		nitroC.setSize(170, 20);
		nitroC.setLocation(116, 255);

		// Vzhled auta------

		appearance = new Button(buildCarComposite, SWT.NULL);
		appearance.setSize(125, 40);
		appearance.setText("Appearance");
		appearance.setLocation(165, 310);
		appearance.setFont(perfAndAppeFont);

		bodywork = new Label(appearanceComposite, SWT.NULL);
		bodywork.setSize(75, 20);
		bodywork.setText("Bodywork");
		bodywork.setLocation(35, 200);
		bodywork.setFont(perfAndAppeFont);
		bodywork.setForeground(colorSilver2);

		bodyworkC = new Combo(appearanceComposite, SWT.NULL);
		bodyworkC.setSize(170, 20);
		bodyworkC.setLocation(116, 195);

		glass = new Label(appearanceComposite, SWT.NULL);
		glass.setSize(75, 20);
		glass.setText("Glass");
		glass.setLocation(35, 230);
		glass.setFont(perfAndAppeFont);
		glass.setForeground(colorSilver2);

		glassC = new Combo(appearanceComposite, SWT.NULL);
		glassC.setSize(170, 20);
		glassC.setLocation(116, 225);

		carLabel = new Label(appearanceComposite, SWT.TRANSPARENT);
		carLabel.setLocation(148, 67);

		addPerformanceComponents();
		addAppearanceComponents();

		// Loading----------

		loadingComposite.setSize(320, 410);

		loadingText = new Label(loadingComposite, SWT.NULL);
		loadingText.setText("Waiting for opponents, \n   please wait...");
		loadingText.setSize(260, 40);
		loadingText.setLocation(50, 110);
		loadingText.setForeground(colorSilver2);
		loadingText.setFont(myfont);

		loading = new ProgressBar(loadingComposite, SWT.HORIZONTAL
				| SWT.INDETERMINATE);
		loading.setSize(250, 20);
		loading.setLocation(35, 290);

		loading.setBackground(display.getSystemColor(SWT.TRANSPARENT));

		shell.setSize(320, 430);
		shell.setMinimumSize(320, 430);
		shell.setText("Highway Race");

		Listener newGameL = new Listener() {
			public void handleEvent(Event event) {
				mainMenuComposite.setVisible(false);
				newGameComposite.setVisible(true);

				String maps = client.getMaps();

				createdMaps.removeAll();

				for (String a : maps.split(",")) {
					if (!a.isEmpty()) {
						createdMaps.add("Map : " + a);
					}
				}
				if (maps.length() > 0) {
					createdMaps.select(0);
				}
			}
		};

		Listener joinGameL = new Listener() {
			public void handleEvent(Event event) {
				mainMenuComposite.setVisible(false);
				joinGameComposite.setVisible(true);

				String games = client.getGames();

				createdGames.removeAll();

				for (String a : games.split(",")) {
					if (!a.isEmpty()) {
						createdGames.add("Game id: " + a);
					}
				}
				if (games.length() > 0) {
					createdGames.select(0);
				}
			}
		};

		Listener buildCarL = new Listener() {
			public void handleEvent(Event event) {
				mainMenuComposite.setVisible(false);
				if (!saveAllCarComponents) {
					nullCombo();
				} else {
					setAllCarComponents();
				}

				buildCarComposite.setVisible(true);
				performance.forceFocus();
				performanceComposite.setVisible(true);
			}
		};

		Listener backL = new Listener() {
			public void handleEvent(Event event) {
				newGameComposite.setVisible(false);
				mainMenuComposite.setVisible(true);
			}
		};

		Listener playL = new Listener() {
			public void handleEvent(Event event) {
				if (createdMaps.getItemCount() > 0) {
					String m = createdMaps.getItem(
							createdMaps.getSelectionIndex()).split(" ")[2];
					boolean mapIsLoaded = false;
					String a = null;
					File dir = new File(".");
					File[] directoryListing = dir.listFiles();
					if (directoryListing != null) {
						for (File child : directoryListing) {
							if (child.getName().endsWith(".png")) {
								a = child
										.getName()
										.subSequence(0,
												child.getName().length() - 4)
										.toString();
								if (a.equals(m)) {
									mapIsLoaded = true;
									break;
								}
							}
						}
					}

					if (!mapIsLoaded) {
						byte[] map = client.loadMap(m);
						Render.createImg(map, m);
					}
					game.setMapName(m);

					int u = Integer.parseInt(countPlayers.getText());
					game.createCars(u);

					client.createGame(
							m,
							myDisplay,
							u,
							game,
							bodyworkComponent,
							glassComponent,
							((Engine.engines[engineComponent].getValue() + Exhaust.exhausts[exhaustComponent]
									.getValue()) / 2),
							((Absorbers.absorbers[absorbersComponent]
									.getValue() + Wheel.wheels[wheelComponent]
									.getValue()) / 2), nitroComponent,
							filterComponent);
					setComponentsToCar(game);

					newGameComposite.setVisible(false);
					loadingComposite.setVisible(true);

					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							client.syncStart();
							client.getImgs(game.getMap().getCars());
							display.syncExec(new Runnable() {

								@Override
								public void run() {
									loadingComposite.setVisible(false);
								}
							});
							sp.Start();
						}
					});
					t.start();
				}
			}
		};

		Listener back2L = new Listener() {
			public void handleEvent(Event event) {
				joinGameComposite.setVisible(false);
				mainMenuComposite.setVisible(true);
			}
		};

		Listener connectL = new Listener() {
			public void handleEvent(Event event) {
				if (createdGames.getItemCount() > 0) {
					int u = Integer.parseInt(createdGames.getItem(
							createdGames.getSelectionIndex()).split(" ")[2]);
					String m = client.getMapName(u);
					boolean mapIsLoaded = false;
					String a = null;
					File dir = new File(".");
					File[] directoryListing = dir.listFiles();
					if (directoryListing != null) {
						for (File child : directoryListing) {
							if (child.getName().endsWith(".png")) {
								a = child
										.getName()
										.subSequence(0,
												child.getName().length() - 4)
										.toString();
								if (a.equals(m)) {
									mapIsLoaded = true;
									break;
								}
							}
						}
					}

					if (!mapIsLoaded) {
						byte[] map = client.loadMap(m);
						Render.createImg(map, m);
					}
					game.setMapName(m);

					int cars = client
							.joinGame(
									u,
									myDisplay,
									game,
									bodyworkComponent,
									glassComponent,
									((Engine.engines[engineComponent]
											.getValue() + Exhaust.exhausts[exhaustComponent]
											.getValue()) / 2),
									((Absorbers.absorbers[absorbersComponent]
											.getValue() + Wheel.wheels[wheelComponent]
											.getValue()) / 2), nitroComponent,
									filterComponent);
					game.createCars(cars);
					setComponentsToCar(game);

					joinGameComposite.setVisible(false);

					loadingComposite.setVisible(true);
					Thread t = new Thread(new Runnable() {

						@Override
						public void run() {
							client.syncStart();
							client.getImgs(game.getMap().getCars());
							display.syncExec(new Runnable() {

								@Override
								public void run() {
									loadingComposite.setVisible(false);
								}
							});
							sp.Start();
						}
					});
					t.start();
				}
			}
		};

		Listener back3L = new Listener() {
			public void handleEvent(Event event) {
				buildCarComposite.setVisible(false);
				mainMenuComposite.setVisible(true);
			}
		};

		Listener saveL = new Listener() {
			public void handleEvent(Event event) {
				saveAllCarComponents = saveAllCarComponents();

				buildCarComposite.setVisible(false);
				mainMenuComposite.setVisible(true);
			}
		};

		Listener performanceL = new Listener() {
			public void handleEvent(Event event) {
				appearanceComposite.setVisible(false);
				performanceComposite.setVisible(true);
			}
		};

		Listener appearanceL = new Listener() {
			public void handleEvent(Event event) {
				performanceComposite.setVisible(false);
				appearanceComposite.setVisible(true);

				String karosery = Bodywork.bodyworks[bodyworkC
						.getSelectionIndex()].getCode();
				String glass = Glass.glasses[glassC.getSelectionIndex()]
						.getCode();

				Image carImage = new Image(display,
						DesktopMenu.class.getResourceAsStream("/images/"
								+ karosery + glass + ".png"));
				carLabel.setSize(carImage.getBounds().width,
						carImage.getBounds().height);
				carLabel.setImage(carImage);
			}
		};

		SelectionListener engineAndExhaustSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				textKomp.setText(textKomponents[0]);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};

		SelectionListener absorberAndWheelSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				textKomp.setText(textKomponents[1]);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};

		SelectionListener filterSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				textKomp.setText(textKomponents[2]);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};

		SelectionListener nitroSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				textKomp.setText(textKomponents[3]);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		};

		SelectionListener bodyworkSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				String karosery = Bodywork.bodyworks[bodyworkC
						.getSelectionIndex()].getCode();
				String glass = Glass.glasses[glassC.getSelectionIndex()]
						.getCode();

				Image carImage = new Image(display,
						Render.class.getResourceAsStream("/images/" + karosery
								+ glass + ".png"));
				carLabel.setSize(carImage.getBounds().width,
						carImage.getBounds().height);
				carLabel.setImage(carImage);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				bodyworkC.select(0);
			}
		};

		SelectionListener glassSelectionL = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String karosery = Bodywork.bodyworks[bodyworkC
						.getSelectionIndex()].getCode();
				String glass = Glass.glasses[glassC.getSelectionIndex()]
						.getCode();

				Image carImage = new Image(display,
						Render.class.getResourceAsStream("/images/" + karosery
								+ glass + ".png"));
				carLabel.setSize(carImage.getBounds().width,
						carImage.getBounds().height);
				carLabel.setImage(carImage);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				glassC.select(0);
			}
		};

		PaintListener backgroundButtonPaintL = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (e.widget instanceof Button) {
					Button button = (Button) e.widget;

					e.gc.setBackground(colorSilver);
					e.gc.fillRoundRectangle(0, 0, button.getBounds().width,
							button.getBounds().height, 10, 10);

					e.gc.setBackground(colorBlack2);
					e.gc.fillRoundRectangle(5, 5,
							button.getBounds().width - 10,
							button.getBounds().height - 10, 10, 10);

					e.gc.setForeground(colorSilver);

					Point textSize = e.gc.stringExtent(button.getText());
					int x = (button.getBounds().width - textSize.x) / 2;
					int y = (button.getBounds().height - textSize.y) / 2;

					e.gc.drawText(button.getText(), x, y);
				}
			}
		};

		PaintListener backgroundButtonImgL = new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				if (e.widget instanceof Button) {
					Button button = (Button) e.widget;

					e.gc.setBackground(colorSilver);
					e.gc.fillRoundRectangle(0, 0, button.getBounds().width,
							button.getBounds().height, 10, 10);

					Image image = new Image(display,
							Render.class
									.getResourceAsStream("/images/back2.png"));
					e.gc.drawImage(image, 0, 0);
				}
			}
		};

		PaintListener backgroundPlace = new PaintListener() {
			@Override
			public void paintControl(PaintEvent e) {
				if (e.widget instanceof Label) {
					Label label = (Label) e.widget;
					Image image = new Image(display,
							Render.class
									.getResourceAsStream("/images/mapName.png"));
					e.gc.drawImage(image, 0, 0, image.getBounds().width,
							image.getBounds().height, 0, 0,
							label.getBounds().width, label.getBounds().height);
					e.gc.setForeground(colorBlack2);
					e.gc.setFont(new Font(display, new FontData("Capture it",
							18, SWT.NORMAL)));
					e.gc.drawText("HIGHWAY\n RACING", 45, 25, true);
				}
			}
		};

		gameName.addPaintListener(backgroundPlace);
		newGame.addPaintListener(backgroundButtonPaintL);
		joinGame.addPaintListener(backgroundButtonPaintL);
		buildCar.addPaintListener(backgroundButtonPaintL);
		play.addPaintListener(backgroundButtonPaintL);
		connect.addPaintListener(backgroundButtonPaintL);
		save.addPaintListener(backgroundButtonPaintL);
		performance.addPaintListener(backgroundButtonPaintL);
		appearance.addPaintListener(backgroundButtonPaintL);

		backNewGame.addPaintListener(backgroundButtonImgL);
		backJoinGame.addPaintListener(backgroundButtonImgL);
		backBuildCar.addPaintListener(backgroundButtonImgL);

		engineC.addSelectionListener(engineAndExhaustSelectionL);
		exhaustC.addSelectionListener(engineAndExhaustSelectionL);

		filterC.addSelectionListener(filterSelectionL);
		nitroC.addSelectionListener(nitroSelectionL);

		wheelC.addSelectionListener(absorberAndWheelSelectionL);
		absorberC.addSelectionListener(absorberAndWheelSelectionL);

		bodyworkC.addSelectionListener(bodyworkSelectionL);
		glassC.addSelectionListener(glassSelectionL);

		newGame.addListener(SWT.Selection, newGameL);
		joinGame.addListener(SWT.Selection, joinGameL);
		buildCar.addListener(SWT.Selection, buildCarL);
		backNewGame.addListener(SWT.Selection, backL);
		play.addListener(SWT.Selection, playL);
		backJoinGame.addListener(SWT.Selection, back2L);
		connect.addListener(SWT.Selection, connectL);
		backBuildCar.addListener(SWT.Selection, back3L);
		save.addListener(SWT.Selection, saveL);
		performance.addListener(SWT.Selection, performanceL);
		appearance.addListener(SWT.Selection, appearanceL);

		loadingComposite.setVisible(false);
		appearanceComposite.pack();
		appearanceComposite.setVisible(false);
		performanceComposite.pack();
		performanceComposite.setVisible(false);
		buildCarComposite.setVisible(false);
		joinGameComposite.setVisible(false);
		newGameComposite.setVisible(false);
	}

	private void addPerformanceComponents() {
		addEngine();
		addExhaust();
		addFilter();
		addAbsorber();
		addWheel();
		addNitro();
	}

	private void addAppearanceComponents() {
		addBodywork();
		addGlass();
	}

	private void addEngine() {
		for (Engine e : Engine.engines) {
			engineC.add(e.getName() + ", " + e.getValue());
		}
		engineC.select(0);
	}

	private void addExhaust() {
		for (Exhaust e : Exhaust.exhausts) {
			exhaustC.add(e.getName() + ", " + e.getValue());
		}
		exhaustC.select(0);
	}

	private void addFilter() {
		for (Filter f : Filter.filters) {
			filterC.add(f.getName() + ", " + f.getValue());
		}
		filterC.select(0);
	}

	private void addAbsorber() {
		for (Absorbers a : Absorbers.absorbers) {
			absorberC.add(a.getName() + ", " + a.getValue());
		}
		absorberC.select(0);
	}

	private void addWheel() {
		for (Wheel w : Wheel.wheels) {
			wheelC.add(w.getName() + ", " + w.getValue());
		}
		wheelC.select(0);
	}

	private void addNitro() {
		for (Nitro n : Nitro.nitrous) {
			nitroC.add(n.getName() + ", " + n.getValue());
		}
		nitroC.select(0);
	}

	private void addBodywork() {
		for (Bodywork b : Bodywork.bodyworks) {
			bodyworkC.add(b.getName() + ", " + b.getType() + ", "
					+ b.getColor());
		}
		bodyworkC.select(0);
	}

	private void addGlass() {
		for (Glass g : Glass.glasses) {
			glassC.add(g.getName() + ", " + g.getColor());
		}
		glassC.select(0);
	}

	private boolean setComponentsToCar(Game myGame) {
		int idPlayer = myGame.getIDplayer();

		myGame.getMap().getCars().get(idPlayer)
				.setEngine(Engine.engines[engineComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setExhaust(Exhaust.exhausts[exhaustComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setFilter(Filter.filters[filterComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setAbsorbers(Absorbers.absorbers[absorbersComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setWheel(Wheel.wheels[wheelComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setNitro(Nitro.nitrous[nitroComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setBodywork(Bodywork.bodyworks[bodyworkComponent]);
		myGame.getMap().getCars().get(idPlayer)
				.setGlass(Glass.glasses[glassComponent]);

		myGame.getMap().getCars().get(idPlayer).setTrajectory();

		return true;
	}

	private boolean saveAllCarComponents() {
		engineComponent = engineC.getSelectionIndex();
		exhaustComponent = exhaustC.getSelectionIndex();
		filterComponent = filterC.getSelectionIndex();
		absorbersComponent = absorberC.getSelectionIndex();
		wheelComponent = wheelC.getSelectionIndex();
		nitroComponent = nitroC.getSelectionIndex();

		bodyworkComponent = bodyworkC.getSelectionIndex();
		glassComponent = glassC.getSelectionIndex();

		return true;
	}

	private void setAllCarComponents() {
		engineC.select(engineComponent);
		exhaustC.select(exhaustComponent);
		filterC.select(filterComponent);
		absorberC.select(absorbersComponent);
		wheelC.select(wheelComponent);
		nitroC.select(nitroComponent);

		bodyworkC.select(bodyworkComponent);
		glassC.select(glassComponent);
	}

	private void nullCombo() {
		bodyworkC.select(0);
		glassC.select(0);

		engineC.select(0);
		exhaustC.select(0);
		filterC.select(0);
		absorberC.select(0);
		wheelC.select(0);
		nitroC.select(0);
	}
}
