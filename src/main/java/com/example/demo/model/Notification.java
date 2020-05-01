package com.example.demo.model;

public class Notification {
    private Integer id;

    private Integer notifier;

    private Integer receiver;

    private Integer outerId;

    private Integer type;

    private Long gmtCreate;

    private Integer status;

    public Notification(Integer id, Integer notifier, Integer receiver, Integer outerId, Integer type, Long gmtCreate, Integer status) {
        this.id = id;
        this.notifier = notifier;
        this.receiver = receiver;
        this.outerId = outerId;
        this.type = type;
        this.gmtCreate = gmtCreate;
        this.status = status;
    }

    public Notification() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotifier() {
        return notifier;
    }

    public void setNotifier(Integer notifier) {
        this.notifier = notifier;
    }

    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    public Integer getOuterId() {
        return outerId;
    }

    public void setOuterId(Integer outerId) {
        this.outerId = outerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}