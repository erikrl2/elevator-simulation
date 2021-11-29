package com.erik.fahrstuhl;

public class Fahrstuhl {

	// Anfang Attribute
	private int zustand = 0;
	private char motor;
	private boolean rufEG;
	private boolean ruf1OG;
	private boolean ruf2OG;
	private boolean istEG;
	private boolean ist1OG;
	private boolean ist2OG;
	// Ende Attribute

	// Anfang Methoden

	public void setRufEG(boolean rufEG) {
		this.rufEG = rufEG;
	}

	public void setRuf1OG(boolean ruf1OG) {
		this.ruf1OG = ruf1OG;
	}

	public void setRuf2OG(boolean ruf2OG) {
		this.ruf2OG = ruf2OG;
	}

	public void setIstEG(boolean istEG) {
		this.istEG = istEG;
	}

	public void setIst1OG(boolean ist1OG) {
		this.ist1OG = ist1OG;
	}

	public void setIst2OG(boolean ist2OG) {
		this.ist2OG = ist2OG;
	}

	public int folgezustand() {

		// System.out.println("Vorher: " + zustand);
		// System.out.println("Ruf: " + rufEG + " " + ruf1OG + " " + ruf2OG);
		// System.out.println("IST: " + istEG + " " + ist1OG + " " + ist2OG);

		switch (zustand) {

		case 0: // Steht im EG
			if (ruf1OG) {
				zustand = 3;
				ruf1OG = false;
			}
			if (ruf2OG) {
				zustand = 3;
				ruf2OG = false;
			}
			istEG = true;
			break;
		case 1: // Steht im 1. OG
			if (ruf2OG) {
				zustand = 3; // hoch
				ruf2OG = false;
			}
			if (rufEG) {
				zustand = 4; // runter
				rufEG = false;
			}
			ist1OG = true;
			break;
		case 2: // Steht im 2. OG
			if (ruf1OG) {
				zustand = 4; // runter
				ruf1OG = false;
			}
			if (rufEG) {
				zustand = 4; // runter
				rufEG = false;
			}
			ist2OG = true;
			break;
		case 3: // faehrt hoch
			if (ist1OG) {
				zustand = 1;
			}
			if (ist2OG) {
				zustand = 2;
			}
			istEG = false;
			ist1OG = false;
			ist2OG = false;
			break;
		case 4: // faehrt runter
			if (ist1OG) {
				zustand = 1;
			}
			if (istEG) {
				zustand = 0;
			}
			istEG = false;
			ist1OG = false;
			ist2OG = false;
			break;
		default:
			zustand = 99;
		}

		// System.out.println("Nachher: " + zustand);
		// System.out.println("Ruf: " + rufEG + " " + ruf1OG + " " + ruf2OG);
		// System.out.println("IST: " + istEG + " " + ist1OG + " " + ist2OG);
		// System.out.println("----------------------------------------");
		return zustand;
	}

	public char ausgabe() {

		switch (zustand) {

		case 0:
		case 1:
		case 2:
			motor = '0';
			break; // Steht
		case 3:
			motor = 'H';
			break; // Hochfahren
		case 4:
			motor = 'R';
			break; // Runterfahren
		default:
			motor = 'F'; // Fehler

		}

		return motor;

	}

	// Ende Methoden
} // end of Fahrstuhl
