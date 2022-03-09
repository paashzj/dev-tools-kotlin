package com.github.shoothzj.dev.module.shell;

public class NetstatResult {

    private String proto;

    private String recvQ;

    private String sendQ;

    private String localAddress;

    private String state;

    public NetstatResult() {
    }

    public String getProto() {
        return proto;
    }

    public void setProto(String proto) {
        this.proto = proto;
    }

    public String getRecvQ() {
        return recvQ;
    }

    public void setRecvQ(String recvQ) {
        this.recvQ = recvQ;
    }

    public String getSendQ() {
        return sendQ;
    }

    public void setSendQ(String sendQ) {
        this.sendQ = sendQ;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "NetstatResult{"
                + "proto='" + proto + '\''
                + ", recvQ='" + recvQ + '\''
                + ", sendQ='" + sendQ + '\''
                + ", localAddress='" + localAddress + '\''
                + ", state='" + state + '\''
                + '}';
    }
}
