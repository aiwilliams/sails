package org.opensails.sails.oem;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.IResourceResolver;

public class ResourceResolver implements IResourceResolver {
    protected List<IResourceResolver> resolvers;

    public ResourceResolver() {
        this.resolvers = new ArrayList<IResourceResolver>();
    }

    public void push(IResourceResolver resolver) {
        resolvers.add(0, resolver);
    }

    public InputStream resolve(String identifier) {
        for (IResourceResolver resolver : resolvers) {
            InputStream stream = resolver.resolve(identifier);
            if (stream != null) return stream;
        }
        return null;
    }
}
