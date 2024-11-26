/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 *
 * @author ADMIN
 */
public class ValidateInput {
    public String patternNumber = "\\d*";
    public String patternString = "^[a-zA-Z]*$";
    public String patternDecimal = "\\d*(\\.\\d*)?";
    public String patternPhone = "^\\d{1,10}$";
    public String patternSymbol = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯàáâãèéêìíòóôõùúăđĩũơưĂÂÊÔƠƯáàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệóòỏõọốồổỗộớờởỡợúùủũụứừửữựỳỵýỷỹ ,\\.]+$";
    public String patternText = "^[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠƯàáâãèéêìíòóôõùúăđĩũơưĂÂÊÔƠƯáàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệóòỏõọốồổỗộớờởỡợúùủũụứừửữựỳỵýỷỹ]+$"; 

    
    public void inputString(JTextField textField, int length) {
       ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MaxLength(length));
    }
    
    public void inputUnicode(JTextField textField, int length) {
       ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MaxLengthUnicode(length));
    }
    
    public void inputNumber(JTextField textField, int length) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MaxLengthNumber(length));

    }
    
    public void inputDecimal(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches(patternDecimal)) { // Chỉ chấp nhận các ký tự số
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches(patternDecimal)) { // Chỉ chấp nhận các ký tự số
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); // Cho phép xóa bình thường
            }
        });
    }
    
    public void inputPhone(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches(patternPhone)) { // Chỉ chấp nhận các ký tự số
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches(patternPhone)) { // Chỉ chấp nhận các ký tự số
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); // Cho phép xóa bình thường
            }
        });
        
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new MaxLength(10));
    }
    
    public void inputSymbol(JTextField textField) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches(patternSymbol)) { // Chỉ chấp nhận các ký tự số
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches(patternSymbol)) { // Chỉ chấp nhận các ký tự số
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length); // Cho phép xóa bình thường
            }
        });
    }
}
