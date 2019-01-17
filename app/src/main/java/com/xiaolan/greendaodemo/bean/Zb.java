package com.xiaolan.greendaodemo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Zb {

    @Id(autoincrement = true)
    private Long id;
    private String name;
    private int age;
    private String nickName;
    private String aa;
    private String bb;
    @Generated(hash = 459343409)
    public Zb(Long id, String name, int age, String nickName, String aa,
            String bb) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.nickName = nickName;
        this.aa = aa;
        this.bb = bb;
    }
    @Generated(hash = 18163174)
    public Zb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getNickName() {
        return this.nickName;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public String getAa() {
        return this.aa;
    }
    public void setAa(String aa) {
        this.aa = aa;
    }
    public String getBb() {
        return this.bb;
    }
    public void setBb(String bb) {
        this.bb = bb;
    }

    @Override
    public String toString() {
        return "Zb{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                ", aa='" + aa + '\'' +
                ", bb='" + bb + '\'' +
                '}';
    }
}
