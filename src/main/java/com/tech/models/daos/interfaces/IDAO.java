package com.tech.models.daos.interfaces;

import java.util.List;

/**
 * Created by Aenaos on 25/11/2015.
 */
public interface IDAO {
    void save(IDAO idao);
    List<IDAO> list();
}
