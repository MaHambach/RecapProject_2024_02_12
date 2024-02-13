package org.github.mahambach.product;

import lombok.With;

@With
public record Product(
        String id,
        String name
) {
}
