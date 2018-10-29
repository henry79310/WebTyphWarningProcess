package cwb.webtyphwarn.config;

public enum ResourceEnum {

	SEA("2"),
	LAND("6"),
	END("30");

	private String value;
	
    private ResourceEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
