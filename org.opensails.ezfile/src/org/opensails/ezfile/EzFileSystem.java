package org.opensails.ezfile;

public interface EzFileSystem {
    /**
     * Answers an EzDir for path. The directory may not exist. If it does, and
     * is a file, an exception will be thrown.
     */
    EzDir dir(String path);

    /**
     * Answers an EzFile for path. The file may not exist. The EzFile may be a
     * directory.
     */
    EzFile file(String path);

	EzDir mkdir(String path);
}
