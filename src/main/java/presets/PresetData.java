package presets;

public class PresetData {

    private String name;
    private byte[] secret;
    String text;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSecret(byte[] secret) {
        this.secret = secret;
    }

    public byte[] getSecret() {
        return this.secret;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

}
