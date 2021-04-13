package model;

public class User {
    final private long id;
    final private String name;

    public User(final long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "User {"
                + "id=" + this.id
                + ", name=" + this.name
                + "}";
    }
}
