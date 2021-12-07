package com.github.wnebyte.jcli.processor;

import com.github.wnebyte.jarguments.factory.AbstractArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jarguments.factory.ArgumentCollectionFactoryBuilder;
import com.github.wnebyte.jarguments.util.Objects;
import com.github.wnebyte.jcli.InstanceTracker;

public class MethodTransformationBuilder {

    private InstanceTracker tracker;

    private AbstractArgumentCollectionFactoryBuilder builder = new ArgumentCollectionFactoryBuilder();

    public MethodTransformationBuilder setInstanceTracker(InstanceTracker tracker) {
        this.tracker = tracker;
        return this;
    }

    public MethodTransformationBuilder setArgumentCollectionFactoryBuilder(
            AbstractArgumentCollectionFactoryBuilder builder
    ) {
        if (builder != null) {
            this.builder = builder;
        }
        return this;
    }

    public MethodTransformation build() {
        if (Objects.isNull(tracker, builder)) {
            throw new NullPointerException(
                    ""
            );
        }
        return new MethodTransformation(tracker, builder);
    }
}
