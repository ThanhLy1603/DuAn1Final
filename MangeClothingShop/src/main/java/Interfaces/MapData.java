/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interfaces;
import java.util.Map;
/**
 *
 * @author ADMIN 
 */
public interface MapData<Key, Value> {
    public Map<Key, Value> getMapData();
    public Value getValueByID(Key id);
    public Key getIDByValue(Value value);
}
