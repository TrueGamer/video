package com.huanxi.renrentoutiao.model.bean;

/**
 * Created by Dinosa on 2018/1/31.
 */

public class UserBean {


    /**
     * addtime :
     * avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTI7rN1s2LpobRt3l4qTZJJ1Y1ko7YROqmjtF8A06de99Thjb5XJ6sJVYqfSAQ3ARKlqyb1uvmQ9Bw/132
     * city :
     * country :
     * email : 546861506@qq.com
     * id : 77
     * integral : 20
     * invitationcode :
     * money : 0
     * nickname : Goo
     * openid : oiSc40yaWxnkN8VSYlGvkmbXYz24
     * phone : 18186532593
     * phonetype : 0
     * province :
     * sex : 1
     * userid : 81
     */

    private String addtime;
    private String avatar;
    private String city;
    private String country;
    private String email;
    private String integral;
    private String invitationcode;
    private String money;
    private String nickname;
    private String openid;
    private String phone;
    private String phonetype;
    private String province;
    private String sex;
    private String userid;
    private String birthday;
    private String friend;


    private String token;

    private int limitmoney; //限额，单位元

    public int getLimitmoney() {
        return limitmoney;
    }

    public void setLimitmoney(int limitmoney) {
        this.limitmoney = limitmoney;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //记录TabMenu里面的内容；


    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getInvitationcode() {
        return invitationcode;
    }

    public void setInvitationcode(String invitationcode) {
        this.invitationcode = invitationcode;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhonetype() {
        return phonetype;
    }

    public void setPhonetype(String phonetype) {
        this.phonetype = phonetype;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
