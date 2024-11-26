/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;

import java.util.List;

/**
 *
 * @author ADMIN
 */
public interface DAO <Entity, Type>{
    public List<Entity> getAllData();
    public List<Entity> getDataByValue(Type value);
    public Entity getDataById(Type ma);
    public void insertData(Entity o);
    public void updateData(Entity o);
    public void deleteById(Type ma);
}
