package com.java.myapp;

import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.text.*;

abstract class ChainedDocumentFilter extends DocumentFilter {

    private DocumentFilter filter;

    public ChainedDocumentFilter() {
        this(null);
    }

    public ChainedDocumentFilter(DocumentFilter filter) {
        setFilter(filter);
    }

    public DocumentFilter getFilter() {
        return filter;
    }

    public void setFilter(DocumentFilter filter) {
        this.filter = filter;
    }

    public void installFilter(JTextComponent... components) {
        for (JTextComponent component : components) {
            Document doc = component.getDocument();

            if (doc instanceof AbstractDocument) {
                ((AbstractDocument) doc).setDocumentFilter(this);
            }
        }
    }

    public void uninstallFilter(JTextComponent... components) {
        for (JTextComponent component : components) {
            Document doc = component.getDocument();

            if (doc instanceof AbstractDocument) {
                ((AbstractDocument) doc).setDocumentFilter(null);
            }
        }
    }

    public void provideErrorFeedback() {
        LookAndFeel laf = UIManager.getLookAndFeel();

        if (laf == null) {
            Toolkit.getDefaultToolkit().beep();
        } else {
            KeyboardFocusManager fm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            Component component = fm.getFocusOwner();
            laf.provideErrorFeedback(component);
        }
    }

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
            throws BadLocationException {
        if (filter == null) {
            super.insertString(fb, offs, str, a);
        } else {
            filter.insertString(fb, offs, str, a);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
            throws BadLocationException {
        if (filter == null) {
            super.replace(fb, offs, length, str, a);
        } else {
            filter.replace(fb, offs, length, str, a);
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
            throws BadLocationException {
        if (filter == null) {
            super.remove(fb, offset, length);
        } else {
            filter.remove(fb, offset, length);
        }
    }
}
