package com.esolution.vastrabasic.apis.request;

public class FollowerRequest {
    int designerId;
    int shopperId;

    public FollowerRequest(int designerId, int shopperId) {
        this.designerId = designerId;
        this.shopperId = shopperId;
    }
}
