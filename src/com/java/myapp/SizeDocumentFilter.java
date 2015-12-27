package com.java.myapp;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.JTextComponent;

public class SizeDocumentFilter extends ChainedDocumentFilter {

    private int size;
    private boolean autoTab = true;

    public SizeDocumentFilter() {
        this(0);
    }

    public SizeDocumentFilter(int size) {
        this(size, null);
    }

    public SizeDocumentFilter(int size, DocumentFilter filter) {
        super(filter);
        setSize(size);
    }

    public boolean getAutoTab() {
        return autoTab;
    }

    public void setAutoTab(boolean autoTab) {
        this.autoTab = autoTab;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
            throws BadLocationException {
        int possibleSize = fb.getDocument().getLength() + str.length();
        int allowedSize = getAllowedSize(fb);

        if (possibleSize <= allowedSize) {
            super.insertString(fb, offs, str, a);
            handleAutoTab(possibleSize, allowedSize, fb);
        } else {
            provideErrorFeedback();
        }
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
            throws BadLocationException {
        int possibleSize = fb.getDocument().getLength() + str.length() - length;
        int allowedSize = getAllowedSize(fb);

        if (possibleSize <= allowedSize) {
            super.replace(fb, offs, length, str, a);
            handleAutoTab(possibleSize, allowedSize, fb);
        } else {
            provideErrorFeedback();
        }
    }

    private int getAllowedSize(FilterBypass fb) {
        return (size == 0) ? getColumns(fb) : size;
    }

    private int getColumns(FilterBypass fb) {

        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

        if (c != null && c instanceof JTextField) {
            JTextField textField = (JTextField) c;
            Document doc = textField.getDocument();

            if (doc.equals(fb.getDocument())) {
                return textField.getColumns();
            }
        }

        return 0;
    }

    protected void handleAutoTab(int possibleSize, int allowedSize, FilterBypass fb) {
        if (autoTab == false
                || possibleSize != allowedSize) {
            return;
        }

        Component c = KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();

        if (c != null && c instanceof JTextComponent) {
            JTextComponent component = (JTextComponent) c;
            Document doc = component.getDocument();

            if (doc.equals(fb.getDocument())) {
                c.transferFocus();
            }
        }
    }
}
