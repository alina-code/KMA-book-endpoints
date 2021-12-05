package com.example.productsmicroservice.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class BookRequest implements Serializable {


    @NotNull
    @Size(min = 2, max = 128)
    private String name;

    @NotNull
    @Size(min = 2, max = 128)
    private String author;

    @NotNull
    @Positive
    private BigDecimal price;


}
