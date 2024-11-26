/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

/**
 *
 * @author ADMIN
 */
public interface Initialize<Entity> {
    public void init();
    public void fillToTable();
    public void filterTable();
    public void generateCbx();
    public void setForm(Entity o);
    public void getForm(int index);
    public void showDetail();
}
