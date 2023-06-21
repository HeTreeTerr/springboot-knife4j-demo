package com.geekplus.rms.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * <p>
 * UpdateStatus 入参视图
 * </p>
 *
 * @author Hss
 * @date 2023-06-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "入参")
public class UpdateStatusReqVO {

    @ApiModelProperty(value = "主键id", name = "id", required = true, example = "15")
    @NotNull(message = "id不能为空")
    @Positive(message = "id必须大于0")
    private Long id;

    @ApiModelProperty(value = "状态（1：oo 2:xx）", name = "status", required = true, example = "1")
    @Positive(message = "状态必须大于0")
    private Integer status;
}
