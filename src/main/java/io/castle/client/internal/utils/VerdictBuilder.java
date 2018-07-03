package io.castle.client.internal.utils;

import io.castle.client.model.AuthenticateAction;
import io.castle.client.model.Verdict;

public class VerdictBuilder {
    private String action;
    private String userId;
    private boolean failover;
    private String failoverReason;

    private VerdictBuilder() {
    }

    public static VerdictBuilder success() {
        return new VerdictBuilder()
                .withFailover(false);
    }

    public static VerdictBuilder failover(String failoverReason) {
        return new VerdictBuilder()
                .withFailover(true)
                .withFailoverReason(failoverReason);
    }

    public VerdictBuilder withAction(String action) {
        this.action = action;
        return this;
    }

    public VerdictBuilder withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public Verdict build() {
        Verdict verdict = new Verdict();
        verdict.setAction(action);
        verdict.setUserId(userId);
        verdict.setFailover(failover);
        verdict.setFailoverReason(failoverReason);
        return verdict;
    }


    public VerdictBuilder withFailover(boolean failover) {
        this.failover = failover;
        return this;
    }

    public VerdictBuilder withFailoverReason(String failoverReason) {
        this.failoverReason = failoverReason;
        return this;
    }

    public static Verdict fromTransport(VerdictTransportModel transport) {
        return success()
                .withAction(transport.getAction())
                .withUserId(transport.getUserId())
                .build();
    }
}