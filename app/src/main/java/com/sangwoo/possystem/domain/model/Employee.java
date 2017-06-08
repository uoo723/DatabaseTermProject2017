package com.sangwoo.possystem.domain.model;

public final class Employee {
    private int id;
    private String name;
    private String employeeId;
    private Rank rank;
    private int totalResult;

    public Employee(int id, String name, String employeeId, Rank rank, int totalResult) {
        this.id = id;
        this.name = name;
        this.employeeId = employeeId;
        this.rank = rank;
        this.totalResult = totalResult;
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

    public int getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
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
    }
}
