package com.geekplus.rms.analysis.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

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
    @NotBlank(message = "名称不能为空")
    @Length(min = 5,max = 50,message = "名称长度限制5-50")
    private String name;

    @ApiModelProperty(value = "年龄", name = "age", required = true, example = "12")
    @NotNull
    @Positive(message = "年龄必须大于0")
    private Integer age;

}
