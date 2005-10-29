package org.opensails.sails;

import java.io.InputStream;

public interface IResourceResolver {
    InputStream resolve(String identifier);
}
