package ui;

import java.util.ArrayList;
import java.util.Scanner;

public class View {
	private static View view;
	protected enum cancel {ALLOW, DISALLOW};
	protected Scanner input;
	
	/*
	 * Singleton Instance
	 */
	public static View getInstance() {
		if (view == null) {
			view = new View();
		}
		return view;
	}
	
	/*
	 * Force singleton instances to exist
	 * Setup all views
	 */
	public void setup() {
		GameView.getInstance();
		BoardView.getInstance().setup();
		SpecialActionView.getInstance();
		PlayerView.getInstance();
	}
	
	/*
	 * Constructor
	 */
	protected View() {
	    input = new Scanner(System.in);
	}
	
	/*
	 * Provide the player with a list of choices
	 */
	protected Object promptPlayerForItemFromList(ArrayList items, String message, cancel c) {
		int in = -1;
		while (in < 0) {
			System.out.println(message);
			for (int i = 0; i < items.size(); i++) {
				System.out.println("\t[" + (i+1) + "] " + items.get(i));
			}
			
			if (c == cancel.ALLOW) {
				System.out.println("\t[" + (items.size()+1) + "] CANCEL");
			}
			
			try {
				in = Integer.parseInt(input.nextLine());
			} catch (Exception NumberFormatError) {
				in = -1;
			}
			
			if(c == cancel.ALLOW) {
				if (in >= 1 && in <= items.size()) {
					return items.get(in-1);
				} else if (in == items.size() + 1) {
					return null;
				}
				
				System.out.println("Invalid input. Input should be in the range 1 to " + (items.size()+1) + "\n");
			} else {
				if (in >= 1 && in <= items.size()) {
					return items.get(in-1);
				}
				
				System.out.println("Invalid input. Input should be in the range 1 to " + items.size() + "\n");
			}
		}
		return null;
	}
	
	/*
	 * Ask player a yes or no question
	 */
	protected boolean promptYesNo(String message) {
		while (true) {
			System.out.println(message + "\n\t[Y] Yes\n\t[N] No");
			switch (input.nextLine()) {
				case "Y": case "y": return true;
				case "N": case "n": return false;
			}
			System.out.println("Invalid input. Input should be either Y or N");
		}
	}
}
