
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Manager do kontrolowania tego co sie dzieje w grzejniku
 * 
 */
public class KaloryferManager extends JDialog implements ActionListener, ChangeListener
{
	//Temperatura grzenika i to czy jest wlaczony
	public int temperatura;
	public boolean isWlaczone;

	//Zmienne pocnicze w przypadku gdy jednak uzytkownik sie rozmysli i bedzie chcial anulowac zmiany
	private int lasttemp;
	private boolean laststate;

	//Guziczki do okienka, etykietki i suwaczek do temperatury
	private JLabel k_etykieta;
	private JButton bok, bcancel, wlacznik;
	private JSlider suwak;

	/**
	 * Ultra wazna funkcja ktora odpowiada bezposrednio za wysylanie zmian do urzadzenia... W teorii...
	 * Bo w praktyce to System.out.println(); heheheh
	 * @param temp ustawia temperature grzejnika
	 * @param stan wlacza i go wylacza
	 */
	private void Wyslij(int temp, boolean stan)
	{
		System.out.println("temp: "+temp+" stan: "+stan);
	}

	/**
	 * Konstruktor Okna, ktory jest wywolywany przez JFrame i wywoluje podwladnego jakim jest JDialog
	 * @param wlasciciel JFrame od Glownego Okna
	 */
	public KaloryferManager(JFrame wlasciciel)
	{
		//Ustaw parametry JDialog
		super(wlasciciel, "Manager Grzejnika", true);
		setSize(300, 300);
		setLayout(null);
		setResizable(false);	//nie da sie rozciagac
	
		//Ustaw poczatkowe dane dla kaloryfera
		lasttemp = temperatura = 0;
		laststate = isWlaczone = false;

		//Buttony
		bok = new JButton("OK");
		bok.setBounds(120, 220, 70, 20);
		add(bok);
		bok.addActionListener(this);

		bcancel = new JButton("Anuluj");
		bcancel.setBounds(196, 220, 70, 20);
		add(bcancel);
		bcancel.addActionListener(this);

		//(poczatek, koniec, wart.poczatkowa)
		suwak = new JSlider(0, 40, 0);
		suwak.setEnabled(isWlaczone);
		suwak.setBounds(20, 60, 260, 50);
		suwak.setMajorTickSpacing(10); //Os glowna
		suwak.setMinorTickSpacing(2); //Mala podzialka
		suwak.setPaintTicks(true);
		suwak.setPaintLabels(true);
		suwak.addChangeListener(this); //kaze mu sprawdzac na biezaco zmiany temp
		add(suwak);

		k_etykieta = new JLabel("Grzejnik nie pracuje.");
		k_etykieta.setBounds(80, 150, 200, 30);
		add(k_etykieta);

		wlacznik = new JButton("WLACZ");
		wlacznik.setBounds(50, 10, 200, 30);
		add(wlacznik);
		wlacznik.addActionListener(this);

		//Funkcja do poprawnego wyswietlania wszystkiego
		SwingUtilities.updateComponentTreeUI(this);
	}

	//Sprawdzaj zmiany zachodzace po wciskaniu przyciskow
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bok)
		{
			laststate = isWlaczone;
			if(isWlaczone)
			{
				//jezeli wlaczone to ustaw temperature w grzejniku
				lasttemp = temperatura = suwak.getValue();
			}
			else
			{
				//Jak kaloryfer jest wylaczony to nie grzeje co nie?
				lasttemp=temperatura=0;
			}
			//Wyslij dane do sterownika i pokaz uzytkowniki na oknie co nabroil
			Wyslij(lasttemp, laststate);
			JOptionPane.showMessageDialog(null, "Ustawiles grzejnik na temp: "+lasttemp+" oraz dzialanie na: "+laststate, "Potwierdzenie ustawienia", JOptionPane.INFORMATION_MESSAGE);
			dispose(); //Zamknij okno synu
		}
		else if (zrodlo == bcancel)
		{
			//Lo jezu, gosc sie rozmyslij i mi tu anuluje zmiany
			temperatura = lasttemp;
			isWlaczone = laststate;
			dispose();
			k_etykieta.setText("Temperatura to: " + temperatura + " C");
			suwak.setValue(temperatura);
			wlacznik.setEnabled(isWlaczone);
			if (isWlaczone)
			{
				//Ustaw dostepnosc przyciskow w zaleznosci od stanu pracy grzejnika
				wlacznik.setText("WYLACZ");
				suwak.setEnabled(true);
			}
			else
			{
				wlacznik.setEnabled(true);
				wlacznik.setText("WLACZ");
				suwak.setEnabled(false);
			}
		}
		else if (zrodlo == wlacznik)
		{
			if (isWlaczone == false)
			{
				isWlaczone = true;
				wlacznik.setText("WYLACZ");
				suwak.setEnabled(isWlaczone);
				k_etykieta.setText("Temperatura to: " + temperatura + " C");
				Wyslij(temperatura, isWlaczone);
			}
			else
			{
				isWlaczone = false;
				wlacznik.setText("WLACZ");
				suwak.setEnabled(isWlaczone);
				k_etykieta.setText("Grzejnik nie pracuje.");
				Wyslij(0, isWlaczone);
			}
		}
	}

	//Jak krecisz suwakiem to ustawia temperature co nie? hehehe
	@Override
	public void stateChanged(ChangeEvent ce)
	{
		temperatura = suwak.getValue();
		k_etykieta.setText("Temperatura to: " + temperatura + " C");
		Wyslij(temperatura, isWlaczone); //Wysylaj do grzejnika non-stop info zeby grzalo jak mu kaze
	}
}
