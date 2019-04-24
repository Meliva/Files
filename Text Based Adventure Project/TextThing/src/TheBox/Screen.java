/*
package TheBox;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javafx.application.Application;

public class Screen
{
	private JFrame Frame;
	private Scanner kb;
	//-------------------------------------------------------
	private JPanel ResPanel;
	private JButton screen1;
	private JButton screen2;
	private JButton screen3;
	private JButton screen4;
	//-------------------------------------------------------
	private JPanel HomePanel;
	private JButton MoveTo;
	private JButton CharacterType1;
	private Stats Character;
	//-------------------------------------------------------
	private JPanel Panel1;
	private JTextField Actions;
	private JTextField Command;
	private JTextField Status;
	private String Peep;
	private JLabel Key;
	//-------------------------------------------------------
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					Screen window = new Screen();
					window.Frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	public Screen()
	{
		Activate();
		kb = new Scanner(System.in);
		Character = null;
	}
	private void Activate() 
	{
		Frame = new JFrame();
		Frame.setBounds(100, 100, 600, 300);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Frame.getContentPane().setLayout(null);
		Frame.getContentPane().setLayout(new CardLayout(0, 0));
		//-------------------------------------------------------
		ResPanel = new JPanel();
		ResPanel.setToolTipText("");
		Frame.getContentPane().add(ResPanel, "ResPanel");
		ResPanel.setLayout(null);
		screen1 = new JButton("1360|900");
		screen1.setBounds(125, 100, 100, 50);
		ResPanel.add(screen1);
		screen1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Frame.setBounds(100, 100, 1360, 900);
				ResPanel.setVisible(false);
				HomePanel.setVisible(true);
			}
		});
		screen2 = new JButton("1600|900");
		screen2.setBounds(225, 100, 100, 50);
		ResPanel.add(screen2);
		screen2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Frame.setBounds(100, 100, 1600, 900);
				ResPanel.setVisible(false);
				HomePanel.setVisible(true);
			}
		});
		screen3 = new JButton("1600|1024");
		screen3.setBounds(325, 100, 100, 50);
		ResPanel.add(screen3);
		screen3.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Frame.setBounds(100, 100, 1600, 1024);
				ResPanel.setVisible(false);
				HomePanel.setVisible(true);
			}
		});
		screen4 = new JButton("1680|1050");
		screen4.setBounds(425, 100, 100, 50);
		ResPanel.add(screen4);
		screen4.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Frame.setBounds(100, 100, 1680, 1050);
				ResPanel.setVisible(false);
				HomePanel.setVisible(true);
			}
		});
		//-------------------------------------------------------
		HomePanel = new JPanel();
		HomePanel.setToolTipText("Tip");
		Frame.getContentPane().add(HomePanel, "HomePage");
		MoveTo = new JButton("MoveTo");
		MoveTo.setBounds(200, 150, 100, 30);
		HomePanel.add(MoveTo);
		MoveTo.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				HomePanel.setVisible(false);
				Panel1.setVisible(true);
			}
		});
		CharacterType1 = new JButton("5-5");
		CharacterType1.setBounds(500, 150, 100, 30);
		HomePanel.add(CharacterType1);
		CharacterType1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int Health = 5;
				int Armour = 5;
				Character = new Stats(Health, Armour);
				//System.out.println(Character);
			}
		});
		//-------------------------------------------------------
		Panel1 = new JPanel();
		Frame.getContentPane().add(Panel1, "Panel1");
		Panel1.setLayout(null);
		Actions = new JTextField();
		Command = new JTextField();
		Status = new JTextField();
		Status.setBounds(300, 300, 200, 200);
		Panel1.add(Status);

		Status.setText(Peep);
		Key = new JLabel();
		//-------------------------------------------------------
	}
}
*/