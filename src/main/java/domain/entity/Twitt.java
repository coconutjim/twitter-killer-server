package domain.entity;

/**
 * Created by Александра on 04.07.14.
 */
public class Twitt {
    private int id;
    private String message;

    private Twitt(){}

    public Twitt(int id, String message)
    {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
