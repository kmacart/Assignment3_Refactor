package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * The type Form layout.
 */
public class FormLayout {

    private GridBagConstraints lastConstraints = null;
    private GridBagConstraints middleConstraints = null;
    private GridBagConstraints labelConstraints = null;
    private GridBagConstraints fullConstraints = null;

    /**
     * Instantiates a new Form layout.
     */
    public FormLayout() {
        // Set up the constraints for the "last" field in each
        // row first, then copy and modify those constraints.

        // weightx is 1.0 for fields, 0.0 for labels
        // gridwidth is REMAINDER for fields, 1 for labels
        lastConstraints = new GridBagConstraints();

        // Stretch components horizontally (but not vertically)
        lastConstraints.fill = GridBagConstraints.HORIZONTAL;

        // Components that are too short or narrow for their space
        // Should be pinned to the northwest (upper left) corner
        lastConstraints.anchor = GridBagConstraints.NORTHWEST;

        // Give the "last" component as much space as possible
        lastConstraints.weightx = 1.0;

        // Give the "last" component the remainder of the row
        lastConstraints.gridwidth = GridBagConstraints.REMAINDER;

        // Add a little padding
        lastConstraints.insets = new Insets(1, 1, 1, 1);

        // Now for the "middle" field components
        middleConstraints = (GridBagConstraints) lastConstraints.clone();

        // These still get as much space as possible, but do
        // not close out a row
        middleConstraints.gridwidth = GridBagConstraints.RELATIVE;

        // And finally the "label" constrains, typically to be
        // used for the first component on each row
        labelConstraints = (GridBagConstraints) lastConstraints.clone();

        // Give these as little space as necessary
        labelConstraints.weightx = 0.0;
        labelConstraints.gridwidth = 1;

        fullConstraints = (GridBagConstraints) lastConstraints.clone();

        // Give the "last" component the remainder of the row
        fullConstraints.gridwidth = GridBagConstraints.LINE_END;

        // Add a little padding
        fullConstraints.insets = new Insets(1, 1, 1, 1);

    }

    /**
     * Adds a field component. Any component may be used. The
     * component will be stretched to take the remainder of
     * the current row.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addLastField(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        c.setFont(new Font("Arial", Font.PLAIN, 15));
        gbl.setConstraints(c, lastConstraints);
        parent.add(c);
    }

    /**
     * Add full field.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addFullField(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        c.setFont(new Font("Arial", Font.PLAIN, 15));
        gbl.setConstraints(c, fullConstraints);
        parent.add(c);
    }

    /**
     * Add last field.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addLastField(Container c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        c.setFont(new Font("Arial", Font.PLAIN, 15));
        gbl.setConstraints(c, lastConstraints);
        parent.add(c);
    }

    /**
     * Adds an arbitrary label component, starting a new row
     * if appropriate. The width of the component will be set
     * to the minimum width of the widest component on the
     * form.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addLabelC(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, labelConstraints);
        parent.add(c);
    }

    /**
     * Add label c.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addLabelC(Container c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, labelConstraints);
        parent.add(c);
    }

    /**
     * Adds a JLabel with the given string to the label column
     *
     * @param s      the s
     * @param parent the parent
     * @return the j label
     */
    public JLabel addLabel(String s, Container parent) {
        JLabel c = new JLabel(s);
        c.setFont(new Font("Arial", Font.PLAIN, 20));
        c.setHorizontalAlignment(SwingConstants.RIGHT);
        c.setVerticalAlignment(SwingConstants.BOTTOM);
        c.setBorder(new EmptyBorder(0,0,10,10));
        addLabelC(c, parent);
        return c;
    }

    /**
     * Adds a "middle" field component. Any component may be
     * used. The component will be stretched to take all of
     * the space between the label and the "last" field. All
     * "middle" fields in the layout will be the same width.
     *
     * @param c      the c
     * @param parent the parent
     */
    public void addMiddleField(Component c, Container parent) {
        GridBagLayout gbl = (GridBagLayout) parent.getLayout();
        gbl.setConstraints(c, middleConstraints);
        parent.add(c);
    }

}
