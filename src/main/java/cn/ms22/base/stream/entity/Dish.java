package cn.ms22.base.stream.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author baopz
 */
@Data
@AllArgsConstructor
public class Dish {
    private String type;
    private int calories;
    private String name;
    private boolean isVegetarian;
}
