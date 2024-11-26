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
public interface CheckForm <Entity, Type> {
    public boolean isCheckValid();
    public boolean isCheckContain(List<Entity> list, Type ma);
    public boolean isCheckDuplicate();
    public boolean isCheckUpdate();
    public boolean isCheckLength();
    public boolean isCheckDelete();
}
