
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author kastel
 */
class SwiatloManager extends JDialog implements ActionListener
{
	private JButton bok, bcancel;
	//Checkboxy z odpowiednimi pokojami
	public JCheckBox bPokoj1, bPokoj2, bPokoj3, bPokoj4;
	//Swiatlo w analogicznych pokojach
	public boolean p1, p2, p3, p4;

	/**
	 * Wyslij potrzebne info do urzadzenia
	 * @param pokoj O chlopie, caly checkBox mu wyslij zeby wiadomo co sie dzieje
	 */
	private void Wyslij(JCheckBox pokoj)
	{
		//CheckBox zawiera w sobie wszystko wiec tak jest lepiej
		System.out.println("Swiatlo w: "+pokoj.getText()+" "+pokoj.isSelected());
	}

	/**
	 * Konstruktor klasy SwiatloManager podwladnego klasy OknoGlowne
	 * @param wlasciciel JFrame OknaGlownego
	 */
	public SwiatloManager(JFrame wlasciciel)
	{
		super(wlasciciel, "Manager Oswietlenia", true);
		setSize(300, 300);
		setLayout(null);
		setResizable(false);	//nie da sie rozciagac

		//Wszytkie checkboxy
		bPokoj1 = new JCheckBox("Gabinet");
		bPokoj1.setBounds(30, 30, 100, 20);
		add(bPokoj1);
		bPokoj1.addActionListener(this);

		bPokoj2 = new JCheckBox("Kuchnia");
		bPokoj2.setBounds(30, 50, 100, 20);
		add(bPokoj2);
		bPokoj2.addActionListener(this);

		bPokoj3 = new JCheckBox("Lazienka");
		bPokoj3.setBounds(30, 70, 100, 20);
		add(bPokoj3);
		bPokoj3.addActionListener(this);

		bPokoj4 = new JCheckBox("Salon");
		bPokoj4.setBounds(30, 90, 100, 20);
		add(bPokoj4);
		bPokoj4.addActionListener(this);

		bok = new JButton("OK");
		bok.setBounds(120, 220, 70, 20);
		add(bok);
		bok.addActionListener(this);

		bcancel = new JButton("Anuluj");
		bcancel.setBounds(196, 220, 70, 20);
		add(bcancel);
		bcancel.addActionListener(this);
		
		//Ustaw domyslne swiatla takie jak sa w pokojach
		p1 = bPokoj1.isSelected();
		p2 = bPokoj2.isSelected();
		p3 = bPokoj3.isSelected();
		p4 = bPokoj4.isSelected();	

		SwingUtilities.updateComponentTreeUI(this);
	}

	@Override
	public void actionPerformed(ActionEvent ae)
	{
		Object zrodlo = ae.getSource();
		if (zrodlo == bok) //Zatwierdzam zmiany
		{
			p1 = bPokoj1.isSelected();
			p2 = bPokoj2.isSelected();
			p3 = bPokoj3.isSelected();
			p4 = bPokoj4.isSelected();
			JOptionPane.showMessageDialog(null, "Ustawienia zostaly zapisane", "Potwierdzenie ustawienia", JOptionPane.INFORMATION_MESSAGE);
			dispose(); //Zamykam okno
			
		}
		else if (zrodlo == bcancel) //Anuluje zmiany
		{	
			bPokoj1.setSelected(p1);
			bPokoj2.setSelected(p2);
			bPokoj3.setSelected(p3);
			bPokoj4.setSelected(p4);
			JOptionPane.showMessageDialog(null, "Ustawienia zostaly anulowane", "Anulowales", JOptionPane.WARNING_MESSAGE);
			Wyslij(bPokoj1);
			Wyslij(bPokoj2);
			Wyslij(bPokoj3);
			Wyslij(bPokoj4);	
			dispose();
		}
		else if (zrodlo == bPokoj1)
		{
			Wyslij(bPokoj1);	//Zapalam lub gasze w gabinecie
		}
		else if (zrodlo == bPokoj2)
		{
			Wyslij(bPokoj2);	//W kuchni
		}
		else if (zrodlo == bPokoj3)
		{
			Wyslij(bPokoj3);	//W lazience
		}
		else if (zrodlo == bPokoj4)
		{
			Wyslij(bPokoj4);	//W salonie
		}
	}
}
