import java.util.List;

public class Knife {
    private String type;
    private String handy;
    private String origin;
    private List<Visual> visual;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandy() {
        return handy;
    }

    public void setHandy(String handy) {
        this.handy = handy;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<Visual> getVisual() {
        return visual;
    }

    public void setVisual(List<Visual> visual) {
        this.visual = visual;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

