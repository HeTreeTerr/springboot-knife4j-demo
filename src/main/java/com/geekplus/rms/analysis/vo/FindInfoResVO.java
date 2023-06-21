package com.geekplus.rms.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * FindInfo 出参视图
 * </p>
 *
 * @author Hss
 * @date 2023-06-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "出参")
public class FindInfoResVO {

    @ApiModelProperty(value = "主键id",required = true)
    private Long id;

    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;
}
