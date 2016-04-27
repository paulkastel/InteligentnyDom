import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Glowna klasa programu. Okno zawierajace menu, uramiajace caly program,
 * zaczynajacy sie od wywolania okna z logowaniem. Panel sterowania.
 * 
 */
public class OknoGlowne extends JFrame implements ActionListener
{
	
	//Obiekty odpowiadajace za zarzadzanie swoimi klasami
	public static KaloryferManager KM;
	public static RoletyManager RM;
	public static SwiatloManager SM;
	public static RadioManager RDM;
	public static TelewizorManager TVM;

	//Obiekt odpowiadajacy za logowanie
	private static Logowanie log;

	/**
	 * Funkcja wyswietlajaca wszystkie parametry wszystkich urzadzen jakimi da
	 * sie zarzadzac w domu z naszego programu
	 */
	public static void PokazSytuacjeDomu()
	{
		JOptionPane.showMessageDialog(null,
				"Grzejnik jest: " + KM.isWlaczone + " i ma temp: " + KM.temperatura
				+ "\nRolety sa zasuniete na: " + RM.wysokosc + " [cm]"
				+ "\nSwiatlo w: Gabinet = " + SM.p1 + ". Kuchnia = " + SM.p2 + ", Lazienka = " + SM.p3 + ", Salon = " + SM.p4
				+ "\nRadio jest: " + RDM.isWlaczone + " na " + RDM.czestotliwosc + " [MHz] z moca " + RDM.vol
				+ "\nTV jest: " + TVM.isWlaczone + " na kanal " + TVM.kanal + " z moca " + TVM.vol,
				"Aktualna sytuacja w Domu", JOptionPane.PLAIN_MESSAGE);
	}

	/**
	 * Funkcja ktora ma odpowiadac za sprawdzenie poprawnosci polaczenia z danym urzadzeniem
	 * @return true jezeli polaczenie zostalo poprawnie nawiazane z urzadzeniem lub false jezeli 
	 * polaczenie nie zostalo nawiazane
	 */
	private static boolean Polacz()
	{
		return true;
	}

	//Przyciski na glownym oknie do uruchamiania odpowiednich okien
	JButton butKaloryfer, butRolety, butSwiatlo, butRadio, butTV, butExit;

	//Pasek z gornym menu
	JMenuBar menuBar;
	JMenu menuPlik, menuPomoc, menuUruchom, menuNarzedzia;
	JMenuItem mExit, mOProgramie, m1, m2, m3, m4, m5, mPokaz;

	public OknoGlowne()
	{
		setSize(220, 500);		//rozmiar okna glownego panelu
		setTitle("Menu");		//tytul okna glownego
		setVisible(true);		//jest widoczny
		setResizable(false);	//nie da sie rozciagac
		setLayout(null);		//layout
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Tworze menu z trzema opcjami Plik, Narzedzia i Pomoc
		menuBar = new JMenuBar();
		menuPlik = new JMenu("Plik");
		menuNarzedzia = new JMenu("Narzedzia");
		menuPomoc = new JMenu("Pomoc");

		//W menu Plik zakladka wyjscie
		mExit = new JMenuItem("Wyjscie");

		//W menu Plik podmenu Zarzadzaj z opcjami do uruchamiania
		menuUruchom = new JMenu("Zarzadzaj...");
		m1 = new JMenuItem("Kaloryfer");
		m2 = new JMenuItem("Rolety");
		m3 = new JMenuItem("Swiatlo");
		m4 = new JMenuItem("Radio");
		m5 = new JMenuItem("Telewizja");

		mPokaz = new JMenuItem("Pokaz stan domu");
		menuNarzedzia.add(mPokaz);
		mPokaz.addActionListener(this);

		//Dodaje do podmenu Zarzadzaj wszystkie opcje powyzej
		menuUruchom.add(m1);
		m1.addActionListener(this);
		menuUruchom.add(m2);
		m2.addActionListener(this);
		menuUruchom.add(m3);
		m3.addActionListener(this);
		menuUruchom.add(m4);
		m4.addActionListener(this);
		menuUruchom.add(m5);
		m5.addActionListener(this);

		//Do menu Plik dodaje zakladke "Zarzadzaj..."
		menuPlik.add(menuUruchom);
		menuPlik.addSeparator();		//Dodaje separator
		menuPlik.add(mExit);			//Dodaje wyjscie
		mExit.addActionListener(this);

		//W Menu Pomoc zakladka O programie
		mOProgramie = new JMenuItem("O Programie");
		menuPomoc.add(mOProgramie);
		mOProgramie.addActionListener(this);

		//Dodaje wszystko i koncze tworzenie gornego menu
		setJMenuBar(menuBar);
		menuBar.add(menuPlik);
		menuBar.add(menuNarzedzia);
		menuBar.add(menuPomoc);

		//Wszystkie parametry przyciskow odpowiedzialnych za rozruch
		butKaloryfer = new JButton("Ustaw temperature");
		//(prawa, gora, szerokosc, wysokosc)
		butKaloryfer.setBounds(30, 40, 150, 30);
		add(butKaloryfer);
		butKaloryfer.addActionListener(this);

		butRolety = new JButton("Ustaw rolety");
		butRolety.setBounds(30, 80, 150, 30);
		add(butRolety);
		butRolety.addActionListener(this);

		butSwiatlo = new JButton("Ustaw swiatlo");
		butSwiatlo.setBounds(30, 120, 150, 30);
		add(butSwiatlo);
		butSwiatlo.addActionListener(this);

		butRadio = new JButton("Zarzadzaj Radiem");
		butRadio.setBounds(30, 160, 150, 30);
		add(butRadio);
		butRadio.addActionListener(this);

		butTV = new JButton("Zarzadzaj TV");
		butTV.setBounds(30, 200, 150, 30);
		add(butTV);
		butTV.addActionListener(this);

		butExit = new JButton("Wyjscie");
		butExit.setBounds(30, 360, 150, 30);
		add(butExit);
		butExit.addActionListener(this);
		
		//Funkcja dzieki ktorej to wszystko dobrze sie wyswietla
		SwingUtilities.updateComponentTreeUI(this);

		//Stworzone obiekty wszystkich okien do zarzadzania
		SM = new SwiatloManager(this);
		KM = new KaloryferManager(this);
		RM = new RoletyManager(this);
		RDM = new RadioManager(this);
		TVM = new TelewizorManager(this);
	}

	/** -----------------------------------------------------
	 * No main. Uruchom to wszystkooo
	 * @param args 
	 ----------------------------------------------------- */
	public static void main(String[] args)
	{
		log = new Logowanie();
	}

	//--------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		//Uruchom zachowanie jezeli wcisnieto ktorys z tych przyciskow
		if (zrodlo == butExit || zrodlo == mExit)
		{
			//Pokaz okno ktore sie pyta czy jestes pewnien opuszczenia
			int odp = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyjść?", "Potwierdź wyjście", JOptionPane.YES_NO_OPTION);
			if (odp == JOptionPane.YES_OPTION) //Ta, jestem pewien
			{
				dispose(); //Zamknij okno
			}

		}
		else if (zrodlo == butKaloryfer || zrodlo == m1)
		{
			if (Polacz()) //Sprawdz czy aktualnie polaczenie zostalo wykonane poprawnie
			{
				KM.setVisible(true); //Poka to okno do zarzadzania (od grzejnika)
			}
			else
			{
				//Brak polaczenia z urzadzeniem okno dialogowe
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem!", "Blad Grzejnika!", JOptionPane.ERROR_MESSAGE);
			}

		}
		else if (zrodlo == butRolety || zrodlo == m2)
		{
			if (Polacz())
			{
				RM.setVisible(true); //Poka to okno do zarzadzania (od rolet)
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem!", "Blad Rolety!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (zrodlo == butSwiatlo || zrodlo == m3)
		{
			if (Polacz())
			{
				SM.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem!", "Blad Oswietlenia!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (zrodlo == butRadio || zrodlo == m4)
		{
			if (Polacz())
			{
				RDM.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem!", "Blad Radia!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (zrodlo == butTV || zrodlo == m5)
		{
			if (Polacz())
			{
				TVM.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem!", "Blad Telewizora", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if (zrodlo == mOProgramie)
		{
			//To my!! <3
			JOptionPane.showMessageDialog(null,
					"Tworcy programu:\nKastelik, Krzeminska, Kuna, Kozla\nna potrzeby projektu z przedmiotu IO");
		}
		else if (zrodlo == mPokaz)
		{
			if (Polacz()) //Jezeli polaczenie z urzadzeniami jest ok to:
			{
				PokazSytuacjeDomu(); //Co tam sie dzieje z tymi urzadzeniami
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Brak polaczenia z urzadzeniem", "Blad ogolny", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
