package com.example.stock_microservice.infrastructure.adapter.input.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequest {
    private List<Integer> itemsId;
}
