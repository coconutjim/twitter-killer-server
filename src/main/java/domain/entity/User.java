package domain.entity;

import domain.util.UserUtil;

import java.util.Date;

public class User {
    private String login;
    private String passwordHash;
    private String salt;
    private int id;
    private String token;
    private Date expiration;

    /**
     * Нужно доделать, так как при регистрации нового пользователя нужно генерировать salt, token, expiration
     * @param login
     * @param password
     */
    public User(String login, String password) {
        this.login = login;

        //this.salt = "179d4b586a5746a73367f4ed8141ebc71";
        salt = UserUtil.generateSalt();
        this.passwordHash = UserUtil.getSHA256(password, salt);
    }


    public boolean checkPassword(String password) {
        return passwordHash.equals(UserUtil.getSHA256(password, salt));
    }

    public String getName() {
        return login;
    }

    public void setName(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = UserUtil.getSHA256(password, salt);
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

}
