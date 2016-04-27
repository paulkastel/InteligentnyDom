
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Okno do sterowania i zarzadzania zmianami rolet
 *
 */
public class RoletyManager extends JDialog implements ActionListener, ChangeListener
{

	public int wysokosc;
	private int lastwys;
	private boolean zasun = false;

	private JLabel k_etykieta;
	private JButton bok, bcancel, bZasun;
	private JSlider suwak;

	/**
	 * Ustawia wysokosc rolety na podstawie suwaka
	 *
	 * @return wysokosc na jaka ustawil rolete
	 */
	private int UstawRolete()
	{
		wysokosc = suwak.getValue();
		k_etykieta.setText("Wysokosc rolet to: " + wysokosc + " [cm]");
		return wysokosc;
	}

	/**
	 * Ustawia rolete albo 100% zaunieta albo odslonieta
	 */
	private void ZasunRol()
	{

		if (zasun == false)
		{
			zasun = true;
			suwak.setValue(100);
			Wyslij(100);
			bZasun.setText("Odslon");
		}
		else
		{
			zasun = false;
			suwak.setValue(0);
			Wyslij(0);
			bZasun.setText("Zasun");
		}
	}

	/**
	 * Funkcja ktora wysyla ustawienie do rolety
	 *
	 * @param wys na jak wysoko ma byc ta roleta dziecko
	 */
	private void Wyslij(int wys)
	{
		System.out.println("wysokosc rolet: " + wys);
	}

	/**
	 * Konstruktor okna i klasy RoletyManager wywolywana przez klase OknoGlowne
	 *
	 * @param wlasciciel
	 */
	public RoletyManager(JFrame wlasciciel)
	{
		//Parametry okienka
		super(wlasciciel, "Manager Rolet", true);
		setSize(300, 300);
		setLayout(null);
		setResizable(false);	//nie da sie rozciagac

		//Dane na szytwno
		lastwys = wysokosc = 0;

		//Buttony
		bZasun = new JButton("Zasun");
		bZasun.setBounds(36, 220, 80, 20);
		add(bZasun);
		bZasun.addActionListener(this);

		bok = new JButton("OK");
		bok.setBounds(120, 220, 70, 20);
		add(bok);
		bok.addActionListener(this);

		bcancel = new JButton("Anuluj");
		bcancel.setBounds(196, 220, 70, 20);
		add(bcancel);
		bcancel.addActionListener(this);

		//(poczatek, koniec, wart.poczatkowa)
		suwak = new JSlider(0, 100, 0);
		suwak.setEnabled(true);
		suwak.setBounds(20, 60, 260, 50);
		suwak.setMajorTickSpacing(10);
		suwak.setMinorTickSpacing(5);
		suwak.setPaintTicks(true);
		suwak.setPaintLabels(true);
		suwak.addChangeListener(this);
		add(suwak);

		k_etykieta = new JLabel("Wysoksoc rolet to: " + wysokosc + " [cm]");
		k_etykieta.setBounds(50, 150, 200, 30);
		add(k_etykieta);

		//Ladnie wyswietlaj okno
		SwingUtilities.updateComponentTreeUI(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bok) //Zatwierdz zmiany
		{
			lastwys = wysokosc = suwak.getValue();

			//Komunikat do urzadzenia
			Wyslij(lastwys);

			//Komunikat dla uzytkownika
			JOptionPane.showMessageDialog(null, "Ustawiles rolety na wys: " + lastwys, "Potwierdzenie ustawienia", JOptionPane.INFORMATION_MESSAGE);
			dispose(); //zamknij okno
		}
		else if (zrodlo == bcancel)	//Anuluj zmiany
		{
			wysokosc = lastwys; //Przywróc poprzednia wartosc
			dispose();
			k_etykieta.setText("Wysoksoc rolet to: " + wysokosc + " [cm]");
			suwak.setValue(wysokosc);
		}
		else if (zrodlo == bZasun)
		{
			ZasunRol(); //Zasun albo odslon kompletenie rolete
		}
	}

	@Override
	public void stateChanged(ChangeEvent ce)
	{
		UstawRolete(); //Ustaw rolete na podstawie suwaka
	}
}
