package sde.utils;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class GenericFileFilter extends FileFilter implements FilenameFilter {

    private ArrayList<String> filters = null;
    private String description = null;

    public GenericFileFilter() {
        this.filters = new ArrayList<>();
    }

    public GenericFileFilter(String extension, String description) {
        this();
        addExtension(extension);
        setDescription(description);
    }

    @Override
    public boolean accept(File f) {

        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }

            String extension = getExtension(f);
            if (extension != null && filters.contains(extension)) return true;
        }

        return false;
    }

    @Override
    public boolean accept(File dir, String name) {

        if (name.indexOf(".") >= 0) name = name.substring(name.indexOf(".") + 1);
        if (filters.contains(name)) {
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {

        StringBuffer description = new StringBuffer();
        if (this.description != null) {
            description.append(this.description + " (");
        }

        for (int j = 0; j < filters.size(); j++) {

            if (j > 0) description.append(", ");
            description.append("*." + filters.get(j));
        }
        description.append(")");

        return description.toString();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtension(File file) {

        if (file != null) {
            String filename = file.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1) return filename.substring(i + 1).toLowerCase();
        }
        return null;
    }

    public void addExtension(String extension) {

        filters.add(extension.toLowerCase());
    }
}
