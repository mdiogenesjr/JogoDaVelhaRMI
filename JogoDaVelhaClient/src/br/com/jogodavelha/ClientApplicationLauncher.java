package br.com.jogodavelha;

import java.awt.EventQueue;

import br.com.jogodavelha.view.View;

public class ClientApplicationLauncher {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
