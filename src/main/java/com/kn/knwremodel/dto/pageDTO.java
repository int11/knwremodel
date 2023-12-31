package com.kn.knwremodel.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class pageDTO<T> {
    private List<T> data;
    private Long page;
    private Long pageSize;

    public pageDTO(List<T> data, Long page, Long perPage){
        Long e = Math.min(perPage * page, data.size());

        this.data = data.subList((int) (perPage * (page - 1)), e.intValue());
        this.page = page;
        this.pageSize = (long)Math.ceil((float)data.size()/perPage);
    }
}
