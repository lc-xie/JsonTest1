package com.example.stephen.jsontest1;

import org.litepal.crud.DataSupport;

/**
 * Created by stephen on 17-4-13.
 */

public class Province extends DataSupport{
    private int id;
    private String name;

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
}
