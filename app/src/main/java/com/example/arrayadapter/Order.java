package com.example.arrayadapter;


public class Order {
    private final int pcImage;
    private final int kbImage;
    private final int msImage;
    private final int mrImage;
    private final int pcAmount;
    private final String pcSet;
    private final String mouse;
    private final String keyboard;
    private final String monitor;
    private final String username;
    private final long orderDate;
    private final Double price;

    Order(String pcSet,String keyboard,String mouse,String monitor,String username,
          int pcImage, int kbImage,  int msImage, int mrImage,int pcAmount
            ,Double price){
        this.pcImage = pcImage;
        this.pcAmount = pcAmount;
        this.pcSet = pcSet;
        this.username = username;
        this.price = price;
        this.mouse = mouse;
        this.keyboard = keyboard;
        this.monitor = monitor;
        this.kbImage = kbImage;
        this.msImage = msImage;
        this.mrImage = mrImage;
        orderDate = System.currentTimeMillis();
    }

    public int getPcImage() {
        return pcImage;
    }

    public int getKbImage() {
        return kbImage;
    }

    public int getMsImage() {
        return msImage;
    }

    public int getMrImage() {
        return mrImage;
    }

    public int getPcAmount() {
        return pcAmount;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public String getPcSet() {
        return pcSet;
    }

    public String getMouse() {
        return mouse;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public String getMonitor() {
        return monitor;
    }

    public String getUsername() {
        return username;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "pcImage=" + pcImage +
                ", kbImage=" + kbImage +
                ", msImage=" + msImage +
                ", mrImage=" + mrImage +
                ", pcAmount=" + pcAmount +
                ", orderDate=" + orderDate +
                ", pcSet='" + pcSet + '\'' +
                ", mouse='" + mouse + '\'' +
                ", keyboard='" + keyboard + '\'' +
                ", monitor='" + monitor + '\'' +
                ", username='" + username + '\'' +
                ", price=" + price +
                '}';
    }
}
