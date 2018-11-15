package com.singletonapps.demo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("Game")
public class GameDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull
    @ApiModelProperty("Name of the game")
    private String name;

    @NotNull
    @ApiModelProperty("Year when was published")
    private Long yearPublished;

    @Nullable
    @ApiModelProperty(hidden = true)
    private LocalDateTime createOn;
}
