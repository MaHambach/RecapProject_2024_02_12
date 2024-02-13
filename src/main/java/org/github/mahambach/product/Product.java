package org.github.mahambach.product;

import lombok.Getter;
import lombok.Setter;
import lombok.With;

@With
public record Product(
        String id,
        String name,
        int quantity
) {
}
