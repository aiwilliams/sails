package org.opensails.ezfile;

public interface EzFile {
    boolean exists();

    boolean isDirectory();

    void save(String text);

    String getPath();

    String text();

    /**
     * Causes the creation of all parent directories. If this is a directory, it
     * will also be created.
     */
    void mkdirs();

    /**
     * A convenience method that converts this EzFile into an EzDir. This will
     * fail if the EzFile is not a directory AND exists.
     * 
     * @return
     */
    EzDir asDir();

    /**
     * @return the EzDir that contains this file, null if this is root.
     */
    EzDir parent();

    /**
     * Creates this file if it doesn't exist. This will not mkdirs on parent.
     */
    void touch();
}
