package com.example.simplenewschannel.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationRequest {
    private int pageSize;
    private int pageNumber;
    public PageRequest pageRequest(){
        return PageRequest.of(pageNumber,pageSize);
    }
}
