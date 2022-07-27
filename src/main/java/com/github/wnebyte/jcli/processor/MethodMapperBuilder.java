package com.github.wnebyte.jcli.processor;

import com.github.wnebyte.jarguments.adapter.AbstractTypeAdapterRegistry;

public class MethodMapperBuilder {

    private InstanceTracker tracker;

    private AbstractTypeAdapterRegistry adapters = null;

    public MethodMapperBuilder setInstanceTracker(InstanceTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public MethodMapperBuilder setTypeAdapterRegistry(AbstractTypeAdapterRegistry adapters) {
        this.adapters = adapters;
        return this;
    }

    public MethodMapperImpl build() {
        if (tracker == null || adapters == null) {
            throw new NullPointerException(
                    "InstanceTracker and/or AdapterRegistry may not be null."
            );
        }
        return new MethodMapperImpl(tracker, adapters);
    }
}
