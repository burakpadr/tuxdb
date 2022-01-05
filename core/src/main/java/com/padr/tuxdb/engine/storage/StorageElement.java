package com.padr.tuxdb.engine.storage;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class StorageElement {

    // Static variables

    public static final char PREFIX = System.getProperty("os.name").toLowerCase().startsWith("linux")
            || System.getProperty("os.name").toLowerCase().startsWith("mac") ? '/' : '\'';
    private static final String BASE_STORAGE_PATHNAME = "data";

    // Member variables

    protected File file;
    private String pathname;

    public static boolean domainIsSuited(String domain) {
        Pattern pattern = Pattern.compile(".*[^-_.A-Za-z0-9].*");
        Matcher matcher = pattern.matcher(domain);

        return !domain.isEmpty() && !matcher.matches();
    }

    public StorageElement(String pathname) {
        this.pathname = pathname;

        file = new File(String.format("%s%s", BASE_STORAGE_PATHNAME, pathname));
    }

    public String getDomain() {
        return file.getName();
    }

    // This function gives the absolute pathname after BASE_STORAGE_PATHNAME
    protected String getAbosultePathname() {
        return pathname;
    }

    public void setDomain(String newDomain) {
        File newFile = new File(file.getParent(), newDomain);

        file.renameTo(newFile);

        file = newFile;
    }

    public boolean isExist() {
        return file.exists();
    }

    public abstract void create() throws IOException;

    public abstract Object read() throws IOException;

    public abstract void update(Map<String, Object> updateData) throws IOException;

    public abstract void delete();

    @Override
    public int hashCode() {
        return file.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        else if (!(object instanceof StorageElement))
            return false;
        else if (this == object)
            return true;

        StorageElement objectStorageElement = (StorageElement) object;

        return getAbosultePathname().equals(objectStorageElement.getAbosultePathname());
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
