package com.qaladies.reactive.unittest.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
@Document(collection = "ladies")
public class Lady {

    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String role;

    @NotBlank
    private String stack;
}
