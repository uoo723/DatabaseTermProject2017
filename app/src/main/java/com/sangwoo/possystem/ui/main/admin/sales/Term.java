package com.sangwoo.possystem.ui.main.admin.sales;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Term {

    private Date date;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Term(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return dateFormat.format(date);
    }
}
