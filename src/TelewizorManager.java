
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Klasa od Elewizora
 */
class TelewizorManager extends JDialog implements ActionListener, ChangeListener
{

	/**
	 * Wysylam do urzadzenia telewizora ustawienia
	 * @param k kanal
	 * @param v glosnosc
	 * @param stan Czy wlaczony
	 */
	private void Wyslij(int k, int v, boolean stan)
	{
		System.out.println("Ustawiles kanal: " + k + ", vol: " + v + " stan: " + stan);
	}
	
	//Parametry z jakimi dziala telewizor
	public int kanal, lastkanal, vol, lastvol;
	public boolean isWlaczone, laststate;

	//JButtony, labele i inne Java GUI cuda
	private JButton bok, wlacznik, prawo, lewo, k;
	private JLabel k_etykieta;
	private JTextField polekanal;
	private JSlider suwakvol;

	/**
	 * Konstruktor tej klasy
	 * @param wlasciciel 
	 */
	public TelewizorManager(JFrame wlasciciel)
	{
		//Ustaw prarametry okna
		super(wlasciciel, "Manager Telewizora", true);
		setSize(300, 300);
		setLayout(null);
		setResizable(false);	//nie da sie rozciagac

		//Ustaw defaultowe wartosci w TV
		kanal = lastkanal = 1;
		vol = lastvol = 0;
		isWlaczone = laststate = false;

		bok = new JButton("OK");
		bok.setBounds(120, 220, 70, 20);
		add(bok);
		bok.addActionListener(this);

		wlacznik = new JButton("WLACZ");
		wlacznik.setBounds(50, 10, 200, 30);
		add(wlacznik);
		wlacznik.addActionListener(this);

		polekanal = new JTextField("1");
		polekanal.setBounds(50, 50, 75, 20);
		polekanal.requestFocus();
		add(polekanal);

		k = new JButton("OK");
		k.setBounds(130, 50, 120, 19);
		add(k);
		k.addActionListener(this);

		prawo = new JButton("<=");
		prawo.setBounds(50, 80, 95, 30);
		add(prawo);
		prawo.addActionListener(this);

		lewo = new JButton("=>");
		lewo.setBounds(155, 80, 95, 30);
		add(lewo);
		lewo.addActionListener(this);

		suwakvol = new JSlider(0, 100, 0);
		suwakvol.setEnabled(isWlaczone);
		suwakvol.setBounds(20, 120, 260, 50);
		suwakvol.setMajorTickSpacing(10);
		suwakvol.setMinorTickSpacing(5);
		suwakvol.setPaintTicks(true);
		suwakvol.setPaintLabels(true);
		suwakvol.addChangeListener(this);
		add(suwakvol);

		k_etykieta = new JLabel("TV aktualnie nie gra.");
		k_etykieta.setBounds(80, 185, 200, 30);
		add(k_etykieta);

		suwakvol.setEnabled(isWlaczone);
		lewo.setEnabled(isWlaczone);
		prawo.setEnabled(isWlaczone);
		k.setEnabled(isWlaczone);
		polekanal.setEnabled(isWlaczone);

	}

	//Co sie dzieje jak sie wciska przyciski
	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bok)
		{
			//Zatwierdzam dane niech gra
			Wyslij(kanal, vol, isWlaczone);
			JOptionPane.showMessageDialog(null, "Ustawiles kanal: " + kanal + ", vol: " + vol + " stan: " + isWlaczone, "Potwierdzenie ustawienia", JOptionPane.INFORMATION_MESSAGE);
			dispose(); //zamknij okno
		}
		else if (zrodlo == lewo)
		{
			zmienkanal(true); //Zmieniam kanal na wyzszy
		}
		else if (zrodlo == prawo)
		{
			zmienkanal(false); //Na poprzedni kanal ustaw
		}
		else if (zrodlo == wlacznik)
		{
			Wlacz();	//Wlacz TV
		}
		else if (zrodlo == k)
		{
			//Wczytaj wartosc z jTextField
			lastkanal = kanal;
			kanal = Integer.parseInt(polekanal.getText());

			if (kanal < 0 || kanal > 25) //Masz 25 kanalow. To nie jest jakies NC+
			{
				kanal = lastkanal;
				JOptionPane.showMessageDialog(null, "Brak takiego kanalu!", "Brak takiego kanalu", JOptionPane.ERROR_MESSAGE);
			}
			Wyslij(kanal, vol, isWlaczone);
			k_etykieta.setText("Wlaczyles kanal: " + kanal);
		}
	}

	/**
	 * Wlacz wylacz i co sie dzieje wtedy jak to wciskasz
	 */
	private void Wlacz()
	{
		
		laststate = isWlaczone;
		isWlaczone = !isWlaczone;
		Wyslij(kanal, vol, isWlaczone);
		suwakvol.setEnabled(isWlaczone);
		lewo.setEnabled(isWlaczone);
		prawo.setEnabled(isWlaczone);
		k.setEnabled(isWlaczone);
		polekanal.setEnabled(isWlaczone);
		if(!isWlaczone)
		{
			k_etykieta.setText("TV aktualnie nie gra");
		}
		else
		{
			k_etykieta.setText("Wlaczyles kanal: " + kanal);
		}
		
	}

	/**
	 * Zmienia kanal w telewizorze
	 * @param b w zaleznosci od niego jest albo wyzszy albo nizszy
	 */
	private void zmienkanal(boolean b)
	{
		if (b)
		{
			if (kanal > 24)
			{
				kanal = 0;
			}
			kanal++;
		}
		else
		{
			if (kanal < 2)
			{
				kanal = 26;
			}
			kanal--;

		}
		Wyslij(kanal, vol, isWlaczone);
		k_etykieta.setText("Wlaczyles kanal: " + kanal);
	}

	@Override
	public void stateChanged(ChangeEvent ce)
	{
		vol = suwakvol.getValue();
		Wyslij(kanal, suwakvol.getValue(), isWlaczone);
	}
}
