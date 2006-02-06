package org.opensails.ezfile.memory;

class MemoryEzFileImpl {
    protected MemoryEzFileSystem fs;

    protected boolean exists;

    protected boolean isDirectory;

    protected String path;

    protected String text;

    protected MemoryEzFileImpl(MemoryEzFileSystem fs, String path) {
        this.fs = fs;
        this.path = path;
    }

    public MemoryEzFileImpl parent() {
        int last = path.lastIndexOf("/");
        if (last > 0) return fs.fileImpl(path.substring(0, last));
        return fs.fileImpl("/");
    }

    public void mkdirs() {
        parent().becomeDir();
    }

    private void becomeDir() {
        if (!"/".equals(path)) {
            exists = true;
            isDirectory = true;
            parent().becomeDir();
        }
    }

    public void save(String text) {
        exists = true;
        this.text = text;
    }
}
