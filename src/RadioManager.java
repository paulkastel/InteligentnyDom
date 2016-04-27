
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Okno odpowiedzialne za sterowanie radiem
 * 
 */
public class RadioManager extends JDialog implements ActionListener, ChangeListener
{
	//czestotliwosc fal, glosnosc jaka ma radio i czy jest wlaczone
	public int czestotliwosc, vol;
	public boolean isWlaczone;

	//zmienne pomocnicze jakby ktos sie rozmyslil
	private int lastfale, lastvol;
	private boolean laststate;

	//buttony, etykiety i slider
	private JLabel k_etykieta;
	private JButton bok, bcancel, wlacznik;
	private JSlider suwak, suwakvol;

	/**
	 * Funkcja laczaca sie z radiem w teorii
	 * @param temp wysylana czestotliwosc w radiu
	 * @param vol wysylana glosnosc jaka ma radio
	 * @param stan czy jest wlaczone
	 */
	private void Wyslij(int temp, int vol, boolean stan)
	{
		System.out.println("Czestotli: " + temp +" vol: "+vol+ " stan: " + stan);
	}

	/**
	 * Konstruktor okna
	 * @param wlasciciel JFrame okna glownego 
	 */
	public RadioManager(JFrame wlasciciel)
	{
		super(wlasciciel, "Manager Radia", true);
		setSize(300, 300);
		setLayout(null);
		setResizable(false);	//nie da sie rozciagac

		//defultowe wlasnosci
		lastfale = czestotliwosc = 80;
		lastvol = vol = 0;
		laststate = isWlaczone = false;

		bok = new JButton("OK");
		bok.setBounds(120, 220, 70, 20);
		add(bok);
		bok.addActionListener(this);

		bcancel = new JButton("Anuluj");
		bcancel.setBounds(196, 220, 70, 20);
		add(bcancel);
		bcancel.addActionListener(this);

		//suwak od czestotliwosci
		//(poczatek, koniec, wart.poczatkowa)
		suwak = new JSlider(80, 130, 80);
		suwak.setEnabled(isWlaczone);
		suwak.setBounds(20, 60, 260, 50);
		suwak.setMajorTickSpacing(10);
		suwak.setMinorTickSpacing(2);
		suwak.setPaintTicks(true);
		suwak.setPaintLabels(true);
		suwak.addChangeListener(this);
		add(suwak);
		
		//suwak od glosnosci
		suwakvol = new JSlider(0, 100, 0);
		suwakvol.setEnabled(isWlaczone);
		suwakvol.setBounds(20, 120, 260, 50);
		suwakvol.setMajorTickSpacing(10);
		suwakvol.setMinorTickSpacing(5);
		suwakvol.setPaintTicks(true);
		suwakvol.setPaintLabels(true);
		suwakvol.addChangeListener(this);
		add(suwakvol);

		k_etykieta = new JLabel("Radio aktualnie nie gra.");
		k_etykieta.setBounds(80, 180, 200, 30);
		add(k_etykieta);

		wlacznik = new JButton("WLACZ");
		wlacznik.setBounds(50, 10, 200, 30);
		add(wlacznik);
		wlacznik.addActionListener(this);

		SwingUtilities.updateComponentTreeUI(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bok)
		{
			//Zatwiedz zmiany i wyslij je do urzadzenia
			laststate = isWlaczone;
			if (isWlaczone)
			{
				lastfale = czestotliwosc = suwak.getValue();
				lastvol = vol = suwakvol.getValue();
			}
			else
			{
				lastfale = czestotliwosc;
				lastvol = vol;
			}

			Wyslij(lastfale, lastvol, laststate);
			JOptionPane.showMessageDialog(null, "Ustawiles radio na fale: " + lastfale + ", glosnosc: "+lastvol+" oraz dzialanie na: " + laststate, "Potwierdzenie ustawienia", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		}
		else if (zrodlo == bcancel)
		{
			//Co sie dzieje jak gosc sie rozmysli i anuluje zmiany
			czestotliwosc = lastfale;
			vol = lastvol;
			isWlaczone = laststate;
			dispose();
			k_etykieta.setText("Czestotliwosc to: " + czestotliwosc + " MHz");
			suwak.setValue(czestotliwosc);
			suwakvol.setValue(vol);
			wlacznik.setEnabled(isWlaczone);
			if (isWlaczone)
			{
				wlacznik.setText("WYLACZ");
				suwak.setEnabled(true);
				suwakvol.setEnabled(true);
			}
			else
			{
				wlacznik.setEnabled(true);
				wlacznik.setText("WLACZ");
				suwak.setEnabled(false);
				suwakvol.setEnabled(false);
			}
		}
		else if (zrodlo == wlacznik)
		{
			//Co sie dzieje jak gosc bierze i wlacza radio. 
			//PS: Ja wiem ze ta funkcja jest toporna, ale jak ja pisalem nie bylem soba
			lastfale=czestotliwosc;
			lastvol = vol;
			if (isWlaczone == false)
			{
				isWlaczone = true;
				wlacznik.setText("WYLACZ");
				suwak.setEnabled(isWlaczone);
				suwak.setValue(lastfale);
				suwakvol.setValue(lastvol);
				suwakvol.setEnabled(isWlaczone);
				k_etykieta.setText("Czestotliwosc to: " + czestotliwosc + " MHz");
				Wyslij(czestotliwosc, vol, isWlaczone);
			}
			else
			{
				isWlaczone = false;
				wlacznik.setText("WLACZ");
				suwak.setEnabled(isWlaczone);
				suwakvol.setEnabled(isWlaczone);
				k_etykieta.setText("Radio aktualnie nie gra.");
				Wyslij(80, 0, isWlaczone);
			}
		}
	}

	//Nasluchuj zmian ktore nastaplily jak gosc krecil suwakami i jak je wyslal do urzadenia
	@Override
	public void stateChanged(ChangeEvent ce)
	{
		czestotliwosc = suwak.getValue();
		vol = suwakvol.getValue();
		k_etykieta.setText("Czestotliwosc to: " + czestotliwosc + " MHz");
		Wyslij(suwak.getValue(), suwakvol.getValue(), isWlaczone);
	}
}
