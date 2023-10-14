package cards;

public class Card<E> {
	/* Instance variables */
	public E type;
	
	/* Constructor */
	public Card(E type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return this.type.toString();
	}
}