
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.MutableComboBoxModel;

/**
 * The default model for combo boxes.
 *
 * @param <E> the type of the elements of this model
 * @author Arnaud Weber
 * @author Tom Santos
 */

public class ArrayListComboBoxModel<E> extends AbstractListModel<E> implements MutableComboBoxModel<E>, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -9063683997933772897L;
    
    private ArrayList<E> objects;
    private E selectedObject;

    /**
     * Constructs an empty DefaultComboBoxModel object.
     */
    public ArrayListComboBoxModel() {
        objects = new ArrayList<E>();

        selectFirstAvailable();
    }
    
    private ArrayListComboBoxModel(boolean isNull) {
        objects = isNull ? null : new ArrayList<E>();
    }

    /**
     * Constructs a DefaultComboBoxModel object initialized with an array of objects.
     *
     * @param items an array of Object objects
     */
    public ArrayListComboBoxModel(final E items[]) {
        objects = new ArrayList<E>(Arrays.asList(items));

        selectFirstAvailable();
    }

    /**
     * Constructs a DefaultComboBoxModel object initialized with a vector.
     *
     * @param v a Vector object ...
     */
    public ArrayListComboBoxModel(Vector<E> v) {
        objects = new ArrayList<E>(v);

        selectFirstAvailable();
    }

    public ArrayListComboBoxModel(List<E> v) {
        objects = new ArrayList<E>(v);

        selectFirstAvailable();
    }
    
   public static <E> ArrayListComboBoxModel<E> wrap(ArrayList<E> list) {
       ArrayListComboBoxModel<E> model = new ArrayListComboBoxModel<E>(true);
       model.objects = list;
       model.selectFirstAvailable();
       return model;
   }

   private void selectFirstAvailable() {
       if (!objects.isEmpty()) {
           selectedObject = getElementAt(0);
       }
   }

    // implements javax.swing.ComboBoxModel
    /**
     * Set the value of the selected item. The selected item may be null.
     *
     * @param anObject The combo box value or null for no selection.
     */
    @SuppressWarnings("unchecked")
    public void setSelectedItem(Object anObject) {
        try {
            if ((selectedObject != null && !selectedObject.equals(anObject)) || selectedObject == null && anObject != null) {
                selectedObject = (E)anObject;
                fireContentsChanged(this, -1, -1);
            }
        } catch (ClassCastException e) {
            throw new Error("Type: " + anObject.getClass().getName() + " does not extend this class' generic type");
        }
    }

    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem() {
        return selectedObject;
    }

    // implements javax.swing.ListModel
    public int getSize() {
        return objects.size();
    }

    // implements javax.swing.ListModel
    public E getElementAt(int index) {
        return index >= 0 && index < objects.size() ? objects.get(index) : null;
    }

    /**
     * Returns the index-position of the specified object in the list.
     *
     * @param anObject
     * @return an int representing the index position, where 0 is the first position
     */
    public int getIndexOf(Object anObject) {
        return objects.indexOf(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void addElement(E anObject) {
        objects.add(anObject);
        fireIntervalAdded(this, objects.size() - 1, objects.size() - 1);
        if (objects.size() == 1 && selectedObject == null && anObject != null) {
            setSelectedItem(anObject);
        }
    }

    // implements javax.swing.MutableComboBoxModel
    public void insertElementAt(E anObject, int index) {
        objects.set(index, anObject);
        fireIntervalAdded(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElementAt(int index) {
        if (getElementAt(index) == selectedObject) {
            if (index == 0) {
                setSelectedItem(getSize() == 1 ? null : getElementAt(index + 1));
            } else {
                setSelectedItem(getElementAt(index - 1));
            }
        }

        objects.remove(index);

        fireIntervalRemoved(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElement(Object anObject) {
        int index = objects.indexOf(anObject);
        if (index != -1) {
            removeElementAt(index);
        }
    }

    /**
     * Empties the list.
     */
    public void removeAllElements() {
        if (!objects.isEmpty()) {
            int firstIndex = 0;
            int lastIndex = objects.size() - 1;
            objects.clear();
            selectedObject = null;
            fireIntervalRemoved(this, firstIndex, lastIndex);
        } else {
            selectedObject = null;
        }
    }
}
