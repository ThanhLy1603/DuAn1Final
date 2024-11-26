/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ADMIN
 */
public class MaxLengthUnicode extends DocumentFilter {
    
    private final int maxLength;
    private String patternUnicode = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯàáâãèéêìíòóôõùúăđĩũơưĂÂÊÔƠƯáàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệóòỏõọốồổỗộớờởỡợúùủũụứừửữựỳỵýỷỹ\\s]+$";

    public MaxLengthUnicode(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isValidInput(fb, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isValidInput(fb, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
        super.remove(fb, offset, length);
    }

    // Kiểm tra đầu vào
    private boolean isValidInput(DocumentFilter.FilterBypass fb, String text) throws BadLocationException {
        if (text == null) {
            return true;
        }
        
        if (!text.matches(patternUnicode)) {
            return false;
        }
        // Kiểm tra độ dài tối đa
        int newLength = fb.getDocument().getLength() + text.length();
        
        return newLength <= maxLength;
    }
}
