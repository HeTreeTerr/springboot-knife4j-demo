package com.geekplus.rms.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long id;

    @ApiModelProperty(value = "状态（0：oo 1:xx）", name = "status", required = true, example = "1")
    private Integer status;
}
