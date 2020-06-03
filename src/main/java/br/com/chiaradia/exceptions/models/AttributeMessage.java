package br.com.chiaradia.exceptions.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttributeMessage implements Serializable {

    private String attribute;
    private String message;
}