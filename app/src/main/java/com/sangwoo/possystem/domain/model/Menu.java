package com.sangwoo.possystem.domain.model;

public final class Menu {
    private int id;
    private String name;
    private int price;

    public Menu(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Menu(String name, int price) {
        this(-1, name, price);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", price: " + price;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Menu))
            return false;

        Menu other = (Menu) obj;

        return this.id == other.id && this.name.equals(other.name) && this.price == other.price;
    }
}
