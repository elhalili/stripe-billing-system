package com.elhalili.stripebillingsystem.service;

import com.elhalili.stripebillingsystem.entity.Client;
import com.elhalili.stripebillingsystem.entity.Plan;
import com.elhalili.stripebillingsystem.exception.NotSupportedFeatureException;

public class BusinessServiceImpl implements BusinessService {
    @Override
    public String serviceA(Client client) {
        Plan plan = client.getPlan();
        if (plan.getName().equals("free") || plan.getName().equals("pro")) return "service A";

        throw new NotSupportedFeatureException("feature unavailable");
    }

    @Override
    public String serviceB(Client client) {
        Plan plan = client.getPlan();
        if (plan.getName().equals("pro")) return "service B";

        throw new NotSupportedFeatureException("feature unavailable");
    }
}
