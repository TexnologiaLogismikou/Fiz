package com.tech.models.dtos.responses;

import com.tech.configurations.tools.Pair;

import java.util.Dictionary;
import java.util.List;
import java.util.Objects;

/**
 * Created by Andreas on 16/12/2015.
 */
public class AvailableChatroomResponseDTO {
    private List<String> list;
    private String error;
    private String size;

    public List<String> getList() {
        return list;
    }

    public String getError() {
        return error;
    }

    public String getSize() {
        return size;
    }
}
