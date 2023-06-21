package com.geekplus.rms.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * FindInfo 入参视图
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
public class FindInfoReqVO {

    @ApiModelProperty(value = "名称", name = "name", required = true, example = "hss")
    private String name;

    @ApiModelProperty(value = "年龄", name = "age", required = true, example = "12")
    private Integer age;

}
