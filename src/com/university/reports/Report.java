package com.university.reports;

public abstract class Report {
    protected String title;

    public Report(String title) {
        this.title = title;
    }

    public abstract void generate();

    public void printHeader() {
        System.out.println("\n==============================");
        System.out.println(title);
        System.out.println("==============================");
    }
}
