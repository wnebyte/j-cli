package com.github.wnebyte.jcli.processor;

import com.github.wnebyte.jarguments.factory.AbstractArgumentFactoryBuilder;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;

public class MethodMapperBuilder {

    private InstanceTracker tracker;

    private AbstractArgumentFactoryBuilder builder = new ArgumentFactoryBuilder();

    public MethodMapperBuilder setInstanceTracker(InstanceTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public MethodMapperBuilder setArgumentFactoryBuilder(AbstractArgumentFactoryBuilder builder) {
        if (builder != null) {
            this.builder = builder;
        }
        return this;
    }

    public MethodMapperImpl build() {
        if (tracker == null) {
            throw new NullPointerException(
                    "InstanceTrackerImpl may not be null."
            );
        }
        return new MethodMapperImpl(tracker, builder);
    }
}
