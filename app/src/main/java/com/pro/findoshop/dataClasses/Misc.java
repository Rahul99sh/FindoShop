package com.pro.findoshop.dataClasses;

public class Misc {
    boolean maintainance_shop;
    String desc;

    public Misc(){};

    public Misc(boolean maintainance_user, String desc) {
        this.maintainance_shop = maintainance_user;
        this.desc = desc;
    }

    public boolean isMaintainance_shop() {
        return maintainance_shop;
    }

    public void setMaintainance_shop(boolean maintainance_shop) {
        this.maintainance_shop = maintainance_shop;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
