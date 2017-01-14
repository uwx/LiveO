import java.io.File;

import javax.swing.ListCellRenderer;

/**
 * A ListCellRenderer that renders files by their name, without including their extension.
 * 
 * @author Rafael
 *
 */
public class FileCellRenderer extends GenericListCellRenderer<File> implements ListCellRenderer<File> {
    /**
     * 
     */
    private static final long serialVersionUID = 6506492085446882734L;
    
    public FileCellRenderer() {
        super();
    }

    @Override
     protected String stringifyValue(File value) {
        final String n = value.getName();
        int i = n.lastIndexOf('.');
        return n.substring(0, i == -1 ? n.length() : i);
    }

}
