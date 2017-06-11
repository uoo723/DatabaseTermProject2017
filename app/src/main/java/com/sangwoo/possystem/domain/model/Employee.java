package com.sangwoo.possystem.domain.model;

public final class Employee {
    private int id;
    private String name;
    private String employeeId;
    private Rank rank;

    public Employee(int id, String name, String employeeId, Rank rank) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
        this.rank = rank;
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public enum Rank {
        SUPERVISOR("Supervisor"), STAFF("Staff");

        private final String value;

        Rank(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static Rank parse(String rankStr) {
            Rank rank;

            if (Rank.SUPERVISOR.getValue().equals(rankStr)) {
                rank = Rank.SUPERVISOR;
            } else if (Rank.STAFF.getValue().equals(rankStr)){
                rank = Rank.STAFF;
            } else {
                throw new IllegalArgumentException("Cannot convert to Rank");
            }

            return rank;
        }


        @Override
        public String toString() {
            return getValue();
        }
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", employeeId: " + employeeId + ", rank: " + rank;
    }
}
