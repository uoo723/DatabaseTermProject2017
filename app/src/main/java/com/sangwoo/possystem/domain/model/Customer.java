package com.sangwoo.possystem.domain.model;

import java.util.Date;

public final class Customer {
    private int id;
    private String name;
    private Date birthDate;
    private String phoneNum;
    private Level level;
    private int purchaseAmount;

    public Customer(int id, String name, Date birthDate, String phoneNum, Level level, int purchaseAmount) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNum = phoneNum;
        this.level = level;
        this.purchaseAmount = purchaseAmount;
    }

    public Customer(String name, Date birthDate, String phoneNum, Level level, int purchaseAmount) {
        this(-1, name, birthDate, phoneNum, level, purchaseAmount);
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(int purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public enum Level {
        GOLD("Gold"), SILVER("Silver"), BRONZE("Bronze"), NORMAL("Normal");

        private final String value;

        Level(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Level parse(String levelStr) {
            Level level;

            if (Level.GOLD.getValue().equals(levelStr)) {
                level = Level.GOLD;
            } else if (Level.SILVER.getValue().equals(levelStr)) {
                level = Level.SILVER;
            } else if (Level.BRONZE.getValue().equals(levelStr)) {
                level = Level.BRONZE;
            } else if (Level.NORMAL.getValue().equals(levelStr)) {
                level = Level.NORMAL;
            } else {
                throw new IllegalArgumentException("Cannot convert to Level");
            }

            return level;
        }

        public static int getPurchaseAmount(Level level) {
            switch (level) {
            case GOLD:
                return 1000000;
            case SILVER:
                return 500000;
            case BRONZE:
                return 300000;
            default:
                return 0;
            }
        }


        @Override
        public String toString() {
            return getValue();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", birthDate: " + birthDate + ", phoneNum: " + phoneNum + ", level: " +
                level + ", purchaseAmount: " + purchaseAmount;
    }
}
