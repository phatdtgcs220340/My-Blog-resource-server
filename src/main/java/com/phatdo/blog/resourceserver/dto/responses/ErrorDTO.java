package com.phatdo.blog.resourceserver.dto.responses;

import com.phatdo.blog.resourceserver.classification.TypeDTO;

public record ErrorDTO (String message) implements TypeDTO {
}
