
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Przecudowny jedyny w swoim rodzaju panel do logowania <3
 *
 */
public class Logowanie extends JFrame implements ActionListener
{

	JButton bLog, bAnal;	//buttony
	JPasswordField haslo; //turbo tajne okno do haslowania
	JTextField uzyt;		//turbo znane okno do uzytkownika

	/**
	 * Sól tej klasy, funkcja do logowania sie i mozliwosci dalszej pracy w programie
	 */
	public void ZalogujSie()
	{
		//Jezeli jestes polaczony do internetu
		if (SprawdzLacze())
		{
			//i Jezeli wpisales poprawnego uzytkownika i haslo
			if (SprawdzLogin(uzyt.getText(), String.valueOf(haslo.getPassword())))
			{
				//To otworz ty mi Okno glowne
				OknoGlowne okno = new OknoGlowne();
				dispose(); // a potem zamknij mi je zeby sie nie paletalo bez potrzeby
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Zla nazwa uzytkownika lub haslo!", "Blad Polaczenia!", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Brak polaczenia z internetem!", "Blad Polaczenia!", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Teoretycznie sprawdza polaczenie z interentem. W praktyce... no coz.
	 * @return true jak polaczy z netem, false jak nie ma takiego polaczenia
	 */
	private static boolean SprawdzLacze()
	{
		return true;
	}

	/**
	 * Teoretycznie sprawdza w bazie danych czy jest taki uzytkownik o zadanym loginie i hasle
	 * @param nazwa nazwa uzytkownika
	 * @param password odpowiednie do niego haslo
	 * @return true jak taki uzytkownik istnieje, false jak nie ma
	 */
	private static boolean SprawdzLogin(String nazwa, String password)
	{
		boolean login = false;
		if (nazwa.equals("admin") && password.equals("admin"))
		{
			login = true;
		}
		return login;
	}

	/**
	 * Konstuktor do okna logowania
	 */
	public Logowanie()
	{
		//Ustaw parametry okna
		setSize(250, 250);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		setTitle("Zaloguj sie!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);

		//Etykieta z napisem "LOGOWANIE"
		JLabel tytul = new JLabel("LOGOWANIE");
		tytul.setBounds(35, 20, 120, 20);
		add(tytul);

		bLog = new JButton("Zaloguj sie!");
		bLog.setBounds(10, 170, 100, 20);
		bLog.addActionListener(this);
		add(bLog);

		bAnal = new JButton("Anuluj");
		bAnal.setBounds(120, 170, 100, 20);
		bAnal.addActionListener(this);
		add(bAnal);

		JLabel lUzyt = new JLabel("Nazwa użytkownika:");
		lUzyt.setBounds(35, 60, 120, 20);
		add(lUzyt);

		uzyt = new JTextField("admin");
		uzyt.setBounds(35, 80, 150, 20);
		add(uzyt);

		JLabel lHaslo = new JLabel("Haslo:");
		lHaslo.setBounds(35, 100, 120, 20);
		add(lHaslo);

		haslo = new JPasswordField("admin");
		haslo.setBounds(35, 120, 150, 20);
		add(haslo);

		SwingUtilities.updateComponentTreeUI(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bAnal)
		{
			dispose(); //Jezeli nie chcesz sie logowac to nara
		}
		else if (zrodlo == bLog)
		{
			ZalogujSie();	//Ale jak dasz OK to loguj sie!
		}
	}
}
