package route;

public class Address {

	private String name;
	private int number;
	
	public Address(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override
	public String toString() {
		if(number != 0) {
			return name + ", " + number;
		} else {
			return name + ", s/n";
		}
	}
}
