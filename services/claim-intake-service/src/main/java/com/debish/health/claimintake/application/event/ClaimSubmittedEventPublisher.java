package com.debish.health.claimintake.application.event;

import com.debish.health.claimintake.domain.model.Claim;

public interface ClaimSubmittedEventPublisher {

    void publish(Claim claim);
}
