package organization;

public enum Operation {

	READ("read"), 
	CREATE("create"), 
	DELETE("delete"), 
	UPDATE("update");
	
	private final String text;
	
	private Operation(String text) {
		this.text = text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
	public static Operation getEnum(String value) {
		for(Operation v : values()) {
			if(v.toString().equalsIgnoreCase(value)) {
				return v;
			}
		}
		
		throw new IllegalArgumentException("Value " + value + " not supported for operation");
	}
}
