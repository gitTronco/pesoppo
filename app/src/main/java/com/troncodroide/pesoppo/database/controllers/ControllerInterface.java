package com.troncodroide.pesoppo.database.controllers;

import java.util.List;

/**
 * Created by Tronco on 17/03/2015.
 */
public interface ControllerInterface<E> {
    public int create();
    public E get(int id);
    public boolean remove();
    public boolean update();
    public List<E> getAll();
}
