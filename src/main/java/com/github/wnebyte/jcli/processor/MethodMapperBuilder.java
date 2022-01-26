package com.github.wnebyte.jcli.processor;

import com.github.wnebyte.jarguments.factory.AbstractArgumentFactoryBuilder;
import com.github.wnebyte.jarguments.factory.ArgumentFactoryBuilder;

public class MethodMapperBuilder {

    private IInstanceTracker tracker;

    private AbstractArgumentFactoryBuilder builder = new ArgumentFactoryBuilder();

    public MethodMapperBuilder setInstanceTracker(IInstanceTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public MethodMapperBuilder setArgumentFactoryBuilder(AbstractArgumentFactoryBuilder builder) {
        if (builder != null) {
            this.builder = builder;
        }
        return this;
    }

    public MethodMapper build() {
        if (tracker == null) {
            throw new NullPointerException(
                    "InstanceTracker may not be null."
            );
        }
        return new MethodMapper(tracker, builder);
    }
}
