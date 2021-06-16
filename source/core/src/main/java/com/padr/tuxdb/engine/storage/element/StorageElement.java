package com.padr.tuxdb.engine.storage.element;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class StorageElement {

    private static final String STORAGE_BASE_PATH = System.getProperty("user.home") + "/.tuxdb/storage";

    private File file;

    public static boolean domainIsSuited(String domain) {
        Pattern pattern = Pattern.compile(".*[^-_.A-Za-z0-9].*");
        Matcher matcher = pattern.matcher(domain);

        return !matcher.matches();
    }

    public StorageElement(String superDirectory, String domain) {
        if (!superDirectory.startsWith("/") || domain.contains("/"))
            return;

        file = new File(String.format("%s%s/%s", STORAGE_BASE_PATH, superDirectory, domain));
    }

    public String getDomain() {
        return file.getName();
    }

    protected String getSuperDirectory() {
        return file.getParent();
    }

    protected File getFile() {
        return file;
    }

    public void setDomain(String newDomain) {
        if (!isExist())
            return;

        File newFile = new File(String.format("%s/%s", getSuperDirectory(), newDomain));

        if (newFile.exists())
            return;

        file.renameTo(newFile);

        file = newFile;
    }

    public boolean isExist() {
        return file.exists();
    }

    public abstract void create() throws IOException;

    public abstract Object read() throws IOException;

    public abstract void update(Map<String, Object> updateData) throws IOException;

    public abstract void delete() throws IOException;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        else if (!(obj instanceof StorageElement))
            return false;
        else if (this == obj)
            return true;
        else {
            StorageElement storageElement = (StorageElement) obj;

            return getSuperDirectory().equals(storageElement.getSuperDirectory())
                    && getDomain().equals(storageElement.getDomain());
        }
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash += getSuperDirectory().hashCode();
        hash += getDomain().hashCode();

        return hash;
    }
}
